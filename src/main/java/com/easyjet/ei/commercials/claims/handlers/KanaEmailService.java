package com.easyjet.ei.commercials.claims.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;
import com.easyjet.ei.commercials.claims.pojo.caseservice.NameValuePairList;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.geteresbooinginfo.BookingInfo;

public class KanaEmailService implements WorkItemHandler {

	private static final Logger logger = Logger.getLogger(KanaEmailService.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
	

	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
				
		SOAPConnection soapConnection = null;
		Map<String, Object> map = new HashMap<String, Object>();
		long mailDelay = 0;

		Map<String, Object> map_return = new HashMap<>();
		try{
			Map<String, Object> map_params = arg0.getParameters();
			Properties props = ReadFromPropertyFile.readfromPropertyFile();
			String url = props.getProperty("kana_email_url");
			int retry_count = Integer.parseInt(props.getProperty("Kana_Retry_Count"));
			long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));

			String actionFlag = (String) arg0.getParameter("actionFlag");

			logger.debug("performing action : "+actionFlag);

			if("calculateMailDelay".equals(actionFlag)){

				logger.debug("***Here to calculate delay****");
				mailDelay = calculateDelay(props);
				map_return.put("mailDelay", mailDelay+"H");

			}
			if("sendKanaMail".equals(actionFlag)){
				BookingInfo bookInfo = null;
				if(map_params.get("booking_obj") != null){
					bookInfo = (BookingInfo) arg0.getParameter("booking_obj");
				}
				Claims claims_obj = new Claims();
				claims_obj = (Claims) arg0.getParameter("claim_obj");
	     		String emailAddress = claims_obj.getClaimant().getEmailAddress();
				String locale = claims_obj.getLanguage();
				String templateName = (String) arg0.getParameter("templateName");
				NameValuePairList placeHolderList = new NameValuePairList();
		
				//String templateName_lang = populateTemplateForLanguage(templateName, claims_obj.getLanguage());
				String templateName_lang = claims_obj.getLanguage().toUpperCase()+"_"+templateName;
				
				logger.debug("templateName_lang  " +  templateName_lang);
				
				int case_id = Integer.parseInt(claims_obj.getKanaCaseReference());

				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
				soapConnection = soapConnectionFactory.createConnection();
				
				
				
				if(templateName.contains("&")){
					
					String temp_name[] = templateName.split("&");
					
					for(int i=0;i<temp_name.length;i++){
						placeHolderList = KanaEmailTemplatePopulation.populateNvpairList(temp_name[i],arg0);
						SOAPMessage soapRequest = createSOAPRequest(case_id,emailAddress,locale,temp_name[i],placeHolderList,bookInfo,props,claims_obj);
						doSOAPCall(soapConnection,soapRequest,url,1, retry_count,
								retry_interval,map_return);
					}
					
					
				}
				else{
					placeHolderList = KanaEmailTemplatePopulation.populateNvpairList(templateName,arg0);	
					SOAPMessage soapRequest = createSOAPRequest(case_id,emailAddress,locale,templateName,placeHolderList,bookInfo,props,claims_obj);
					doSOAPCall(soapConnection,soapRequest,url,1, retry_count,
							retry_interval,map_return);
				}

				logger.debug("map before sending it to process  " + map_return);
				
			}
			

		}catch(IOException  e){
		
			logger.error(e);
			map_return.put("resp_msg", "Error while preparing request for KANA Email"				
					+ "Error is : " + e.toString());

		}catch(SOAPException  e){
		
			logger.error(e);
			map_return.put("resp_msg", "Error while preparing request for KANA Email"				
					+ "Error is : " + e.toString());

		}catch(EncryptedDocumentException  e){
		
			logger.error(e);
			map_return.put("resp_msg", "Error while preparing request for KANA Email"				
					+ "Error is : " + e.toString());

		}catch( InvalidFormatException e){
		
			logger.error(e);
			map_return.put("resp_msg", "Error while preparing request for KANA Email"				
					+ "Error is : " + e.toString());

		}
		
		
		
		finally {
			try {
				if(soapConnection != null){
					soapConnection.close();
				}

			} catch (SOAPException e) {

				logger.error(e);
				map_return.replace("resp_msg",
						"Error while closing SOAP connetion for KANA Email" + "Error is : " + e.toString());
			}

		}
		arg1.completeWorkItem(arg0.getId(), map_return);

	}
	

	public static long calculateDelay(Properties props){

		long mailDelay = 0;

		
			LocalDateTime currentTime = LocalDateTime.now();
			int rejHr = currentTime.getHour();
			int wrkHrStrt = Integer.parseInt(props.getProperty("work_hour_start"));
			int wrkHrEnd = Integer.parseInt(props.getProperty("work_hour_end"));
			int defltDely = Integer.parseInt(props.getProperty("default_delay"));

			if (wrkHrStrt <= rejHr && rejHr <= wrkHrEnd){

				mailDelay = defltDely;
				logger.debug("Default Delay : "+ mailDelay+ "hr");
			}else{

				if(rejHr>wrkHrEnd && rejHr>12 && rejHr<24){

					mailDelay = (long) defltDely + (24 - rejHr) + wrkHrStrt;
					logger.debug("after working hr delay not past 00hr : "+ mailDelay+ "hr");
				}

				if(rejHr<wrkHrStrt && rejHr<12){

					mailDelay = (long) defltDely + (wrkHrStrt - rejHr);
					logger.debug("before working hr past 00hr : "+mailDelay + "hr");
				}
			}
			
		

		return mailDelay;
	}

	public static void doSOAPCall(SOAPConnection soapConnection, SOAPMessage soapRequest,String url,int count, int retry_count,
			long retry_interval, Map<String, Object> map_return){

		Map<String, Object> res_map = new HashMap<>();

		SOAPMessage soapResponse = null;
		OutputStream osReq = new ByteArrayOutputStream();
		OutputStream osRes = new ByteArrayOutputStream();

		try {


			soapRequest.writeTo(osReq);
			//System.out.println(osReq.toString());
			logger.debug("\nRequest SOAP Message = "); 
			logger.debug(osReq.toString());
			

			soapResponse = soapConnection.call(soapRequest, url);

			soapResponse.writeTo(osRes);
		
			logger.debug("\nResponse SOAP Message = ");
			logger.debug(osRes.toString());
			

			res_map = checkReturnCode(soapResponse);
			map_return.putAll(res_map);
			if(!"0".equals(res_map.get("return_code"))){
				if(!"2".equals(res_map.get("return_code")) && !"3".equals(res_map.get("return_code"))){
					if (count < retry_count) {
						logger.debug("Retrying for non 0 return code");
						Thread.sleep(retry_interval);
						doSOAPCall(soapConnection, soapRequest, url, count + 1,
								retry_count, retry_interval,map_return);						
					}
				}
			}

		}catch(SOAPException e){
			logger.error(e);

			try {
				if (count < retry_count) {
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection,soapRequest, url, count + 1,
							retry_count, retry_interval,map_return);					
					/*res_map.put("return_code", "Error");
					res_map.put("resp_msg",
							"Error while retrying for KANA Email" + ". Error is :" + e.toString());
					res_map.put("request", osReq.toString());*/
				}
			} catch (InterruptedException e1) {
				res_map.put("return_code", "Error");
				res_map.put("resp_msg",
						"Error while retrying for KANA Email" + ". Error is :" + e1.toString());
				res_map.put("request", osReq.toString());
			}
		}
		catch (IOException | ParserConfigurationException | SAXException | InterruptedException e) {
			logger.error(e);
		
				logger.error(e);
				res_map.put("resp_msg",
						"Error while retrying for KANA Email" + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
		

		}finally{

			try {
				osReq.close();
				osRes.close();
			} catch (IOException e) {
				logger.error(e);
				res_map.put("resp_msg",
						"Error while retrying for KANA Email" + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
			}

		}

		//return res_map;
	}

	public static Map<String, Object> checkReturnCode(SOAPMessage soapResponse) throws SOAPException, IOException, ParserConfigurationException, SAXException {

		Map<String, Object> map = new HashMap<>();
		

			String return_code = null;
			String resp_msg = null;


			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				soapResponse.writeTo(stream);
				String message = new String(stream.toByteArray(), "utf-8");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(message));
				Document xmlDoc = db.parse(is);
				NodeList nodeList = xmlDoc.getElementsByTagName("m:SendEmailResponseBody");
				for (int temp = 0; temp < nodeList.getLength(); temp++) {

					Node nNode = nodeList.item(temp);

					logger.debug("\nCurrent Element :" + nNode.getNodeType());

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						return_code = eElement.getElementsByTagName("returnCode").item(0).getTextContent();
						resp_msg = eElement.getElementsByTagName("returnMessage").item(0).getTextContent();
						logger.debug("Retur code: " + return_code + "   " + "Resp Msg: " + resp_msg);
					}

					map.put("return_code", return_code);
					map.put("resp_msg", resp_msg);

				} 
			} finally {
				if(stream != null){
					stream.close();
				}
			}
		return map;
	}

	private static SOAPMessage createSOAPRequest (Integer case_id, String emailAddress, String locale, String templateName, NameValuePairList placeHolderList, BookingInfo bookInfo, Properties props, Claims claims_obj) throws SOAPException, IOException{

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = ReadFromPropertyFile.readfromPropertyFile().getProperty("kana_email_server_url");

		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("ema", serverURI);

		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("SendEmailRequestBody", "ema");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("case_id", "ema");
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("emailAddress", "ema");
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("locale", "ema");
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("templateName", "ema");
		
		SOAPElement soapBodyElem5 = null;
		SOAPElement soapBodyElem6 = null;
		
		if(templateName.equals(props.get("Comp_Reject_ExtraOrdinary_Generic"))){
			soapBodyElem5 = soapBodyElem.addChildElement("disruptionFlightNum", "ema");
			soapBodyElem6 = soapBodyElem.addChildElement("disruptionFlightDate", "ema");
		}
		SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("placeholderList", "ema");

		soapBodyElem1.addTextNode(Integer.toString(case_id));			
		
		soapBodyElem4.addTextNode(templateName);
		
		if(templateName.equals(props.get("Comp_Awaiting_Booker_Consent"))){
			soapBodyElem2.addTextNode(emailAddress);
			//soapBodyElem4.addTextNode(claims_obj.getLanguage().toUpperCase()+"_"+templateName);
			
		}
		else if(templateName.equals(props.get("Comp_Request_Booker_Consent"))){
			soapBodyElem2.addTextNode(bookInfo.getBookerInfo().getEmailAddress());
			//soapBodyElem4.addTextNode(claims_obj.getLanguage().toUpperCase()+"_"+templateName);
			
		}
		else if(templateName.equals(props.get("Comp_Reject_ExtraOrdinary_Generic"))){
			soapBodyElem2.addTextNode(emailAddress);
			
		}
		else{
			soapBodyElem2.addTextNode(emailAddress);
			//soapBodyElem4.addTextNode(claims_obj.getLanguage().toUpperCase()+"_"+templateName);
		}
		
		if("en".equalsIgnoreCase(locale)){
			soapBodyElem3.addTextNode(locale.toLowerCase()+"-GB");
		}
		else{
			soapBodyElem3.addTextNode(locale.toLowerCase());
		}
		
		if(templateName.equals(props.get("Comp_Reject_ExtraOrdinary_Generic"))){
			soapBodyElem5.addTextNode(claims_obj.getFlightNumber());
			soapBodyElem6.addTextNode(claims_obj.getFlightDate().split("T")[0].replace("-", ""));

		}

		for(int i = 0; i < placeHolderList.getNameValuePair().size(); i++){
			SOAPElement soapBodyElem8 = soapBodyElem7.addChildElement("NameValuePair", "ema");
			SOAPElement soapBodyElem9 = soapBodyElem8.addChildElement("name", "ema");
			SOAPElement soapBodyElem10 = soapBodyElem8.addChildElement("value", "ema");
			if(null!=placeHolderList.getNameValuePair().get(i).getName()){
				soapBodyElem9.addTextNode(placeHolderList.getNameValuePair().get(i).getName());
			}
			if(null!=placeHolderList.getNameValuePair().get(i).getValue()){
			
			soapBodyElem10.addTextNode(placeHolderList.getNameValuePair().get(i).getValue());
			}
		}

		MimeHeaders headers = soapMessage.getMimeHeaders();
		
		headers.addHeader("SOAPAction",  "EmailServiceService#SendEmail");

		soapMessage.saveChanges();

		return soapMessage;

	}
	


}
