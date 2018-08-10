package com.easyjet.ei.commercials.claims.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.easyjet.ei.commercial.claims.pojo.getdocumentfroms3.GetDocumentLink;
import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;
import com.easyjet.ei.commercials.claims.common.RestUtils;
import com.easyjet.ei.commercials.claims.pojo.inputmsg.InitiateClaimProcessRequest;
import com.easyjet.ei.commercials.claims.pojo.inputmsg.InitiateClaimProcessRequestDocumentLinks;

public class KanaAddAttachment implements WorkItemHandler {

	private static final Logger logger = Logger.getLogger(KanaAddAttachment.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		Map<String, Object> map = new HashMap<String, Object>();
		SOAPMessage soapRequest = null;
		SOAPConnection soapConnection = null;

		try {

			String kana_url = (String) arg0.getParameter("kana_url");
			int caseId = (Integer) arg0.getParameter("caseId");
			String serverURI = (String) arg0.getParameter("serverURI");
			InitiateClaimProcessRequest input_obj = (InitiateClaimProcessRequest) arg0.getParameter("input_obj");

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			Properties props = ReadFromPropertyFile.readfromPropertyFile();

			int retry_count = Integer.parseInt(props.getProperty("Kana_Retry_Count"));
			long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));
			int rest_retry_count = Integer.parseInt(props.getProperty("Rest_Retry_Count"));
			
			//logger.debug("input_obj   " +  input_obj);

			List<InitiateClaimProcessRequestDocumentLinks> document_link = input_obj.getDocumentLinks();
			
			//logger.debug("document_link  " +  document_link);

			RestUtils rest_call_obj = new RestUtils();
			String getDoc_url = props.getProperty("get_document_from_S3_rest_url");

			for (InitiateClaimProcessRequestDocumentLinks doc_link : document_link) {

				JSONObject json_obj = new JSONObject();
				json_obj.put("documentLink", doc_link.getDocumentLink());
				
				//logger.debug("json_obj  " +  json_obj);

				Map<String, Object> rest_response = getAttachmentCall(rest_call_obj, getDoc_url, json_obj, caseId,
						props, 1, rest_retry_count);

				if ((Integer) rest_response.get("Status") >= 200 && (Integer) rest_response.get("Status") < 300) {

					soapRequest = createSOAPRequest(caseId, rest_response.get("encoded_file").toString(),
							doc_link.getDocumentLink().split("\\.")[1], doc_link.getDocumentLink().split("\\.")[0], serverURI);

					//logger.debug("soapRequest  " + soapRequest);

					map = doSOAPCall(soapConnection, soapRequest, kana_url, 1, retry_count, retry_interval, caseId,
							props);
				}

			}

		} /*
		catch (InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | InstantiationException | IllegalAccessException
				| IOException | UnsupportedOperationException | SOAPException | InterruptedException
				| ParseException | NullPointerException e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} 
		
		*/
		
		
		catch (InvalidKeyException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch (ClassNotFoundException e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( NoSuchAlgorithmException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( NoSuchPaddingException e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( IllegalBlockSizeException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( BadPaddingException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( InstantiationException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( IllegalAccessException e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch (IOException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( UnsupportedOperationException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( SOAPException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch ( InterruptedException e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch (ParseException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} catch (NullPointerException  e) {
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString() + " For request "	+ soapRequest.toString());
			logger.error("Error while sending attachments to KANA. Error is " + e.toString() + " For request "			+ soapRequest.toString());
			logger.error(e);
		} 
		
		
		
		
		
		
		finally {
			try {
				if (soapConnection != null)
					soapConnection.close();
				// count = 0;
			} catch (SOAPException e) {

				map.put("return_code", "Error");
				logger.error("Error while sending attachments to KANA. Error is " + e.toString());
				map.put("resp_msg", "Error while sending attachments to KANA. Error is " + e.toString()
						+ " For request " + soapRequest.toString());
				logger.error(e);
			}

		}

		arg1.completeWorkItem(arg0.getId(), map);

	}

	public static Map<String, Object> doSOAPCall(SOAPConnection soapConnection, SOAPMessage soapRequest, String url,
			int count, int retry_count, long retry_interval, int case_id, Properties props) {

		Map<String, Object> res_map = new HashMap<>();

		SOAPMessage soapResponse = null;
		OutputStream osReq = new ByteArrayOutputStream();
		OutputStream osRes = new ByteArrayOutputStream();

		try {

			soapRequest.writeTo(osReq);
			logger.debug("\nRequest SOAP Message = ");
			logger.debug(osReq.toString());
		
			soapResponse = soapConnection.call(soapRequest, url);

			soapResponse.writeTo(osRes);
			logger.debug("\nResponse SOAP Message = ");
			logger.debug(osRes.toString());

			res_map = checkReturnCode(soapResponse);

			if (!"0".equals(res_map.get("return_code"))) {
				if (count < retry_count) {
					logger.error("Retrying for non 0 return code");
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, soapRequest, url, count + 1, retry_count, retry_interval, case_id,
							props);
				} else {
					res_map.put("resp_msg",
							"Error while sending attachments to KANA. Error is: " + res_map.get("resp_msg"));
					logger.error("Response is not 0");
					String kana_note = "Receipts unable to be uploaded, please contact customer to re-send receipts. Failed to attach document"
							+ "." + " Reason : " + (String) res_map.get("resp_msg");
					KanaCreateUpdate.kanaNoteUpdateOnServiceFailure(kana_note, Integer.toString(case_id), props);
				}
			}

		} catch (SOAPException e) {
			logger.error(e);
			try {
				if (count < retry_count) {
					logger.error("Retrying for soap exception return code");
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, soapRequest, url, count + 1, retry_count, retry_interval, case_id,
							props);
				} else {
					res_map.put("return_code", "Error");
					res_map.put("resp_msg",
							"Retry limit for KANA Add-Attachment service exhausted" + ". Error is :" + e.toString());
					res_map.put("request", osReq.toString());
					logger.debug("soap fault for add atachment");
					String kana_note = "Receipts unable to be uploaded, please contact customer to re-send receipts. Failed to attach document";

					KanaCreateUpdate.kanaNoteUpdateOnServiceFailure(kana_note, Integer.toString(case_id), props);

				}
			} catch (InterruptedException | IOException | InvalidKeyException | UnsupportedOperationException
					| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | ParseException | SOAPException e1) {
				logger.error(e1);
				res_map.put("return_code", "Error");
				res_map.put("resp_msg", "Error while retrying for KANA Add-Attachment" + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
			}

		} catch (InterruptedException | IOException | InvalidKeyException | UnsupportedOperationException
				| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| ParseException | ParserConfigurationException | SAXException e) {
			logger.error(e);
			res_map.put("return_code", "Error");
			res_map.put("resp_msg", "Error while retrying for KANA Add-Attachment" + ". Error is :" + e.toString());
			res_map.put("request", osReq.toString());
		} finally {

			try {
				osReq.close();
				osRes.close();
			} catch (IOException e) {
				logger.error(e);
				res_map.put("resp_msg", "Error while retrying for KANA Add-Attachment" + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
				logger.error("Error while retrying for KANA Add-Attachment" + ". Error is :" + e.toString());
			}

		}

		return res_map;

	}

	public static Map<String, Object> checkReturnCode(SOAPMessage soapResponse)
			throws SOAPException, IOException, ParserConfigurationException, SAXException {

		Map<String, Object> map;
		ByteArrayOutputStream stream = null;
		try {
			map = new HashMap<>();
			String return_code = null;
			String resp_msg = null;
			stream = new ByteArrayOutputStream();
			
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(message));
			
			Document xmlDoc = db.parse(is);
			
			NodeList nodeList = xmlDoc.getElementsByTagName("AddAttachmentServiceResponseBody");
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node nNode = nodeList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					return_code = eElement.getElementsByTagName("returnCode").item(0).getTextContent();
					resp_msg = eElement.getElementsByTagName("returnMessage").item(0).getTextContent();

				}

			}
			map.put("return_code", return_code);
			map.put("resp_msg", resp_msg);
		} finally {
			if(stream != null){
				stream.close();
			}
		}
		return map;
	}

	private static SOAPMessage createSOAPRequest(int caseId, String encodedAttachmentFile, String fileExtensionType,
			String fileName, String serverURI) throws SOAPException {

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cas", serverURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("AddAttachmentServiceRequestBody", "cas");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseId", "cas");
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("encodedAttachmentFile", "cas");
		SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("fileExtensionType", "cas");
		SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("fileName", "cas");

		soapBodyElem1.addTextNode(String.valueOf(caseId));
		soapBodyElem2.addTextNode(encodedAttachmentFile);
		if(isFileExtensionUpperCase(fileExtensionType)){
			soapBodyElem3.addTextNode(fileExtensionType.toLowerCase());
		}else{
			soapBodyElem3.addTextNode(fileExtensionType);
		}
		soapBodyElem4.addTextNode(fileName);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", "CaseServiceService#AddAttachmentService");

		soapMessage.saveChanges();
		

		/* Print the request message */
		/*logger.debug("Request SOAP Message = ");
		logger.debug(soapMessage.toString());*/

		return soapMessage;
	}

	private Map<String, Object> getAttachmentCall(RestUtils rest_call_obj, String getDoc_url, JSONObject json_obj,
			int caseId, Properties props, int count, int retry_count)
			throws InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InstantiationException, IllegalAccessException, IOException,
			UnsupportedOperationException, SOAPException, InterruptedException, ParseException {

		Map<String, Object> rest_response = rest_call_obj.restCall(getDoc_url,
				"com.easyjet.ei.commercial.claims.pojo.getdocumentfroms3.GetDocumentLink", "POST",
				json_obj.toJSONString(), "getReceipts");

		if ((Integer) rest_response.get("Status") >= 200 && (Integer) rest_response.get("Status") < 300) {
			GetDocumentLink res_obj = (GetDocumentLink) rest_response.get("Result");
			String base64 = res_obj.getData();
			rest_response.put("encoded_file", base64);
		} else if ((Integer) rest_response.get("Status") == 404) {
			String kana_note = "Unable to fetch Welfare receipts from S3. Receipt not available. Kindly ask customer to upload again";
			KanaCreateUpdate.kanaNoteUpdateOnServiceFailure(kana_note, Integer.toString(caseId), props);
		} else {
			if (count < retry_count) {
				getAttachmentCall(rest_call_obj, getDoc_url, json_obj, caseId, props, count + 1, retry_count);
				String kana_note = "Unable to fetch Welfare receipts from S3. Receipt not available. Kindly ask customer to upload again";
				KanaCreateUpdate.kanaNoteUpdateOnServiceFailure(kana_note, Integer.toString(caseId), props);
			}
		}
		return rest_response;
	}

	/*
     * changes for EA 800  uploadin receipt(S3 Upper Cases)
     */
	private static boolean isFileExtensionUpperCase(String fileExtensionType){

		char[] charArray = fileExtensionType.toCharArray();
		for(int i=0; i < charArray.length; i++){

			//if any character is not in upper case, return false
			if( !Character.isUpperCase( charArray[i] ))
				return false;
		}
		return true;
	}
}
