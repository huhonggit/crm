package com.crm.auth.po;

import com.crm.auth.enums.MenuType;
import com.crm.common.enums.StatusType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : gaowei123
 * @Date : 2019/4/1 16:18
 * @Version : 1.0.0
 * @description : 菜单
 */
@Document(collection = "sys_menu")
public class SysMenu implements Serializable{
    @Id
    private String id;

    //菜单类型
    private MenuType type;


    //菜单名称
    private String name;

    //路径
    private String router;

    //父级菜单id
    private String parentId;

    //图标
    private String icon;

    private StatusType status;

    private String uri;

    //排序
    private Integer sort;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //创建人
    private String creater ;

    //更新人
    private String updater;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}
