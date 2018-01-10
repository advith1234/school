package com.plsoft.elearn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * JPA entity class.<p>
 * The table name can be derived from the class name.<p>
 * @author ravikanth.tangeti
 */
@Entity
@Table(name = "USERS")
@NamedQueries({@NamedQuery(name = "Users.findAllUsers", query = "SELECT u FROM Users u ORDER BY u.username"),
  @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
  @NamedQuery(name = "Users.findByEnabled", query = "SELECT u FROM Users u WHERE u.enabled = :enabled"),
  @NamedQuery(name = "Users.findByUserseqnum", query = "SELECT u FROM Users u WHERE u.userseqnum = :userseqnum")})
public class Users implements Serializable {
  private static final long serialVersionUID = 1L;

  @Basic(optional = false)
  @Column(name = "USERNAME")
  private String username;
  @Column(name = "ENABLED")
  private Integer enabled;
  @Column(name = "OCS_ID")
  private Integer ocsId;
  @Column(name="LOGIN_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date loginDate;

  @Id
  @GeneratedValue(generator="UserIdSeq")
  @SequenceGenerator(name="UserIdSeq", sequenceName="USERS_ID_SEQ", allocationSize=1)
  @Basic(optional = false)
  @Column(name = "USERSEQNUM")
  private Integer userseqnum;

  public Users() {}

  public Users(Integer userseqnum) {
    this.userseqnum = userseqnum;
  }

  public Users(Integer userseqnum, String username) {
    this.userseqnum = userseqnum;
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getEnabled() {
    return enabled;
  }

  public void setEnabled(Integer enabled) {
    this.enabled = enabled;
  }

  public Integer getUserseqnum() {
    return userseqnum;
  }

  public void setUserseqnum(Integer userseqnum) {
    this.userseqnum = userseqnum;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (userseqnum != null ? userseqnum.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Users)) {
      return false;
    }
    Users other = (Users) object;
    if ((this.userseqnum == null && other.userseqnum != null) || (this.userseqnum != null && !this.userseqnum.equals(other.userseqnum))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.plsoft.indiana.elearn.entity.Users[userseqnum=" + userseqnum + "]";
  }

  public Integer getOcsId() {
	return ocsId;
  }

  public void setOcsId(Integer ocsId) {
    this.ocsId = ocsId;
  }

  /**
   * @param loginDate the loginDate to set
   */
  public void setLoginDate( Date loginDate) {
    this.loginDate = loginDate;
  }

  /**
   * @return the loginDate
   */
  public Date getLoginDate() {
    return loginDate;
  }

}
