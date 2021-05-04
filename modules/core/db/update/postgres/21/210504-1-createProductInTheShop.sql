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
);