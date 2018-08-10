package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
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
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.RestUtils;
import com.easyjet.ei.commercials.claims.exception.InvalidClaimLineStatus;
import com.easyjet.ei.commercials.claims.pojo.claims.ClaimLines;
import com.easyjet.ei.commercials.claims.pojo.claims.Claims;
import com.easyjet.ei.commercials.claims.pojo.exchangerates.GetExchangeRateResponse;
import com.easyjet.ei.commercials.claims.pojo.exchangerates.QuoteDetail;

public class CalculateClaimWelfr implements WorkItemHandler {

	private static Logger logger = Logger.getLogger(CalculateClaimWelfr.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {

		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> map_params = new HashMap<>();
		
		//logger.debug("Calculating claim value ******* 1 ");
		try {
			Claims claim_obj = (Claims) arg0.getParameter("claim_obj");
			
			
			Properties props = (Properties) arg0.getParameter("prop_obj");
			
			Float claimLineAmnt = 0f;
			Float exchangeRate = 0f;

			long retry_interval = Long.parseLong(props.getProperty("Kana_Retry_Interval"));
			int rest_retry_count = Integer.parseInt(props.getProperty("Rest_Retry_Count"));

			//logger.debug("Calculating claim value ******* 2 " + eucAmount);

			

			ClaimLines[] clmLines = claim_obj.getClaimLines();

			if ("Welfare".equals(claim_obj.getClaimType())) {
				for (ClaimLines clmLine : clmLines) {
					if ("ACC".equals(clmLine.getStatus())) {

						/*
						 * if (!clmLine.getSubmittedCurrency().equals(claim_obj.
						 * getCurrencyCode())) { Commented as Payable amount
						 * updated by the Agent is with currency conversion.
						 * This code needs to be uncommented for Phase 2
						 * 
						 * for (QuoteDetail quotes : quoteList) {
						 * 
						 * if (quotes.getTargetCurrency().equals(clmLine.
						 * getSubmittedCurrency())) {
						 * 
						 * exchangeRate = Float.parseFloat(quotes.getValue());
						 * break; }
						 * 
						 * }
						 * 
						 * claimLineAmnt = claimLineAmnt +
						 * clmLine.getPayableAmount().floatValue() *
						 * exchangeRate; }
						 */ // else {
						claimLineAmnt = claimLineAmnt + clmLine.getPayableAmount().floatValue();
						// }
					} else if ("SUB".equals(clmLine.getStatus())) {
						map.put("error_msg",
								"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
										+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
						arg1.abortWorkItem(arg0.getId());
						logger.error(
								"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
										+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
						throw new InvalidClaimLineStatus(
								"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
										+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
					}

				}
			} else {
				
				
				
				logger.debug("Calculating claim value for EUC *******  ");
				int paxCount = 0;
				if ("AUTO_ACCEPTED".equals(claim_obj.getStatus())) {
					
					Float eucAmount = (Float) arg0.getParameter("eucAmount");
					
					GetExchangeRateResponse exchange_obj = (GetExchangeRateResponse) arg0.getParameter("exchange_obj");
					
					List<QuoteDetail> quoteList = exchange_obj.getQuoteDetails();
					
					RestUtils rest_call_obj = new RestUtils();
					String getRest_url = props.getProperty("Claims_Update_REST_URL");

					JSONArray array = new JSONArray();
					JSONObject dataset = new JSONObject();
					JSONArray jArray_EucClaimLines = new JSONArray();
					float eucAmountConv=0f;
					for (ClaimLines clmLine : clmLines) {
						if ("ACC".equals(clmLine.getStatus())) {

							//paxCount = paxCount + 1;
							
							for (QuoteDetail quotes : quoteList) {

								if (quotes.getTargetCurrency().equals(claim_obj.getSubmittedCurrency())) {

									exchangeRate = Float.parseFloat(quotes.getValue());
									eucAmountConv = eucAmount * exchangeRate;
									logger.debug("Currency Matched " + quotes.getTargetCurrency() + " : exchange rate "
											+ exchangeRate);
									break;
								}
								else{
									eucAmountConv = eucAmount;
								}

							}

							JSONObject jobj = new JSONObject();
							jobj.put("PayableAmount", String.format("%.2f", eucAmountConv));
							jobj.put("EucClaimLineId", clmLine.getClaimLineId());
							jArray_EucClaimLines.add(jobj);
							
							claimLineAmnt = claimLineAmnt + eucAmountConv;

						} else if ("SUB".equals(clmLine.getStatus())) {
							map.put("error_msg",
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
							arg1.abortWorkItem(arg0.getId());
							logger.error(
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
							throw new InvalidClaimLineStatus(
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
						}

					}
					dataset.put("ClaimReference", claim_obj.getClaimReference());
					dataset.put("EucClaimLines", jArray_EucClaimLines);
					array.add(dataset);

					Map<String, Object> rest_response = saveAmountInClaimsDB(rest_call_obj, getRest_url, array, props,
							1, rest_retry_count);

					if ((Integer) rest_response.get("Status") >= 200 && (Integer) rest_response.get("Status") < 300) {

						logger.debug("Payble amount for all the claimlines have udated successfully...............");
					} else {
						logger.debug(
								"Error while updating Payble amount for all the claimlines. Update Claims service returns [ "
										+ rest_response.get("Status") + " ]");
						map.put("error_msg",
								"Error while updating Payble amount for all the claimlines. Update Claims service returns [ "
										+ rest_response.get("Status") + " ]");
					}

					logger.debug("Calculating claim value for EUC *******  eucAmount  = " + eucAmount);
					/*claimLineAmnt = eucAmount * paxCount;
					for (QuoteDetail quotes : quoteList) {

						if (quotes.getTargetCurrency().equals(claim_obj.getCurrencyCode())) {

							exchangeRate = Float.parseFloat(quotes.getValue());
							claimLineAmnt = claimLineAmnt * exchangeRate;
							logger.debug("Currency Matched " + quotes.getTargetCurrency() + " : exchange rate "
									+ exchangeRate);
							break;
						}

					}*/

					JSONArray array1 = new JSONArray();
					JSONObject dataset1 = new JSONObject();

					dataset1.put("ClaimReference", claim_obj.getClaimReference());
					dataset1.put("TotalPayableAmount", String.format("%.2f", claimLineAmnt));
					array1.add(dataset1);

					Map<String, Object> rest_response_1 = saveAmountInClaimsDB(rest_call_obj, getRest_url, array1, props,
							1, rest_retry_count);

					if ((Integer) rest_response_1.get("Status") >= 200
							&& (Integer) rest_response_1.get("Status") < 300) {

						logger.debug("TotalPayableAmount amount udated successfully...............");
					} else {
						logger.debug("Error while updating TotalPayableAmount. Update Claims service returns [ "
								+ rest_response_1.get("Status") + " ]");
						map.put("error_msg", "Error while updating TotalPayableAmount. Update Claims service returns [ "
								+ rest_response_1.get("Status") + " ]");
					}

				} else {
					for (ClaimLines clmLine : clmLines) {
						if ("ACC".equals(clmLine.getStatus())) {
							logger.debug("clmLine.getPayableAmount().floatValue()  " + clmLine.getPayableAmount().floatValue());
							claimLineAmnt = clmLine.getPayableAmount().floatValue() + claimLineAmnt;
							logger.debug("claimLineAmnt inside loop " + claimLineAmnt);
						}
						else if ("SUB".equals(clmLine.getStatus())) {
							map.put("error_msg",
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
							arg1.abortWorkItem(arg0.getId());
							logger.error(
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
							throw new InvalidClaimLineStatus(
									"One of the Claim lines for Welfare claim is not Accepted or Declined. Claim line type: "
											+ clmLine.getLineType() + " , Status: " + clmLine.getStatus());
						}
					}
					
				}
			}

			logger.debug("Calculating claim value for EUC *******  claimAmntEuroConv  = " + claimLineAmnt);
			map.put("claimAmntEuroConv", claimLineAmnt);
			arg1.completeWorkItem(arg0.getId(), map);
		} catch (NumberFormatException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());
		} catch (InvalidClaimLineStatus e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		} catch (ParseException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}
		catch (InterruptedException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (SOAPException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (IOException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (IllegalAccessException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (InstantiationException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (BadPaddingException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (IllegalBlockSizeException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (NoSuchPaddingException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (NoSuchAlgorithmException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (ClassNotFoundException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (InvalidKeyException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}catch (NullPointerException e) {
			map.put("error_msg", "Error while calculating credit amount. Error is: " + e.getMessage());
			logger.error(e.getMessage());

		}
	}

	private Map<String, Object> saveAmountInClaimsDB(RestUtils rest_call_obj, String getDoc_url, JSONArray json_obj,
			Properties props, int count, int retry_count)
			throws InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InstantiationException, IllegalAccessException, IOException,
			UnsupportedOperationException, SOAPException, InterruptedException, ParseException {

		Map<String, Object> rest_response = rest_call_obj.restCall(getDoc_url,
				null, "PUT",
				json_obj.toJSONString(), "Update Claim");

		if ((Integer) rest_response.get("Status") >= 200 && (Integer) rest_response.get("Status") < 300) {

		} else if ((Integer) rest_response.get("Status") == 404) {

		} else {
			if (count < retry_count) {
				saveAmountInClaimsDB(rest_call_obj, getDoc_url, json_obj, props, count + 1, retry_count);
			}
		}
		return rest_response;
	}

}
