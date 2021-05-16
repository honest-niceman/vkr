package com.company.vkr.web.screens.analytics.earnings;

import com.company.vkr.entity.analytics.ShopEarnings;
import com.company.vkr.entity.business.PurchasedProduct;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.network.Shop;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@UiController("vkr_Shop.earnings")
@UiDescriptor("shop-earnings-screen.xml")
@LookupComponent("shopsTable")
@LoadDataBeforeShow
public class ShopEarningsScreen extends StandardLookup<Shop> {
    private static final Logger log = LoggerFactory.getLogger(ShopEarningsScreen.class);
    @Inject
    protected DataManager dataManager;
    @Inject
    private CollectionContainer<ShopEarnings> shopEarningsDc;
    @Inject
    private CollectionLoader<PurchasedProduct> purchasedProductsDl;
    @Inject
    private CollectionLoader<Shop> shopsDl;

    @Subscribe("shopsTable")
    public void onShopsTableSelection(Table.SelectionEvent<Shop> event) {
        if (!event.getSelected().isEmpty()) {
            drawGraph(event.getSelected().iterator().next());
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        shopsDl.setParameter("manager", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        shopsDl.setParameter("networkCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getLogin());

        shopsDl.load();
    }

    private void drawGraph(Shop shop) {
        purchasedProductsDl.setQuery("select e from vkr_PurchasedProduct e where e.shop = :shop order by e.purchase.date");
        purchasedProductsDl.setParameter("shop", shop);
        purchasedProductsDl.load();

        List<ShopEarnings> shopEarnings = new ArrayList<>();

        Collection<PurchasedProduct> purchasedProductCollection = purchasedProductsDl.getContainer().getItems();
        HashMap<Date,BigDecimal> hm = new LinkedHashMap<>();

        for (PurchasedProduct p : purchasedProductCollection) {
            try {
                Date date1 = p.getPurchase().getDate();

                DateFormat outputFormatter = new SimpleDateFormat("yyyy/MM/dd");
                String output = outputFormatter.format(date1);

                Date date = DateUtils.parseDate(output, "yyyy/MM/dd");
                BigDecimal profit = hm.getOrDefault(date, BigDecimal.ZERO);
                profit = profit.add((p.getPrice().subtract(p.getProductInTheShop().getProduct().getPrice()).multiply(BigDecimal.valueOf(p.getCount()))));

                hm.put(date,profit);
            } catch (ParseException e) {
                log.error("Error during date converting", e);
            }
        }

        Iterator<Map.Entry<Date, BigDecimal>> it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Date, BigDecimal> pair = it.next();

            shopEarnings.add(shopEarnings(pair.getKey(), pair.getValue()));

            it.remove();
        }

        shopEarningsDc.setItems(shopEarnings);
    }


    private ShopEarnings shopEarnings(Date date, BigDecimal profit) {
        ShopEarnings shopEarnings = dataManager.create(ShopEarnings.class);
        shopEarnings.setDate(date);
        shopEarnings.setProfit(profit);
        return shopEarnings;
    }
}