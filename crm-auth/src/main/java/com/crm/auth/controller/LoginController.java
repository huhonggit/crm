package com.crm.auth.controller;

import com.crm.auth.po.SysUser;
import com.crm.auth.service.SysUserService;
import com.crm.common.bo.JsonResult;
import com.sun.tools.javac.main.Main;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录接口
 *
 * @author huhong
 * @date 2019-06-21 16:51
 */
@RestController
@RequestMapping("/auth/")
@Api(value = "登录控制器")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public JsonResult<SysUser> pclogin(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       HttpServletResponse response) {
        JsonResult<SysUser> result = new JsonResult<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return result.setCode(500).setMsg("参数错误");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return result.setCode(500).setMsg(e.getMessage());
            } else {
                throw e;
            }
        }
        Cookie cookie = new Cookie("X-Token", subject.getSession().getId().toString());
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        Object attribute = subject.getSession().getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) attribute;
        SysUser user = (SysUser) simplePrincipalCollection.getPrimaryPrincipal();
        return result.setData(user);
    }


    @PostMapping("/loadLoginInfo")
    public JsonResult<SysUser> loadLoginInfo() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new JsonResult<SysUser>(401,"用户未登陆");
        }
        SysUser userById = sysUserService.getUserById(user.getId());
        user.setPwdFlag(userById.getPwdFlag());
        return new JsonResult<SysUser>().setData(user);
    }
}
