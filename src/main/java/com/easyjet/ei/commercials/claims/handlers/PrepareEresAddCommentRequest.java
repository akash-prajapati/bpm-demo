package com.easyjet.ei.commercials.claims.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.flightinfo.CustomerFlightStatus;

public class PrepareEresAddCommentRequest implements WorkItemHandler {
	private static final Logger logger = Logger.getLogger(PrepareEresAddCommentRequest.class);

	
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		Map<String, Object> parameters = arg0.getParameters();
		
		Map<String, Object> map_values = new HashMap<String, Object>();
		String error_msg = "";
		Float amount = 0f;
		String auto_comment = "";
		String forNote = "";
		CustomerFlightStatus flight_obj = new CustomerFlightStatus();
		
		Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
		

		if(parameters.get("amount") != null) {
			amount = (Float) arg0.getParameter("amount");
		}
		
		if(parameters.get("auto_comment") != null) {
			auto_comment = (String) arg0.getParameter("auto_comment");
		}
		
		if(parameters.get("forNote") != null) {
			forNote = (String) arg0.getParameter("forNote");
		}
		if(parameters.get("flight_obj") != null) {
			flight_obj = (CustomerFlightStatus) arg0.getParameter("flight_obj");
		}
		
		
		try {
			map_values = populateComment(claim_obj, amount, auto_comment, forNote,flight_obj);
			
			
			
			if(forNote == null || ("").equals(forNote)) {
				String jsonString = prepareJsonString(map_values.get("comment").toString(), map_values.get("comment_type").toString(), map_values.get("ej_comment_type").toString());
				map_values.put("jsonStringReq", jsonString);
			}
			
			
			
		} catch (ParseException e) {
			logger.error(e);
			error_msg = "Error while preparing json request for eRes Add Comments service, Erorr is : " + e.toString();
			logger.error(error_msg);
			
		}
		
		finally {
			map_values.put("error_msg", error_msg);
			
		}
		
		arg1.completeWorkItem(arg0.getId(), map_values);
		
	}
	
	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
		
	}
	
	private Map<String, Object> populateComment(Claims claim_obj, Float amount , String auto_comment, String forNote, CustomerFlightStatus flight_obj) throws ParseException {
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-YYYY");
			String flight_date = format1.format(format.parse(claim_obj.getFlightDate()));
			String ej_comment_type = "";
			String comment_type = "";
			
			StringBuilder stringBuffer = new StringBuilder();
			
			String start;
			
			if("EUC".equals(claim_obj.getClaimType())) {
				start = "********************* EU 261 CLAIMS - COMP ********************** \n ";
			}
			else {
				start = "********************* EU 261 CLAIMS - WELF ********************** \n ";
			}
			
			String end = "*********************" + claim_obj.getClaimReference() + "********************** \n";
			
			switch (claim_obj.getStatus()) {
			case "AUTO_ACCEPTED":
				
				if(forNote == null || ("").equals(forNote)){
					stringBuffer.append(start);
				}
				stringBuffer.append("MR " + claim_obj.getClaimant().getFirstName()+" "+claim_obj.getClaimant().getLastName());
				stringBuffer.append(" // ");
				if("EUC".equals(claim_obj.getClaimType())) {
					stringBuffer.append("EUC Claim for PAX ");
				}
				else {
					stringBuffer.append("Expenses Claim for PAX ");
				}
				stringBuffer.append(claim_obj.getPaxCount());
				stringBuffer.append(" // ");
				stringBuffer.append("FLT " + claim_obj.getFlightNumber());
				stringBuffer.append(" > ");
				stringBuffer.append(flight_date);
				stringBuffer.append(" > \n ");
				stringBuffer.append(claim_obj.getDepartureAirportCode()+claim_obj.getArrivalAirportCode());
				stringBuffer.append(" // ");
				stringBuffer.append("AUTO CHECK");
				stringBuffer.append(" // ");
				stringBuffer.append("NON-EXTRAORDINARY");
				stringBuffer.append(" // ");
				//stringBuffer.append("N - TECHNICAL ISSUES");
				if(flight_obj.getFlights().get(0).getPostFlight().size() < 2){
					stringBuffer.append(flight_obj.getFlights().get(0).getPostFlight().get(0).getDisruptionDetails().getPrimaryCauseCode());
				}
				else{
					for(int i=0;i<flight_obj.getFlights().get(0).getPostFlight().size();i++){
						if(flight_obj.getFlights().get(0).getPostFlight().get(i).getDisruptionDetails().getDisruptionRecordType().equals("DELAY")){
							stringBuffer.append(flight_obj.getFlights().get(0).getPostFlight().get(i).getDisruptionDetails().getPrimaryCauseCode());
						}
					}
				}
				stringBuffer.append(" >> \n ");
				stringBuffer.append("Case # " + claim_obj.getKanaCaseReference());
				stringBuffer.append(" // \n ");
				if(forNote == null || ("").equals(forNote)){
					stringBuffer.append(end);
				}
				
				comment_type = "B";
				ej_comment_type = "G";
				break;
				
			case "AUTO_AWAITING_BUSINESS" :
				
				if(auto_comment != null && !("").equals(auto_comment)) {
					stringBuffer.append(auto_comment);
				}
				else{
					if(forNote == null || ("").equals(forNote)){
						stringBuffer.append(start);
					}
					stringBuffer.append("ESCALATED TO OCC FOR DISRUPTION INFO");
					stringBuffer.append(" // ");
					stringBuffer.append("CASE # " + claim_obj.getKanaCaseReference());
					stringBuffer.append(" // \n ");
					if(forNote == null || ("").equals(forNote)){
						stringBuffer.append(end);
					}
				}
				
				comment_type = "B";
				ej_comment_type = "G";
				
			break;
			
			case "AUTO":
				
				if(auto_comment != null && !("").equals(auto_comment)) {
					stringBuffer.append(auto_comment);
				}
				else{
					stringBuffer.append(start);
					stringBuffer.append("DISRUPTION INFO GIVEN AUTO PROCESS CONTINUES \n");
					stringBuffer.append(end);
				}
				
				comment_type = "B";
				ej_comment_type = "G";
				
			break;
			
			case "SUBMITTED":
				if(auto_comment != null && !("").equals(auto_comment)) {
					stringBuffer.append(auto_comment);
				}
				else {
					stringBuffer.append(start);
					stringBuffer.append("STILL AWAITING DISRUPTION INFO - MANUALLY PROCESS \n");
					stringBuffer.append(end);
				}
				
				comment_type = "B";
				ej_comment_type = "G";
				
			break;
			
			case "ACCEPTED_PAYMENT_REQUESTED":				
				stringBuffer.append("Payment of "+ claim_obj.getCurrencyCode() + amount + " raised for " + claim_obj.getClaimReference());
				
				comment_type = "P";
				ej_comment_type = "R";
				
			break;
			
			case "AUTO_REJECTED":				
				stringBuffer.append(start);
				stringBuffer.append("CLAIM AUTO CLOSED DUE TO BEING IN SUBMITTED FOR OVER A YEAR");
				stringBuffer.append(end);	
				
				comment_type = "B";
				ej_comment_type = "G";
				
			break;
			
			case "PAYMENT_AUTHORISED":				
				stringBuffer.append("Payment of "+ claim_obj.getCurrencyCode() +" "+ amount + " authorised for " + claim_obj.getClaimReference());
				
				comment_type = "P";
				ej_comment_type = "G";
				
			break;
			
			case "PAYMENT_FAILED":				
				stringBuffer.append("Payment of "+ claim_obj.getCurrencyCode() +" "+ amount + " failed for " + claim_obj.getClaimReference());
				
				comment_type = "P";
				ej_comment_type = "G";
				
			break;
			
			case "PAYMENT_SUCCESS":				
				stringBuffer.append("Payment of "+ claim_obj.getCurrencyCode() +" "+ amount + " sent for " + claim_obj.getClaimReference());
				
				comment_type = "P";
				ej_comment_type = "G";
				
			break;
			
			case "PAYMENT_ONHOLD":				
				stringBuffer.append("Payment of "+ claim_obj.getCurrencyCode() +" "+ amount + " on hold for " + claim_obj.getClaimReference());
				
				comment_type = "P";
				ej_comment_type = "G";
				
			break;
			
			case "AUTO_REJECTED_2":				
				stringBuffer.append(auto_comment);
				
				comment_type = "B";
				ej_comment_type = "G";
				
			break;

			default:
				break;
			}

			
			
			
			String comment = stringBuffer.toString();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comment", comment);
			map.put("comment_type", comment_type);
			map.put("ej_comment_type", ej_comment_type);
		
		return map;
		
		
	} 
	
	@SuppressWarnings("unchecked")
	private String prepareJsonString(String comment, String comment_type, String ej_comment_type) {
		
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("commentText", comment);
		jsonObj.put("commentTypeCode", comment_type);
		jsonObj.put("ejCommentTypeCode", ej_comment_type);
		
		String jsonString = jsonObj.toJSONString();
		
		
		return jsonString;
	}

	
	
	}
