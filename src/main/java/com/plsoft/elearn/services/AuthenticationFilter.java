package com.plsoft.elearn.services;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.plsoft.elearn.util.SystemConstants;

/**
 * Overrides <tt>attemptAuthentication<tt> to catch locked out users.
 *
 * @author Ravikanth.Tangeti
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * Called by Spring Security framework before an authentication takes place.
	 * <p>
	 * The username is checked to ensure the login attempts has not been
	 * exceeded. If the username is valid, and the login attempts have been
	 * exceeded the allowed number, then a LockedException is thrown and the
	 * login fails.
	 * <p>
	 *
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		HttpSession session = request.getSession(true);

		if (username == null) {
			username = "";
		}

		username = username.trim();
		String[] splitUser = username.split("\\.");
		String firstName = String.format("%s%s",
				Character.toUpperCase(splitUser[0].charAt(0)),
				splitUser[0].substring(1));
		String lastName = String.format("%s%s",
				Character.toUpperCase(splitUser[1].charAt(0)),
				splitUser[1].substring(1));
		username = firstName + "." + lastName;

		LdapContext ldapContext = getLdapContext(password,  lastName + ", " + firstName);
		if(ldapContext == null)
			throw new BadCredentialsException("Bad Credentials");
		else
		  getUserBasicAttributes(username, ldapContext, session);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(username, password,
				authorities);
	}

	public LdapContext getLdapContext(String password, String commonName) {
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			env.put(Context.SECURITY_PRINCIPAL, commonName);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.PROVIDER_URL, "ldap://ccidc04:389");
			ctx = new InitialLdapContext(env, null);
			System.out.println("Connection Successful.");
		} catch (NamingException nex) {
			System.out.println("LDAP Connection: FAILED");
			throw new BadCredentialsException("Bad Credentials");
		}
		return ctx;
	}

	private void getUserBasicAttributes(String username, LdapContext ctx, HttpSession session) {
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] attrIDs = {"sn", "givenname",
					"mail", "telephonenumber", "cn", "title", "manager" };
			constraints.setReturningAttributes(attrIDs);
			NamingEnumeration answer = ctx.search("OU=India,OU=plsoft Accounts - Active,DC=plsoft,DC=com",
					"sAMAccountName=" + username, constraints);
			if (answer.hasMore()) {
				Attributes attrs = ((SearchResult) answer.next()).getAttributes();
				session.setAttribute(SystemConstants.SESSION_USER_NAME, (String) attrs.get("cn").get());
				session.setAttribute(SystemConstants.SESSION_FIRST_NAME, (String) attrs.get("givenname").get());
				session.setAttribute(SystemConstants.SESSION_LAST_NAME, (String) attrs.get("sn").get());
				session.setAttribute(SystemConstants.SESSION_USER_MAIL, (String) attrs.get("mail").get());
				session.setAttribute(SystemConstants.SESSION_USER_PHONE, (String) attrs.get("telephonenumber").get());
				session.setAttribute(SystemConstants.SESSION_USER_TITLE, (String) attrs.get("title").get());
			} else {
				throw new BadCredentialsException("Bad Credentials");
			}
		} catch (Exception ex) {
			throw new BadCredentialsException("Bad Credentials");
		}
		return;
	}

}
