package com.company.vkr.web.screens.productintheshop;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.ProductInTheShop;

import javax.inject.Inject;
import java.util.Collection;

@UiController("vkr_ProductInTheShopForScript.browse")
@UiDescriptor("product-in-the-shop-browse-for-script.xml")
@LookupComponent("productInTheShopsTable")
@LoadDataBeforeShow
public class ProductInTheShopBrowseForScript extends StandardLookup<ProductInTheShop> {
    @Inject
    private CollectionLoader<ProductInTheShop> productInTheShopsDl;
    @Inject
    private GroupTable<ProductInTheShop> productInTheShopsTable;
    @Inject
    private TextArea<String> textArea;

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        productInTheShopsDl.load();
        if(productInTheShopsTable.getItems()!=null){
            Collection<ProductInTheShop> productInTheShopCollection = productInTheShopsTable.getItems().getItems();
            StringBuilder stringBuilder = new StringBuilder();
            for (ProductInTheShop productInTheShop : productInTheShopCollection){
                stringBuilder.append("insert into VKR_PRODUCT_IN_THE_SHOP (ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, PRODUCT_ID, SHOP_ID, PRICE, COUNT_) values (")
                        .append("'").append(productInTheShop.getUuid()).append("'").append(",")
                        .append(1).append(",")
                        .append("'").append("2021-05-14 12:45:14").append("'").append(",")
                        .append("'").append("admin").append("'").append(",")
                        .append("'").append("2021-05-14 12:45:14").append("'").append(",")
                        .append("null").append(",")
                        .append("null").append(",")
                        .append("null").append(",")
                        .append("'").append(productInTheShop.getProduct().getUuid()).append("'").append(",")
                        .append("'").append(productInTheShop.getShop().getUuid()).append("'").append(",")
                        .append(productInTheShop.getPrice()).append(",")
                        .append(productInTheShop.getCount()).append(");")
                        .append("\n");
            }
            textArea.setValue(stringBuilder.toString());
        }
    }



    @Subscribe
    public void onInit(InitEvent event) {
        productInTheShopsDl.load();
        if(productInTheShopsTable.getItems()!=null){
            Collection<ProductInTheShop> productInTheShopCollection = productInTheShopsTable.getItems().getItems();
            StringBuilder stringBuilder = new StringBuilder();
            for (ProductInTheShop productInTheShop : productInTheShopCollection){
                stringBuilder.append("insert into VKR_PRODUCT_IN_THE_SHOP (ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, PRODUCT_ID, SHOP_ID, PRICE, COUNT_) values (")
                        .append("'").append(productInTheShop.getUuid()).append("'").append(",")
                        .append(1).append(",")
                        .append("'").append("2021-05-14 12:45:14").append("'").append(",")
                        .append("'").append("admin").append("'").append(",")
                        .append("'").append("2021-05-14 12:45:14").append("'").append(",")
                        .append("null").append(",")
                        .append("null").append(",")
                        .append("null").append(",")
                        .append("'").append(productInTheShop.getProduct().getUuid()).append("'").append(",")
                        .append("'").append(productInTheShop.getShop().getUuid()).append("'").append(",")
                        .append(productInTheShop.getPrice()).append(",")
                        .append(productInTheShop.getCount()).append(");")
                        .append("\n");
            }
            textArea.setValue(stringBuilder.toString());
        }
    }


}