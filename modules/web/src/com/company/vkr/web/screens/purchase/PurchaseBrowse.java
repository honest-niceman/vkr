package com.company.vkr.web.screens.purchase;

import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.Purchase;

import javax.inject.Inject;
import java.util.Collection;

@UiController("vkr_Purchase.browse")
@UiDescriptor("purchase-browse.xml")
@LookupComponent("purchasesTable")
@LoadDataBeforeShow
public class PurchaseBrowse extends StandardLookup<Purchase> {
    @Inject
    private CollectionLoader<Purchase> purchasesDl;
    @Inject
    private TextArea<String> textArea;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        purchasesDl.load();
        Collection<Purchase> purchaseCollection = purchasesDl.getContainer().getItems();
        for (Purchase p : purchaseCollection){
            stringBuilder.append("insert into VKR_PURCHASE (ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, TOTAL_PRICE, DATE_, CUSTOMER_ID) values (");
            stringBuilder.append("'").append(p.getId()).append("'").append(",")
                    .append("1").append(",")
                    .append("'2021-05-14 15:49:06'").append(",")
                    .append("'admin'").append(",")
                    .append("'2021-05-14 15:49:06'").append(",")
                    .append("null").append(",")
                    .append("null").append(",")
                    .append("null").append(",")
                    .append(p.getTotalPrice()).append(",")
                    .append("'2021-05-14 15:49:06'").append(",")
                    .append("'").append(p.getCustomer().getId()).append("'");
            stringBuilder.append(");\n");
        }

        textArea.setValue(stringBuilder.toString());
    }

}