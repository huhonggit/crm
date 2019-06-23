package com.crm.auth.vo;

import com.crm.auth.po.SysMenu;

import java.io.Serializable;
import java.util.List;

public class MenuTree extends SysMenu implements Serializable {
    List<MenuTree> children;


    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }
}
