package com.plsoft.elearn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plsoft.elearn.entity.Users;
import com.plsoft.elearn.util.SystemConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base action for Struts2 action classes in the project.<p>
 * Contains utility methods that sub classes may find useful.
 * <p>
 * @author Ravikanth.Tangeti
 *
 */
public abstract class ElearnBaseAction extends ActionSupport implements SessionAware {
  private static final Logger logger = Logger.getLogger( ElearnBaseAction.class);

  private static final long serialVersionUID = 1L;

  private SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, MMMM dd, yyyy");

  private String currentDate;
  private String staffUser;
  private String submissionUnitName;
  private String multiEmployer;

  /** Title of the page that will show up on the screen to tell the user where he is in the application. */
  private String pageTitle;

  @Autowired
  @Qualifier("elearnProperties")
  private Properties elearnProps;

  protected Map<String,Object> session;

  /**
   * Get the title of the page that will show up on the screen to tell the user where he is in the application.
   *
   * @return title of the page
   */
  public String getPageTitle() {
		return pageTitle;
  }

  /**
   * Set the title of the page that will show up on the screen to tell the user where he is in the application.
   *
   * @param pageTitle the title of the page
   */
  public void setPageTitle(String pageTitle) {
	  this.pageTitle = pageTitle;
  }

  public Date getTodaysDate() {
		return new Date();
  }

  /**
   * @return today's date formatted as "MM/dd/yyyy"
   */
  public String getTodaysDateStandard() {
	return new SimpleDateFormat("MM/dd/yyyy").format(new Date());
  }

  protected PrintWriter getWriter() throws IOException {
    ActionContext actionContext = ActionContext.getContext();
    HttpServletResponse response = (HttpServletResponse) actionContext.get(
            "com.opensymphony.xwork2.dispatcher.HttpServletResponse");
    return response.getWriter();

  }

//  /**
//   * @return the currently logged in user
//   */
//  public Users getLoggedInUser() {
//
//	  return (Users) session.get( SystemConstants.SESSION_USER);
//  }

  /**
   *
   */
  protected void initAction() {
    setCurrentDate( sdf.format( new Date()));
  }

  /**
   * Format a double value to have a single decimal place and will have a 0 (zero) if there is none.<p>
   * @param num
   * @return
   */
  protected String formatSingleDecimal( Double num) {
    DecimalFormat format = new DecimalFormat( "##.0");
    return format.format( num).toString();
  }

  /**
   *
   * @param email
   * @param password
   * @throws Exception
   */
  protected void sendEmail( String email, String password) throws Exception {

    Properties props = System.getProperties();
    props.setProperty(  "mail.smtp.host", "localhost");

    Session session = Session.getDefaultInstance( props);
    MimeMessage message = new MimeMessage( session);
    message.setFrom( new InternetAddress( "no-reply@plsoft.com"));
    message.addRecipient( Message.RecipientType.TO, new InternetAddress( email));
    message.setSubject( elearnProps.getProperty( "email.subject"));

    String emailMsg = elearnProps.getProperty( "email.message");
    emailMsg = StringUtils.replace( emailMsg, "@password@", password, 100);
    message.setText(  emailMsg);

    Transport.send( message);

    logger.info( "Password email sent to: " + email);
  }

  /**
   *
   * @return
   */
  public String getHasFundAccess() {
    Users user = (Users)session.get( SystemConstants.SESSION_USER);

    return "T";
  }

  /**
   * @return the staffUser
   */
  public String getStaffUser() {
    Users user = (Users)session.get( SystemConstants.SESSION_USER);
    return "F";
  }

  /**
   * @return true if the user is a staff user, false if the user is an employer user
   */
  public boolean isStaffUser() {

	  boolean staffUser = false;
	  String flag = getStaffUser();

	  if ("T".equals(flag)) {
		  staffUser = true;
	  }

	  return staffUser;
  }


  /**
   * @param currentDate the currentDate to set
   */
  public void setCurrentDate( String currentDate) {
    this.currentDate = currentDate;
  }

  /**
   * @return the currentDate
   */
  public String getCurrentDate() {
    return currentDate;
  }

  public void setSession( Map<String, Object> arg0) {
    session = arg0;
  }

  /**
   * @param submissionUnitName the submissionUnitName to set
   */
  public void setSubmissionUnitName( String submissionUnitName) {
    this.submissionUnitName = submissionUnitName;
  }

  /**
   * @return the submissionUnitName
   */
  public String getSubmissionUnitName() {
    return submissionUnitName;
  }

  /**
   * @param multiEmployer the multiEmployer to set
   */
  public void setMultiEmployer( String multiEmployer) {
    this.multiEmployer = multiEmployer;
  }

  /**
   * @return the multiEmployer
   */
  public String getMultiEmployer() {
    return multiEmployer;
  }
}
