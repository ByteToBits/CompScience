
-- Drop SQL Tables Created From Customer Orders

DROP TABLE CUSTOMER cascade constraints purge;

DROP TABLE ORDERLINE cascade constraints purge;

DROP TABLE ORDERS cascade constraints purge;

DROP TABLE PROD_CATEGORY cascade constraints purge;

DROP TABLE PRODUCT cascade constraints purge;

-- cat = Catalog which liss all the Items in the recycle bin
select * from cat; 