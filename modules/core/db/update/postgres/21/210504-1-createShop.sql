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
);