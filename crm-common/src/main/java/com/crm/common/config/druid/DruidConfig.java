package com.crm.common.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean druidServlet() {
    	ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //添加初始化参数：initParams  
//        /** 白名单，如果不配置或value为空，则允许所有 */  
//        servletRegistrationBean.addInitParameter("allow","127.0.0.1,192.0.0.1");  
        /** 用户名 */  
        servletRegistrationBean.addInitParameter("loginUsername","admin");  
        /** 密码 */  
        servletRegistrationBean.addInitParameter("loginPassword","admin");
        /** 禁用页面上的“Reset All”功能 */  
        servletRegistrationBean.addInitParameter("resetEnable","false");  
        return servletRegistrationBean;
    }
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
		 FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		    /** 过滤规则 */  
		    filterRegistrationBean.addUrlPatterns("/*");  
		    /** 忽略资源 */  
		    filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");  
		    return filterRegistrationBean; 
    }
	
   
	
}