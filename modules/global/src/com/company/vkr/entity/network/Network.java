package com.company.vkr.entity.network;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "VKR_NETWORK")
@Entity(name = "vkr_Network")
@NamePattern("%s|name")
public class Network extends StandardEntity {
    private static final long serialVersionUID = -3217420613543518435L;

    @Column(name = "NAME", length = 63)
    private String name;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "network")
    @Composition
    private List<Shop> shops;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NETWORK_CEO_ID")
    private User networkCeo;

    public User getNetworkCeo() {
        return networkCeo;
    }

    public void setNetworkCeo(User networkCeo) {
        this.networkCeo = networkCeo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}