package com.easyjet.ei.commercials.claims.common;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
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

import org.apache.log4j.Logger;



public class DynamicEmail{
	
	private static Logger logger = Logger.getLogger(DynamicEmail.class);

	public void sendEmail(String toEmail, String fromEmail, String template_name) {
		
		try{
			
			Properties smtpProp = ReadFromPropertyFile.readfromPropertyFile();
			
			Properties props = System.getProperties();
			
			System.out.println(smtpProp);
			props.put("mail.smtp.host",smtpProp.getProperty("mail.smtp.host"));
			props.put("mail.smtp.auth",smtpProp.getProperty("mail.smtp.auth"));
			props.put("mail.debug",smtpProp.getProperty("mail.debug"));
			props.put("mail.smtp.port",Integer.parseInt(smtpProp.getProperty("mail.smtp.port")));
			props.put("mail.smtp.socketFactory.port",Integer.parseInt(smtpProp.getProperty("mail.smtp.socketFactory.port")));
			props.put("mail.smtp.starttls.enable",smtpProp.getProperty("mail.smtp.starttls.enable"));
			props.put("mail.transport.protocol",smtpProp.getProperty("mail.transport.protocol"));			
			props.setProperty("mail.password", "password@123");			

			Session mailSession = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
							return new javax.mail.PasswordAuthentication(
									"Sender.Test@tcsdev.com", "password@123");

						}
					});

				Transport transport = mailSession.getTransport();
				MimeMessage message = new MimeMessage(mailSession);			// Create a default MimeMessage object.
				message.setFrom(new InternetAddress("Sender.Test@tcsdev.com"));
				String[] tomail = new String[] { "Receiver.Test@tcsdev.com" };

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						tomail[0]));

				
				message.setSubject("Test Subjet");
				
				
				
				BodyPart messageBodyPart1 = new MimeBodyPart();  
			    messageBodyPart1.setText("This is message body");  
			    
			    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
			    
			    String filename = System.getenv("JAVA_HOME")+"\\cvsmail.properties";
			    DataSource source = new FileDataSource(filename);  
			    messageBodyPart2.setDataHandler(new DataHandler(source));  
			    messageBodyPart2.setFileName(filename);  
			    
			    Multipart multipart = new MimeMultipart();  
			    multipart.addBodyPart(messageBodyPart1);  
			    multipart.addBodyPart(messageBodyPart2);  
			  
			    message.setContent(multipart);  
				
				transport.connect();
				transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
				transport.close();
				System.out.println("Sent message successfully....");
			
		}catch(IOException e){
			logger.error(e);
		}catch(NoSuchElementException e){
			logger.error(e);
		}catch(AddressException e){
			logger.error(e);
		}catch(MessagingException e){
			logger.error(e);
		}
			
		
	
	}

}
