package com.company.vkr.web.screens.user;

import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

@UiController("sec$UserManager.edit")
@UiDescriptor("manager-edit.xml")
@EditedEntityContainer("userDc")
@LoadDataBeforeShow
public class ManagerEdit extends StandardEditor<User> {
    private static final String COMPANY_GROUP_ID = "0fa2b1a5-1d68-4d69-9fbd-dff348347f93";
    private static final String MANAGER_ROLE_ID = "f3b74065-e863-8133-e8d4-ce3558711a7a";

    @Inject
    private TextField<String> firstNameField;
    @Inject
    private PasswordEncryption passwordEncryption;
    @Inject
    private TextField<String> lastNameField;
    @Inject
    private DataManager dataManager;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        event.getDataContext().remove(getEditedEntity());

        User manager = createManager(event);
        assignRole(manager, event);

        Group group = dataManager.load(LoadContext.create(Group.class).setId(UUID.fromString(COMPANY_GROUP_ID)));
        manager.setGroup(group);
    }

    private void assignRole(User manager, BeforeCommitChangesEvent event) {
        //находим роль, которую создали программно
        Role role = dataManager.load(LoadContext.create(Role.class).setId(UUID.fromString(MANAGER_ROLE_ID)));
        UserRole userRole = event.getDataContext().create(UserRole.class); //создаем юзерРоль

        ArrayList<UserRole> userRoles = new ArrayList<>(); // создаем список юзерролей
        userRoles.add(userRole); // добавляем ранее созданную юзер роль

        manager.setUserRoles(userRoles); // присваем роли нашему доктору-юзеру

        // у юзерроли указываем роль, пользователя и имя роли которую создали в классе ManagerRole
        userRole.setRole(role);
        userRole.setUser(manager);
        userRole.setRoleName("ManagerRole");
    }

    private User createManager(BeforeCommitChangesEvent event){
        User manager = event.getDataContext().create(User.class);
        manager.setLogin(firstNameField.getValue());
        manager.setPassword(passwordEncryption.getPasswordHash(manager.getId(), firstNameField.getValue()));
        manager.setChangePasswordAtNextLogon(true);
        manager.setFirstName(firstNameField.getValue());
        manager.setLastName(lastNameField.getValue());

        return manager;
    }

}