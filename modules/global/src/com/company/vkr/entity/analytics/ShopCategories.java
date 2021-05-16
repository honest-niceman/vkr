package com.company.vkr.entity.analytics;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "vkr_ShopCategories")
public class ShopCategories extends BaseUuidEntity {
    private static final long serialVersionUID = -6697675064384498703L;

    @MetaProperty
    private Long purchasedNumber;

    @MetaProperty
    private String productCategory;

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getPurchasedNumber() {
        return purchasedNumber;
    }

    public void setPurchasedNumber(Long purchasedNumber) {
        this.purchasedNumber = purchasedNumber;
    }
}