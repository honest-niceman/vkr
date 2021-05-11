package com.company.vkr.web.screens.product;

import com.company.vkr.entity.company.ProductCategory;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.components.SuggestionPickerField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.company.Product;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("vkr_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {
    @Inject
    protected DataManager dataManager;
    @Inject
    private SuggestionPickerField<ProductCategory> categoryField;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private CollectionContainer<ProductCategory> productCategoriesDc;
    @Inject
    private CollectionLoader<ProductCategory> productCategoriesLoader;

    @Subscribe
    public void onInit(InitEvent event) {
        productCategoriesLoader.load();
        List<ProductCategory> productCategoryList = new ArrayList<>(productCategoriesDc.getItems());
        categoryField.setSearchExecutor((searchString, searchParams) ->
                productCategoryList.stream()
                        .filter(productCategory ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(productCategory), searchString))
                        .collect(Collectors.toList()));
    }

    @Install(to = "categoryField", subject = "enterActionHandler")
    private void categoryFieldEnterActionHandler(String searchString) {
        if(!isProductCategorieExist(searchString)){
            ProductCategory productCategory = dataManager.create(ProductCategory.class);
            productCategory.setName(searchString);
            categoryField.setValue(productCategory);
            productCategoriesLoader.load();
        }
    }

    private boolean isProductCategorieExist(String name){
        productCategoriesLoader.load();
        return productCategoriesDc.getItems().stream().anyMatch(productCategory -> {
            return productCategory.getName().equalsIgnoreCase(name);
        });
    }
}