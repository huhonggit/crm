package com.crm.common.page;


import java.io.Serializable;

/**
 * 封装分页对象，包含范型德查询对象 及其基础分页对象
 *
 * @author huhong
 */
public class PageObject<T> implements Serializable {

    BasePage page;

    T obj;

    public BasePage getPage() {
        return page;
    }

    public void setPage(BasePage page) {
        this.page = page;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

