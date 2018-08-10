package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;



public class ParseEventMessage implements WorkItemHandler{
	private static Logger logger = Logger.getLogger(ParseEventMessage.class);
	
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			String event_message = (String) arg0.getParameter("event_message");
			String action = (String) arg0.getParameter("action");
			
			
			
			if("ClaimStateChange".equals(action) || "Accertify".equals(action)){
				map = parseClaimSateChangeMessage(event_message,action);
				
			}
			else{
				map = parsePaymentSateChangeMessage(event_message,action);
				
			}
			
			arg1.completeWorkItem(arg0.getId(), map);
		} catch (ParseException e) {
			logger.error(e);
			map.put("error_msg", "Error while parsing event message. Error is " + e.toString());
		}
		catch (IOException e) {
			logger.error(e);
			map.put("error_msg", "Error while parsing event message. Error is " + e.toString());
		}
		
	}

	public Map<String, Object> parseClaimSateChangeMessage(String event_message, String action) throws ParseException, IOException{
		Map<String, Object> map = new HashMap<>();
		
						
			logger.debug("event_message " + event_message);	
			JSONParser jParser = new JSONParser();			
			JSONObject jObj = (JSONObject) jParser.parse(event_message);

			JSONObject jBody = (JSONObject) jObj.get("ClaimBody");
			String manual_status = null;
			String manual_status_old = null;
			
			String claim_reference = null;
			
			if("ClaimStateChange".equals(action)){
			
				manual_status = (String)jBody.get("NewState");
				manual_status_old = (String)jBody.get("OldState");
				claim_reference = jBody.get("ClaimReference").toString();
			}
			else if("Accertify".equals(action)){
				
				manual_status = (String)jObj.get("FraudResult");
				claim_reference = jObj.get("ClaimReference").toString();
			}
			
			map.put("claim_reference", claim_reference);
			map.put("manual_status", manual_status);
			map.put("manual_status_old", manual_status_old);
			
			logger.debug("map " + map);
		
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> parsePaymentSateChangeMessage(String event_message, String action) throws ParseException, IOException{
		Map<String, Object> map = new HashMap<>();
		
						
		Properties prop_obj = ReadFromPropertyFile.readfromPropertyFile(); 
				
			JSONParser jParser = new JSONParser();			
			JSONObject jObj = (JSONObject) jParser.parse(event_message);

			JSONObject jBody = (JSONObject) jObj.get("body");
			
			String paymntid = jBody.get("paymentId").toString();

			String paymntsts = (String)jBody.get("transactionStatus");
			
			String rest_url = prop_obj.getProperty("Update_Payment_Staus_url")+"?paymentId="+paymntid;
			
			JSONObject jobj1 = new JSONObject();
			
			String manual_status;
			
			if(paymntsts == "authorized" || ("authorized").equals(paymntsts)){
				jobj1.put("PaymentSuccessful","Y");
				manual_status = prop_obj.getProperty("Claims_Status_Payment_Success");
			}
			else{
				jobj1.put("PaymentSuccessful","N");
				manual_status = prop_obj.getProperty("Claims_Status_Payment_Failed");
			}
			
			String reqstBody = jobj1.toJSONString();
			
			map.put("reqstBody", reqstBody);
			map.put("rest_url", rest_url);	
			map.put("manual_status", manual_status);
			
		
		if(prop_obj!=null){
			prop_obj.clear();
			
		}
		return map;
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}
	
	public static void main(String args[]){
		
		
		
		String input_json_string = "  {\r\n" + 
				"    \"ClaimReference\": \"ETG5N7P-6772-001\",\r\n" + 
				"    \"FraudResult\": \"AUTO-ACCEPT-BT\"}";
		
		
		String input_json_string1 = "{\r\n" + 
				"  \"header\": {\r\n" + 
				"    \"EventSource\": \"Claims API\",\r\n" + 
				"    \"EventName\": \"ClaimEu261Updated\"\r\n" + 
				" },\r\n" + 
				"  \"ClaimBody\": {\r\n" + 
				"    \"ClaimReference\": \"ESZ4QLG-448-003\",\r\n" + 
				"    \"OldState\": \"SUBMITTED\",\r\n" +					
				"    \"NewState\": \"REJECTED\"\r\n" + 
				"  }\r\n" + 
				"}";
		
		
		try {
			System.out.println(new ParseEventMessage().parseClaimSateChangeMessage(input_json_string, "Accertify"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	

}

