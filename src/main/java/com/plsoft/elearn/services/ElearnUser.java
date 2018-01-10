package com.plsoft.elearn.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Extends UserDetails to allow a modifiable set of <code>authorities</code>.
 * Authorities must be changed when the user changes the submission unit that is considered to
 * be the current context.
 * <p>
 * @author Ravikanth.Tangeti
 *
 */
@SuppressWarnings( "serial")
public class ElearnUser implements UserDetails {

  private String password;
  private final String username;
  private Collection<GrantedAuthority> authorities;
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;

  public ElearnUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> authorities) {

    if (((username == null) || "".equals(username)) || (password == null)) {
        throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
    }

    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.authorities = authorities;
  }

  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public boolean isEnabled() {
    return enabled;
  }


}
