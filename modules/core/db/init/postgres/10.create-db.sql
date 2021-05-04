-- begin VKR_PURCHASE
create table VKR_PURCHASE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TOTAL_PRICE decimal(19, 2),
    DATE_ timestamp,
    CUSTOMER_ID uuid,
    --
    primary key (ID)
)^
-- end VKR_PURCHASE
-- begin VKR_PURCHASED_PRODUCT
create table VKR_PURCHASED_PRODUCT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_IN_THE_SHOP_ID uuid not null,
    COUNT_ integer,
    PRICE decimal(19, 2),
    PURCHASE_ID uuid not null,
    --
    primary key (ID)
)^
-- end VKR_PURCHASED_PRODUCT
-- begin VKR_NETWORK
create table VKR_NETWORK (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(63),
    NETWORK_CEO_ID uuid not null,
    --
    primary key (ID)
)^
-- end VKR_NETWORK
-- begin VKR_PRODUCT
create table VKR_PRODUCT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(63),
    COUNT_ integer,
    PRICE decimal(19, 2),
    CATEGORY_ID uuid not null,
    COMPANY_ID uuid,
    --
    primary key (ID)
)^
-- end VKR_PRODUCT
-- begin VKR_PRODUCT_IN_THE_SHOP
create table VKR_PRODUCT_IN_THE_SHOP (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uuid not null,
    SHOP_ID uuid not null,
    PRICE decimal(19, 2),
    COUNT_ integer,
    --
    primary key (ID)
)^
-- end VKR_PRODUCT_IN_THE_SHOP
-- begin VKR_PRODUCT_CATEGORY
create table VKR_PRODUCT_CATEGORY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(63),
    --
    primary key (ID)
)^
-- end VKR_PRODUCT_CATEGORY
-- begin VKR_SHOP
create table VKR_SHOP (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(63),
    ADDRESS_ID uuid not null,
    NETWORK_ID uuid not null,
    MANAGER_ID uuid not null,
    --
    primary key (ID)
)^
-- end VKR_SHOP
-- begin VKR_COMPANY
create table VKR_COMPANY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(63),
    COMPANY_CEO_ID uuid,
    --
    primary key (ID)
)^
-- end VKR_COMPANY
-- begin VKR_ADDRESS
create table VKR_ADDRESS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COUNTRY varchar(63),
    CITY varchar(63),
    STREET varchar(63),
    NUMBER_ varchar(63),
    POSTAL_CODE varchar(63),
    --
    primary key (ID)
)^
-- end VKR_ADDRESS
-- begin SEC_USER
alter table SEC_USER add column DTYPE varchar(31) ^
update SEC_USER set DTYPE = 'sec$User' where DTYPE is null ^
-- end SEC_USER
