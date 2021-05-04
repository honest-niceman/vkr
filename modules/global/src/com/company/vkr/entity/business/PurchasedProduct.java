package com.company.vkr.entity.business;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Table(name = "VKR_PURCHASED_PRODUCT")
@Entity(name = "vkr_PurchasedProduct")
@NamePattern("%s %s|purchase,productInTheShop")
public class PurchasedProduct extends StandardEntity {
    private static final long serialVersionUID = -7057428057820911902L;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_IN_THE_SHOP_ID")
    private ProductInTheShop productInTheShop;

    @Column(name = "COUNT_")
    @Positive
    private Integer count;

    @Column(name = "PRICE")
    @Positive
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PURCHASE_ID")
    private Purchase purchase;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductInTheShop getProductInTheShop() {
        return productInTheShop;
    }

    public void setProductInTheShop(ProductInTheShop productInTheShop) {
        this.productInTheShop = productInTheShop;
    }
}