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
);