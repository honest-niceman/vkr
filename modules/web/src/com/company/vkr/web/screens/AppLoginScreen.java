package com.company.vkr.web.screens;

import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.app.login.LoginScreen;
import com.haulmont.cuba.web.gui.screen.ScreenDependencyUtils;
import com.vaadin.ui.Dependency;
import javax.inject.Inject;


@Route(path = "login", root = true)
@UiController("app-login")
@UiDescriptor("app-login-screen.xml")
public class AppLoginScreen extends LoginScreen {
    @Inject
    private MessageDialogFacet helpDialog;

    @Subscribe
    public void onAppLoginScreenInit(InitEvent event) {
        loadStyles();
    }

    @Subscribe("submit")
    public void onSubmit(Action.ActionPerformedEvent event) {
        login();
    }

    protected void loadStyles() {
        ScreenDependencyUtils.addScreenDependency(this,
                "vaadin://brand-login-screen/login.css", Dependency.Type.STYLESHEET);
    }

    @Override
    protected void initLogoImage() {
        logoImage.setSource(RelativePathResource.class)
                .setPath("VAADIN/brand-login-screen/cart.svg");
        logoImage.setWidth("300px");
        logoImage.setHeight("300px");
    }

    @Subscribe("helpBtn")
    public void onHelpBtnClick(Button.ClickEvent event) {
        helpDialog.show();
    }
}