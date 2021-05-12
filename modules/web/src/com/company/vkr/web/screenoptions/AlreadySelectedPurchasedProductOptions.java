package com.company.vkr.web.screenoptions;

import com.haulmont.cuba.gui.screen.ScreenOptions;

public class AlreadySelectedPurchasedProductOptions implements ScreenOptions {
    private StringBuilder purchasedProducts;

    public AlreadySelectedPurchasedProductOptions(StringBuilder purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public StringBuilder getPurchasedProducts() {
        return purchasedProducts;
    }
}
