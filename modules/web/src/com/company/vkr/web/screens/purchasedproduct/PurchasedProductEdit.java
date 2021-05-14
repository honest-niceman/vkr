package com.company.vkr.web.screens.purchasedproduct;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.web.screenoptions.AlreadySelectedPurchasedProductOptions;
import com.company.vkr.web.screens.productintheshop.ProductInTheShopBrowse;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.PurchasedProduct;

import javax.inject.Inject;
import java.util.Collection;

@UiController("vkr_PurchasedProduct.edit")
@UiDescriptor("purchased-product-edit.xml")
@EditedEntityContainer("purchasedProductDc")
@LoadDataBeforeShow
public class PurchasedProductEdit extends StandardEditor<PurchasedProduct> {
    private Collection<PurchasedProduct> alreadySelectedProductsNames;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private PickerField<ProductInTheShop> productInTheShopField;
    @Inject
    private TextField<Integer> countField;
    @Inject
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof AlreadySelectedPurchasedProductOptions) {
            alreadySelectedProductsNames = ((AlreadySelectedPurchasedProductOptions) options).getPurchasedProducts();
        }
    }

    @Subscribe("productInTheShopField.lookup")
    public void onProductInTheShopFieldLookup(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(productInTheShopField)
                .withScreenClass(ProductInTheShopBrowse.class)
                .withOptions(new AlreadySelectedPurchasedProductOptions(alreadySelectedProductsNames))
                .build()
                .show();
    }

    @Subscribe("productInTheShopField")
    public void onProductInTheShopFieldValueChange(HasValue.ValueChangeEvent<ProductInTheShop> event) {
        if(event.getValue()!=null){
            getEditedEntity().setPrice(event.getValue().getPrice());
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if(countField.getValue()!=null && productInTheShopField.getValue()!=null){
            if(countField.getValue() > productInTheShopField.getValue().getCount()){
                notifications.create()
                        .withCaption("Available number of "
                                + productInTheShopField.getValue().getProduct().getName()
                                + " in the "
                                + productInTheShopField.getValue().getShop().getName()
                                + " is "
                                + productInTheShopField.getValue().getCount())
                        .show();
                event.preventCommit();
            } else {
                getEditedEntity().setShop(productInTheShopField.getValue().getShop());
            }
        }
    }
}