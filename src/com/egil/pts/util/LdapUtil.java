/*
 * package com.egil.pts.util;
 * 
 * import javax.naming.Context; import javax.naming.NamingEnumeration; import
 * javax.naming.NamingException; import
 * javax.naming.directory.InitialDirContext; import
 * javax.naming.directory.SearchControls; import
 * javax.naming.directory.SearchResult; import javax.naming.ldap.LdapContext;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Component;
 * 
 * import java.util.Hashtable;
 * 
 * @Component public class LdapUtil {
 * 
 * @Value("${rico.ldaps.DN}") private final String DN =
 * "CN=REFLEXUSER,OU=CA,OU=SvcAccount,OU=P001,OU=ID,OU=Data,DC=ericsson,DC=se";
 * 
 * @Value("${rico.ldaps.Manager.Pass}") private final String MANAGER_PASSWORD =
 * "uQwYKAVJkQsJHV2k"; private InitialDirContext initialDirContext;
 * 
 * @Value("${rico.ldaps.baseDN}") private String
 * baseDN="OU=ID,OU=Data,DC=ericsson,DC=se";
 * 
 * public LdapUtil() { this.initialDirContext = connect(DN, MANAGER_PASSWORD); }
 * 
 * public boolean authenticateUser(String signum, String userPassword) { String
 * filter = "cn=" + signum;
 * 
 * NamingEnumeration<?> namingEnum; String userDN = ""; try { if
 * (initialDirContext == null) { this.initialDirContext = connect(DN,
 * MANAGER_PASSWORD); } namingEnum = initialDirContext.search(baseDN, filter,
 * getSimpleSearchControls()); SearchResult sr = (SearchResult)
 * namingEnum.next(); userDN = sr.getNameInNamespace(); namingEnum.close(); }
 * catch (NullPointerException e) { throw e; } catch (Exception e) { return
 * false; } if (userDN.isEmpty()) { return false; } else { return
 * connect(userDN, userPassword) != null; } }
 * 
 * private InitialDirContext connect(String dn, String userPassword) {
 * Hashtable<String, String> env = new Hashtable<>();
 * env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
 * env.put(Context.PROVIDER_URL,
 * "ldaps://ldap-egad.internal.ericsson.com:3269/");
 * env.put(Context.SECURITY_PROTOCOL, "ssl");
 * env.put(Context.SECURITY_PRINCIPAL, dn); // SET USER
 * env.put(Context.SECURITY_CREDENTIALS, userPassword);
 * env.put(Context.SECURITY_AUTHENTICATION, "simple");
 * env.put(LdapContext.CONTROL_FACTORIES, "com.sun.jndi.ldap.ControlFactory");
 * InitialDirContext ctx; try { ctx = new InitialDirContext(env); } catch
 * (NamingException e) { e.printStackTrace(); return null; } return ctx; }
 * 
 * private SearchControls getSimpleSearchControls() { SearchControls
 * searchControls = new SearchControls();
 * searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
 * searchControls.setTimeLimit(30000); return searchControls; }
 * 
 * public void closeConnection() { try { initialDirContext.close(); } catch
 * (Exception e) { e.printStackTrace(); } } }
 */