package com.company.vkr.web.screens.productintheshop;

import com.company.vkr.entity.company.Product;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.SuggestionPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.business.ProductInTheShop;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private Integer oldCount;
    @Inject
    private DataManager dataManager;

    private Random random;

    @Subscribe
    public void onInit(InitEvent event) {
        productsDl.load();
        List<Product> productList = new ArrayList<>(productsDc.getItems());
        productField.setSearchExecutor((searchString, searchParams) ->
                productList.stream()
                        .filter(product ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(product), searchString))
                        .collect(Collectors.toList()));
        random = new Random();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (!new EntityStates().isNew(getEditedEntity())) {
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

        if (!new EntityStates().isNew(getEditedEntity())) {
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

    @Subscribe("productField")
    public void onProductFieldValueChange(HasValue.ValueChangeEvent<Product> event) {
        if (event.getValue() != null) {
            int rand = random.nextInt(20);
            String coef = "";
            if (rand < 10) {
                coef = "1.0" + rand;
            } else {
                coef = "1." + rand;
            }
            getEditedEntity().setPrice(event.getValue().getPrice().multiply(BigDecimal.valueOf(Double.parseDouble(coef))));
            if (event.getValue().getCount() > 10_000) {
                getEditedEntity().setCount(event.getValue().getCount() / (1 + random.nextInt(50)));
            } else if (event.getValue().getCount() > 1_000) {
                getEditedEntity().setCount(event.getValue().getCount() / (1 + random.nextInt(10)));
            } else if (event.getValue().getCount() > 100) {
                getEditedEntity().setCount(event.getValue().getCount() / 2);
            } else {
                getEditedEntity().setCount(event.getValue().getCount());
            }
        }
    }
}