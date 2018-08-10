package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;

public class LoadPropertiesFromFile implements WorkItemHandler {
	
	private static final Logger logger = Logger.getLogger(LoadPropertiesFromFile.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		String error="";
		HashMap<String, Object> map_prop = new HashMap<>();
		try{			
			
			Properties props = ReadFromPropertyFile.readfromPropertyFile();		
			
			
			map_prop.put("prop_obj", props);			
			
			
		
		}catch(IOException e){
			logger.error(e);
			error = "Error while loading properties from property file. Error is " + e.toString();
			map_prop.put("error", error);			
		
		}finally{
		
		}
		arg1.completeWorkItem(arg0.getId(), map_prop);
	}
	
	

}
