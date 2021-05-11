package com.company.vkr.web.screens.shop.manager;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.network.Address;
import com.company.vkr.web.screens.productintheshop.ProductInTheShopEdit;
import com.company.vkr.web.screens.shop.ceo.ShopEdit;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.SuggestionPickerField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("vkr_manager_Shop.edit")
@UiDescriptor("manager-shop-edit.xml")
@EditedEntityContainer("shopDc")
@LoadDataBeforeShow
public class ManagerShopEdit extends StandardEditor<Shop> {
    @Inject
    private CollectionLoader<ProductInTheShop> productsInTheShop;
    @Inject
    private CollectionLoader<Address> addressesLoader;
    @Inject
    private CollectionContainer<Address> addressesDc;
    @Inject
    private SuggestionPickerField<Address> addressField;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<ProductInTheShop> productsInTheShopTable;
    @Inject
    private DataContext dataContext;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        productsInTheShop.setParameter("shop", getEditedEntity());
        productsInTheShop.load();
    }

    @Subscribe
    public void onInit(InitEvent event) {
        addressesLoaderSearchExecutor();
    }

    public void addressesLoaderSearchExecutor(){
        addressesLoader.load();
        List<Address> addressList = new ArrayList<>(addressesDc.getItems());
        addressField.setSearchExecutor((searchString, searchParams) ->
                addressList.stream()
                        .filter(address ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(address), searchString))
                        .collect(Collectors.toList()));
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        screenBuilders.editor(productsInTheShopTable)
                .newEntity()
                .withInitializer(productInTheShop -> {          // lambda to initialize new instance
                    productInTheShop.setShop(getEditedEntity());
                })
                .withScreenClass(ProductInTheShopEdit.class)    // specific editor screen
                .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                .withAfterCloseListener(productInTheShopEditAfterScreenCloseEvent -> {
                    productsInTheShop.load();
                    this.getScreenData().loadAll();
                })
                .build()
                .show();
    }

}