package com.company.vkr.web.screens.analytics.categories;

import com.company.vkr.entity.analytics.ShopCategories;
import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.entity.company.ProductCategory;
import com.company.vkr.entity.network.Shop;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@UiController("vkr_ShopCategoriesScreen")
@UiDescriptor("shop-categories-screen.xml")
public class ShopCategoriesScreen extends Screen {
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionContainer<ShopCategories> shopCategoriesDc;
    @Inject
    private CollectionLoader<Shop> shopsDl;

    @Subscribe("shopsTable")
    public void onShopsTableSelection(Table.SelectionEvent<Shop> event) {
        if(!event.getSelected().isEmpty()){
            drawCategories(event.getSelected().iterator().next());
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        shopsDl.setParameter("manager", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        shopsDl.setParameter("networkCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser());

        shopsDl.load();

        if(shopsDl.getContainer().getItems().size()!=0){
            drawCategories(shopsDl.getContainer().getItems().get(0));
        }
    }

    private void loadPurchasedProducts(Shop shop){
        purchasedProductsDl.setQuery("select e from vkr_PurchasedProduct e where e.shop = :shop");
        purchasedProductsDl.setParameter("shop",shop);
        purchasedProductsDl.load();
    }

    private HashMap<ProductCategory,Long> getProductCategoryHashMap(Map<ProductInTheShop, IntSummaryStatistics> map){
        HashMap<ProductCategory,Long> productCategoryLongHashMap = new LinkedHashMap<>();

        Iterator<Map.Entry<ProductInTheShop, IntSummaryStatistics>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ProductInTheShop, IntSummaryStatistics> pair = it.next();

            ProductCategory productCategory = pair.getKey().getProduct().getCategory();
            Long currentProductTotal = pair.getValue().getSum();

            Long total = currentProductTotal + productCategoryLongHashMap.getOrDefault(productCategory, Long.valueOf("0"));

            productCategoryLongHashMap.put(productCategory,total);

            it.remove();
        }
        return productCategoryLongHashMap;
    }

    private void drawCategories(Shop shop) {
        loadPurchasedProducts(shop);

        Map<ProductInTheShop, IntSummaryStatistics> map = purchasedProductsDl.getContainer().getItems().stream()
                .collect(Collectors.groupingBy(PurchasedProduct::getProductInTheShop, Collectors.summarizingInt(PurchasedProduct::getCount)));

        HashMap<ProductCategory,Long> productCategoryLongHashMap = getProductCategoryHashMap(map);

        ArrayList<ShopCategories> shopCategories = getCategoriesValuesList(productCategoryLongHashMap);

        shopCategoriesDc.setItems(shopCategories);
    }

    private ArrayList<ShopCategories> getCategoriesValuesList(HashMap<ProductCategory, Long> productCategoryLongHashMap) {
        ArrayList<ShopCategories> shopCategories = new ArrayList<>();

        for (Map.Entry<ProductCategory, Long> pair : productCategoryLongHashMap.entrySet()) {
            shopCategories.add(shopCategories(pair.getKey(), pair.getValue()));
        }

        return shopCategories;
    }


    private ShopCategories shopCategories(ProductCategory productCategory, Long total){
        ShopCategories shopCategories = dataManager.create(ShopCategories.class);
        shopCategories.setProductCategory(productCategory.getName());
        shopCategories.setPurchasedNumber(total);
        return shopCategories;
    }

}