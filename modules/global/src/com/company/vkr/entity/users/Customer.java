package com.company.vkr.entity.users;

import com.company.vkr.entity.business.Purchase;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "vkr_Customer")
@NamePattern("%s %s %s|firstName,lastName,email")
public class Customer extends User {
    private static final long serialVersionUID = 3716414241776108698L;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

}