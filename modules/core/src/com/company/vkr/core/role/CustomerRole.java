package com.company.vkr.core.role;

import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.Purchase;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.entity.network.Network;
import com.company.vkr.entity.network.Shop;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = CustomerRole.NAME)
public class CustomerRole extends AnnotatedRoleDefinition {
    public final static String NAME = "Customer";

    @ScreenAccess(screenIds = {"vkr_Purchase.browse", "application-vkr", "vkr_PurchasedProduct.edit",
            "vkr_Purchase.edit", "vkr_ProductInTheShop.browse"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @EntityAccess(entityClass = Purchase.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = PurchasedProduct.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = Network.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Shop.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = ProductInTheShop.class, operations = {EntityOp.READ})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityClass = Purchase.class, modify = "*")
    @EntityAttributeAccess(entityClass = PurchasedProduct.class, modify = "*")
    @EntityAttributeAccess(entityClass = Network.class, modify = "*")
    @EntityAttributeAccess(entityClass = Shop.class, modify = "*")
    @EntityAttributeAccess(entityClass = ProductInTheShop.class, modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }
}