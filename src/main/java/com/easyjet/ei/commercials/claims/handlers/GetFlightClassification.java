package com.easyjet.ei.commercials.claims.handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class GetFlightClassification implements WorkItemHandler {

	private static Logger logger = Logger.getLogger(GetFlightClassification.class);

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		try {
			String disruption_reason = (String) arg0.getParameter("disruption_reason");
			
			
			 String classification = getClassification(disruption_reason);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("classification", classification);
			
			arg1.completeWorkItem(arg0.getId(), map);
		} catch (EncryptedDocumentException e) {
			logger.error("Error while getiing classification from the list. Error is - " + e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error("Error while getiing classification from the list. Error is - " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error while getiing classification from the list. Error is - " + e.getMessage());
		}

	}

	private static String getClassification(String classification_reason)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String classification;
		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(System.getenv("JBOSS_HOME") + "/DisruptionReason.xlsx");
			classification = null;
			int ctr = 0;
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			
			boolean isNull = false;
			do {
				try {
					Row row = sheet.getRow(ctr);
					Cell cell = row.getCell(1);
					if (classification_reason.equals(cell.toString())) {
						classification = row.getCell(2).toString();

					}
					ctr++;
				} catch (Exception e) {
					isNull = true;
				}

			} while (isNull != true);
			
			logger.debug("classification is   " + classification);
		} finally {
			if(inp != null){
				inp.close();
			}
			if(wb != null){
				wb.close();
			}
		}
		return classification;
	}
	
	public static Map<String, Object> getClassification(String classification_reason, String language)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Map<String, Object> map;
		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(System.getenv("JBOSS_HOME") + "/DisruptionReason.xlsx");
			map = new HashMap<String, Object>();
			String classification = null;
			String disruptionReason = null;
			int ctr = 0;
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			boolean isNull = false;
			do {
				try {
					Row row = sheet.getRow(ctr);
					Cell cell = row.getCell(1);
					if (classification_reason.equals(cell.toString())) {
						classification = row.getCell(2).toString();
						disruptionReason = getDisruptionReasonInByLanguage(row, language);

						break;
					}
					ctr++;
				} catch (Exception e) {
					isNull = true;
				}

			} while (isNull != true);
			inp.close();
			wb.close();
			logger.debug("classification is   " + classification);
			logger.debug("disruptionReason in   " + language + "is " + disruptionReason);
			map.put("classification", classification);
			map.put("disruptionReason", disruptionReason);
		} finally {
			if(inp != null){
				inp.close();
			}
			if(wb != null){
				wb.close();
			}
		}
		return map;
	}
	
	public static String getDisruptionReasonInByLanguage(Row row,String language){
		String disruptionReasonLang=null;
		
		switch(language.toLowerCase()){
			case "en":
				disruptionReasonLang = row.getCell(1).toString();
			break;
			
			case "fr":
				disruptionReasonLang = row.getCell(4).toString();
			break;
				
			case "de":
				disruptionReasonLang = row.getCell(5).toString();
			break;
			
			case "it":
				disruptionReasonLang = row.getCell(6).toString();
			break;
			
			case "es":
				disruptionReasonLang = row.getCell(7).toString();
			break;
			
			case "pt":
				disruptionReasonLang = row.getCell(8).toString();
			break;
			
			case "nl":
				disruptionReasonLang = row.getCell(9).toString();
			break;
		
		}
		
		
		return disruptionReasonLang;
	} 

	/*public static void main(String[] args) throws InvalidFormatException, IOException {
		try{
			InputStream inp = new FileInputStream(System.getenv("JBOSS_HOME") + "/DisruptionReason.xlsx");		
			String classification = null;
			int ctr = 0;
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			Row row = null;
			Cell cell = null;
			boolean isNull = false;
			do {
				try {
					row = sheet.getRow(ctr);
					cell = row.getCell(1);
					if ("Missed Curfew".equals(cell.toString())) {
						classification = row.getCell(2).toString();
						System.out.println("disruptionReason == " +  getDisruptionReasonInByLanguage(row, "fr"));
						break;
					}
					ctr++;
				} catch (Exception e) {
					isNull = true;
				}

			} while (isNull != true);
			inp.close();
			System.out.println("classification is   " + classification);	
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
	

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
	}

}
