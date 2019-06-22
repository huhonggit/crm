package com.crm.auth.service;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author huhong
 * @date 2019-06-22 13:58
 */
@Document
public class Test {

    private BigDecimal tt;

    public BigDecimal getTt() {
        return tt;
    }

    public void setTt(BigDecimal tt) {
        this.tt = tt;
    }
}
