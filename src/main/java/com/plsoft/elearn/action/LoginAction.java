package com.plsoft.elearn.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.sf.json.JSON;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plsoft.elearn.services.UserService;

/**
 * Handles content and AJAX requests for the main login page.<p>
 * @author Ravikanth.Tangeti
 *
 */
@Namespace(value="/elearnLogin")
public class LoginAction extends ElearnBaseAction {
  private static final Logger logger = Logger.getLogger( LoginAction.class);

  @Autowired
  @Qualifier("elearnProperties")
  private Properties elearnProps;

  private String username;

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private SimpleDateFormat sdf = new SimpleDateFormat( "EEEE, MMMM dd, yyyy");

  private Map<String, Object> session;

  private Boolean lockedOut;
  private Boolean failedLogin;
  private Boolean disabledLogin;
  private JSON questionList;


  private UserService userService;

  @Autowired
  public LoginAction( UserService user) {
    userService = user;
  }

  /**
   * Struts2 Action
   *
   * Setup data required for the login screen
   * @return
   */
  @Action(value="loginInitAction", results={@Result(name="success", location="/WEB-INF/content/login.ftl", type="freemarker")})
  public String initLogin() {
    logger.debug( "Init Login");
    session.put( "currentDate", sdf.format( new Date()));

    return SUCCESS;
  }

  /**
   * Struts2 Action
   *
   * Called after a failed login attempt for a lockout due to too many login attempts
   * @return
   */
  @Action(value="loginFailureInitAction", results={@Result(name="success", location="/WEB-INF/content/login.ftl", type="freemarker")})
  public String initFailLogin() {
    logger.debug( "Init Failure Login");
    session.put( "currentDate", sdf.format( new Date()));
    failedLogin = Boolean.TRUE;

    return SUCCESS;
  }

  /**
   * Struts2 Action
   *
   * Called after a failed login attempt for a lockout due to too many login attempts
   * @return
   */
  @Action(value="loginDisabledAction", results={@Result(name="success", location="/WEB-INF/content/login.ftl", type="freemarker")})
  public String initDisabledLogin() {
    logger.debug( "Init Failure Login");
    session.put( "currentDate", sdf.format( new Date()));
    disabledLogin = Boolean.TRUE;

    return SUCCESS;
  }

  /**
   *
   * @param email
   * @param password
   * @throws Exception
   */
  public void sendEmail( String email, String password) throws Exception {

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
   * @param username the username to set
   */
  public void setUsername( String username) {
    this.username = username;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Struts2 calls this method to set the session object into the Action class.
   */
  public void setSession( Map<String, Object> arg0) {
    session = arg0;
  }

  /**
   * @param questionList the questionList to set
   */
  public void setQuestionList( JSON questionList) {
    this.questionList = questionList;
  }

  /**
   * @return the questionList
   */
  public JSON getQuestionList() {
    return questionList;
  }

  /**
   * @param lockedOut the lockedOut to set
   */
  public void setLockedOut( Boolean lockedOut) {
    this.lockedOut = lockedOut;
  }

  /**
   * @return the lockedOut
   */
  public Boolean getLockedOut() {
    return lockedOut;
  }

  /**
   * @param failedLogin the failedLogin to set
   */
  public void setFailedLogin( Boolean failedLogin) {
    this.failedLogin = failedLogin;
  }

  /**
   * @return the failedLogin
   */
  public Boolean getFailedLogin() {
    return failedLogin;
  }

  /**
   * @param disabledLogin the disabledLogin to set
   */
  public void setDisabledLogin( Boolean disabledLogin) {
    this.disabledLogin = disabledLogin;
  }

  /**
   * @return the disabledLogin
   */
  public Boolean getDisabledLogin() {
    return disabledLogin;
  }
}
