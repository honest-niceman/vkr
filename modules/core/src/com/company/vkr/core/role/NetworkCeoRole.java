package com.company.vkr.core.role;

import com.company.vkr.entity.analytics.ShopStatistic;
import com.company.vkr.entity.network.Network;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

//use manager role with this role to configure networkCeo correctly
@Role(name = NetworkCeoRole.NAME)
public class NetworkCeoRole extends AnnotatedRoleDefinition {
    public final static String NAME = "NetworkCeo";

    @ScreenAccess(screenIds = {"vkr_Network.browse", "vkr_SalesScreen", "vkr_Network.edit"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @EntityAccess(entityClass = Network.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = ShopStatistic.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = User.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = UserRole.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityClass = Network.class, modify = "*")
    @EntityAttributeAccess(entityClass = ShopStatistic.class, modify = "*")
    @EntityAttributeAccess(entityClass = User.class, modify = "*")
    @EntityAttributeAccess(entityClass = UserRole.class, modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }
}
