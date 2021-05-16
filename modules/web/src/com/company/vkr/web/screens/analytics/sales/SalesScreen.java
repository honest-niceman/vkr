package com.company.vkr.web.screens.analytics.sales;

import com.company.vkr.entity.analytics.ShopStatistic;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.entity.network.Shop;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@UiController("vkr_SalesScreen")
@UiDescriptor("sales-screen.xml")
public class SalesScreen extends Screen {
    @Inject
    protected DataManager dataManager;
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;
    @Inject
    private CollectionContainer<ShopStatistic> shopStatisticDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        purchasedProductsDl.setParameter("networkCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        purchasedProductsDl.load();
        Map<Shop, IntSummaryStatistics> map = purchasedProductsDl.getContainer().getItems().stream()
                .collect(Collectors.groupingBy(PurchasedProduct::getShop, Collectors.summarizingInt(PurchasedProduct::getCount)));

        List<ShopStatistic> shopStatisticList = new ArrayList<>();

        for (Map.Entry<Shop, IntSummaryStatistics> entry : map.entrySet()) {
            shopStatisticList.add(shopStatistic(entry.getKey(), entry.getValue().getSum()));
        }

        shopStatisticDc.setItems(shopStatisticList);
    }

    private ShopStatistic shopStatistic(Shop shop, Long totalCount) {
        ShopStatistic shopStatistic = dataManager.create(ShopStatistic.class);
        shopStatistic.setShop(shop);
        shopStatistic.setTotalPurchasedProducts(totalCount);
        return shopStatistic;
    }

}