alter table VKR_PRODUCT add constraint FK_VKR_PRODUCT_ON_CATEGORY foreign key (CATEGORY_ID) references VKR_PRODUCT_CATEGORY(ID);
alter table VKR_PRODUCT add constraint FK_VKR_PRODUCT_ON_COMPANY foreign key (COMPANY_ID) references VKR_COMPANY(ID);
create index IDX_VKR_PRODUCT_ON_CATEGORY on VKR_PRODUCT (CATEGORY_ID);
create index IDX_VKR_PRODUCT_ON_COMPANY on VKR_PRODUCT (COMPANY_ID);
