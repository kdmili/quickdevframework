package org.lm.quick.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.alibaba.fastjson.JSONObject;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(value = { DefaultWebSecurityManager.class, Realm.class })
@ConfigurationProperties(prefix = "security.shiro")
@PropertySource(value = "classpath:security.properties")
public class ShiroAutoConfig implements ServletContextInitializer {

	@Bean("shiroFilter")
	@ConditionalOnMissingBean(value = ShiroFilterFactoryBean.class)
	protected ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager) {
		ShiroFilterFactoryBean facb = new ShiroFilterFactoryBean();
		facb.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = convertShiroFilterMap();
		// security filter mapper
		facb.setFilterChainDefinitionMap(filterChainDefinitionMap);

		facb.setLoginUrl(loginUrl);

		facb.setSuccessUrl(successUrl);

		facb.setUnauthorizedUrl(unauthorizedUrl);
		
		return facb;
	}

	Map<String, String> convertShiroFilterMap() {
		String filter = getFilterChain();

		JSONObject map = JSONObject.parseObject(filter);
		HashMap<String, String> result = new HashMap<String, String>();
		for (Entry<String, Object> en : map.entrySet()) {
			result.put(en.getKey(), en.getValue().toString());
		}
		return result;
	}

	@Bean
	@ConditionalOnMissingBean(value = SecurityManager.class)
	protected SecurityManager securityManager(@Autowired Realm realm) {
		DefaultWebSecurityManager securityM = new DefaultWebSecurityManager();
		securityM.setRealm(realm);
		return securityM;
	}

	@Bean
	@ConditionalOnMissingBean(value = Realm.class)
	// @ConditionalOnBean(value = EmbeddedDatabase.class)
	protected Realm realM(DataSource ds) {
		JdbcRealm jdbcRealm = new JdbcRealm();

		jdbcRealm.setAuthenticationCacheName(authenticationCacheName);

		jdbcRealm.setAuthenticationCachingEnabled(authenticationCachingEnabled);

		if (StringUtils.hasLength(authenticationQuery))
			jdbcRealm.setAuthenticationQuery(authenticationQuery);

		jdbcRealm.setAuthorizationCacheName(authorizationCacheName);

		jdbcRealm.setCachingEnabled(cachingEnabled);
		jdbcRealm.setDataSource(ds);
 
		if (StringUtils.hasLength(userRolesQuery))
			jdbcRealm.setUserRolesQuery(userRolesQuery);
		if (StringUtils.hasLength(permissionsQuery))
			jdbcRealm.setPermissionsQuery(permissionsQuery);

		jdbcRealm.setPermissionsLookupEnabled(permissionsLookupEnabled);
		return jdbcRealm;
	}

	private String authenticationCacheName;
	private boolean authenticationCachingEnabled;
	private String authenticationQuery;
	private String authorizationCacheName;
	private boolean cachingEnabled;
	private String userRolesQuery;
	private String permissionsQuery;
	private boolean permissionsLookupEnabled;
	private String filterChain;
	private String loginUrl;
	private String successUrl;
	private String unauthorizedUrl;

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getAuthenticationCacheName() {
		return authenticationCacheName;
	}

	public void setAuthenticationCacheName(String authenticationCacheName) {
		this.authenticationCacheName = authenticationCacheName;
	}

	public boolean isAuthenticationCachingEnabled() {
		return authenticationCachingEnabled;
	}

	public void setAuthenticationCachingEnabled(boolean authenticationCachingEnabled) {
		this.authenticationCachingEnabled = authenticationCachingEnabled;
	}

	public String getAuthenticationQuery() {
		return authenticationQuery;
	}

	public void setAuthenticationQuery(String authenticationQuery) {

		this.authenticationQuery = authenticationQuery;
	}

	public String getAuthorizationCacheName() {
		return authorizationCacheName;
	}

	public void setAuthorizationCacheName(String authorizationCacheName) {
		this.authorizationCacheName = authorizationCacheName;
	}

	public boolean isCachingEnabled() {
		return cachingEnabled;
	}

	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}


	public String getUserRolesQuery() {
		return userRolesQuery;
	}

	public void setUserRolesQuery(String userRolesQuery) {

		this.userRolesQuery = userRolesQuery;
	}

	public String getPermissionsQuery() {
		return permissionsQuery;
	}

	public void setPermissionsQuery(String permissionsQuery) {
		this.permissionsQuery = permissionsQuery;
	}

	public boolean isPermissionsLookupEnabled() {
		return permissionsLookupEnabled;
	}

	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
		this.permissionsLookupEnabled = permissionsLookupEnabled;
	}

	public String getFilterChain() {
		return filterChain;
	}

	public void setFilterChain(String filterChain) {
		this.filterChain = filterChain;
	}

	public static void main(String[] args) {
		String json = "{'/css/**':'anon','/js/**':'anon','/images/**':'anon'}";
		JSONObject map = JSONObject.parseObject(json);
		System.out.println(map.size());
		for (Entry<String, Object> en : map.entrySet()) {
			System.out.println(en.getKey() + "----" + en.getValue());
		}
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		System.out.println("==============add shiro filter==============");
		Dynamic f = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
		EnumSet<DispatcherType> set = EnumSet.allOf(DispatcherType.class);
		f.addMappingForUrlPatterns(set, false, new String[] { "/*" });
	
	}

}
