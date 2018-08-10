package com.easyjet.ei.commercials.claims.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
/*import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;*/
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.easyjet.ei.commercials.claims.common.AESEncryption;
import com.easyjet.ei.commercials.claims.common.ReadFromPropertyFile;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.inputmsg.InitiateClaimProcessRequestBankAccountDetails;

public class KanaCreateUpdate implements WorkItemHandler {

	private static final Logger logger = Logger.getLogger(KanaCreateUpdate.class);

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		SOAPConnection soapConnection = null;
		String kana_operation = null;
		Map<String, Object> map = new HashMap<>();
		SOAPMessage soapRequest = null;

		try {

			Map<String, Object> params = arg0.getParameters();

			
			Claims claims_obj = (Claims) arg0.getParameter("claim_obj");
			kana_operation = (String) arg0.getParameter("kana_operation");
			String kana_state = (String) arg0.getParameter("kana_state");
			String kana_note = (String) arg0.getParameter("kana_note");
			///Properties prop_obj = (Properties) arg0.getParameter("prop_obj");
			InitiateClaimProcessRequestBankAccountDetails bank_obj = null;
			String flag = "false";
			String case_id = "";
			if (params.get("bank_obj") != null) {
				bank_obj = (InitiateClaimProcessRequestBankAccountDetails) arg0.getParameter("bank_obj");
			}

			Properties props = ReadFromPropertyFile.readfromPropertyFile();

			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = props.getProperty("kana_url");
			int retry_count = Integer.parseInt(props.getProperty("Kana_Retry_Count"));
			long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));
			String claim_type_kana = "";
			if ("EUC".equalsIgnoreCase(claims_obj.getClaimType())) {
				claim_type_kana = claims_obj.getLanguage().toUpperCase() + " " + props.getProperty("Claim_Type_EUC");
			} else {
				claim_type_kana = claims_obj.getLanguage().toUpperCase() + " " + props.getProperty("Claim_Type_Welfare");
			}

			soapRequest = createSOAPRequest(kana_operation, kana_state, kana_note, claims_obj, claim_type_kana,
					bank_obj, flag, case_id.toUpperCase(), props);

			map = doSOAPCall(soapConnection, kana_operation, kana_state, kana_note, soapRequest, url, 1, retry_count,
					retry_interval);

		} catch (ParseException p) {
			logger.error(p);
			logger.error("Error while Constructing KANA Note " + p.getMessage());
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while Constructing KANA Note " + kana_operation + ". Error is :" + p.toString());
		} catch (IOException e) {
			logger.error(e);
			logger.error("Error while preparing request for Updating KANA. Error is : " + e.getMessage());
			map.put("return_code", "Error");
			if (kana_operation != null && "Update".equals(kana_operation)) {
				map.put("resp_msg", "Error while preparing request for Updating KANA. Error is : " + e.toString());
			} else {
				map.put("resp_msg",
						"Error while preparing request for Add KANA " + kana_operation + "Error is : " + e.toString());
			}
		} catch (UnsupportedOperationException e) {
			logger.error(e);
			logger.error("Error while preparing request for Updating KANA. Error is : " + e.getMessage());
			map.put("return_code", "Error");
			if (kana_operation != null && "Update".equals(kana_operation)) {
				map.put("resp_msg", "Error while preparing request for Updating KANA. Error is : " + e.toString());

			} else {
				map.put("resp_msg",
						"Error while preparing request for Add KANA " + kana_operation + "Error is : " + e.toString());
			}
		} catch (SOAPException e) {
			logger.error(e);
			map.put("return_code", "Error");
			if (kana_operation != null && "Update".equals(kana_operation)) {
				map.put("resp_msg", "Error while preparing request for Updating KANA. Error is : " + e.toString());

			} else {
				map.put("resp_msg",
						"Error while preparing request for Add KANA " + kana_operation + "Error is : " + e.toString());
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("resp_msg", "Error while decrypting bank details. Error is : " + e.toString());

		} finally {
			try {
				if (soapConnection != null) {
					soapConnection.close();
				}

			} catch (SOAPException e) {
				logger.error(e);

				map.replace("resp_msg",
						"Error while closing SOAP connetion for KANA " + kana_operation + "Error is : " + e.toString());
			}

		}
		arg1.completeWorkItem(arg0.getId(), map);
	}

	public static Map<String, Object> doSOAPCall(SOAPConnection soapConnection, String kana_operation,
			String kana_state, String kana_note, SOAPMessage soapRequest, String url, int count, int retry_count,
			long retry_interval) {

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

			res_map = checkReturnCode(soapResponse, kana_operation);

			if (!"0".equals(res_map.get("return_code"))) {
				if (count < retry_count) {
					logger.debug("Retrying for non 0 return code");
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, kana_operation, kana_state, kana_note, soapRequest, url, count + 1,
							retry_count, retry_interval);
				}
			}

		} catch (SOAPException e) {
			logger.error(e);

			try {
				if (count < retry_count) {
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, kana_operation, kana_state, kana_note, soapRequest, url, count + 1,
							retry_count, retry_interval);
					/*res_map.put("return_code", "Error");
					res_map.put("resp_msg",
							"Error while retrying for KANA " + kana_operation + ". Error is :" + e.toString());
					res_map.put("request", osReq.toString());*/
				}
			} catch (InterruptedException e1) {
				logger.error(e1);
				res_map.put("return_code", "Error");
				res_map.put("resp_msg",
						"Error while retrying for KANA " + kana_operation + ". Error is :" + e1.toString());
				res_map.put("request", osReq.toString());

			}

		} catch (InterruptedException | SAXException  | IOException | ParserConfigurationException e) {
			logger.error(e);
			res_map.put("resp_msg", "Error while retrying for KANA " + kana_operation + ". Error is :" + e.toString());
			res_map.put("request", osReq.toString());
		} finally {

			try {
				if (osReq != null) {
					osReq.close();
				}
				if (osRes != null) {
					osRes.close();
				}
			} catch (IOException e) {
				logger.error(e);
				res_map.put("resp_msg",
						"Error while retrying for KANA " + kana_operation + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
			}

		}

		return res_map;

	}

	public static Map<String, Object> checkReturnCode(SOAPMessage soapResponse, String kana_operation) throws SOAPException, IOException, ParserConfigurationException, SAXException {

		Map<String, Object> map = new HashMap<>();
		

			String return_code = null;
			String resp_msg = null;
			int kana_case_id = 0;

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				soapResponse.writeTo(stream);
				String message = new String(stream.toByteArray(), "utf-8");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(message));
				Document xmlDoc = db.parse(is);
				if ("Update".equals(kana_operation)) {
					NodeList nodeList = xmlDoc.getElementsByTagName("EditCaseServiceResponseBody");

					for (int temp = 0; temp < nodeList.getLength(); temp++) {
						Node nNode = nodeList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;

							return_code = eElement.getElementsByTagName("returnCode").item(0).getTextContent();
							resp_msg = eElement.getElementsByTagName("returnMessage").item(0).getTextContent();

						}

					}

				} else {
					NodeList nodeList = xmlDoc.getElementsByTagName("CreateCaseServiceResponseBody");

					for (int temp = 0; temp < nodeList.getLength(); temp++) {
						Node nNode = nodeList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;

							return_code = eElement.getElementsByTagName("returnCode").item(0).getTextContent();

							if (return_code != null || return_code != "") {
								kana_case_id = Integer
										.parseInt(eElement.getElementsByTagName("caseId").item(0).getTextContent());
							}

							resp_msg = eElement.getElementsByTagName("returnMessage").item(0).getTextContent();

						}
					}

				}
				map.put("return_code", return_code);
				map.put("resp_msg", resp_msg);
				if (!"Update".equals(kana_operation)) {
					map.put("kana_case_id", kana_case_id);
				} 
			} finally {
				if(stream != null){
					stream.close();
				}
			}
		return map;
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

	}

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 * 
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ParseException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */

	public static SOAPMessage createSOAPRequest(String operation, String kana_new_status, String kana_note,
			Claims claim_obj, String claim_type_kana, InitiateClaimProcessRequestBankAccountDetails bank_obj,
			String flag, String case_id, Properties prop_obj)
			throws SOAPException, IOException, ParseException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = ReadFromPropertyFile.readfromPropertyFile().getProperty("kana_server_url");

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cas", serverURI);

		// SOAP Body
		if ("Update".equals(operation)) { // KANA case update
			SOAPBody soapBody = envelope.getBody();
			SOAPElement soapBodyElem = soapBody.addChildElement("EditCaseServiceRequestBody", "cas");
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseId", "cas");

			if (("false").equalsIgnoreCase(flag)) {
				SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("newState", "cas");
				soapBodyElem2.addTextNode(kana_new_status);
			}

			SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("caseNote", "cas");

			if (("false").equalsIgnoreCase(flag)) {
				soapBodyElem1.addTextNode(claim_obj.getKanaCaseReference());
			} else {
				soapBodyElem1.addTextNode(case_id);
			}

			soapBodyElem3.addTextNode(kana_note);

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", "CaseServiceService#EditCaseService");
		} else {// KANA case create
			SOAPBody soapBody = envelope.getBody();
			SOAPElement soapBodyElem = soapBody.addChildElement("CreateCaseServiceRequestBody", "cas");
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("caseType", "cas");
			SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("caseSummary", "cas");
			SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("customerEmailAddress", "cas");
			SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("customerFirstName", "cas");
			SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("customerLastName", "cas");
			SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("caseNotes", "cas");
			SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("bookingReference", "cas");
			SOAPElement soapBodyElem8 = soapBodyElem.addChildElement("language", "cas");

			soapBodyElem1.addTextNode(claim_type_kana);
			soapBodyElem2.addTextNode(claim_obj.getClaimReference());
			soapBodyElem3.addTextNode(claim_obj.getClaimant().getEmailAddress());
			soapBodyElem4.addTextNode(claim_obj.getClaimant().getFirstName());
			soapBodyElem5.addTextNode(claim_obj.getClaimant().getLastName());
			soapBodyElem6.addTextNode(constructCaseNote(claim_obj, bank_obj, prop_obj));
			soapBodyElem7.addTextNode(claim_obj.getBookingReference());
			soapBodyElem8.addTextNode(claim_obj.getLanguage());

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", "CaseServiceService#CreateCaseService");

		}

		soapMessage.saveChanges();
		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 * 
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws ParseException
	 * @throws Exception
	 */

	public static String constructCaseNote(Claims claim_obj, InitiateClaimProcessRequestBankAccountDetails bank_obj,
			Properties prop_obj) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, ParseException {

		String secKey = prop_obj.getProperty("secKey");

		SecretKeySpec secKeySpec = AESEncryption.getAesKey(Base64.getDecoder().decode(secKey.getBytes()));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/YYYY");
		String flight_date = format1.format(format.parse(claim_obj.getFlightDate()));
		String address = claim_obj.getClaimant().getAddress().getAddressLine1() + ", "
				+ claim_obj.getClaimant().getAddress().getAddressLine2() + ", "
				+ claim_obj.getClaimant().getAddress().getTown() + ", "
				+ claim_obj.getClaimant().getAddress().getCounty() + ", "
				+ claim_obj.getClaimant().getAddress().getCountry() + ", "
				+ claim_obj.getClaimant().getAddress().getPostCode();

		StringBuilder stringBuffer = new StringBuilder();

		// stringBuffer.append(claim_obj.getClaimant().getFirstName());
		// stringBuffer.append(claim_obj.getClaimant().getLastName() + "\n");
		// stringBuffer.append(claim_obj.getClaimant().getEmailAddress() +
		// "\n");
		stringBuffer.append(claim_obj.getLanguage().toUpperCase() + "\n");
		stringBuffer.append("----------------" + "\n");
		stringBuffer.append("Claim Details" + "\n");
		stringBuffer.append("----------------" + "\n");
		stringBuffer.append("Language Code : " + claim_obj.getLanguage().toUpperCase() + "\n");
		stringBuffer.append("Claim Reference : " + claim_obj.getClaimReference() + "\n");
		stringBuffer.append("Booking Reference : " + claim_obj.getBookingReference() + "\n");
		stringBuffer.append("Flight Number : " + claim_obj.getFlightNumber() + "\n");
		stringBuffer.append("Flight Date : " + flight_date + "\n");
		stringBuffer.append("Departure Airport : " + claim_obj.getDepartureAirportCode() + "\n");
		stringBuffer.append("Arrival Airport : " + claim_obj.getArrivalAirportCode() + "\n");
		stringBuffer.append("Reason For Code : " + claim_obj.getReasonForClaim() + "\n");
		stringBuffer.append("-----------------" + "\n");
		stringBuffer.append("Claimant Details" + "\n");
		stringBuffer.append("-----------------" + "\n");
		if ("EUC".equals(claim_obj.getClaimType())) {
			stringBuffer.append(
					"Travel Agency : " + claim_obj.getClaimant().getTravelAgent() != null ? "Yes" : "No" + "\n");
			stringBuffer.append("Travel Agency Name : " + claim_obj.getClaimant().getTravelAgent() + "\n");
		}
		stringBuffer.append("Name : " + claim_obj.getClaimant().getFirstName() + " "
				+ claim_obj.getClaimant().getLastName() + "\n");
		stringBuffer.append("Address : " + address + "\n");
		stringBuffer.append("Email : " + claim_obj.getClaimant().getEmailAddress() + "\n");
		stringBuffer.append("Phone Number " + claim_obj.getClaimant().getTelephone() + "\n");
		if ("EUC".equals(claim_obj.getClaimType())) {
			if (claim_obj.getClaimant().getIsLeadBooker()) {
				stringBuffer.append("Claimant is Booker Yes/No : " + "Yes" + "\n");
			} else {
				stringBuffer.append("Claimant is Booker Yes/No : " + "No" + "\n");
			}
		}
		stringBuffer.append("-----------------" + "\n");
		stringBuffer.append("Payment Details" + "\n");
		stringBuffer.append("-----------------" + "\n");
		stringBuffer.append("Payment Currency Request : " + claim_obj.getCurrencyCode() + "\n");

		if (bank_obj.getBankAccountHolderName() != null) {

			stringBuffer.append("Bank Account Holder Name : "
					+ AESEncryption.decryptText(bank_obj.getBankAccountHolderName(), secKeySpec) + "\n");
		} else {
			stringBuffer.append("Bank Account Holder Name : " + null + "\n");
		}

		if (bank_obj.getIBAN() != null) {
			stringBuffer.append("Bank Code : " + AESEncryption.decryptText(bank_obj.getIBAN(), secKeySpec) + "\n");

		} else {
			stringBuffer.append("Bank Code : " + null + "\n");
		}

		if (bank_obj.getBankAccountNumber() != null) {
			stringBuffer.append("Last 4 digits of ACC : "
					+ AESEncryption.decryptText(bank_obj.getBankAccountNumber(), secKeySpec).substring(
							AESEncryption.decryptText(bank_obj.getBankAccountNumber(), secKeySpec).length() - 4)
					+ "\n");
		} else {
			stringBuffer.append("Last 4 digits of ACC : " + null + "\n");
		}
		stringBuffer.append("-----------------" + "\n");
		stringBuffer.append("Passanger Details [" + claim_obj.getPaxCount() + "]" + "\n");
		stringBuffer.append("-----------------" + "\n");
		
		for (int i = 0; i < claim_obj.getPassengers().size(); i++) {
			stringBuffer.append(claim_obj.getPassengers().get(i).getFirstName() + " "
					+ claim_obj.getPassengers().get(i).getLastName() + "\n");
		}
		
		/*for (int i = 0; i < claim_obj.getClaimLines().length; i++) {
			stringBuffer.append(claim_obj.getClaimLines()[i].getFirstName() + " "
					+ claim_obj.getClaimLines()[i].getLastName() + "\n");
		}*/
		stringBuffer.append("-----------------" + "\n");
		if ("Welfare".equals(claim_obj.getClaimType())) {
			stringBuffer.append("Expense Details [" + claim_obj.getPaxCount() + "]" + "\n");
			stringBuffer.append("-----------------" + "\n");
			for (int i = 0; i < claim_obj.getClaimLines().length; i++) {
				stringBuffer.append(claim_obj.getClaimLines()[i].getSubmittedAmount() + " : "
						+ claim_obj.getClaimLines()[i].getSubmittedCurrency() + "\n");
			}
		}

		String caseNote = stringBuffer.toString();

		return caseNote;

	}

	public static String kanaNoteUpdateOnServiceFailure(String kana_note, String case_id, Properties props)
			throws UnsupportedOperationException, SOAPException, InterruptedException, IOException, ParseException,
			InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		Map<String, Object> map;
		SOAPMessage soapRequest;
		SOAPConnection soapConnection;
		int count = 1;

		int retry_count = Integer.parseInt(props.getProperty("Kana_Retry_Count"));
		long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));
		String url = props.getProperty("kana_url");

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		soapConnection = soapConnectionFactory.createConnection();

		soapRequest = createSOAPRequest("Update", "", kana_note, null, "", null, "true", case_id, null);
		

		map = doSOAPCall(soapConnection, "Update", "", kana_note, soapRequest, url, 1, retry_count, retry_interval);

		if (!"0".equals(map.get("return_code"))) {
			if (count < retry_count) {
				logger.debug("Retrying for non 0 return code");
				Thread.sleep(retry_interval);
				doSOAPCall(soapConnection, "Update", "", kana_note, soapRequest, url, count + 1, retry_count,
						retry_interval);
			}
		}

		return null;

	}

}
