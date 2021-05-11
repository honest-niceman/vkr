package com.company.vkr.web.screens.shop.ceo;

import com.company.vkr.entity.network.Address;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.components.SuggestionPickerField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;
import com.haulmont.cuba.security.entity.User;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("vkr_Shop.edit")
@UiDescriptor("shop-edit.xml")
@EditedEntityContainer("shopDc")
@LoadDataBeforeShow
public class ShopEdit extends StandardEditor<Shop> {
    @Inject
    private SuggestionPickerField<Address> addressField;
    @Inject
    private CollectionLoader<Address> addressesLoader;
    @Inject
    private CollectionContainer<Address> addressesDc;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private CollectionLoader<User> managersLoader;
    @Inject
    private CollectionContainer<User> managersDc;
    @Inject
    private SuggestionPickerField<User> managerField;

    @Subscribe
    public void onInit(InitEvent event) {
        addressesLoaderSearchExecutor();
        managersLoaderSearchExecutor();
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

    public void managersLoaderSearchExecutor(){
        //todo change query to select users with role manager
        //UUID managerGroup = UUID.fromString("daed0d15-c513-c70b-2893-a67ca0895522"); // manager group id
        UUID managerGroup = UUID.fromString("0fa2b1a5-1d68-4d69-9fbd-dff348347f93"); // company group id, just for testing
        managersLoader.setQuery("select u from sec$User u where u.group.id = :group");
        managersLoader.setParameter("group",managerGroup);
        managersLoader.load();
        List<User> managers = new ArrayList<>(managersDc.getItems());
        managerField.setSearchExecutor((searchString, searchParams) ->
                managers.stream()
                        .filter(user ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(user), searchString))
                        .collect(Collectors.toList()));
    }

}