package com.company.vkr.web.screens.purchase;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.web.screenoptions.AlreadySelectedPurchasedProductOptions;
import com.company.vkr.web.screens.purchasedproduct.PurchasedProductEdit;
import com.haulmont.cuba.core.global.*;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<ProductInTheShop> productsInTheShopDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        generatePurchasedProducts();

        purchasedProductsDl.setParameter("purchase", getEditedEntity());
        purchasedProductsDl.load();

        if(!new EntityStates().isNew(getEditedEntity())){
            buttonsPanel.setVisible(false);
        }

        getEditedEntity().setDate(AppBeans.get(TimeSource.class).currentTimestamp());
    }

    private Random random = new Random();

    private void generatePurchasedProducts(){
        productsInTheShopDl.load();

        CommitContext commitContext = new CommitContext();

        for (int i = 0; i < 1000; i++) {
            Purchase purchase = dataManager.create(Purchase.class);
            List<ProductInTheShop> productInTheShopList = productsInTheShopDl.getContainer().getItems();
            int productInTheShopListSize = productInTheShopList.size();

            int numberOfPurchasedProducts = 1 + random.nextInt(12);

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (int j = 0; j < numberOfPurchasedProducts; j++) {
                ProductInTheShop productInTheShop = productInTheShopList.get(random.nextInt(productInTheShopListSize));

                int purchasedCount = 0;

                if(productInTheShop.getCount()>10_000){
                    purchasedCount = productInTheShop.getCount() / (200 + random.nextInt(50));
                } else  if(productInTheShop.getCount()>1_000){
                    purchasedCount = productInTheShop.getCount() / (30 + random.nextInt(20));
                } else if(productInTheShop.getCount()>100){
                    purchasedCount = productInTheShop.getCount() / (5 + random.nextInt(10));
                } else if(productInTheShop.getCount()>10) {
                    purchasedCount = productInTheShop.getCount() / 2;
                } else {
                    purchasedCount = productInTheShop.getCount();
                }

                PurchasedProduct purchasedProduct = dataManager.create(PurchasedProduct.class);
                purchasedProduct.setShop(productInTheShop.getShop());
                purchasedProduct.setCount(purchasedCount);
                purchasedProduct.setPrice(productInTheShop.getPrice());
                purchasedProduct.setPurchase(purchase);
                purchasedProduct.setProductInTheShop(productInTheShop);
                purchasedProduct.setPositionPrice(purchasedProduct.getPrice().multiply(BigDecimal.valueOf(purchasedProduct.getCount())));

                totalPrice = totalPrice.add(purchasedProduct.getPrice().multiply(BigDecimal.valueOf(purchasedProduct.getCount())));

                commitContext.addInstanceToCommit(purchasedProduct);
            }

            String date_01_01_2020 = "1577822400000";
            String date_31_01_2020 = "1580414400000";

            Date randomDate = new Date(ThreadLocalRandom.current()
                    .nextLong(Long.parseLong(date_01_01_2020), Long.parseLong(date_31_01_2020)));

            purchase.setDate(randomDate);

            purchase.setCustomer(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
            purchase.setTotalPrice(totalPrice);
            commitContext.addInstanceToCommit(purchase);
        }

        dataManager.commit(commitContext);
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