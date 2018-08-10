package com.easyjet.ei.commercials.claims.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.CSVUtils;
import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;


public class LogClaimProcessExceptions implements WorkItemHandler {
	
	private Logger logger = Logger.getLogger(LogClaimProcessExceptions.class);
	private static final String FILE_HEADER = "Claim Reference, Date, Time, Process ID, Process Name,Signal Name,Service Call, Claim Status, Error, Error Type";


	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		FileWriter writer=null;
		
		
		try {
			
			Map<String, String> error_map = new HashMap<String, String>();			
			error_map = (HashMap<String, String>) arg0.getParameter("error_map");
			
			logger.debug("Loggin exception. Processing failed for claim reference " + error_map.get("claim_reference") + "Due to error " + error_map.get("error"));			
			
			String csvFile="";
			String seperator="";
			
				csvFile = ReadFromPropertyFile.readfromPropertyFile().getProperty("CSV_file_Location");
				seperator = ReadFromPropertyFile.readfromPropertyFile().getProperty("DEFAULT_SEPARATOR");
			
			File file = new File(csvFile);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				writer = new FileWriter(file.getAbsoluteFile(), true);	
				
				writer.append(FILE_HEADER.toString());
				writer.append("\n");
		
			}
			if(writer == null){
				writer = new FileWriter(file.getAbsoluteFile(), true);
			}
			
			LocalDateTime currentTime = LocalDateTime.now();
			CSVUtils.writeLine(writer, Arrays.asList(error_map.get("claim_reference"),currentTime.toLocalDate().toString(),currentTime.toLocalTime().toString(), error_map.get("processId"), error_map.get("processName"),error_map.get("signal_name"),error_map.get("service"), error_map.get("claim_sttaus"), error_map.get("error"),error_map.get("error_type")),seperator.charAt(0));
			
			writer.flush();	
			
									
			arg1.completeWorkItem(arg0.getId(), null);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		
		} catch (NullPointerException e) {
			logger.error(e);
			e.printStackTrace();
		
		}
		finally{
			try {
				if(writer != null) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				logger.error(e);
		
			}
		}
		
	}
        

}
