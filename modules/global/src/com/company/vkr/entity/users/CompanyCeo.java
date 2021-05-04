package com.company.vkr.entity.users;

import com.company.vkr.entity.company.Company;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "vkr_CompanyCeo")
@NamePattern("%s %s %s|firstName,lastName,email")
public class CompanyCeo extends User {
    private static final long serialVersionUID = -6447606505799872620L;

    @OneToMany(mappedBy = "companyCeo")
    private List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}