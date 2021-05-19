package com.company.vkr.web.screens.purchase.randomdata;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.Purchase;
import com.company.vkr.entity.business.PurchasedProduct;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@UiController("vkr_RandomPurchasesGeneratorScreen")
@UiDescriptor("random-purchases-generator-screen.xml")
public class RandomPurchasesGeneratorScreen extends Screen {
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<ProductInTheShop> productsInTheShopDl;

    private Random random = new Random();

    private void generatePurchasedProducts(){
        productsInTheShopDl.setView("productInTheShop-view");
        productsInTheShopDl.load();

        CommitContext commitContext = new CommitContext();

        for (int i = 0; i < 250; i++) {
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
            String date_31_12_2020 = "1609459140000";

            Date randomDate = new Date(ThreadLocalRandom.current()
                    .nextLong(Long.parseLong(date_01_01_2020), Long.parseLong(date_31_12_2020)));

            purchase.setDate(randomDate);

            purchase.setCustomer(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
            purchase.setTotalPrice(totalPrice);
            commitContext.addInstanceToCommit(purchase);
        }

        dataManager.commit(commitContext);
    }

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        generatePurchasedProducts();
    }
}