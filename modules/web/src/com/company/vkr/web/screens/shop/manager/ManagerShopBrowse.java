package com.company.vkr.web.screens.shop.manager;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("vkr_manage_Shop.browse")
@UiDescriptor("manager-shop-browse.xml")
@LookupComponent("shopsTable")
@LoadDataBeforeShow
public class ManagerShopBrowse extends StandardLookup<Shop> {
    @Named("shopsTable.edit")
    private EditAction<Shop> shopsTableEdit;
    @Inject
    private CollectionLoader<Shop> shopsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        shopsTableEdit.setScreenClass(ManagerShopEdit.class);

        shopsDl.setParameter("networkCeo",AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        shopsDl.setParameter("manager", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
    }

}