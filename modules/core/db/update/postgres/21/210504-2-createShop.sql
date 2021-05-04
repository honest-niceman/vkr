alter table VKR_SHOP add constraint FK_VKR_SHOP_ON_ADDRESS foreign key (ADDRESS_ID) references VKR_ADDRESS(ID);
alter table VKR_SHOP add constraint FK_VKR_SHOP_ON_NETWORK foreign key (NETWORK_ID) references VKR_NETWORK(ID);
alter table VKR_SHOP add constraint FK_VKR_SHOP_ON_MANAGER foreign key (MANAGER_ID) references SEC_USER(ID);
create index IDX_VKR_SHOP_ON_ADDRESS on VKR_SHOP (ADDRESS_ID);
create index IDX_VKR_SHOP_ON_NETWORK on VKR_SHOP (NETWORK_ID);
create index IDX_VKR_SHOP_ON_MANAGER on VKR_SHOP (MANAGER_ID);
