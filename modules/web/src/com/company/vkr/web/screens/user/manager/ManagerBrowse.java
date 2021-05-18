package com.company.vkr.web.screens.user.manager;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("sec$UserManager.browse")
@UiDescriptor("manager-browse.xml")
@LookupComponent("usersTable")
@LoadDataBeforeShow
public class ManagerBrowse extends StandardLookup<User> {
    @Inject
    private CollectionLoader<User> usersDl;
    @Named("usersTable.create")
    private CreateAction<User> usersTableCreate;
    @Named("usersTable.edit")
    private EditAction<User> usersTableEdit;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        usersDl.setParameter("networkCeo", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getLogin());
        usersDl.load();

        usersTableCreate.setScreenClass(ManagerEdit.class);
        usersTableEdit.setScreenClass(ManagerEdit.class);
    }

}