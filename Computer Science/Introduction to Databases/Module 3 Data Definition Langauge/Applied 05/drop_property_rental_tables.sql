
-- Drop SQL Tables Created From Property Rentals
-- The cascade Constraints Removes all Foreign Key Constraints;
-- This makeas the order of Table Deletion not important

DROP TABLE DAMAGE cascade constraints purge;

DROP TABLE MAINTENANCE cascade constraints purge;

DROP TABLE OWNER cascade constraints purge;

DROP TABLE PAYMENT cascade constraints purge;

DROP TABLE PAYMETHOD cascade constraints purge;

DROP TABLE PROPERTY cascade constraints purge;

DROP TABLE RENT cascade constraints purge;

DROP TABLE TENANT cascade constraints purge;

-- cat = Catalog which liss all the Items in the recycle bin
select * from cat; 

-- purge Recycle Bin
purge recyclebin; 