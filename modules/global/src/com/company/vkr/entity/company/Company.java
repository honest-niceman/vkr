package com.company.vkr.entity.company;

import com.company.vkr.entity.users.CompanyCeo;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

@Table(name = "VKR_COMPANY")
@Entity(name = "vkr_Company")
@NamePattern("%s|name")
public class Company extends StandardEntity {
    private static final long serialVersionUID = 4057765411962094053L;

    @Column(name = "NAME", length = 63)
    private String name;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "company")
    private List<Product> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_CEO_ID")
    private CompanyCeo companyCeo;

    public CompanyCeo getCompanyCeo() {
        return companyCeo;
    }

    public void setCompanyCeo(CompanyCeo companyCeo) {
        this.companyCeo = companyCeo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}