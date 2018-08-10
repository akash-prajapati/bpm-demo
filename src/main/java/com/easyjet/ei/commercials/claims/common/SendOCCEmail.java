package com.easyjet.ei.commercials.claims.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.flightinfo.CustomerFlightStatus;


public class SendOCCEmail implements WorkItemHandler {

	private static Logger logger = Logger.getLogger(SendOCCEmail.class);
	
	/*public static void main(String args[]) throws JAXBException, IOException, AddressException, MessagingException, ParseException{
		
		
	}*/


	public static boolean sendEmail(String email_template_name, Claims claim_obj, CustomerFlightStatus flight_obj, String emailType, String exception, String processName, String process_id, String claim_reference) throws IOException, AddressException, MessagingException, ParseException {
		boolean mailSent;
		 
		logger.debug("send email triggered.....");
		Transport transport = null;
			try {
				Properties props = System.getProperties();
				Properties prop1 = ReadFromPropertyFile.readfromPropertyFile();
				String fromMail;
				String toMail;
				if (!"itdc".equalsIgnoreCase(emailType)) {
					fromMail = prop1.getProperty("occ_from_email");
					toMail = prop1.getProperty("occ_to_email");
				} else {
					fromMail = prop1.getProperty("itsd_from_email");
					toMail = prop1.getProperty("itsd_to_email");
				}
				//System.out.println(prop1);
				props.put("mail.smtp.host", prop1.getProperty("mail.smtp.host"));
				props.put("mail.smtp.auth", prop1.getProperty("mail.smtp.auth"));
				props.put("mail.debug", prop1.getProperty("mail.debug"));
				props.put("mail.smtp.port", Integer.parseInt(prop1.getProperty("mail.smtp.port")));
				props.put("mail.smtp.socketFactory.port",
						Integer.parseInt(prop1.getProperty("mail.smtp.socketFactory.port")));
				props.put("mail.smtp.starttls.enable", prop1.getProperty("mail.smtp.starttls.enable"));
				props.put("mail.transport.protocol", prop1.getProperty("mail.transport.protocol"));
				props.setProperty("mail.password", prop1.getProperty("mail.password"));
				Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication("potadeashish@gmail.com", "Change@2011");

					}
				});
				transport = mailSession.getTransport();
				MimeMessage message = new MimeMessage(mailSession); // Create a
				
				// default
				// MimeMessage
				// object.
				message.setFrom(new InternetAddress(fromMail));
				//String[] tomail = new String[] {toMail};
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
				String email_template_path = System.getenv("JBOSS_HOME") + "/email_templates";
				Map<String, Object> map = getStringTemplate(email_template_name + ".txt", email_template_path);
				String mailBody = map.get("html_content").toString();
				String subject = map.get("subject").toString();
				logger.info("Email Subject : " + subject);
				message.setSubject(subject);
				VelocityContext context = new VelocityContext();
				if (!"itdc".equalsIgnoreCase(emailType)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-YYYY");
					String flight_date = format1.format(format.parse(claim_obj.getFlightDate()));

					context.put("Flight_date", flight_date);
					
					context.put("Flight_Number", claim_obj.getFlightNumber());
					String disruption_classification = null;

					if (prop1.getProperty("OCC_Flight_Missing_Classification_Data").equals(email_template_name)) {

						if (flight_obj.getFlights().get(0).getPostFlight().size() > 1) {

							for (int i = 0; i < flight_obj.getFlights().get(0).getPostFlight().size(); i++) {
								if (flight_obj.getFlights().get(0).getPostFlight().get(i).getDisruptionDetails()
										.getDisruptionRecordType().equals(prop1.getProperty("Impact_DELAY"))) {
									disruption_classification = flight_obj.getFlights().get(0).getPostFlight().get(i)
											.getDisruptionDetails().getPrimaryCauseCode();
									break;
								}
							}
						} else {

							disruption_classification = flight_obj.getFlights().get(0).getPostFlight().get(0)
									.getDisruptionDetails().getPrimaryCauseCode();
						}

						context.put("Disruption_Classification", disruption_classification);

					} else if (prop1.getProperty("OCC_Flight_Unexpected_Disruption_Type").equals(email_template_name)) {

						disruption_classification = flight_obj.getFlights().get(0).getPostFlight().get(0)
								.getDisruptionDetails().getPrimaryCauseCode();
						context.put("Disruption_Classification", disruption_classification);
					}

				} else {
					logger.debug("for ITSD ......");					
					context.put("Claim_Reference", claim_reference);					
					context.put("Process_Name", processName);
					context.put("Process_Id", process_id);
					context.put("Exception", exception);
					context.put("Log_Path", prop1.getProperty("logPath"));
				}
				// Create the Writer you would use as the output
				StringWriter writer = new StringWriter();
				// Evaluate your text entry
				//System.out.println("mailBody   " +  mailBody);
				Velocity.evaluate(context, writer, "", mailBody);
				logger.debug("Mail Body " + writer.toString());
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(writer.toString(), "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				message.setContent(multipart);
				transport.connect();
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
				
				logger.debug("Sent message successfully....");
			} finally {
				if(transport != null){
					transport.close();
				}
			}
			mailSent = true;
		
		
		return mailSent;
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		

	}

	private static Map<String, Object> getStringTemplate(String email_template_name, String email_template_path) throws IOException {
		BufferedReader br = null;
		FileReader fr = null;
		String html_content = null;
		StringBuilder srStringBuilder = new StringBuilder();
		String subject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			fr = new FileReader(email_template_path + "/" + email_template_name);
			br = new BufferedReader(fr);

			String sCurrentLine;
			int counter = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				if (counter == 0) {
					subject = sCurrentLine;
				} else {
					srStringBuilder.append(sCurrentLine);
				}
				counter++;
			}
			html_content = srStringBuilder.toString();
			
			
			map.put("subject", subject);
			map.put("html_content", html_content);
			

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				logger.error(ex);
			

			}
		}
		return map;
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		

	}

}
