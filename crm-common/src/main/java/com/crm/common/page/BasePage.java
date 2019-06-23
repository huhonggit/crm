package com.crm.common.page;

/**
 * 基础分页
 *
 * @author huhong
 * @date 2018/11/16
 */
public class BasePage {

    /**
     * 页码
     */
    private int pageNum;
    /**
     * 当前页最大size
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSkip (){
        return pageSize*(pageNum-1);
    }
}
