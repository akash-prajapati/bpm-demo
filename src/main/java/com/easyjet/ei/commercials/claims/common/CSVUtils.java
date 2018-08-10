package com.easyjet.ei.commercials.claims.common;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVUtils {
	
	private CSVUtils(){
		
	}

    private static final char DEFAULT_SEPARATOR=',';

    public static void writeLine(Writer w, List<String> values) throws IOException{
    	ReadFromPropertyFile.readfromPropertyFile().getProperty("CSV_file_Location");
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if(result != null){
	        if (result.contains("\"")) {
	            result = result.replace("\"", "\"\"");
	        }
	        if (result.contains(",")) {
	            result = result.replace(",", "|");
	        }
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;
        
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
            	if (separators != ' ') {
            		sb.append(separators);
            	}
            	else{
            		sb.append(DEFAULT_SEPARATOR);
            	}
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        
        w.append(sb.toString());
        
        


    }

}