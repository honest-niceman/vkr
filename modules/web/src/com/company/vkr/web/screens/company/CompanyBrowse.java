package com.company.vkr.web.screens.company;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.company.Company;

import javax.inject.Inject;

@UiController("vkr_Company.browse")
@UiDescriptor("company-browse.xml")
@LookupComponent("companiesTable")
@LoadDataBeforeShow
public class CompanyBrowse extends StandardLookup<Company> {
    @Inject
    private CollectionLoader<Company> companiesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        companiesDl.setParameter("companyCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        companiesDl.load();
    }

}