package com.easyjet.ei.commercials.claims.handlers;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.CalculateDateDifference;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;

public class CalculateDelayForProcessing implements WorkItemHandler{
	
	private static Logger logger = Logger.getLogger(CalculateDelayForProcessing.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		Map<String, Object> map = new HashMap<>();
		try {
			Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
			Properties prop_obj = (Properties) arg0.getParameter("prop_obj");
			String delay;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			if("Welfare".equalsIgnoreCase(claim_obj.getClaimType())){
				delay = calculateWelfareDelay(claim_obj, prop_obj, format);
			}
			else{
				delay = calculateEUCDelay(claim_obj, prop_obj, format);
			}
			
			logger.debug("Applying Delay of ["+delay+"] hours to process flight disruption data." );
			
			
			map.put("delay", delay);
			arg1.completeWorkItem(arg0.getId(), map);
		} catch (java.text.ParseException e) {
			logger.error(e);
			map.put("error_msg", "Error while Calculating delay for Disruption processing. Error is : " + e.toString());
			
		}
	}
	
	private String calculateEUCDelay(Claims claim_obj, Properties prop_obj, SimpleDateFormat format) throws java.text.ParseException{		
		
		String eucDelay;
		
		if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 12){
			eucDelay = prop_obj.getProperty("delay_euc_120h");
		}		
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 12 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 36){
			eucDelay = prop_obj.getProperty("delay_euc_96h");
		}
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 36 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 60){
			eucDelay = prop_obj.getProperty("delay_euc_72h");
		}
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 60 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 84){
			eucDelay = prop_obj.getProperty("delay_euc_48h");
		}
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 84 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 108){
			eucDelay = prop_obj.getProperty("delay_euc_24h");
		}
		else{			
			eucDelay = "0m";
		}
		
		return eucDelay;
		
	}
	
	private String calculateWelfareDelay(Claims claim_obj, Properties prop_obj, SimpleDateFormat format) throws java.text.ParseException{
		
		String welfareDelay;
		if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 12){
			welfareDelay = prop_obj.getProperty("delay_welfare_72h");
		}
		
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 12 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 36){
			welfareDelay = prop_obj.getProperty("delay_welfare_48h");
		}
		else if(CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")> 36 && CalculateDateDifference.getDateDifference(claim_obj.getCreateDate(), claim_obj.getFlightDate(), "Hours")<= 60){
			welfareDelay = prop_obj.getProperty("delay_welfare_24h");
		}
		else{
			welfareDelay = "0m";
		}
		
		return welfareDelay;
		
	}

}
