package com.crm.auth.controller;

import com.crm.auth.po.SysUser;
import com.crm.common.bo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 登录接口
 * @author huhong
 * @date 2019-06-21 16:51
 */
@RestController
@RequestMapping("/auth/")
@Api(value = "登录控制器")
public class LoginController {

    @ApiOperation(value="登录")
    @RequestMapping("/login")
    public JsonResult<SysUser> pclogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletResponse response) {
        JsonResult<SysUser> result = new JsonResult<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return  result.setCode(500).setMsg("参数错误");
        }
        Cookie cookie = new Cookie("JSESSIONID", UUID.randomUUID().toString());
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return result;
    }

}
