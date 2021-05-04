package com.company.vkr.entity.business;

import com.company.vkr.entity.users.Customer;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "VKR_PURCHASE")
@Entity(name = "vkr_Purchase")
@NamePattern("%s %s|customer,date")
public class Purchase extends StandardEntity {
    private static final long serialVersionUID = 1627534536662969685L;

    @Column(name = "TOTAL_PRICE")
    @Positive
    private BigDecimal totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_")
    private Date date;

    @OneToMany(mappedBy = "purchase")
    private List<PurchasedProduct> purchasedProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}