package com.crm.auth.vo;

import com.crm.auth.po.SysRole;
import com.crm.auth.po.SysUser;

import java.io.Serializable;
import java.util.List;

public class SysUserVO extends SysUser implements Serializable {

    List<SysRole> roleList ;

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
