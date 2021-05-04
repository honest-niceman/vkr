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
);