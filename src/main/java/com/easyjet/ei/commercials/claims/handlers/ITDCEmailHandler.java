package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.SendOCCEmail;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;


public class ITDCEmailHandler implements WorkItemHandler {
	
	private Logger logger = Logger.getLogger(ITDCEmailHandler.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {		
		
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		try {
			logger.debug("inside ITDCEmailHandler..............");
			Properties props = (Properties) arg0.getParameter("prop_obj");
			//Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
			String claim_reference = (String) arg0.getParameter("claim_reference");
			String processName = (String) arg0.getParameter("processName");
			long processId = (Long) arg0.getParameter("processId");
			String exception = (String) arg0.getParameter("exception");		
		
			SendOCCEmail.sendEmail(props.getProperty("itsd_email_template"), null, null, "itdc", exception, processName, Long.toString(processId), claim_reference );
			
			
			
		} catch (IOException | MessagingException | ParseException  | NullPointerException e) {
			logger.debug("Error while sending ITSD email : " + e.getMessage() );
			logger.error("Error while sending ITSD email : " + e.getMessage() );
		}
		finally{
			arg1.completeWorkItem(arg0.getId(), null);
		}
		
	}

}
