package com.plsoft.elearn.util;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.plsoft.elearn.entity.Users;
import com.plsoft.elearn.entity.controller.UsersJpaController;
import com.plsoft.elearn.services.ElearnUser;

public class KerbUserDetailsService implements UserDetailsService {

  /**
   * Load pelearnissions for the user after it has been authenticated.
   * <p>
   * Users authenticated by SSO/Kerberos must have their roles and pelearnissions loaded manually because
   * only the username is available.
   * <p>
   * SSO users must be present in the Users table along with associated groups and pelearnissions so they may
   * be loaded by this method upon successful authentication.
   * <p>
   * @return
   */
  public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException, DataAccessException {
    UsersJpaController uc = new UsersJpaController();
    Users user = uc.findUserByName( username);
    if( user != null) {
      StringBuilder authString = new StringBuilder();
      List<GrantedAuthority> authorityList;


      authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList( authString.toString());
      return new ElearnUser( username, "notUsed", true, true, true, true, authorityList);
    }

    throw new UsernameNotFoundException( "User not found");
  }
}
