package com.company.vkr.web.screens.shop.manager;

import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;

import javax.inject.Named;

@UiController("vkr_manage_Shop.browse")
@UiDescriptor("manager-shop-browse.xml")
@LookupComponent("shopsTable")
@LoadDataBeforeShow
public class ManagerShopBrowse extends StandardLookup<Shop> {
    @Named("shopsTable.edit")
    private EditAction<Shop> shopsTableEdit;

    @Subscribe
    public void onInit(InitEvent event) {
        shopsTableEdit.setScreenClass(ManagerShopEdit.class);
    }

}