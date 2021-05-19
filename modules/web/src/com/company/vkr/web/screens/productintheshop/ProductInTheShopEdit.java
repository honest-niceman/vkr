package com.company.vkr.web.screens.productintheshop;

import com.company.vkr.entity.company.Product;
import com.company.vkr.entity.network.Shop;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.SuggestionPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.ProductInTheShop;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("vkr_ProductInTheShop.edit")
@UiDescriptor("product-in-the-shop-edit.xml")
@EditedEntityContainer("productInTheShopDc")
@LoadDataBeforeShow
public class ProductInTheShopEdit extends StandardEditor<ProductInTheShop> {
    @Inject
    private CollectionContainer<Product> productsDc;
    @Inject
    private SuggestionPickerField<Product> productField;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private TextField<Integer> countField;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionLoader<Product> productsDl;
    @Inject
    private DataManager dataManager;

    private Integer oldCount;

    @Subscribe
    public void onInit(InitEvent event) {
        productsDl.load();
        List<Product> productList = new ArrayList<>(productsDc.getItems());
        productField.setSearchExecutor((searchString, searchParams) ->
                productList.stream()
                        .filter(product ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(product), searchString))
                        .collect(Collectors.toList()));
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if(!new EntityStates().isNew(getEditedEntity())){
            oldCount = getEditedEntity().getCount();
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (productField.getValue() != null && countField.getValue() != null) {
            if (!isEnoughProducts(productField.getValue(), countField.getValue())) {
                event.preventCommit();
            }
        }

        if(!new EntityStates().isNew(getEditedEntity())){
            updateCountOfTheProduct(getEditedEntity());
        }
    }

    private void updateCountOfTheProduct(ProductInTheShop notCommitedEntity) {
        ProductInTheShop productInTheShop = dataManager.load(ProductInTheShop.class).id(notCommitedEntity.getId()).one();
        Product product = dataManager.load(Product.class).id(productInTheShop.getProduct().getId()).one();

        Integer productCount = product.getCount();

        Integer newCount = notCommitedEntity.getCount();
        Integer oldCount = productInTheShop.getCount();

        if (newCount > oldCount) {
            product.setCount(productCount - (newCount - oldCount));
        } else if (newCount < oldCount) {
            product.setCount(productCount + (oldCount - newCount));
        }

        dataManager.commit(product);
    }

    private boolean isEnoughProducts(Product product, Integer wantedValue) {
        if (new EntityStates().isNew(getEditedEntity())) {
            if (product.getCount().compareTo(wantedValue) == -1) {
                notifications.create()
                        .withCaption("Max value for current product is " + productField.getValue().getCount())
                        .show();
                return false;
            } else {
                return true;
            }
        } else {
            Integer sum = oldCount + product.getCount();
            if (sum.compareTo(wantedValue) == -1) {
                notifications.create()
                        .withCaption("Max value for current product is " + sum)
                        .show();
                return false;
            } else {
                return true;
            }
        }
    }
}