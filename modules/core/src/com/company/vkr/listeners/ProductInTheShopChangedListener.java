package com.company.vkr.listeners;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.company.Product;
import com.company.vkr.entity.network.Shop;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Component("vkr_ProductInTheShopChangedListener")
public class ProductInTheShopChangedListener {

    @Inject
    private DataManager dataManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<ProductInTheShop, UUID> event) {
        if (event.getType().equals(EntityChangedEvent.Type.CREATED)) {
            reduceCountOfTheProduct(event);
            mergeProductsInTheShop(event);
        } else if (event.getType().equals(EntityChangedEvent.Type.UPDATED)) {
            updateCountOfTheProduct(event);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onProductInTheShopBeforeCommit(EntityChangedEvent<ProductInTheShop, UUID> event) {
        if (event.getType().equals(EntityChangedEvent.Type.DELETED)) {
            increaseCountOfTheProduct(event);
        }
    }

    private void updateCountOfTheProduct(EntityChangedEvent<ProductInTheShop, UUID> event) {
        UUID uuid = event.getEntityId().getValue();
        ProductInTheShop productInTheShop = dataManager.load(ProductInTheShop.class).id(uuid).one();
        Product product = dataManager.load(Product.class).id(productInTheShop.getProduct().getId()).one();

        Integer oldCount = product.getCount();

        Integer oldProductInTheShopCount = (Integer) event.getChanges().getOldValue("count");

        if (oldProductInTheShopCount < product.getCount()) {
            product.setCount(oldCount - (productInTheShop.getCount() - oldProductInTheShopCount));
        } else if (oldProductInTheShopCount > product.getCount()) {
            product.setCount(oldCount + (oldProductInTheShopCount - productInTheShop.getCount()));
        }

        dataManager.commit(product);
    }

    private void increaseCountOfTheProduct(EntityChangedEvent<ProductInTheShop, UUID> event) {
        UUID uuid = event.getEntityId().getValue();
        ProductInTheShop productInTheShop = dataManager.load(ProductInTheShop.class).id(uuid).one();
        Product product = dataManager.load(Product.class).id(productInTheShop.getProduct().getId()).one();

        Integer oldCount = product.getCount();

        product.setCount(oldCount + productInTheShop.getCount());

        dataManager.commit(product);
    }

    private void reduceCountOfTheProduct(EntityChangedEvent<ProductInTheShop, UUID> event) {
        UUID uuid = event.getEntityId().getValue();
        ProductInTheShop productInTheShop = dataManager.load(ProductInTheShop.class).id(uuid).one();
        Product product = dataManager.load(Product.class).id(productInTheShop.getProduct().getId()).one();

        Integer oldCount = product.getCount();

        product.setCount(oldCount - productInTheShop.getCount());

        dataManager.commit(product);
    }

    private void mergeProductsInTheShop(EntityChangedEvent<ProductInTheShop, UUID> event) {
        UUID uuid = event.getEntityId().getValue();
        ProductInTheShop newProductInTheShop = dataManager.load(ProductInTheShop.class).id(uuid).one();
        Shop shop = dataManager.load(Shop.class).id(newProductInTheShop.getShop().getId()).view("shop-view-with-products").one();
        List<ProductInTheShop> productInTheShopList = shop.getProducts();

        for (ProductInTheShop existedProduct : productInTheShopList) {
            if (existedProduct.getProduct().equals(newProductInTheShop.getProduct())) {
                if(!existedProduct.equals(newProductInTheShop)){
                    Integer existedCount = existedProduct.getCount();
                    existedProduct.setCount(existedCount + newProductInTheShop.getCount());
                    existedProduct.setPrice(newProductInTheShop.getPrice());

                    dataManager.remove(newProductInTheShop);
                    dataManager.commit(existedProduct);
                }
            }
        }
    }
}