package com.crm.auth.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "dict")
public class Dict implements Serializable{

    @Id
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音码
     */
    private String pym;

    /**
     * 代码
     */
    private String code;

    /**
     * 父ID
     */
    private String pid;

    /**
     * 类型
     */
    private String type;

    /**
     * sql语句 当type==sql生效
     */
    private String sqlvalue;

    /**
     * 字典数据类型
     */
    private String dataType;

    /**
     * 说明
     */
    private String info;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取代码
     *
     * @return code - 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置代码
     *
     * @param code 代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取父ID
     *
     * @return pid - 父ID
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置父ID
     *
     * @param pid 父ID
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 获取字典数据类型
     *
     * @return data_type - 字典数据类型
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 设置字典数据类型
     *
     * @param dataType 字典数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 获取说明
     *
     * @return info - 说明
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置说明
     *
     * @param info 说明
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSqlvalue() {
        return sqlvalue;
    }

    public void setSqlvalue(String sqlvalue) {
        this.sqlvalue = sqlvalue;
    }
}