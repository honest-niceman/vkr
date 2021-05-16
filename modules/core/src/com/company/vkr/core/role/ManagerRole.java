package com.company.vkr.core.role;

import com.company.vkr.entity.analytics.ShopCategories;
import com.company.vkr.entity.analytics.ShopEarnings;
import com.company.vkr.entity.analytics.ShopStatistic;
import com.company.vkr.entity.business.ProductInTheShop;
import com.company.vkr.entity.business.Purchase;
import com.company.vkr.entity.business.PurchasedProduct;
import com.company.vkr.entity.company.Product;
import com.company.vkr.entity.company.ProductCategory;
import com.company.vkr.entity.network.Address;
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

@Role(name = ManagerRole.NAME)
public class ManagerRole extends AnnotatedRoleDefinition {
    public final static String NAME = "ManagerRole";

    @ScreenAccess(screenIds = {"application-vkr", "vkr_manage_Shop.browse", "vkr_Shop.earnings",
            "vkr_ShopCategoriesScreen", "aboutWindow", "help", "settings", "vkr_manager_Shop.edit", "vkr_Address.edit",
            "vkr_ProductInTheShop.browse", "vkr_ProductInTheShop.edit", "vkr_Shop.edit", "sec$User.changePassword"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @EntityAccess(entityClass = Address.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = Shop.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = ProductInTheShop.class, operations = EntityOp.READ)
    @EntityAccess(entityClass = ShopCategories.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = ShopEarnings.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = PurchasedProduct.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Product.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = ProductCategory.class, operations = {EntityOp.READ})
    @EntityAccess(entityClass = Purchase.class, operations = {EntityOp.READ})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityClass = Address.class, modify = "*")
    @EntityAttributeAccess(entityClass = Shop.class, modify = "*")
    @EntityAttributeAccess(entityClass = ProductInTheShop.class, modify = "*")
    @EntityAttributeAccess(entityClass = ShopCategories.class, modify = "*")
    @EntityAttributeAccess(entityClass = ShopEarnings.class, modify = "*")
    @EntityAttributeAccess(entityClass = ShopStatistic.class, modify = "*")
    @EntityAttributeAccess(entityClass = PurchasedProduct.class, modify = "*")
    @EntityAttributeAccess(entityClass = Product.class, modify = "*")
    @EntityAttributeAccess(entityClass = ProductCategory.class, modify = "*")
    @EntityAttributeAccess(entityClass = Purchase.class, modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }
}
