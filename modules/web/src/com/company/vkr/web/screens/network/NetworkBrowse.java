package com.company.vkr.web.screens.network;

import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Network;

@UiController("vkr_Network.browse")
@UiDescriptor("network-browse.xml")
@LookupComponent("networksTable")
@LoadDataBeforeShow
public class NetworkBrowse extends StandardLookup<Network> {
}