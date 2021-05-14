package com.company.vkr.web.screenoptions;

import com.company.vkr.entity.business.PurchasedProduct;
import com.haulmont.cuba.gui.screen.ScreenOptions;

import java.util.Collection;

public class AlreadySelectedPurchasedProductOptions implements ScreenOptions {
    private Collection<PurchasedProduct> purchasedProducts;

    public AlreadySelectedPurchasedProductOptions(Collection<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public Collection<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }
}
