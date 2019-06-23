package com.crm.auth.vo;

import com.crm.auth.po.SysRole;

import java.io.Serializable;
import java.util.List;

public class SysRoleVO extends SysRole implements Serializable {

    private List<String> checkMenuId;

    public List<String> getCheckMenuId() {
        return checkMenuId;
    }

    public void setCheckMenuId(List<String> checkMenuId) {
        this.checkMenuId = checkMenuId;
    }
}
