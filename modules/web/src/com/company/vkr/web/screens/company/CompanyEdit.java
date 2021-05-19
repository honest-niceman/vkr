package com.company.vkr.web.screens.company;

import com.company.vkr.entity.company.Product;
import com.company.vkr.web.screens.product.ProductEdit;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.vkr.entity.company.Company;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;

@UiController("vkr_Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
@LoadDataBeforeShow
public class CompanyEdit extends StandardEditor<Company> {
    @Inject
    private CollectionLoader<Product> productsDl;
    @Inject
    private PickerField<User> companyCeoField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<Product> productsTable;
    @Inject
    private DataContext dataContext;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        productsDl.setParameter("company", getEditedEntity());
        productsDl.load();

        companyCeoField.setValue(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        screenBuilders.editor(productsTable)
                .newEntity()
                .withInitializer(product -> {// lambda to initialize new instance
                    product.setCompany(getEditedEntity());
                })
                .withParentDataContext(dataContext)
                .withScreenClass(ProductEdit.class)    // specific editor screen
                .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                .build()
                .show();
    }
}