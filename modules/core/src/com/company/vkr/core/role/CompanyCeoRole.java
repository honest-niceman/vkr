package com.company.vkr.core.role;

import com.company.vkr.entity.company.Company;
import com.company.vkr.entity.company.Product;
import com.company.vkr.entity.company.ProductCategory;
import com.haulmont.cuba.security.app.role.AnnotatedRoleDefinition;
import com.haulmont.cuba.security.app.role.annotation.EntityAccess;
import com.haulmont.cuba.security.app.role.annotation.EntityAttributeAccess;
import com.haulmont.cuba.security.app.role.annotation.Role;
import com.haulmont.cuba.security.app.role.annotation.ScreenAccess;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.security.role.EntityAttributePermissionsContainer;
import com.haulmont.cuba.security.role.EntityPermissionsContainer;
import com.haulmont.cuba.security.role.ScreenPermissionsContainer;

@Role(name = CompanyCeoRole.NAME)
public class CompanyCeoRole extends AnnotatedRoleDefinition {
    public final static String NAME = "CompanyCeoRole";

    @ScreenAccess(screenIds = {"vkr_Company.browse", "application-vkr", "vkr_Company.edit", "vkr_Product.edit"})
    @Override
    public ScreenPermissionsContainer screenPermissions() {
        return super.screenPermissions();
    }

    @EntityAccess(entityClass = Company.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = Product.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @EntityAccess(entityClass = ProductCategory.class, operations = {EntityOp.CREATE, EntityOp.UPDATE, EntityOp.DELETE, EntityOp.READ})
    @Override
    public EntityPermissionsContainer entityPermissions() {
        return super.entityPermissions();
    }

    @EntityAttributeAccess(entityClass = Company.class, modify = "*")
    @EntityAttributeAccess(entityClass = Product.class, modify = "*")
    @EntityAttributeAccess(entityClass = ProductCategory.class, modify = "*")
    @Override
    public EntityAttributePermissionsContainer entityAttributePermissions() {
        return super.entityAttributePermissions();
    }
}