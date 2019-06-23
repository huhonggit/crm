package com.crm.auth.controller;


import com.crm.auth.po.SysRole;
import com.crm.auth.service.SysRoleService;
import com.crm.auth.vo.SysRoleVO;
import com.crm.common.bo.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/auth/role")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    @PostMapping("/saveRole")
    public JsonResult<SysRole> saveRole(@RequestBody SysRole role){
        SysRole sysRole = sysRoleService.saveRole(role);
        return new JsonResult<SysRole>().setData(sysRole);
    }

    @GetMapping("/getRoleById")
    public JsonResult<SysRole> getRoleById(@RequestParam("id") String id){
        SysRole sysRole = sysRoleService.getRoleById(id);
        return new JsonResult<SysRole>().setData(sysRole);
    }

    @GetMapping("/getRoleVoById")
    public JsonResult<SysRoleVO> getRoleVoById(@RequestParam("id") String id){
        SysRoleVO sysRole = sysRoleService.getRoleVoById(id);
        return new JsonResult<SysRoleVO>().setData(sysRole);
    }

    @PostMapping("/loadAllRole")
    public JsonResult<List<SysRole>> loadAllRole(){
        List<SysRole> roleList = sysRoleService.loadAllRole();
        return new JsonResult<List<SysRole>>().setData(roleList);
    }

    @PostMapping("/loadRoles")
    public JsonResult<List<SysRole>> loadRoles(@RequestBody SysRole role){
        List<SysRole> roleList = sysRoleService.loadRoles(role);
        return new JsonResult<List<SysRole>>().setData(roleList);
    }

    @PostMapping("/loadEnableRole")
    public JsonResult<List<SysRole>> loadEnableRole(){
        List<SysRole> roleList = sysRoleService.loadEnableRole();
        return new JsonResult<List<SysRole>>().setData(roleList);
    }


}
