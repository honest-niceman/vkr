package com.company.vkr.web.screens.network;

import com.company.vkr.entity.network.Address;
import com.company.vkr.entity.network.Shop;
import com.company.vkr.web.screens.shop.ShopEdit;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Network;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;

@UiController("vkr_Network.edit")
@UiDescriptor("network-edit.xml")
@EditedEntityContainer("networkDc")
@LoadDataBeforeShow
public class NetworkEdit extends StandardEditor<Network> {
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<Shop> shopsTable;
    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionLoader<Shop> shopsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        shopsDl.setParameter("network", getEditedEntity());
        shopsDl.load();

        getEditedEntity().setNetworkCeo(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        screenBuilders.editor(shopsTable)
                .newEntity()
                .withInitializer(shop -> {          // lambda to initialize new instance
                    shop.setNetwork(getEditedEntity());
                })
                .withParentDataContext(dataContext)
                .withScreenClass(ShopEdit.class)    // specific editor screen
                .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                .build()
                .show();
    }
}