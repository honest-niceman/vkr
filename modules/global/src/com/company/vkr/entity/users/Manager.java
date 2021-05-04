package com.company.vkr.entity.users;

import com.company.vkr.entity.network.Shop;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "vkr_Manager")
@NamePattern("%s %s %s|firstName,lastName,email")
public class Manager extends User {
    private static final long serialVersionUID = 3629705519303323945L;

    @OneToMany(mappedBy = "manager")
    private List<Shop> shops;

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}