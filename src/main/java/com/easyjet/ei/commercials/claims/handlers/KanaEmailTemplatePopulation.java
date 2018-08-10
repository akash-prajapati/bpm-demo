package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kie.api.runtime.process.WorkItem;

import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;
import com.easyjet.ei.commercials.claims.pojo.caseservice.NameValuePair;
import com.easyjet.ei.commercials.claims.pojo.caseservice.NameValuePairList;
import com.easyjet.ei.commercials.claims.pojo.claims.ClaimLines;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.flightinfo.CustomerFlightStatus;
import com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo.BookingInfo;

public class KanaEmailTemplatePopulation {

	private static final Logger logger = Logger.getLogger(KanaEmailTemplatePopulation.class);

	private static NameValuePairList nvpairList = null;


	public static NameValuePairList populateNvpairList(String templateName,WorkItem arg0) throws EncryptedDocumentException, InvalidFormatException, IOException {		

		Claims claims_obj = (Claims) arg0.getParameter("claim_obj");
		BookingInfo bookInfo = (BookingInfo) arg0.getParameter("booking_obj");
		CustomerFlightStatus csFltStatus = new CustomerFlightStatus();
		float claimAmount = 0;
		Properties props = ReadFromPropertyFile.readfromPropertyFile();
		Map<String,Object> params = arg0.getParameters(); 

		if(templateName.equals(props.get("Comp_Awaiting_Booker_Consent"))){
			nvpairList = bookingNotLocated(claims_obj);
		}

		if(templateName.equals(props.get("Comp_Request_Booker_Consent"))){
			nvpairList = requestBookerConsent(claims_obj, bookInfo);
		}

		if(templateName.equals(props.getProperty("booking_not_located_template"))){
			nvpairList = bookingNotLocated(claims_obj);
		}

			if (templateName.equals(props.getProperty("Welfare_Reject_Full_Rejection"))){
				nvpairList = exClaimFullRejection1(claims_obj);
			}


			if(templateName.equals(props.getProperty("Comp_Reject_Delay_Under_3Hours"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 
				
				nvpairList = disruptionImpactIsDiversion (claims_obj,csFltStatus, templateName);
			}

			if(templateName.equals(props.getProperty(" new_occ_flight_awaiting_classification_unconfirmed_status"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = newoccFltAwClasUnconSt(claims_obj,csFltStatus);

			}

			if(templateName.equals(props.getProperty(" new_occ_flight_awaiting_classification_unexpected_disruption_type"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = newoccFltAwClasUnconSt(claims_obj,csFltStatus);

			}

			if(templateName.equals(props.getProperty("Welfare_Reject_Delay_Under_2Hours"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = disruptionImpactIsDiversion (claims_obj,csFltStatus, templateName);
			}

			if(templateName.equals(props.getProperty(" new_occ_flight_awaiting_classification_missing_flight"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = newoccFltAwClasUnconSt(claims_obj,csFltStatus);

			}

			if(templateName.equals(props.getProperty("Comp_Reject_Staff_Booking"))){
				nvpairList = rejStuffBooking (claims_obj);
			}

			if(templateName.equals(props.getProperty("Comp_Reject_Outside_Legal_limit"))){
				nvpairList = rejStuffBooking (claims_obj);
			}

			if(templateName.equals(props.getProperty("Comp_Reject_Cancelled_In_Advance"))){
				nvpairList = rejCanInAdv (claims_obj);
			}

			if(templateName.equals(props.getProperty("Comp_Reject_ExtraOrdinary"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = disruptionImpactIsDiversion (claims_obj,csFltStatus, templateName);
			}
			
			if(templateName.equals(props.getProperty("Comp_Reject_ExtraOrdinary_Generic"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 

				nvpairList = disruptionImpactIsDiversionForGenericTemplate (claims_obj,csFltStatus, templateName);
			}


			if(templateName.equals(props.getProperty("Cust_Entitled_Non_Extraordinary"))){

				if(params.get("customerflight_obj") != null){
					csFltStatus = (CustomerFlightStatus) arg0.getParameter("customerflight_obj");
				} 
				
				if(params.get("claimAmntEuroConv") != null){
					claimAmount = (float) arg0.getParameter("claimAmntEuroConv");
				}

				nvpairList = custEntNonExt (claims_obj,csFltStatus,claimAmount);
			}

		

		return nvpairList;

	}

	public static NameValuePairList custEntNonExt (Claims claims_obj,CustomerFlightStatus csFltStatus,float claimAmount) throws EncryptedDocumentException,
	InvalidFormatException, IOException{

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();
		
		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(claims_obj.getClaimant().getFirstName());
		nvpair1.setName("Claimant first name");
		nvList.add(nvpair1);
		
		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(claims_obj.getFlightNumber());
		nvpair2.setName("Flight number");
		nvList.add(nvpair2);
		
		NameValuePair nvpair3 = new NameValuePair();
		nvpair3.setValue(convertDate(claims_obj.getFlightDate()));
		nvpair3.setName("Flight date");
		nvList.add(nvpair3);
		
		//if(csFltStatus.getFlights().get(0).getPostFlight().size()>0){
		if(!csFltStatus.getFlights().get(0).getPostFlight().isEmpty()){
			
			NameValuePair nvpair4 = new NameValuePair();
			Map<String, Object> disruptionReasonMap = GetFlightClassification.getClassification(csFltStatus.getFlights().get(0).
					getPostFlight().get(0).getDisruptionDetails().getPrimaryCauseCode(), claims_obj.getLanguage());
			String disruptionReason = (String)disruptionReasonMap.get("disruptionReason");
			//nvpair4.setValue(csFltStatus.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getPrimaryCauseCode());
			nvpair4.setName("Disruption primary reason");
			nvpair4.setValue(disruptionReason);
			nvList.add(nvpair4);
	
			if (csFltStatus.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getDelay() != null) {
				NameValuePair nvpair5 = new NameValuePair();
				nvpair5.setValue(csFltStatus.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getDelay());
				nvpair5.setName("Disruption delay length");
				nvList.add(nvpair5);
			}
		}
		
		NameValuePair nvpair6 = new NameValuePair();
		nvpair6.setValue(claims_obj.getCurrencyCode().toUpperCase()+" "+String.format("%.2f", claimAmount));
		nvpair6.setName("Claim amount");
		nvList.add(nvpair6);
		
		nvpairList.setNameValuePair(nvList);

		return nvpairList;
		
	}

	public static NameValuePairList rejCanInAdv (Claims claims_obj) {

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(claims_obj.getClaimant().getFirstName());
		nvpair1.setName("Claimant first name");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(convertDate(claims_obj.getCreateDate()));
		nvpair2.setName("Claim date");
		nvList.add(nvpair2);

		/*NameValuePair nvpair3 = new NameValuePair();
		nvpair3.setValue("TBD");
		nvpair3.setName("Cancellation communication date");
		nvList.add(nvpair3);*/

		nvpairList.setNameValuePair(nvList);

		return nvpairList;
	}


	public static NameValuePairList rejStuffBooking (Claims claims_obj) {

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(claims_obj.getClaimant().getFirstName());
		nvpair1.setName("Claimant first name");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(convertDate(claims_obj.getCreateDate()));
		nvpair2.setName("Claim date");
		nvList.add(nvpair2);

		nvpairList.setNameValuePair(nvList);

		return nvpairList;
	}

	public static NameValuePairList newoccFltAwClasUnconSt (Claims claims_obj,CustomerFlightStatus csFltStatus) {

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(claims_obj.getFlightNumber());
		nvpair1.setName("Flight number");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(convertDate(claims_obj.getFlightDate()));
		nvpair2.setName("Flight date");
		nvList.add(nvpair2);

		NameValuePair nvpair3 = new NameValuePair();
		
		//if(csFltStatus.getFlights().get(0).getPostFlight().size()>0){
		if(!csFltStatus.getFlights().get(0).getPostFlight().isEmpty()){
			if(csFltStatus.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getPrimaryCauseCode().startsWith("NE")){
				nvpair3.setValue("Non-Extraordinary");
			}else{
				nvpair3.setValue("Extraordinary");
			}
		}
		nvpair3.setName("Disruption Classifcation");
		nvList.add(nvpair3);

		nvpairList.setNameValuePair(nvList);

		return nvpairList;
	}

	public static NameValuePairList disruptionImpactIsDiversion (Claims claims_obj,CustomerFlightStatus csFltStatus, String templateName) throws EncryptedDocumentException, 
	InvalidFormatException, IOException{

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(dateFormat.format(date));
		nvpair1.setName("Todays date");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(claims_obj.getClaimant().getFirstName());
		nvpair2.setName("Claimant first name");
		nvList.add(nvpair2);

		NameValuePair nvpair3 = new NameValuePair();
		nvpair3.setValue(claims_obj.getClaimant().getLastName());
		nvpair3.setName("Claimant Surname");
		nvList.add(nvpair3);

		NameValuePair nvpair4 = new NameValuePair();
		nvpair4.setValue(claims_obj.getClaimant().getAddress().getAddressLine1()+","+claims_obj.getClaimant().getAddress().getAddressLine2()+","+
				claims_obj.getClaimant().getAddress().getCountry()+","+claims_obj.getClaimant().getAddress().getCounty()+","+claims_obj.getClaimant().getAddress().getPostCode()+","+
				claims_obj.getClaimant().getAddress().getTown());
		nvpair4.setName("Claimant Address");
		nvList.add(nvpair4);

		NameValuePair nvpair5 = new NameValuePair();
		nvpair5.setValue(convertDate(claims_obj.getCreateDate()));
		nvpair5.setName("Claim date");
		nvList.add(nvpair5);
		
		//if(!templateName.contains("EXTRAREJ")){		

			NameValuePair nvpair6 = new NameValuePair();
			nvpair6.setValue(claims_obj.getDepartureAirportCode());
			nvpair6.setName("Departure airport");
			nvList.add(nvpair6);
	
			NameValuePair nvpair7 = new NameValuePair();
			nvpair7.setValue(claims_obj.getArrivalAirportCode());
			nvpair7.setName("Arrival airport");
			nvList.add(nvpair7);
		
			
			//if(csFltStatus.getFlights().get(0).getPostFlight().size()>0){
			if(!csFltStatus.getFlights().get(0).getPostFlight().isEmpty()){
		
				NameValuePair nvpair8 = new NameValuePair();
			Map<String, Object> disruptionReasonMap = GetFlightClassification.getClassification(csFltStatus.getFlights().get(0).
					getPostFlight().get(0).getDisruptionDetails().getPrimaryCauseCode(), claims_obj.getLanguage());
			String disruptionReason = (String)disruptionReasonMap.get("disruptionReason");
			
			nvpair8.setValue(disruptionReason);
			nvpair8.setName("Disruption primary reason");
			nvList.add(nvpair8);

			NameValuePair nvpair9 = new NameValuePair();
			nvpair9.setValue(csFltStatus.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getDelay());
			nvpair9.setName("Disruption delay length");
			nvList.add(nvpair9);
		}
		//}
		nvpairList.setNameValuePair(nvList);
		return nvpairList;
	}

	public static NameValuePairList bookingNotLocated (Claims claims_obj) {

		

			nvpairList = new NameValuePairList();
			List<NameValuePair> nvList = new ArrayList<NameValuePair>();

			NameValuePair nvpair1 = new NameValuePair();
			nvpair1.setValue(claims_obj.getClaimant().getFirstName());
			nvpair1.setName("Claimant first name");
			nvList.add(nvpair1);

			NameValuePair nvpair2 = new NameValuePair();
			nvpair2.setValue(claims_obj.getKanaCaseReference());
			nvpair2.setName("Kana Case ID");
			nvList.add(nvpair2);

			nvpairList.setNameValuePair(nvList);

			logger.debug("place holder list : "+nvpairList.toString());		
		
		return nvpairList;
	}
	
	public static NameValuePairList requestBookerConsent (Claims claims_obj, BookingInfo bookInfo) {

		

		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(claims_obj.getClaimant().getFirstName());
		nvpair1.setName("Claimant first name");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(claims_obj.getKanaCaseReference());
		nvpair2.setName("Kana Case ID");
		nvList.add(nvpair2);
		
		NameValuePair nvpair3 = new NameValuePair();
		nvpair3.setValue(bookInfo.getBookerInfo().getFirstName());
		nvpair3.setName("Lead booker name");
		nvList.add(nvpair3);

		NameValuePair nvpair4 = new NameValuePair();
		nvpair4.setValue(bookInfo.getBookingRef());
		nvpair4.setName("Booking reference");
		nvList.add(nvpair4);


		nvpairList.setNameValuePair(nvList);

		logger.debug("place holder list : "+nvpairList.toString());		
	
	return nvpairList;
}

	

	public static NameValuePairList exClaimFullRejection1 (Claims claims_obj) {

		
			nvpairList = new NameValuePairList();
			List<NameValuePair> nvList = new ArrayList<NameValuePair>();

			NameValuePair nvpair1 = new NameValuePair();
			nvpair1.setValue(claims_obj.getClaimant().getFirstName());
			nvpair1.setName("Claimant first name");
			nvList.add(nvpair1);

			NameValuePair nvpair2 = new NameValuePair();
			nvpair2.setValue(convertDate(claims_obj.getCreateDate()));
			nvpair2.setName("Claim date");
			nvList.add(nvpair2);

			ClaimLines[] claimLines = claims_obj.getClaimLines();

			int finalSubmtdAmnt = 0;

			String lstSubmtdTyp = null;


			for(ClaimLines clmln:claimLines){

				NameValuePair nvpair = new NameValuePair();
				nvpair.setName(clmln.getSubmittedType());
				
				if(clmln.getSubmittedType().equalsIgnoreCase(lstSubmtdTyp)){

					finalSubmtdAmnt = finalSubmtdAmnt + clmln.getSubmittedAmount().intValue();
				}else{
					finalSubmtdAmnt = clmln.getSubmittedAmount().intValue();
				}

				nvpair.setValue(Integer.toString(finalSubmtdAmnt));
				nvList.add(nvpair); 
				lstSubmtdTyp = clmln.getSubmittedType();
				finalSubmtdAmnt = clmln.getSubmittedAmount().intValue();
			}

			NameValuePair nvpair3 = new NameValuePair();
			nvpair3.setValue(Integer.toString(0));
			nvpair3.setName("Payable amount");
			nvList.add(nvpair3);

			nvpairList.setNameValuePair(nvList);


		
		return nvpairList;
	}
	
	public static NameValuePairList disruptionImpactIsDiversionForGenericTemplate
	(Claims claims_obj,CustomerFlightStatus csFltStatus, String templateName) throws EncryptedDocumentException, 
	InvalidFormatException, IOException{

        System.out.println("-------Entering disruptionImpactIsDiversionForGenericTemplate--------- ");
		nvpairList = new NameValuePairList();
		List<NameValuePair> nvList = new ArrayList<NameValuePair>();

		NameValuePair nvpair1 = new NameValuePair();
		nvpair1.setValue(convertDate(claims_obj.getCreateDate()));
		nvpair1.setName("Claim.date");
		nvList.add(nvpair1);

		NameValuePair nvpair2 = new NameValuePair();
		nvpair2.setValue(claims_obj.getFlightNumber());
		nvpair2.setName("Flight.number");
		nvList.add(nvpair2);


		NameValuePair nvpair3 = new NameValuePair();
		nvpair3.setValue(convertDate(claims_obj.getFlightDate()));
		nvpair3.setName("Flight.date");
		nvList.add(nvpair3);

		NameValuePair nvpair4 = new NameValuePair();
		nvpair4.setValue(claims_obj.getDepartureAirportCode());
		nvpair4.setName("Departure.airport");
		nvList.add(nvpair4);

		NameValuePair nvpair5 = new NameValuePair();
		nvpair5.setValue(claims_obj.getArrivalAirportCode());
		nvpair5.setName("Arrival.airport");
		nvList.add(nvpair5);
		
		NameValuePair nvpair6 = new NameValuePair();
		nvpair6.setValue("Declined");
		nvpair6.setName("Claim.decision");
		nvList.add(nvpair6);
		
		NameValuePair nvpair7 = new NameValuePair();
		nvpair7.setValue(claims_obj.getClaimant().getFirstName());
		nvpair7.setName("Claimant.first.name");
		nvList.add(nvpair7);

		nvpairList.setNameValuePair(nvList);
		 System.out.println("-------Entering disruptionImpactIsDiversionForGenericTemplate---------Ends ");
		return nvpairList; 
	}
	
	public static String convertDate(String date){
		String convertedDate="";
		try{
			

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-YYYY");
			convertedDate = format1.format(format.parse(date));
			
			logger.debug(convertedDate);
			
		
			
		}catch(ParseException e){
			logger.error(e);

		}
		
		return convertedDate;
	}

}
