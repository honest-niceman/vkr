package com.company.vkr.web.screens.productintheshop;

import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.web.screenoptions.AlreadySelectedPurchasedProductOptions;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.ProductInTheShop;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;

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
            Collection<PurchasedProduct> purchasedProductCollection = (((AlreadySelectedPurchasedProductOptions) options).getPurchasedProducts());

            StringBuilder stringBuilder = new StringBuilder();

            String query = "";

            if(purchasedProductCollection.size()!=0){
                stringBuilder.append("select e from vkr_ProductInTheShop e where e.count > 0 and ");
                for (Iterator<PurchasedProduct> iterator = purchasedProductCollection.iterator(); iterator.hasNext(); ) {
                    PurchasedProduct p = iterator.next();

                    if (purchasedProductCollection.size() == 1) {
                        stringBuilder.append("e.id <> ").append("'").append(p.getProductInTheShop().getId()).append("'");
                        break;
                    }
                    else if (!iterator.hasNext()) {
                        stringBuilder.append("e.id <> ").append("'").append(p.getProductInTheShop().getId()).append("'");
                    } else {
                        stringBuilder.append("e.id <> ").append("'").append(p.getProductInTheShop().getId()).append("'").append(" and ");
                    }
                }
                query = stringBuilder.toString();
            } else {
                stringBuilder.append("select e from vkr_ProductInTheShop e where e.count > 0");
            }

            productInTheShopsDl.setQuery(query);
            productInTheShopsDl.load();
        }
    }
}