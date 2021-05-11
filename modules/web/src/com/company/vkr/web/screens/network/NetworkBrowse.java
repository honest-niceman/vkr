package com.company.vkr.web.screens.network;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Network;

import javax.inject.Inject;

@UiController("vkr_Network.browse")
@UiDescriptor("network-browse.xml")
@LookupComponent("networksTable")
@LoadDataBeforeShow
public class NetworkBrowse extends StandardLookup<Network> {
    @Inject
    private CollectionLoader<Network> networksDl;

    @Subscribe
    public void onInit(InitEvent event) {
        networksDl.setParameter("networkCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        networksDl.load();
    }
}