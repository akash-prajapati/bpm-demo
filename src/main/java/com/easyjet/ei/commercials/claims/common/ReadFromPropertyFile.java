package com.easyjet.ei.commercials.claims.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadFromPropertyFile {
	
	public static Properties readfromPropertyFile() throws IOException {

		
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = new FileInputStream(System.getenv("JBOSS_HOME")+"/properties.properties");

			// load a properties file
			prop.load(input);
			
			// get the property value and print it out
		}
		 finally {
			if (input != null) {				
					input.close();				
			}
		}
		return prop;

	}
	
	
}
