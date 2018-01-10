package com.plsoft.elearn.services;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * Extends JdbcDaoImpl in order to create an <code>elearnUser</code> object as opposed to the default <code>User</code> object.
 * @author Ravikanth.Tangeti
 *
 */
public class ElearnJdbcDaoImpl extends JdbcDaoImpl {

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl#createUserDetails(java.lang.String, org.springframework.security.core.userdetails.UserDetails, java.util.List)
   */
  @Override
  protected UserDetails createUserDetails( String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
    String returnUsername = userFromUserQuery.getUsername();

    return new ElearnUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
            true, true, true, combinedAuthorities);
  }

}
