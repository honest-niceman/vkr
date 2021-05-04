package com.company.vkr.entity.users;

import com.company.vkr.entity.network.Network;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "vkr_NetworkCeo")
@NamePattern("%s %s %s|firstName,lastName,email")
public class NetworkCeo extends User {
    private static final long serialVersionUID = 7865272780820144438L;

    @OneToMany(mappedBy = "networkCeo")
    private List<Network> networks;

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }
}