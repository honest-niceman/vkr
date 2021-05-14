package com.company.vkr.web.screens.purchase;

import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.web.screenoptions.AlreadySelectedPurchasedProductOptions;
import com.company.vkr.web.screens.purchasedproduct.PurchasedProductEdit;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.RemoveOperation;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.Purchase;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@UiController("vkr_Purchase.edit")
@UiDescriptor("purchase-edit.xml")
@EditedEntityContainer("purchaseDc")
@LoadDataBeforeShow
public class PurchaseEdit extends StandardEditor<Purchase> {
    @Inject
    private TextField<BigDecimal> totalPriceField;
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;
    @Inject
    private GroupTable<PurchasedProduct> purchasedProductsTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataContext dataContext;
    @Inject
    private ButtonsPanel buttonsPanel;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        purchasedProductsDl.setParameter("purchase", getEditedEntity());
        purchasedProductsDl.load();

        if(!new EntityStates().isNew(getEditedEntity())){
            buttonsPanel.setVisible(false);
        }

        getEditedEntity().setDate(AppBeans.get(TimeSource.class).currentTimestamp());
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if(purchasedProductsTable.getItems()!=null) {
            List<PurchasedProduct> purchasedProductList = (List<PurchasedProduct>) purchasedProductsTable.getItems().getItems();
            for (PurchasedProduct purchasedProduct : purchasedProductList) {
                totalPrice = totalPrice.add(purchasedProduct.getPrice().multiply(BigDecimal.valueOf(purchasedProduct.getCount())));
            }
        }
        return totalPrice;
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        getEditedEntity().setCustomer(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        if (purchasedProductsTable.getItems() != null) {
            screenBuilders.editor(purchasedProductsTable)
                    .newEntity()
                    .withInitializer(purchasedProduct -> {          // lambda to initialize new instance
                        purchasedProduct.setPurchase(getEditedEntity());
                    })
                    .withOptions(new AlreadySelectedPurchasedProductOptions(purchasedProductsTable.getItems().getItems()))
                    .withParentDataContext(dataContext)
                    .withScreenClass(PurchasedProductEdit.class)    // specific editor screen
                    .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                    .withAfterCloseListener(purchasedProductEditAfterScreenCloseEvent -> {
                        totalPriceField.setValue(calculateTotalPrice());
                    })
                    .build()
                    .show();
        }
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        if(purchasedProductsTable.getSingleSelected()!=null){
            if(purchasedProductsTable.getItems()!=null){
                screenBuilders.editor(purchasedProductsTable)
                        .editEntity(purchasedProductsTable.getSingleSelected())
                        .withInitializer(purchasedProduct -> {          // lambda to initialize new instance
                            purchasedProduct.setPurchase(getEditedEntity());
                        })
                        .withOptions(new AlreadySelectedPurchasedProductOptions(purchasedProductsTable.getItems().getItems()))
                        .withParentDataContext(dataContext)
                        .withScreenClass(PurchasedProductEdit.class)    // specific editor screen
                        .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                        .withAfterCloseListener(purchasedProductEditAfterScreenCloseEvent -> {
                            totalPriceField.setValue(calculateTotalPrice());
                        })
                        .build()
                        .show();
            }
        }
    }

    @Install(to = "purchasedProductsTable.remove", subject = "afterActionPerformedHandler")
    private void purchasedProductsTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<PurchasedProduct> afterActionPerformedEvent) {
        totalPriceField.setValue(calculateTotalPrice());
    }

}