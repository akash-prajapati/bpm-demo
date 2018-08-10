package com.easyjet.ei.commercials.claims.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.exception.InvalidClaimLineStatus;
import com.easyjet.ei.commercials.claims.pojo.claims.ClaimLines;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.exchangerates.GetExchangeRateResponse;
import com.easyjet.ei.commercials.claims.pojo.exchangerates.QuoteDetail;

public class PopulateEresCreditPayRequest implements WorkItemHandler{
	
	private static Logger logger = Logger.getLogger(PopulateAccertifyCallBody.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		HashMap<String, Object> map = new HashMap<>();
		try {
			Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
			float amount = (Float) arg0.getParameter("amount");		
			//GetExchangeRateResponse exchange_obj = (GetExchangeRateResponse) arg0.getParameter("exchange_obj");
			Properties props = (Properties) arg0.getParameter("prop_obj");
			Float exchangeRate = 0f;
			
			//List<QuoteDetail> quoteList = exchange_obj.getQuoteDetails();
			
			JSONObject jObj_request = new JSONObject();			

			jObj_request.put("bookingReference",claim_obj.getBookingReference());
			jObj_request.put("claimReference",claim_obj.getClaimReference());
			jObj_request.put("payeeName",claim_obj.getClaimant().getFirstName()+claim_obj.getClaimant().getLastName());
			jObj_request.put("currency",claim_obj.getCurrencyCode());
			
			
			
			JSONObject jObj_creditpay = new JSONObject();
			
			jObj_creditpay.put("amount",String.format("%.2f", -amount));
			if(claim_obj.getPaymentMethod().equalsIgnoreCase(props.getProperty("Claims_Payment_Method_BTransfer"))) {
				jObj_creditpay.put("paymentMethod",props.getProperty("Claims_Payment_Method_BTransfer"));
			}
			else {
				jObj_creditpay.put("paymentMethod",props.getProperty("Claims_Payment_Method_Cheque"));
			}
			
			
			jObj_request.put("creditPayment",jObj_creditpay);
			
			
			
			JSONArray jArray_creditShellEntries = new JSONArray();
			
			ClaimLines[] clmLines = claim_obj.getClaimLines();
			
			
			
			
			
			if ("Welfare".equals(claim_obj.getClaimType())) {
				for (ClaimLines clmLine : clmLines) {
					JSONObject jobj = new JSONObject();
					if("ACC".equals(clmLine.getStatus())) {
						
						/*if(!clmLine.getSubmittedCurrency().equals(claim_obj.getCurrencyCode())) {			 Commented as Payable amount updated by the Agent is with currency conversion. This code needs to be uncommented for Phase 2			
							
							for(QuoteDetail quotes : quoteList){

								if(quotes.getTargetCurrency().equals(clmLine.getSubmittedCurrency())){

					
									exchangeRate = Float.parseFloat(quotes.getValue());
									logger.debug("Currency Matched " + quotes.getTargetCurrency()+" : "+exchangeRate);
									break;
								}

							}

							amount = clmLine.getPayableAmount().floatValue() * exchangeRate;
							logger.debug("amount  " + amount);
						}*/
						//else {
							amount = clmLine.getPayableAmount().floatValue();
							
						//}												
						jobj.put("amount", String.format("%.2f", amount));						
						jobj.put("creditFileCode", clmLine.getLineType());
						jArray_creditShellEntries.add(jobj);
						
					}
					else if("SUB".equals(clmLine.getStatus())) {
						map.put("error_msg", "One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "+clmLine.getLineType()+ " , Status: "+clmLine.getStatus() );
						arg1.abortWorkItem(arg0.getId());
						logger.error("One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "+clmLine.getLineType()+ " , Status: "+clmLine.getStatus());
						throw new InvalidClaimLineStatus("One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "+clmLine.getLineType()+ " , Status: "+clmLine.getStatus() );						
					}
				}
			} else {
				
				
				JSONObject jobj = new JSONObject();
				jobj.put("amount", String.format("%.2f", amount));
				jobj.put("creditFileCode", "EUC");
				jArray_creditShellEntries.add(jobj);
				
			}

			
			jObj_request.put("creditFileEntries", jArray_creditShellEntries);
			
			logger.debug(jObj_request.toJSONString());
			
			map.put("request_string", jObj_request.toJSONString());
			
			arg1.completeWorkItem(arg0.getId(),map);
			
		} catch (InvalidClaimLineStatus e) {
			logger.error(e);
			map.put("error_msg", "Error while preparing json request for Eres Payment service. Error is " + e.toString());

		}
		
	}
	

}
