package com.easyjet.ei.commercials.claims.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.easyjet.ei.commercials.claims.common.DBUtils;

public class ClaimsBpmsMappingTableUpdate implements WorkItemHandler {
	private Logger logger = Logger.getLogger(ClaimsBpmsMappingTableUpdate.class);

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {

	}

	@Override
	public void executeWorkItem(WorkItem arg0, WorkItemManager arg1) {
		Map<String, Object> params = arg0.getParameters();

		String operation = (String) arg0.getParameter("operation");
		String claim_reference = (String) arg0.getParameter("claim_reference");
		long processInstaceId_main = 0;
		if (params.get("processInstaceId_main") != null) {
			processInstaceId_main = (Long) arg0.getParameter("processInstaceId_main");
		}
		String claimState_old = "";
		if (params.get("claimState_old") != null) {
			claimState_old = (String) arg0.getParameter("claimState_old");
		}
		String claimState_new = "";
		if (params.get("claimState_new") != null) {
			claimState_new = (String) arg0.getParameter("claimState_new");
		}
		String signal_name = "";
		if (params.get("signal_name") != null) {
			signal_name = (String) arg0.getParameter("signal_name");
		}
		String payment_id = "";
		if (params.get("payment_id") != null) {
			payment_id = (String) arg0.getParameter("payment_id");
		}
		String error_msg = "";

		HashMap<String, Object> map_obj = new HashMap<String, Object>();

		try {
			Connection conn = DBUtils.getDBConnection();
			logger.debug("Preaparing for updating claim referenc mapping with process instance " + claim_reference);
			if ("ADD".equals(operation)) {
				String sql_add = "INSERT INTO CLAIMS_BPMS_MAPPING_TABLE  " + "VALUES (?,?,?,?,?,?,?)";
				PreparedStatement psmnt_add = conn.prepareStatement(sql_add);
				psmnt_add.setString(1, claim_reference);
				psmnt_add.setString(2, payment_id);
				psmnt_add.setLong(3, processInstaceId_main);
				psmnt_add.setString(4, signal_name);
				psmnt_add.setString(5, claimState_old);
				psmnt_add.setString(6, claimState_new);
				psmnt_add.setString(7, "N");
				psmnt_add.executeUpdate();
			} else {
				logger.debug("Duplicate check ..... " + claim_reference + "--" + processInstaceId_main + "--"
						+ claimState_old + "--" + claimState_new);
				if (checkDuplicate(conn, claim_reference, processInstaceId_main, claimState_old, claimState_new)) {
					logger.debug("Duplicate .....");
					error_msg = "Duplicate";
					map_obj.put("error_msg", error_msg);
				}
				String sql_update = "UPDATE CLAIMS_BPMS_MAPPING_TABLE SET PROCESSED =?, CLAIMSTATE_NEW=?"
						+ " WHERE CLAIM_REFERENCE=? AND PROCESSED=?";
				PreparedStatement psmnt_update = conn.prepareStatement(sql_update);
				psmnt_update.setString(1, "Y");
				psmnt_update.setString(2, claimState_new);
				psmnt_update.setString(3, claim_reference);
				psmnt_update.setString(4, "N");
				psmnt_update.executeUpdate();
			}
			logger.debug("Claim reference and process instance mapping updated for claim reference " + claim_reference
					+ " and process instance id " + processInstaceId_main);
		} catch (SQLException se) {
			logger.error(se);
			error_msg = "Error while inserting or updating data into CLAIMS_BPMS_MAPPING_TABLE. Error is "
					+ se.toString();
			map_obj.put("error_msg", error_msg);
		} catch (IOException e) {
			logger.error(e);

			error_msg = "Error while inserting or updating data into CLAIMS_BPMS_MAPPING_TABLE. Error is "
					+ e.toString();
			map_obj.put("error_msg", error_msg);
		} catch (ClassNotFoundException e) {
			logger.error(e);

			error_msg = "Error while inserting or updating data into CLAIMS_BPMS_MAPPING_TABLE. Error is "
					+ e.toString();
			map_obj.put("error_msg", error_msg);
		}
		arg1.completeWorkItem(arg0.getId(), map_obj);
	}

	private boolean checkDuplicate(Connection conn, String claim_reference, long processinstanceid,
			String claimstate_old, String claimstate_new) throws SQLException {
		PreparedStatement psmnt_quey = null;
		int count;
		try {
			String sql_query = "SELECT COUNT(1) AS COUNT FROM CLAIMS_BPMS_MAPPING_TABLE"
					+ " WHERE CLAIM_REFERENCE=? AND PROCESSINSTACEID = ? AND CLAIMSTATE_OLD=? AND CLAIMSTATE_NEW=? AND PROCESSED=?";
			psmnt_quey = conn.prepareStatement(sql_query);
			psmnt_quey.setString(1, claim_reference);
			psmnt_quey.setString(2, Long.toString(processinstanceid));
			psmnt_quey.setString(3, claimstate_old);
			psmnt_quey.setString(4, claimstate_new);
			psmnt_quey.setString(5, "Y");
			psmnt_quey.execute();
			logger.debug(psmnt_quey.getFetchSize());
			ResultSet resultSet = psmnt_quey.getResultSet();
			count = 0;
			if (resultSet.next()) {

				count = resultSet.getInt("COUNT");
				logger.debug("count   " + count);
			}
		} finally {
			if (psmnt_quey != null) {
				psmnt_quey.close();
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String args[]) throws Exception {
		Connection conn = DBUtils.getDBConnection();
		new ClaimsBpmsMappingTableUpdate().checkDuplicate(conn, "ET6FG3V-4601-006", 220, "SUBMITTED",
				"ACCEPTED_PAYMENT_REQUESTED");
	}
}
