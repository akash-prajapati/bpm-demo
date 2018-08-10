/*
 * 24/04/2018 - EA-799 - Changes foe passing correct IP address in the request. IP address is passed as a part of input message to BPM
 * */
package com.easyjet.ei.commercials.claims.handlers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.AESEncryption;
import com.easyjet.ei.commercials.claims.pojo.claims.ClaimLines;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo.BookingInfo;
import com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo.TravellerInfo;
import com.easyjet.ei.commercials.claims.pojo.inputmsg.InitiateClaimProcessRequestBankAccountDetails;

public class PopulateAccertifyCallBody implements WorkItemHandler {
	
	private static final Logger logger = Logger.getLogger(PopulateAccertifyCallBody.class);
	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
	

	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
	
		
			try {
				Claims claimObj = (Claims)arg0.getParameter("claim_obj");
				BookingInfo bookingObj = (BookingInfo)arg0.getParameter("eresbooking_obj");
	
				InitiateClaimProcessRequestBankAccountDetails bank_obj = (InitiateClaimProcessRequestBankAccountDetails) arg0.getParameter("bank_obj");
				Properties prop_obj = (Properties) arg0.getParameter("prop_obj");
				JSONObject fraudreqJson = new JSONObject();
				String secKey = prop_obj.getProperty("secKey");
				String ipAddress =  (String)arg0.getParameter("IpAddress");//EA-799
				

				JSONObject transactionInformation = new JSONObject();			
				transactionInformation.put("ipAddress",ipAddress); //EA-799
				transactionInformation.put("claimReference", claimObj.getClaimReference());
				fraudreqJson.put("transactionInformation", transactionInformation);

				JSONObject flightInformation = new JSONObject();
				flightInformation.put("bookingRef", claimObj.getBookingReference());
				flightInformation.put("flightNo", claimObj.getFlightNumber());
				flightInformation.put("departureAiport", claimObj.getDepartureAirportCode());
				flightInformation.put("arrivalAirport", claimObj.getArrivalAirportCode());
				flightInformation.put("flightDate", claimObj.getFlightDate());
				fraudreqJson.put("flightInformation", flightInformation);

				JSONObject claimantInformation = new JSONObject();
				claimantInformation.put("travelAgentCompanyName", claimObj.getClaimant().getTravelAgent());
				claimantInformation.put("email", claimObj.getClaimant().getEmailAddress());
				claimantInformation.put("phoneNumber", claimObj.getClaimant().getTelephone());
				claimantInformation.put("firstName", claimObj.getClaimant().getFirstName());
				claimantInformation.put("lastName", claimObj.getClaimant().getLastName());
				claimantInformation.put("addressLine1", claimObj.getClaimant().getAddress().getAddressLine1());
				claimantInformation.put("addressLine2", claimObj.getClaimant().getAddress().getAddressLine2());
				claimantInformation.put("addressTown", claimObj.getClaimant().getAddress().getTown());
				claimantInformation.put("addressCountyState", claimObj.getClaimant().getAddress().getCounty());
				claimantInformation.put("addressPostZipCode", claimObj.getClaimant().getAddress().getPostCode());
				claimantInformation.put("addressCountry", claimObj.getClaimant().getAddress().getCountry());
				if(claimObj.getClaimant().getIsLeadBooker()){
					claimantInformation.put("originalBooker", "Y");
				}else{
					claimantInformation.put("originalBooker", "N");
				}
				claimantInformation.put("language", "EN"); //need correction
				fraudreqJson.put("claimantInformation", claimantInformation);

				JSONObject bankInformation = new JSONObject();
				
				
				SecretKeySpec secKeySpec = AESEncryption.getAesKey(Base64.getDecoder().decode(secKey.getBytes()));
				
				if(bank_obj.getBankAccountCountry() != null){
					bankInformation.put("bankAcctCountry", AESEncryption.decryptText(bank_obj.getBankAccountCountry() , secKeySpec));
				}
				else{
					bankInformation.put("bankAcctCountry", null);
				}
				
				if(bank_obj.getBankAccountHolderName() != null ){
					bankInformation.put("bankAcctName", AESEncryption.decryptText(bank_obj.getBankAccountHolderName(), secKeySpec));
				}
				else{
					bankInformation.put("bankAcctName", null);
				}
				
				if(bank_obj.getSortCode() != null){
					bankInformation.put("ukBankSortCode",  AESEncryption.decryptText(bank_obj.getSortCode(), secKeySpec));
				}
				else{
					bankInformation.put("ukBankSortCode", null);
				}
				bankInformation.put("bankAcctValidated", "Y");
				
				if(bank_obj.getBankAccountNumber() != null ){
					bankInformation.put("ukBankAccountNo", AESEncryption.decryptText(bank_obj.getBankAccountNumber(), secKeySpec));
				}
				else{
					bankInformation.put("ukBankAccountNo", null);
				}
				
				if(bank_obj.getBIC() != null){
					bankInformation.put("intBankBicSwift", AESEncryption.decryptText(bank_obj.getBIC(), secKeySpec));
				}
				else{
					bankInformation.put("intBankBicSwift", null);
				}
				
				if(bank_obj.getIBAN() != null){
					bankInformation.put("intBankIban",  AESEncryption.decryptText(bank_obj.getIBAN(), secKeySpec));
				}
				else{
					bankInformation.put("intBankIban", null);
				}
				
				
				fraudreqJson.put("bankInformation", bankInformation);

				JSONArray passengerLst = new JSONArray();
				List<TravellerInfo> trvlrInfo = bookingObj.getTravellerInfo();
				for(TravellerInfo trvlr : trvlrInfo){
					JSONObject pasngr = new JSONObject();
					pasngr.put("firstName", trvlr.getFirstName());
					pasngr.put("lastName",trvlr.getLastName());
					passengerLst.add(pasngr);
				}	
				fraudreqJson.put("passengerList", passengerLst);

				JSONObject claimDetails = new JSONObject();
				claimDetails.put("type", claimObj.getClaimType());
				claimDetails.put("reason", claimObj.getReasonForClaim());
				claimDetails.put("currency", claimObj.getCurrencyCode());
				fraudreqJson.put("claimDetails", claimDetails);

				JSONArray wlfrList = new JSONArray();
				if("Welfare".equals(claimObj.getClaimType())){
					ClaimLines[] claimLines = claimObj.getClaimLines();
					JSONObject claimLine = new JSONObject();
					for(ClaimLines clmln:claimLines){
						claimLine.put("amount", clmln.getSubmittedAmount());
						claimLine.put("currency", clmln.getSubmittedCurrency());
						claimLine.put("type", clmln.getSubmittedType());
						wlfrList.add(claimLine);
					}
					fraudreqJson.put("welfareDetails", wlfrList);
				}else{
					fraudreqJson.put("welfareDetails", wlfrList);
				}

			

				HashMap<String, Object> map = new HashMap<>();
				map.put("fraudreqJson",fraudreqJson.toJSONString());
				arg1.completeWorkItem(arg0.getId(), map);
			} catch (InvalidKeyException e) {
				logger.error(e);
			
			}  catch (NoSuchAlgorithmException e) {
				logger.error(e);
			
			} catch (NoSuchPaddingException e) {
				logger.error(e);
			
			} catch (IllegalBlockSizeException e) {
				logger.error(e);
			
			} catch (BadPaddingException e) {
				logger.error(e);
			
			}
		

	}
	
	
}
