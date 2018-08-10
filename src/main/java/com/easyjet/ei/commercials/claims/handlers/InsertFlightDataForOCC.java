package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;


import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.DBUtils;
import com.easyjet.ei.commercials.claims.common.SendOCCEmail;
import com.easyjet.ei.commercials.claims.exception.EmailSendingException;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.flightinfo.CustomerFlightStatus;

public class InsertFlightDataForOCC implements WorkItemHandler {
	
	private Logger logger = Logger.getLogger(InsertFlightDataForOCC.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		
		
	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		Connection conn = null;
		Map<String, Object> out_parameters = new HashMap<String, Object>();
		try{
			Map<String, Object> parameters = arg0.getParameters();
			
			Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
			String template_name = (String) arg0.getParameter("template_name");
			CustomerFlightStatus flight_obj = null;
			if(parameters.get("flight_object") != null){
				flight_obj = (CustomerFlightStatus) arg0.getParameter("flight_object");
			}
			boolean mailSent=false;
			
			conn = DBUtils.getDBConnection();
		
			if(!flightExists(claim_obj.getFlightNumber(), claim_obj.getFlightDate(), conn)){
				
				insertRecords(claim_obj.getClaimReference(), claim_obj.getFlightNumber(), claim_obj.getFlightDate(), conn);
			}
			
			if(!emailSent(claim_obj.getFlightNumber(), claim_obj.getFlightDate(), conn)){
				mailSent = SendOCCEmail.sendEmail(template_name, claim_obj, flight_obj, "occ","","","",null);
				if(mailSent == true){
					UpdateRecords(conn,claim_obj.getFlightNumber(), claim_obj.getFlightDate());
				}
				else{
					throw new EmailSendingException("Error while sending OCC email");
				}
				logger.debug("send email..");
			}
			
		}
		catch(IOException io){		
			out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ io.getMessage());			logger.error(io);
			
		}
		catch(SQLException sql){
			out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ sql.getMessage());
			logger.error(sql);
			
		}
		catch(ClassNotFoundException c){
			out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ c.getMessage());
			logger.error(c);
			
		}
		 catch (MessagingException e) {
			 out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ e.getMessage());
				logger.error(e);
			} catch (ParseException e) {
				out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ e.getMessage());
				logger.error(e);
			}
		catch(EmailSendingException e){
			out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ e.getMessage());
			logger.error(e);
			
		}
		
		finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					out_parameters.put("error_msg", "Error while sending OCC email. Error is "+ e.getMessage());
					logger.error(e);
					
				}
			}
			arg1.completeWorkItem(arg0.getId(), out_parameters);
		}
	}
		
	
	
	private boolean flightExists(String flight_number, String flight_date, Connection conn) throws SQLException{
		
		boolean flightExists = false;		
		int count=0;
		ResultSet resultSet = null;
		
		String sql = "SELECT COUNT(1) as count FROM CLAIMS_FLIGHT_MAPPING_OCC WHERE FLIGHT_NUMBER=? AND FLIGHT_DATE=?";
		
		PreparedStatement psmnt = null;
		
		try {
			psmnt = conn.prepareStatement(sql);
			psmnt.setString(1, flight_number);
			psmnt.setString(2, flight_date);
			psmnt.execute();
			resultSet = psmnt.getResultSet();
			if (resultSet.next()) {

				count = resultSet.getInt("COUNT");
			}
			if (count > 0) {
				flightExists = true;
			} 
		} finally {
			if(psmnt != null){
				psmnt.close();
			}
			if(resultSet != null){
				resultSet.close();
			}
		}
		
		return flightExists;	
		
		
	}
	

	
	private void insertRecords(String claim_reference, String flight_number, String flight_date, Connection conn) throws SQLException{
		
		String sql = "INSERT INTO CLAIMS_FLIGHT_MAPPING_OCC (CLAIM_REFERENCE, FLIGHT_NUMBER, FLIGHT_DATE)" + " VALUES (?,?,?)";
		
		PreparedStatement psmnt = null;
		try {
			psmnt = conn.prepareStatement(sql);
			
			psmnt.setString(1, claim_reference);
			psmnt.setString(2, flight_number);
			psmnt.setString(3, flight_date);				
			psmnt.executeUpdate();
		} finally {
			if(psmnt != null){
				psmnt.close();
			}
			
		}
		
		
				
	}
	private void UpdateRecords(Connection conn, String flight_number, String flight_date) throws SQLException{
		
		String sql = "UPDATE CLAIMS_FLIGHT_MAPPING_OCC SET EMAIL_SENT_DATE= ? WHERE FLIGHT_NUMBER=? AND FLIGHT_DATE=? ";
		
		PreparedStatement psmnt = null;
		
		try {
			psmnt = conn.prepareStatement(sql);
			psmnt.setDate(1, new Date(new java.util.Date().getTime()));
			psmnt.setString(2, flight_number);
			psmnt.setString(3, flight_date);
			psmnt.executeUpdate();
		} finally {
			if(psmnt!= null){
				psmnt.close();
			}
		}
		
				
	}
	
	private boolean emailSent(String flight_number, String flight_date, Connection conn) throws SQLException{
		
		boolean isEmailSent = false;		
		
		
		String sql = "SELECT COUNT(1) count FROM CLAIMS_FLIGHT_MAPPING_OCC WHERE FLIGHT_NUMBER=? AND FLIGHT_DATE=? AND EMAIL_SENT_DATE=?";
		
		PreparedStatement psmnt = null;
		ResultSet resultSet = null;
		
		try {
			psmnt = conn.prepareStatement(sql);
			psmnt.setString(1, flight_number);
			psmnt.setString(2, flight_date);
			psmnt.setDate(3, new Date(new java.util.Date().getTime()));
			psmnt.execute();
			resultSet = psmnt.getResultSet();
			
			int count = 0;
			if (resultSet.next()) {

				count = resultSet.getInt("COUNT");
			}
			if (count > 0) {
				isEmailSent = true;
			} 
		} finally {
			if(psmnt != null){
				psmnt.close();
			}
			if(resultSet != null){
				resultSet.close();
			}
			
		}
		
		return isEmailSent;	
		
		
	}
	
	
}
