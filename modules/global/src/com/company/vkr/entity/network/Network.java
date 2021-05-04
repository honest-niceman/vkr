package com.company.vkr.entity.network;

import com.company.vkr.entity.users.NetworkCeo;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

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
    private List<Shop> shops;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NETWORK_CEO_ID")
    @NotNull
    private NetworkCeo networkCeo;

    public NetworkCeo getNetworkCeo() {
        return networkCeo;
    }

    public void setNetworkCeo(NetworkCeo networkCeo) {
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