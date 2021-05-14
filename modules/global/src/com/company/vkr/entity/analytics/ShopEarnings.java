package com.company.vkr.entity.analytics;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

import java.math.BigDecimal;
import java.util.Date;

@MetaClass(name = "vkr_ShopEarnings")
public class ShopEarnings extends BaseUuidEntity {
    private static final long serialVersionUID = -1403320148503395266L;

    @MetaProperty
    private Date date;

    @MetaProperty
    private BigDecimal profit;

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}