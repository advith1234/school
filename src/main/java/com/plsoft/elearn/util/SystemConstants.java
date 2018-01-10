package com.plsoft.elearn.util;

import java.util.HashMap;
import java.util.Map;

import com.plsoft.elearn.entity.Users;
import com.plsoft.elearn.entity.controller.UsersJpaController;
import com.plsoft.elearn.enums.TrueOrFalse;

/**
 * This class has getter methods for the system constants so that they may be retrieved
 * consistently across the application
 *
 * @author Ravikanth.Tangeti
 */
public class SystemConstants {

  // session object access constants
  public static final String SESSION_SUBUNIT = "subUnitCd";
  public static final String SESSION_SUBUNIT_TITLE = "subUnitTitle";
  public static final String SESSION_EMPLOYER = "currentContext";
  public static final String SESSION_USER = "user_session";
  public static final String FUND = "fund";
  public static final Double NOT_NULL = 0.0;
  public static final String SESSION_USER_NAME = "user_name";
  public static final String SESSION_FIRST_NAME = "first_name";
  public static final String SESSION_LAST_NAME = "last_name";
  public static final String SESSION_USER_MAIL = "user_mail";
  public static final String SESSION_USER_PHONE = "user_phone";
  public static final String SESSION_MANAGER_NAME = "manager_name";
  public static final String SESSION_USER_TITLE = "user_title";

  // links label contstans
  public static final String TRF_INTERACTIVE = "TRF_INTERACTIVE";
  public static final String PERF_ONLINE = "PERF_ONLINE";

	/**
	 * @return enum for system-wide setting of whether or not interest will be charged for
	 * late wage and contribution submissions
	 */
	public static TrueOrFalse getInterestFlag() {

		TrueOrFalse interestFlag = TrueOrFalse.F;
		return interestFlag;
	}

	/**
	 * @return The number of wage and contribution reports to display on the screen at one time
	 */
	public static int getNumberOfReportsToShowAtOneTime() {

		int returnVal = 5;
		return returnVal;
	}

	/**
	 * @return the user who has been set up to be used in place of setting a user
	 * to null.
	 */
	public static Users getNullUser() {

		return new UsersJpaController().findUsers(-1);
	}

	/**
	 * @return the database ID that should be used in place of setting a column to null. This
	 * ID is necessary due to NOT NULL and foreign key constraints for some tables
	 */
	public static String getNullId() {
		return "-1";
	}

	public static String getDateFormatString() {
		return "MM/dd/yyyy";
	}

	/**
	 * @return the Map contains the allowed roles for TRF Interactive and PEF Online links
	 */
	public static Map<String, String> getLinksRoleAllowed() {
		Map<String, String> linksRoleAllowed = new HashMap<String, String>();
		linksRoleAllowed.put("TRF_INTERACTIVE_1", "ROLE_TRF_RETIRE_PART_II");
		linksRoleAllowed.put("TRF_INTERACTIVE_2", "ROLE_TRF_RETIRE_PART_II");
		linksRoleAllowed.put("TRF_INTERACTIVE_3", "ROLE_TRF_SERVICE_VERIFY");
		linksRoleAllowed.put("PERF_ONLINE_1", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_2", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_3", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_4", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_5", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_6", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_7", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_8", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_9", "ROLE_PERF_RETIREMENT");
		linksRoleAllowed.put("PERF_ONLINE_10", "ROLE_PERF_PENSION_RELIEF");
		linksRoleAllowed.put("PERF_ONLINE_11", "ROLE_PERF_PENSION_RELIEF");

		return linksRoleAllowed;
	}

	/**
	 * @return the Map contains the allowed funds for PEF Online links
	 */
	public static Map<String, String> getLinksFundAllowed() {
		Map<String, String> linksFundAllowed = new HashMap<String, String>();
		linksFundAllowed.put("PERF_ONLINE_1", "PE");
		linksFundAllowed.put("PERF_ONLINE_2", "PE");
		linksFundAllowed.put("PERF_ONLINE_7", "PE");
		linksFundAllowed.put("PERF_ONLINE_9", "PE");
		linksFundAllowed.put("PERF_ONLINE_10", "77");
		linksFundAllowed.put("PERF_ONLINE_11", "77");

		return linksFundAllowed;
	}

}
