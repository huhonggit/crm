package com.crm.auth.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 * @author huhong
 * @date 2019-06-22 15:31
 */
@Configuration
public class AuthShiroConfig {

    /**
     * 自定义安全域，用户验证、权限等数据在此提供
     * @return com.crm.auth.shiro.MyShiroRealm
     * @author huhong
     * @date 2019-06-22 15:31
     */
    @Bean
    public AuthShiroRealm myShiroRealm() {
        AuthShiroRealm myShiroRealm = new AuthShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器，这里的hash算法用来将前台传入的数据做处理
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     * @author huhong
     * @date 2019-06-22 15:31
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    /**
     * 定义shiro的filter，进行基础配置
     * @param securityManager securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author huhong
     * @date 2019-06-22 15:32
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**", "anon");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 自定义session 管理 将数据存入redis
     * @return java.lang.SecurityManager
     * @author huhong
     * @date 2019-06-22 15:32
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义sessionManager
     * @return org.apache.shiro.session.mgt.SessionManager
     * @author huhong
     * @date 2019-06-22 15:32
     */
    @Bean
    public SessionManager sessionManager() {
        AuthSessionManager mySessionManager = new AuthSessionManager();
//        mySessionManager.setSessionIdCookieEnabled(false);
        mySessionManager.setSessionDAO(sessionDAO());
        return mySessionManager;
    }

    /**
     * sessionDAO
     * @return org.apache.shiro.session.mgt.eis.SessionDAO
     * @author huhong
     * @date 2019-06-22 15:32
     */
    @Bean
    public SessionDAO sessionDAO(){
        return new AuthRedisSessionDAO();
    }


    /**
     *  * 开启shiro aop注解支持.
     *      * 使用代理方式;所以需要开启代码支持;
     * @param securityManager securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author huhong
     * @date 2019-06-22 15:33
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注册全局异常处理
     * @return org.springframework.web.servlet.HandlerExceptionResolver
     * @author huhong
     * @date 2019-06-22 15:33
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new AuthExceptionHandler();
    }
}
