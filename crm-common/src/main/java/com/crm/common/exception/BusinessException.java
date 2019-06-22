package com.crm.common.exception;

import com.crm.common.bo.JsonResult;

import java.io.Serializable;

/**
 * 对外暴露的自定义异常
 */
public class BusinessException extends RuntimeException implements Serializable {

    private Integer code;

    private String msg;



    public BusinessException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public final JsonResult getResult() {
        return new JsonResult(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
