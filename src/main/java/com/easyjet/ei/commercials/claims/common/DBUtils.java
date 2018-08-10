package com.easyjet.ei.commercials.claims.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

	public static Connection getDBConnection() throws IOException, ClassNotFoundException, SQLException{
		
		Properties props = ReadFromPropertyFile.readfromPropertyFile();			
		System.out.println(props.getProperty("DB_URL"));
		Connection conn = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("USER"), props.getProperty("PASS"));
		
		return conn;
		
	}
	public static void main(String args[]){
		
		try {
			getDBConnection();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
