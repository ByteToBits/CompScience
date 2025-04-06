
-- Applied 6-1: Purge Agent Customer Tables
--student id: 30428831
--student name: Tristan Sim Yook Min

DROP TABLE customer CASCADE constraints PURGE; 
DROP TABLE property_agent CASCADE constraints PURGE; 

-- cat = Catalog which liss all the Items in the recycle bin
select * from cat; 

-- purge Recycle Bin
purge recyclebin; 