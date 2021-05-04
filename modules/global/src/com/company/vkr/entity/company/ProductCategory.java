package com.company.vkr.entity.company;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Table(name = "VKR_PRODUCT_CATEGORY")
@Entity(name = "vkr_ProductCategory")
@NamePattern("%s|name")
public class ProductCategory extends StandardEntity {
    private static final long serialVersionUID = 5514310391753884414L;

    @Column(name = "NAME", unique = true, length = 63)
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}