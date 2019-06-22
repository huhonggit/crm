package com.crm.auth.shiro;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.crm.common.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常拦截
 * @author huhong
 * @date 2019-06-22 15:39
 */
public class AuthExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {

        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (ex instanceof UnauthenticatedException) {
            attributes.put("code", 401);
            attributes.put("msg", "用户未登陆");
        } else if (ex instanceof UnauthorizedException) {
            attributes.put("code", 502);
            attributes.put("msg", "用户无权限");
        }else if (ex instanceof AuthenticationException) {
            attributes.put("code", 503);
            attributes.put("msg", "用户名密码不匹配");
        } else {

            LOGGER.error(ex.getMessage(),ex);
            if(ex instanceof BusinessException){
                BusinessException businessException = (BusinessException) ex;
                attributes.put("code", businessException.getCode());
                attributes.put("msg", businessException.getMsg());
            }else {
                attributes.put("code", 500);
                attributes.put("msg", "系统内部错误，请联系管理");
            }
        }
        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }}
