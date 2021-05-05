package com.company.vkr.web.screens.address;

import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Address;

@UiController("vkr_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
@LoadDataBeforeShow
public class AddressEdit extends StandardEditor<Address> {
}