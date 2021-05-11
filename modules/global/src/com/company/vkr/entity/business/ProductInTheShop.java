package com.company.vkr.entity.business;

import com.company.vkr.entity.company.Product;
import com.company.vkr.entity.network.Shop;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@PublishEntityChangedEvents
@Table(name = "VKR_PRODUCT_IN_THE_SHOP")
@Entity(name = "vkr_ProductInTheShop")
@NamePattern("%s %s|shop,product")
public class ProductInTheShop extends StandardEntity {
    private static final long serialVersionUID = -3274085638881915844L;

    @NotNull
    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @NotNull
    @JoinColumn(name = "SHOP_ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Shop shop;

    @Column(name = "PRICE")
    @Positive
    private BigDecimal price;

    @Column(name = "COUNT_")
    @PositiveOrZero
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}