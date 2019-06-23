package com.crm.auth.vo;

import java.io.Serializable;

public class DictItemVo implements Serializable {
    private Long id;

    private Object value;

    private String text;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
