-- update VKR_PURCHASE set CUSTOMER_ID = <default_value> where CUSTOMER_ID is null ;
alter table VKR_PURCHASE alter column CUSTOMER_ID set not null ;
