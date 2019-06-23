package com.crm.auth.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "dict_item")
public class DictItem implements Serializable {

    @Id
    private String id;

    private String dictId;

    private String dictCode;

    private String value;

    private String text;

    private Integer inx;

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
     * @return dict_id
     */
    public String getDictId() {
        return dictId;
    }

    /**
     * @param dictId
     */
    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    /**
     * @return dict_code
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * @param dictCode
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return inx
     */
    public Integer getInx() {
        return inx;
    }

    /**
     * @param inx
     */
    public void setInx(Integer inx) {
        this.inx = inx;
    }
}