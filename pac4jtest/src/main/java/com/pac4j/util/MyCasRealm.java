package com.pac4j.util;

import java.util.LinkedHashMap;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;

/**
 * 认证与授权
 **/
public class MyCasRealm extends Pac4jRealm {

	private String clientName;

	/**
	 * 认证
	 * 
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
		final LinkedHashMap<String, CommonProfile> commonProfileList = pac4jToken.getProfiles();
		final CommonProfile commonProfile = commonProfileList.get(0);
		System.out.println("单点登录返回的信息" + commonProfile.toString());
		final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
		final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
		return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
	}

	/**
	 * 授权/验权（todo 后续有权限在此增加）
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
		authInfo.addStringPermission("user");
		return authInfo;
	}
}