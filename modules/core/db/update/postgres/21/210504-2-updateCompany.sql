-- update VKR_COMPANY set COMPANY_CEO_ID = <default_value> where COMPANY_CEO_ID is null ;
alter table VKR_COMPANY alter column COMPANY_CEO_ID set not null ;
