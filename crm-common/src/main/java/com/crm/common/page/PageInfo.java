package com.crm.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 前端分页返回信息  前端分页只需要total 总数和list数据
 */
public class PageInfo<T> implements Serializable {

    private Long total;

    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
