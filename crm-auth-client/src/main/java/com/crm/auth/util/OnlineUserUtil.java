package com.crm.auth.util;

import com.crm.auth.po.SysUser;
import com.crm.common.exception.BusinessException;
import org.apache.shiro.SecurityUtils;

public class OnlineUserUtil {

    public static SysUser getOnlineUser(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            throw  new BusinessException(401,"用户未登陆");
        }

        return user;
    }
}
