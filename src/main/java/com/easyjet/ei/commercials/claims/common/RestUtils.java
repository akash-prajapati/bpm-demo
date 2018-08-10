package com.easyjet.ei.commercials.claims.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtils implements WorkItemHandler {

	private static Logger logger = Logger.getLogger(RestUtils.class);

	private ClassLoader classLoader = RestUtils.class.getClassLoader();

	public RestUtils() {
	}

	/*
	 * public RestUtils(ClassLoader classLoader) { this.classLoader =
	 * this.getClass().getClassLoader(); }
	 */

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		Map<String, Object> result_map = null;
		String serviceName = null;
		
		try {

			logger.debug("********** Rest call start **********");

			Map<String, Object> map_params = arg0.getParameters();

			String method = (String) arg0.getParameter("Method");
			String url = (String) arg0.getParameter("Url");

			String content = null;
			JSONObject content_json = null;
			String entityClass = null;

			if (!"GET".equals(method)) {

				if (arg0.getParameter("Content") instanceof JSONObject)
					content_json = (JSONObject) arg0.getParameter("Content");
				else
					content = (String) arg0.getParameter("Content");
			}

			
			if (map_params.get("service_name") != null) {
				serviceName = (String) arg0.getParameter("service_name");
			}

			if ("GET".equals(method) || (serviceName != null && "accertify".equalsIgnoreCase(serviceName))) {

				entityClass = (String) arg0.getParameter("ResultClass");
			}

			

			if (!"GET".equals(method)) {
				result_map = restCall(url, entityClass, method,
						(content_json == null) ? content : content_json.toJSONString(), serviceName);
			} else {
				result_map = restCall(url, entityClass, method, null, serviceName);
			}
			//logger.debug("result_map " + result_map);

			

		} 
		
	/*	catch (ClassNotFoundException |  InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InstantiationException
				| IllegalAccessException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}*/
		
		catch (ClassNotFoundException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch (  InvalidKeyException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch ( NoSuchAlgorithmException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch ( NoSuchPaddingException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch ( IllegalBlockSizeException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch ( BadPaddingException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch ( InstantiationException e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		catch (IllegalAccessException  e) {
			
			if(result_map == null){
				result_map = new HashMap<String, Object>();
			}
			result_map.put("Status", -1);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(e);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + e.toString());
		}
		
		
		catch(IOException  i){
			if(result_map == null){
				result_map = new HashMap<>();
			}
			result_map.put("Status", 503);
			result_map.put("StatusMsg", "Error while calling rest service "+ serviceName + ". Error is: " + i.toString());
			
			//logger.debug("result_map in catch" + result_map);
			
			logger.error(i);
			logger.error("Error while calling rest service "+ serviceName + ". Error is: " + i.toString());
		}
		
		finally{
			//logger.debug("result_map in finally" + result_map);
			arg1.completeWorkItem(arg0.getId(), result_map);
			
		}
		
	}

	public Map<String, Object> restCall(String url, String entity, String method, String body, String serviceName)
			throws ClassNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InstantiationException,
			IllegalAccessException, SocketException, SocketTimeoutException{

		Map<String, Object> map = new HashMap<String, Object>();
		HttpURLConnection conn = null;
		DataOutputStream wr = null;
		try {
			URL rest_url = new URL(url);
			conn = (HttpURLConnection) rest_url.openConnection();
			conn.setReadTimeout(120000);
			logger.debug("Servive [" + serviceName + "] URL [" + url + "] Body [" + body + "]");
			conn.setRequestMethod(method);
			if (("accertify").equalsIgnoreCase(serviceName)) {

				Properties props = ReadFromPropertyFile.readfromPropertyFile();
				String user = props.getProperty("accert_user");
				String pwd = props.getProperty("accert_pwd");
				//System.out.println("pwd  " + pwd);
				String secKey = props.getProperty("secKey");
				//logger.debug("Secret key  " + Base64.getDecoder().decode(secKey.getBytes()));
				SecretKeySpec secKeySpec = AESEncryption.getAesKey(Base64.getDecoder().decode(secKey.getBytes()));

				String authorization = user + ":" + AESEncryption.decryptText(pwd, secKeySpec);
				//System.out.println("authorization  " + authorization);
				String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes("UTF-8"));
				conn.setRequestProperty("Authorization", encodedAuth);

			}
			if (!("accertify").equalsIgnoreCase(serviceName)) {
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("X-Client-Transaction-Id", UUID.randomUUID().toString());
				conn.setRequestProperty("X-Client-ID", "BPMS");
				conn.setRequestProperty("X-Channel-ID", "Claims Automation");
				conn.setRequestProperty("X-SubChannel-ID", "Claims Automation");
			}
			conn.setRequestProperty("Content-Type", "application/json");
			if (!"GET".equals(method)) {

				conn.setDoOutput(true);
				wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(body);
				wr.flush();
				wr.close();

			}
			
			String response = null;
			Object res_obj = null;
			StringBuilder stringBuilder = new StringBuilder();
			
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				System.out.println("testing 1......  ");

				while ((output = br.readLine()) != null) {
					stringBuilder.append(output);
				}
				
				System.out.println("testing 2......  ");
				response = stringBuilder.toString();
				System.out.println("testing 3......  ");
				//stringBuilder.delete(0, stringBuilder.length());
				//stringBuilder.setLength(0);
				
				if (serviceName != null) {
					if (!serviceName.equalsIgnoreCase("getReceipts")) {
						logger.debug("****** REST call response ******** : " + response);
					}
				}

				//logger.debug("****** REST call response ******** : " + response);

				if ("GET".equals(method) || (serviceName != null && "accertify".equalsIgnoreCase(serviceName))
						|| (serviceName != null && "getReceipts".equalsIgnoreCase(serviceName))) {

					Class<?> entity_obj = classLoader.loadClass(entity);

					ObjectMapper mapper = new ObjectMapper();

					res_obj = mapper.readValue(response, entity_obj.newInstance().getClass());
				}

				if (res_obj != null) {
					map.put("Result", res_obj);
				} else {
					map.put("Result", conn.getResponseMessage());
				}

				map.put("StatusMsg", "request to endpoint " + rest_url + " successfully completed ");

			} else {
				
				BufferedReader br_error = new BufferedReader(new InputStreamReader((conn.getErrorStream())));

				String output_error;				

				while ((output_error = br_error.readLine()) != null) {
					stringBuilder.append(output_error);
				}
				response = stringBuilder.toString();
				
				//stringBuilder.delete(0, stringBuilder.length());
				//stringBuilder.setLength(0);

				logger.error("Unsuccessful response from REST server >> status: ["+ conn.getResponseCode()
						+ "], endpoint: [" + rest_url + "], Response:[" + conn.getResponseMessage() + "], Message: [" + response + "]");
				
				map.put("StatusMsg", "Error while trigerring end point URL [" + rest_url + "] Response code :  ["
						+ conn.getResponseCode() + "] , Response:[" + conn.getResponseMessage() + "], Message: [" + response + "]");
			}
			map.put("Status", conn.getResponseCode());
			
			logger.debug("********** Rest call end **********");
		} finally {
			if(conn != null){
				conn.disconnect();
			}
			if(wr!=null){
				wr.flush();
				wr.close();
			}
			
			
		}
		return map;

	}
	
	public static void main(String args[]){
		
		try {
			System.out.println(UUID.randomUUID().toString());
			Map<String, Object> map = new HashMap<>();
			for(int i=0 ; i < 10 ; i++ ){
				map = new RestUtils().restCall("http://172.26.168.139/commercial-cpm/v1/claims-receipt/getreceipt", "com.easyjet.ei.commercial.claims.pojo.getdocumentfroms3.GetDocumentLink", "POST" ,"{\"documentLink\": \"02e26573d5f5407a9ae8bbdabdc7a976\"}", "getReceipts");
				//System.out.println(map);
				System.out.println("************ " + map.get("StatusMsg") + " *****************");
			}
			
		} catch (InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | InstantiationException | IllegalAccessException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
