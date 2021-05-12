package com.company.vkr.listeners;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.entity.company.Product;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.UUID;

@Component("vkr_PurchasedProductChangedListener")
public class PurchasedProductChangedListener {

    @Inject
    private DataManager dataManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<PurchasedProduct, UUID> event) {
        if (event.getType().equals(EntityChangedEvent.Type.CREATED)) {
            reduceCountOfTheProduct(event);
        }
    }

    private void reduceCountOfTheProduct(EntityChangedEvent<PurchasedProduct, UUID> event) {
        UUID uuid = event.getEntityId().getValue();
        PurchasedProduct purchasedProduct = dataManager.load(PurchasedProduct.class).id(uuid).one();
        ProductInTheShop productInTheShop = dataManager.load(ProductInTheShop.class).id(purchasedProduct.getProductInTheShop().getId()).one();

        Integer purchasedCount = purchasedProduct.getCount();
        Integer productInTheShopCount = productInTheShop.getCount();

        productInTheShop.setCount(productInTheShopCount-purchasedCount);

        dataManager.commit(productInTheShop);
    }
}