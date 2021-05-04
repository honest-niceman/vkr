package com.company.vkr.entity.network;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "VKR_ADDRESS")
@Entity(name = "vkr_Address")
@NamePattern("%s %s|id,postalCode")
public class Address extends StandardEntity {
    private static final long serialVersionUID = 5169277641646417463L;

    @Column(name = "COUNTRY", length = 63)
    private String country;

    @Column(name = "CITY", length = 63)
    private String city;

    @Column(name = "STREET", length = 63)
    private String street;

    @Column(name = "NUMBER_", length = 63)
    private String number;

    @Column(name = "POSTAL_CODE", length = 63)
    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}