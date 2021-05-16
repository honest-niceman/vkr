package com.company.vkr.web.screens.purchasedproduct;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.PurchasedProduct;

import javax.inject.Inject;
import java.util.Collection;

@UiController("vkr_PurchasedProductScript.browse")
@UiDescriptor("purchased-product-browse-script.xml")
@LookupComponent("purchasedProductsTable")
@LoadDataBeforeShow
public class PurchasedProductBrowseScript extends StandardLookup<PurchasedProduct> {
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;
    @Inject
    private TextArea<String> textArea;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        purchasedProductsDl.load();
        Collection<PurchasedProduct> purchasedProductCollection = purchasedProductsDl.getContainer().getItems();
        StringBuilder stringBuilder = new StringBuilder();
        for(PurchasedProduct p : purchasedProductCollection){
               stringBuilder
                       .append("insert into VKR_PURCHASED_PRODUCT(ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, PRODUCT_IN_THE_SHOP_ID, POSITION_PRICE, SHOP_ID, COUNT_, PRICE, PURCHASE_ID)values (")
                       .append("'").append(p.getId()).append("'").append(",")
                       .append(1).append(",")
                       .append("'2021-05-14 16:42:57'").append(",")
                       .append("'admin'").append(",")
                       .append("'2021-05-14 16:42:57'").append(",")
                       .append("null").append(",")
                       .append("null").append(",")
                       .append("null").append(",")
                       .append("'").append(p.getProductInTheShop().getId()).append("'").append(",")
                       .append(p.getPositionPrice()).append(",")
                       .append("'").append(p.getShop().getId()).append("'").append(",")
                       .append(p.getCount()).append(",")
                       .append(p.getPrice()).append(",")
                       .append("'").append(p.getPurchase().getId()).append("'")
                       .append(");\n");
        }
        textArea.setValue(stringBuilder.toString());
    }

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        purchasedProductsDl.load();
        Collection<PurchasedProduct> purchasedProductCollection = purchasedProductsDl.getContainer().getItems();
        StringBuilder stringBuilder = new StringBuilder();
        for(PurchasedProduct p : purchasedProductCollection){
            stringBuilder
                    .append("insert into VKR_PURCHASED_PRODUCT(ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, PRODUCT_IN_THE_SHOP_ID, POSITION_PRICE, SHOP_ID, COUNT_, PRICE, PURCHASE_ID)values (")
                    .append("'").append(p.getId()).append("'").append(",")
                    .append(1).append(",")
                    .append("'2021-05-14 16:42:57'").append(",")
                    .append("'admin'").append(",")
                    .append("'2021-05-14 16:42:57'").append(",")
                    .append("null").append(",")
                    .append("null").append(",")
                    .append("null").append(",")
                    .append("'").append(p.getProductInTheShop().getId()).append("'").append(",")
                    .append(p.getPositionPrice()).append(",")
                    .append("'").append(p.getShop().getId()).append("'").append(",")
                    .append(p.getCount()).append(",")
                    .append(p.getPrice()).append(",")
                    .append("'").append(p.getPurchase().getId()).append("'")
                    .append(");\n");
        }
        textArea.setValue(stringBuilder.toString());
    }



}