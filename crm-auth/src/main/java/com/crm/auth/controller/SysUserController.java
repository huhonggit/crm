package com.crm.auth.controller;

import com.crm.auth.po.SysUser;
import com.crm.auth.service.SysUserService;
import com.crm.auth.util.OnlineUserUtil;
import com.crm.auth.util.ShiroPasswordUtil;
import com.crm.auth.vo.MenuTree;
import com.crm.auth.vo.SysUserVO;
import com.crm.common.bo.JsonResult;
import com.crm.common.enums.StatusType;
import com.crm.common.page.PageInfo;
import com.crm.common.page.PageObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/auth/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @PostMapping("saveUser")
    public JsonResult<SysUser> saveUser(@RequestBody SysUser user) {
        SysUser sysUser = sysUserService.saveUser(user);
        if (sysUser == null) {
            return new JsonResult<SysUser>(601, "用户名已存在");
        }
        return new JsonResult<SysUser>().setData(sysUser);
    }


    @GetMapping("getUserById")
    public JsonResult<SysUser> getUserById(@RequestParam("id") String id) {
        SysUser sysUser = sysUserService.getUserById(id);
        return new JsonResult<SysUser>().setData(sysUser);
    }

    @PostMapping("queryUser")
    public JsonResult<PageInfo<SysUserVO>> queryUser(@RequestBody PageObject<SysUser> pageObject) {
        PageInfo<SysUserVO> page = sysUserService.queryUser(pageObject);
        return new JsonResult<PageInfo<SysUserVO>>().setData(page);
    }


    @GetMapping("updateStatus")
    public JsonResult updateStatus(@RequestParam("id") String id, @RequestParam("status") StatusType status) {
        sysUserService.updateStatus(id, status);
        return new JsonResult();
    }

    @GetMapping("updatePwd")
    public JsonResult updatePwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd) {
        SysUser onlineUser = OnlineUserUtil.getOnlineUser();

        SysUser user = sysUserService.getUserById(onlineUser.getId());

        String pwd = ShiroPasswordUtil.encode(oldPwd, onlineUser.getUsername());
        if (!user.getPassword().equals(pwd)) {
            return new JsonResult(609, "修改密码错误,旧密码不匹配");
        }
        sysUserService.updatePassword(onlineUser, newPwd, true);
        return new JsonResult();
    }

    @PostMapping("editPassword")
    public JsonResult editPassword(@RequestBody SysUser user) {
        if (StringUtils.isEmpty(user.getId())) {
            return new JsonResult(609, "修改密码错误");
        }
        sysUserService.updatePassword(user, user.getPassword(), false);
        return new JsonResult();
    }

    @PostMapping("loadMenusByOnlineUser")
    public JsonResult<List<MenuTree>> loadMenusByOnlineUser() {
        SysUser user = OnlineUserUtil.getOnlineUser();
        List<MenuTree> menuTreeList = sysUserService.getMenuTreeByUser(user);
        return new JsonResult<List<MenuTree>>().setData(menuTreeList);
    }


}
