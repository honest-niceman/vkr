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
);