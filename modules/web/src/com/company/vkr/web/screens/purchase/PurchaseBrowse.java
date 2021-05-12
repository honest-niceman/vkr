package com.company.vkr.web.screens.purchase;

import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.Purchase;

@UiController("vkr_Purchase.browse")
@UiDescriptor("purchase-browse.xml")
@LookupComponent("purchasesTable")
@LoadDataBeforeShow
public class PurchaseBrowse extends StandardLookup<Purchase> {
}