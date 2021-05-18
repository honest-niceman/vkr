package com.company.vkr.web.screens.purchase;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.Purchase;

import javax.inject.Inject;

@UiController("vkr_Purchase.browse")
@UiDescriptor("purchase-browse.xml")
@LookupComponent("purchasesTable")
@LoadDataBeforeShow
public class PurchaseBrowse extends StandardLookup<Purchase> {
    @Inject
    private CollectionLoader<Purchase> purchasesDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        purchasesDl.setQuery("select e from vkr_Purchase e where e.customer = :customer");
        purchasesDl.setParameter("customer", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        purchasesDl.load();
    }
}