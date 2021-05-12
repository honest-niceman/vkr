package com.company.vkr.web.screens.productintheshop;

import com.company.vkr.web.screenoptions.AlreadySelectedPurchasedProductOptions;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.ProductInTheShop;

import javax.inject.Inject;

@UiController("vkr_ProductInTheShop.browse")
@UiDescriptor("product-in-the-shop-browse.xml")
@LookupComponent("productInTheShopsTable")
@LoadDataBeforeShow
public class ProductInTheShopBrowse extends StandardLookup<ProductInTheShop> {
    @Inject
    private CollectionLoader<ProductInTheShop> productInTheShopsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof AlreadySelectedPurchasedProductOptions) {
            String product_params = (((AlreadySelectedPurchasedProductOptions) options).getPurchasedProducts()).toString();

            String query = "select e from vkr_ProductInTheShop e where e.product.name NOT IN (:product_params) and e.count > 0";

            productInTheShopsDl.setQuery(query);
            productInTheShopsDl.setParameter("product_params", product_params);
            productInTheShopsDl.load();
        }
    }
}