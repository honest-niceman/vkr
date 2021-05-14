package com.company.vkr.entity.analytics;

import com.company.vkr.entity.network.Shop;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "vkr_ShopStatistic")
public class ShopStatistic extends BaseUuidEntity {
    private static final long serialVersionUID = -6216695696264139437L;

    @MetaProperty
    private Shop shop;

    @MetaProperty
    private Long totalPurchasedProducts;

    public void setTotalPurchasedProducts(Long totalPurchasedProducts) {
        this.totalPurchasedProducts = totalPurchasedProducts;
    }

    public Long getTotalPurchasedProducts() {
        return totalPurchasedProducts;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}