package com.easyjet.ei.commercials.claims.handlers;

/*import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;*/
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class CreditPayment implements WorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}
	
	/*private static final Logger logger = Logger.getLogger(CreditPayment.class);
	

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		SOAPConnection soapConnection = null;
		String kana_operation = null;
		Map<String, Object> map = new HashMap<>();
		SOAPMessage soapRequest = null;
		
		// Logger log = Logger.getLogger(KanaCreateUpdate.class);

		try {

			Claims claims_obj = new Claims();
			claims_obj = (Claims) arg0.getParameter("claim_obj");
			
			//logger.debug("stage  = " + stage + "kana_operation = " + kana_operation + "claims_obj = " + claims_obj);
			
			Properties props = ReadFromPropertyFile.readfromPropertyFile();

			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = props.getProperty("Credit_Payment_url");			
			int retry_count = Integer.parseInt(props.getProperty("Kana_Retry_Count"));
			long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));
			
			soapRequest = createSOAPRequest(claims_obj);
			
			map = doSOAPCall(soapConnection, soapRequest, url, 1, retry_count,
					retry_interval);

			//logger.info("return map from KANA call is --  " + map);

			

			//arg1.completeWorkItem(arg0.getId(), map);

		} catch (IOException e) {
			logger.error(e);
			// System.err.println("Error occurred while sending SOAP Request to
			// Server");
			// //e.printStackTrace();
			
				map.put("return_message", "Error while preparing request for Credit Payment " + " Error is : " + e.toString());
			
		} 
		catch (SOAPException e) {
			logger.error(e);
			// System.err.println("Error occurred while sending SOAP Request to
			// Server");
			// //e.printStackTrace();
			
				map.put("return_message", "Error while preparing request for Credit Payment " + " Error is : " + e.toString());
			
		} finally {
			try {
				if(soapConnection != null) {
					soapConnection.close();
				}
				// count = 0;
			} catch (SOAPException e) {
				
				logger.error(e);
				map.replace("return_message",
						"Error while closing SOAP connetion for Credit Payment " + kana_operation + "Error is : " + e.toString());
			}
			
		}
		arg1.completeWorkItem(arg0.getId(), map);
	}

	public static Map<String, Object> doSOAPCall(SOAPConnection soapConnection, SOAPMessage soapRequest, String url, int count, int retry_count,
			long retry_interval) {

		Map<String, Object> res_map = new HashMap<>();
		
		SOAPMessage soapResponse = null;
		OutputStream osReq = new ByteArrayOutputStream();
		OutputStream osRes = new ByteArrayOutputStream();
		
		try {
			//String return_code = "";
			
			soapRequest.writeTo(osReq);
			logger.debug("\nRequest SOAP Message = ");
			logger.debug(osReq.toString());
			
			
			soapResponse = soapConnection.call(soapRequest, url);
			
			soapResponse.writeTo(osRes);
			logger.debug("\nResponse SOAP Message = ");
			logger.debug(osRes.toString());
			
			
			
			
			//printSOAPResponse(soapResponse);
			// Fetch Resp code and msg
			res_map = checkReturnCode(soapResponse);
			
			logger.debug("res_map  " +  res_map);

			
			 * return_code = (String) res_map.get("return_code");
			 * 
			 * if(return_code != null){ if(!return_code.equals("0.0") && count
			 * <5){ doSOAPCall(soapConnection, kana_operation, kana_state,
			 * kana_note, claim_obj, url,count+1); } else{
			 * if(!return_code.equals("0.0")){ res_map.replace("return_message",
			 * "Retry for KANA "+ kana_operation+
			 * " exhusted. Hence exiting process "); } } }
			 

		} catch (SOAPException e) {
			
			logger.error(e);
			try {
				if (count < retry_count) {
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, soapRequest, url, count + 1,
							retry_count, retry_interval);					
					res_map.put("return_code", "Error");
					res_map.put("return_message",
							"Error while retrying for Credit Payment Service " +  ". Error is :" + e.toString());
					res_map.put("request", osReq.toString());
				}
			} catch (InterruptedException e1) {
				logger.error(e1);
				res_map.put("return_code", "Error");
				res_map.put("return_message",
						"Error while retrying for Credit Payment Service " +  ". Error is :" + e1.toString());
				res_map.put("request", osReq.toString());
				// e1.printStackTrace();
			}
			 //e.printStackTrace();
			res_map.replace("return_message",
					"Error while executing SOAP call for KANA " + kana_operation + "Error is : " + e.toString() + " for Request " + soapRequest.toString());
		} catch (Exception e) {
			logger.error(e);
			try {
				if (count < retry_count) {
					Thread.sleep(retry_interval);
					doSOAPCall(soapConnection, soapRequest, url, count + 1,
							retry_count, retry_interval);
					res_map.put("return_code", "Error");
					res_map.put("return_message",
							"Error while retrying for Credit Payment Service " +  ". Error is :" + e.toString());
					res_map.put("request", osReq.toString());
				}
			} catch (InterruptedException e1) {
				logger.error(e1);
				res_map.put("return_message",
						"Error while retrying for Credit Payment Service " +  ". Error is :" + e1.toString());
				res_map.put("request", osReq.toString());
				
				// e1.printStackTrace();
			}
			res_map.replace("return_message",
					"Error while executing SOAP call for KANA " + kana_operation + "Error is : " + e.toString()  + " for Request " + soapRequest.toString());
			
			 //e.printStackTrace();
			//return res_map;
		}
		finally{
			
			try {
				if(osReq != null) {
					osReq.close();
				}
				if(osRes != null) {
					osRes.close();
				}
			} catch (IOException e) {
				logger.error(e);
				// TODO Auto-generated catch block
				////e.printStackTrace();
				res_map.put("return_message",
						"Error while retrying for Credit Payment Service " + ". Error is :" + e.toString());
				res_map.put("request", osReq.toString());
			}
			
		}
		//System.out.println(res_map);
		return res_map;

	}

	public static Map<String, Object> checkReturnCode(SOAPMessage soapResponse) {

		// System.out.println("Inside checkReturnCode");
		Map<String, Object> map = new HashMap<>();
		String return_message="";
		String message_code="";
		String payment_id="";
		
		try {

			String return_code = null;
			//String return_message = null;

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8");

			// System.out.println("\nmsg "+message);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(message));
			

			Document xmlDoc = db.parse(is);
			
			NodeList nodeList = xmlDoc.getElementsByTagName("tem:CreditPaymentResult");
				
				// System.out.println(nodeList.getLength());

				for (int temp = 0; temp < nodeList.getLength(); temp++) {
					Node nNode = nodeList.item(temp);
					
					

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						

						return_message = eElement.getElementsByTagName("eas:Message").item(0).getTextContent();
						message_code = eElement.getElementsByTagName("eas:MessageCode").item(0).getTextContent();
						payment_id = eElement.getElementsByTagName("eas:PaymentId").item(0).getTextContent();
						return_code = eElement.getElementsByTagName("eas:ResultCode").item(0).getTextContent();
						// System.out.println(return_code);
					}
					// S//ystem.out.println("*****");
				}


			// System.out.println("return_code: "+return_code);
			map.put("return_code", return_code);
			map.put("return_message", return_message);
			map.put("payment_id", payment_id);
			map.put("message_code", message_code);
			
		} catch (NumberFormatException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response "  + ". Error is : " + e.getMessage());
			
			 //e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " + ". Error is : " + e.getMessage());
			
			 //e.printStackTrace();
		} catch (DOMException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " + ". Error is : " + e.getMessage());
			
			 //e.printStackTrace();
		} catch (SOAPException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " +  ". Error is : " + e.getMessage());
			
			 //e.printStackTrace();
		} catch (IOException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " +  ". Error is : " + e.getMessage());
			
			 //e.printStackTrace();
		} catch (ParserConfigurationException e) {
			logger.error(e);
			 //e.printStackTrace();
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " +  ". Error is : " + e.getMessage());
		} catch (SAXException e) {
			logger.error(e);
			map.put("return_code", "Error");
			map.put("return_message",
					"Error while retrieving values from Credit Payment response " + ". Error is : " + e.getMessage());
			 //e.printStackTrace();
		}
		return map;
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		

	}

	*//**
	 * Starting point for the SAAJ - SOAP Client Testing
	 *//*

	public static SOAPMessage createSOAPRequest(Claims claim_obj) throws SOAPException, IOException{
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String namespace1 = ReadFromPropertyFile.readfromPropertyFile().getProperty("Credit_Payment_Namespace1");
		String namespace2 = ReadFromPropertyFile.readfromPropertyFile().getProperty("Credit_Payment_Namespace2");

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("eas", namespace1);
		envelope.addNamespaceDeclaration("tem", namespace2);

		// SOAP Body
		
			SOAPBody soapBody = envelope.getBody();
			SOAPElement soapBodyElem = soapBody.addChildElement("CreditPayment", "tem");
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("request", "tem");
			SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("Amount", "eas");
			SOAPElement soapBodyElem3 = soapBodyElem2.addChildElement("CurrencyCode", "eas");
			SOAPElement soapBodyElem4 = soapBodyElem2.addChildElement("Value", "eas");
			SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("Channel", "eas");
			SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("ContextData", "eas");
			SOAPElement soapBodyElem7 = soapBodyElem6.addChildElement("ClientComputerName", "eas");
			SOAPElement soapBodyElem8 = soapBodyElem6.addChildElement("ClientIpAddress", "eas");
			SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("PaymentDetail", "eas");
			SOAPElement soapBodyElem10 = soapBodyElem9.addChildElement("CustomerReference", "eas");
			SOAPElement soapBodyElem11 = soapBodyElem9.addChildElement("PaymentMethod", "eas");
			SOAPElement soapBodyElem12 = soapBodyElem9.addChildElement("SavedPaymentMethodReference", "eas");
			SOAPElement soapBodyElem13 = soapBodyElem1.addChildElement("Reference", "eas");

			soapBodyElem3.addTextNode(claim_obj.getCurrencyCode());
			soapBodyElem4.addTextNode(claim_obj.getTotalPayableAmount());
			
			soapBodyElem5.addTextNode("CallCentre");
			
			soapBodyElem7.addTextNode("Host");
			soapBodyElem8.addTextNode("IP");
			
			soapBodyElem10.addTextNode(claim_obj.getClaimReference());
			soapBodyElem11.addTextNode("BankAccount");
			soapBodyElem12.addTextNode(claim_obj.getPaymentMethodReference());
			soapBodyElem13.addTextNode(claim_obj.getBookingReference());

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace2 + "CreditPayment");

		

		soapMessage.saveChanges();
		 Print the request message 
		//printSOAPRequest(soapMessage);
		
		
		
		
		return soapMessage;
	}*/

	
	/*public static void main(String args[]) {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			Claims claims = new Claims();
			claims.setKanaCaseReference("123");

			// Send SOAP Message to SOAP Server
			// String url =
			// ReadFromPropertyFile.readfromPropertyFile().getProperty("kana_url");
			//doSOAPCall(soapConnection, "Update", "sadsad", "asdsadsa", claims, "http://LS5JV2:8081/update", 1, 5,					60000);
			// SOAPMessage soapResponse =
			// soapConnection.call(KanaCreateUpdate.createSOAPRequest("Update","asdas","sdsad",claims),"http://LS5JV2:8081/update");

			// Process the SOAP Response
			// printSOAPResponse(soapResponse);

			soapConnection.close();
		} catch (Exception e) {
			logger.error(e);
			System.err.println("Error occurred while sending SOAP Request to Server");
			//e.printStackTrace();
		}
	}

*/	
	
	/*public static void test(Claims claims_obj) throws Exception{
		
		SOAPConnection soapConnection = null;
		String kana_operation = null;
		Map<String, Object> map = new HashMap<>();
		SOAPMessage soapRequest = null;
		
		Properties props = ReadFromPropertyFile.readfromPropertyFile();

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = props.getProperty("Credit_Payment_url");
		int retry_count = Integer.valueOf(props.getProperty("Kana_Retry_Count"));
		long retry_interval = Long.valueOf(props.getProperty("Kana_Retry_Interval"));
		
		soapRequest = createSOAPRequest(claims_obj);
		
		map = doSOAPCall(soapConnection, soapRequest, url, 1, retry_count,
				retry_interval);
	}*/

}
