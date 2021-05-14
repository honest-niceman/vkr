package com.company.vkr.web.screens.analytics.earnings;

import com.company.vkr.entity.analytics.ShopEarnings;
import com.company.vkr.entity.business.PurchasedProduct;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@UiController("vkr_Shop.earnings")
@UiDescriptor("shop-earnings-screen.xml")
@LookupComponent("shopsTable")
@LoadDataBeforeShow
public class ShopEarningsScreen extends StandardLookup<Shop> {
    @Inject
    protected DataManager dataManager;
    @Inject
    private CollectionContainer<ShopEarnings> shopEarningsDc;
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;

    @Subscribe("shopsTable")
    public void onShopsTableSelection(Table.SelectionEvent<Shop> event) {
        if (!event.getSelected().isEmpty()) {
            drawGraph(event.getSelected().iterator().next());
        }
    }

    private void drawGraph(Shop shop) {
        purchasedProductsDl.setParameter("shop", shop);
        purchasedProductsDl.load();

        List<ShopEarnings> shopEarnings = new ArrayList<>();

        Collection<PurchasedProduct> purchasedProductCollection = purchasedProductsDl.getContainer().getItems();
        for (PurchasedProduct p : purchasedProductCollection) {
            BigDecimal profit = p.getPrice().subtract(p.getProductInTheShop().getPrice()).multiply(BigDecimal.valueOf(p.getCount()));
            shopEarnings.add(shopEarnings(p.getPurchase().getDate(), profit));
        }

        shopEarningsDc.setItems(shopEarnings);
    }


    private ShopEarnings shopEarnings(Date date, BigDecimal profit) {
        ShopEarnings shopEarnings = dataManager.create(ShopEarnings.class);
        shopEarnings.setDate(date);
        shopEarnings.setProfit(profit);
        return shopEarnings;
    }
}