-- alter table VKR_PURCHASED_PRODUCT add column SHOP_ID uuid ^
-- update VKR_PURCHASED_PRODUCT set SHOP_ID = <default_value> ;
-- alter table VKR_PURCHASED_PRODUCT alter column SHOP_ID set not null ;
alter table VKR_PURCHASED_PRODUCT add column SHOP_ID uuid not null ;
