package com.crm.auth.shiro;

import com.crm.auth.po.SysUser;
import com.crm.auth.service.SysUserService;
import com.crm.auth.util.ShiroPasswordUtil;
import com.crm.common.enums.StatusType;
import com.crm.common.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;


/**
 * 用户验证
 * @author huhong
 * @date 2019-06-22 15:40
 */
public class AuthShiroRealm extends AuthorizingRealm {

    @Resource
    SysUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        authorizationInfo.addStringPermission("/**");
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
     * @param token token
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author huhong
     * @date 2019-06-22 15:26
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        SimpleAuthenticationInfo authenticationInfo;
        try{

            //获取用户的输入的账号.
            String username = (String) token.getPrincipal();
            //通过username从数据库中查找 User对象，如果找到，没找到.
            SysUser user = userService.getUserByUsername(username);

            if (user.getStatus() != StatusType.enable){
                throw new BusinessException(401,"账号已经被禁用");
            }
            authenticationInfo = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(), //密码
                    getName()  //realm name
            );
            authenticationInfo.setCredentialsSalt(ShiroPasswordUtil.getSalt(user.getUsername()));
        }catch (Exception e){
            if (e instanceof BusinessException){
                throw  e;
            }
            throw new BusinessException(401,"账号密码不匹配");
        }
        return authenticationInfo;
    }
}
