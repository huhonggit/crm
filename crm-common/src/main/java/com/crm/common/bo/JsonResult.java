package com.crm.common.bo;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author huhong
 * @date 2019-06-21 16:57
 */
public class JsonResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public JsonResult(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public JsonResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public JsonResult() {
        this.code = 200;
        this.msg = "调用成功！";
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
