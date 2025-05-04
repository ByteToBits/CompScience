/*
  ITO4132
  --Monash Auto Schema File and Initial Data
  --ma-schema-insert.sql

  Description:
  This file creates the Monash Auto tables and populates several of the
  tables (those shown in purple on the supplied model).

  You should read this schema file carefully and be sure you understand the
  various data requirements.

Author: FIT Database Teaching Team
License: Copyright Monash University, unless otherwise stated. All Rights Reserved.
COPYRIGHT WARNING
Warning
This material is protected by copyright. For use within Monash University only. NOT FOR RESALE.
Do not remove this notice.

*/

DROP TABLE customer CASCADE CONSTRAINTS;

DROP TABLE part CASCADE CONSTRAINTS;

DROP TABLE part_charge CASCADE CONSTRAINTS;

DROP TABLE pay_mode CASCADE CONSTRAINTS;

DROP TABLE service CASCADE CONSTRAINTS;

DROP TABLE service_job CASCADE CONSTRAINTS;

DROP TABLE vehicle CASCADE CONSTRAINTS;

DROP TABLE vendor CASCADE CONSTRAINTS;

CREATE TABLE customer (
    cust_no       NUMBER(6) NOT NULL,
    cust_name     VARCHAR2(50) NOT NULL,
    cust_street   VARCHAR2(50) NOT NULL,
    cust_town     VARCHAR2(30) NOT NULL,
    cust_pcode    CHAR(4) NOT NULL,
    cust_phone    CHAR(10) NOT NULL
);

COMMENT ON COLUMN customer.cust_no IS
    'Customer number';

COMMENT ON COLUMN customer.cust_name IS
    'Customer name';

COMMENT ON COLUMN customer.cust_street IS
    'Customer street address';

COMMENT ON COLUMN customer.cust_town IS
    'Customer town';

COMMENT ON COLUMN customer.cust_pcode IS
    'Customer post code';

COMMENT ON COLUMN customer.cust_phone IS
    'Customer contact phone number';

ALTER TABLE customer ADD CONSTRAINT customer_pk PRIMARY KEY ( cust_no );

CREATE TABLE part (
    part_code          CHAR(6) NOT NULL,
    part_description   VARCHAR2(50) NOT NULL,
    part_unit_cost     NUMBER(7, 2) NOT NULL,
    part_stock         NUMBER(3) NOT NULL,
    vendor_id          NUMBER(4) NOT NULL
);

COMMENT ON COLUMN part.part_code IS
    'Part code';

COMMENT ON COLUMN part.part_description IS
    'Part description';

COMMENT ON COLUMN part.part_unit_cost IS
    'Part unit cost';

COMMENT ON COLUMN part.part_stock IS
    'Current part stock';

COMMENT ON COLUMN part.vendor_id IS
    'Vendor identifier';

ALTER TABLE part ADD CONSTRAINT part_pk PRIMARY KEY ( part_code );

CREATE TABLE part_charge (
    part_charge_no   NUMBER(7) NOT NULL,
    pc_quantity      NUMBER(3) NOT NULL,
    pc_linecost      NUMBER(7, 2) NOT NULL,
    serv_no          NUMBER(6) NOT NULL,
    sj_job_no        NUMBER(2) NOT NULL,
    part_code        CHAR(6) NOT NULL
);

COMMENT ON COLUMN part_charge.part_charge_no IS
    'Surrogate key to identify part charges';

COMMENT ON COLUMN part_charge.pc_linecost IS
    'Total line costs for these parts';

COMMENT ON COLUMN part_charge.serv_no IS
    'Service identifier';

COMMENT ON COLUMN part_charge.sj_job_no IS
    'Job number - task to complete within service';

COMMENT ON COLUMN part_charge.part_code IS
    'Part code';

ALTER TABLE part_charge ADD CONSTRAINT part_charge_pk PRIMARY KEY ( part_charge_no
);

ALTER TABLE part_charge
    ADD CONSTRAINT part_charge_uq UNIQUE ( part_code,
                                           serv_no,
                                           sj_job_no );

CREATE TABLE pay_mode (
    pay_mode_code          CHAR(1) NOT NULL,
    pay_mode_description   VARCHAR2(20) NOT NULL
);

COMMENT ON TABLE pay_mode IS
    'Lookup table to control payment types';

COMMENT ON COLUMN pay_mode.pay_mode_code IS
    'Code for payment mode';

COMMENT ON COLUMN pay_mode.pay_mode_description IS
    'Description of payment mode';

ALTER TABLE pay_mode ADD CONSTRAINT pay_mode_pk PRIMARY KEY ( pay_mode_code );

CREATE TABLE service (
    serv_no             NUMBER(6) NOT NULL,
    serv_date           DATE NOT NULL,
    serv_drop_off       DATE NOT NULL,
    serv_req_pickup     DATE NOT NULL,
    serv_ready_pickup   DATE,
    serv_kms            NUMBER(6) NOT NULL,
    serv_labour_cost    NUMBER(6, 2),
    serv_parts_cost     NUMBER(7, 2),
    serv_instructions   VARCHAR2(100) NOT NULL,
    pay_mode_code       CHAR(1) NOT NULL,
    veh_vin             CHAR(17) NOT NULL,
    cust_no             NUMBER(6) NOT NULL
);

COMMENT ON COLUMN service.serv_no IS
    'Service identifier';

COMMENT ON COLUMN service.serv_date IS
    'Date of service';

COMMENT ON COLUMN service.serv_drop_off IS
    'Service drop off time';

COMMENT ON COLUMN service.serv_req_pickup IS
    'Customer requested service pickup time';

COMMENT ON COLUMN service.serv_ready_pickup IS
    'Time that the vehicle was ready for pickup';

COMMENT ON COLUMN service.serv_kms IS
    'Km reading of vehicle at service time';

COMMENT ON COLUMN service.serv_labour_cost IS
    'Total labour cost for this service';

COMMENT ON COLUMN service.serv_parts_cost IS
    'Total cost of all parts used for this service';

COMMENT ON COLUMN service.serv_instructions IS
    'Instructions from owner for this service (jobs to complete)';

COMMENT ON COLUMN service.pay_mode_code IS
    'Code for payment mode';

COMMENT ON COLUMN service.veh_vin IS
    'Vehicle VIN';

COMMENT ON COLUMN service.cust_no IS
    'Customer number';

ALTER TABLE service ADD CONSTRAINT service_pk PRIMARY KEY ( serv_no );

CREATE TABLE service_job (
    serv_no               NUMBER(6) NOT NULL,
    sj_job_no             NUMBER(2) NOT NULL,
    sj_task_description   VARCHAR2(50) NOT NULL
);

COMMENT ON COLUMN service_job.serv_no IS
    'Service identifier';

COMMENT ON COLUMN service_job.sj_job_no IS
    'Job number - task to complete within service';

COMMENT ON COLUMN service_job.sj_task_description IS
    'Job task description';

ALTER TABLE service_job ADD CONSTRAINT service_job_pk PRIMARY KEY ( sj_job_no,
                                                                    serv_no );

CREATE TABLE vehicle (
    veh_vin     CHAR(17) NOT NULL,
    veh_rego    VARCHAR2(6) NOT NULL,
    veh_year    DATE NOT NULL,
    veh_make    VARCHAR2(30) NOT NULL,
    veh_model   VARCHAR2(30) NOT NULL,
    cust_no     NUMBER(6)
);

COMMENT ON COLUMN vehicle.veh_vin IS
    'Vehicle VIN - see https://www.infrastructure.gov.au/vehicles/imports/vins.aspx';

COMMENT ON COLUMN vehicle.veh_rego IS
    'Vehicles current registration number';

COMMENT ON COLUMN vehicle.veh_year IS
    'Year vehicle was manufactured';

COMMENT ON COLUMN vehicle.veh_make IS
    'Make of vehicle';

COMMENT ON COLUMN vehicle.veh_model IS
    'Model of vehicle';

COMMENT ON COLUMN vehicle.cust_no IS
    'MA Customer number of current owner';

ALTER TABLE vehicle ADD CONSTRAINT vehicle_pk PRIMARY KEY ( veh_vin );

CREATE TABLE vendor (
    vendor_id        NUMBER(4) NOT NULL,
    vendor_name      VARCHAR2(50) NOT NULL,
    vendor_address   VARCHAR2(50) NOT NULL,
    vendor_phone     CHAR(10) NOT NULL
);

COMMENT ON COLUMN vendor.vendor_id IS
    'Vendor identifier';

COMMENT ON COLUMN vendor.vendor_name IS
    'Vendor name';

COMMENT ON COLUMN vendor.vendor_address IS
    'Vendore address';

COMMENT ON COLUMN vendor.vendor_phone IS
    'Vendor phone contact';

ALTER TABLE vendor ADD CONSTRAINT vendor_pk PRIMARY KEY ( vendor_id );

ALTER TABLE vendor ADD CONSTRAINT vendor_name_uq UNIQUE ( vendor_name );

ALTER TABLE service
    ADD CONSTRAINT customer_service_fk FOREIGN KEY ( cust_no )
        REFERENCES customer ( cust_no );

ALTER TABLE vehicle
    ADD CONSTRAINT customer_vehicle_fk FOREIGN KEY ( cust_no )
        REFERENCES customer ( cust_no );

ALTER TABLE part_charge
    ADD CONSTRAINT part_part_charge_fk FOREIGN KEY ( part_code )
        REFERENCES part ( part_code );

ALTER TABLE service
    ADD CONSTRAINT pay_mode_service_fk FOREIGN KEY ( pay_mode_code )
        REFERENCES pay_mode ( pay_mode_code );

ALTER TABLE part_charge
    ADD CONSTRAINT service_job_part_charge_fk FOREIGN KEY ( sj_job_no,
                                                         serv_no )
        REFERENCES service_job ( sj_job_no,
                                 serv_no );

ALTER TABLE service_job
    ADD CONSTRAINT service_service_job_fk FOREIGN KEY ( serv_no )
        REFERENCES service ( serv_no );

ALTER TABLE service
    ADD CONSTRAINT vehicle_service_fk FOREIGN KEY ( veh_vin )
        REFERENCES vehicle ( veh_vin );

ALTER TABLE part
    ADD CONSTRAINT vendor_part_fk FOREIGN KEY ( vendor_id )
        REFERENCES vendor ( vendor_id );

----- Initial Data Insert --------------------------------------------

REM INSERTING into CUSTOMER
SET DEFINE OFF;
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1000,'Andres Syphas','093 Sutteridge Court','Caulfield','3162','9571953915');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1010,'Charlotta Schimke','089 Roth Court','Caulfield North','3161','6053395648');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1020,'Cathrine Lynes','20 Texas Place','Clayton','3168','9678403727');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1030,'Farrel Grazier','7043 Jay Circle','Caulfield Junction','3161','3504231495');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1040,'Angie Eouzan','83 Talmadge Junction','Caulfield','3162','7355393095');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1050,'Butch Japp','6660 Eggendart Avenue','Caulfield','3162','6715573197');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1060,'Tasha Obispo','57119 Prentice Park','Caulfield','3162','1803631411');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1070,'Suzi Buxsy','7787 Brickson Park Lane','Caulfield','3162','2338078149');
Insert into CUSTOMER (CUST_NO,CUST_NAME,CUST_STREET,CUST_TOWN,CUST_PCODE,CUST_PHONE) values (1080,'Fredra Doulton','5021 Golf View Park','Caulfield','3162','5625108047');

REM INSERTING into VEHICLE
SET DEFINE OFF;
insert into VEHICLE values ('WBAWR33598P984354','I6W872', to_date('2018','yyyy'), 'Toyota', 'Hilux', 1000);
insert into VEHICLE values ('WAUVFAFH4AN857561','Z7K189', to_date('2020','yyyy'), 'Mazda', '3', 1010);
insert into VEHICLE values ('WAUGFAFC8FN843179','R9X386', to_date('2017','yyyy'), 'Hyundai', 'i30', 1020);
insert into VEHICLE values ('JN1CV6EK6FM569421','C3Y667', to_date('2021','yyyy'), 'Mazda', 'CX-5',1030);
insert into VEHICLE values ('5XXGM4A74DG668202','Y9B216', to_date('2015','yyyy'), 'Jaguar', 'XJ6',1040);
insert into VEHICLE values ('4T1BK3DB4CU320186','C9Z301', to_date('2021','yyyy'), 'Mitsubishi', 'Lancer',1050);
insert into VEHICLE values ('WA1CGCFE6BD111395','Q2V771', to_date('2020','yyyy'), 'BMW', '3 Series',1040);
insert into VEHICLE values ('19UYA42792A297253','K9I306', to_date('2016','yyyy'), 'Volkswagen', 'Tiguan',1060);
insert into VEHICLE values ('19XFB2E59DE245929','J9Z085', to_date('2019','yyyy'), 'Nissan', 'Maxima',1070);
insert into VEHICLE values ('WA1DGAFE9CD490452','F4I963', to_date('2014','yyyy'), 'Ford', 'Falcon',1080);
insert into VEHICLE values ('3GTU1YEJ1BG975085','D1E595', to_date('2016','yyyy'), 'Audi', 'A4',1040);
insert into VEHICLE values ('TRUWT28N921709039','H6D767', to_date('2017','yyyy'), 'Ford', 'Focus',1080);

REM INSERTING into VENDOR
SET DEFINE OFF;
Insert into VENDOR (VENDOR_ID,VENDOR_NAME,VENDOR_ADDRESS,VENDOR_PHONE) values (10,'Australian Automotive Parts','38 Bellona Avenue, Regents Park, NSW 2143','0297381611');
Insert into VENDOR (VENDOR_ID,VENDOR_NAME,VENDOR_ADDRESS,VENDOR_PHONE) values (20,'Automotive SuperStore','6/3 Packard Ave, Castle Hill NSW 2154','1300889116');
Insert into VENDOR (VENDOR_ID,VENDOR_NAME,VENDOR_ADDRESS,VENDOR_PHONE) values (30,'Supercheap Auto','25 Nepean Hwy, Mentone VIC 3194','0395850399');

REM INSERTING into PART
SET DEFINE OFF;
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('WR2419','Wesfil Oil Filter',58.99,35,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('WA5045','Wesfil Air Filter',128,20,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('ACFD22','Sterling Air Cleaner Hose',54.99,5,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('TRB025','Tridon Wiper Blade',22.99,28,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('341490','Castrol GTX Ultra Clean Engine Oil 5 lt',43.85,50,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('ONE2-5','Nulon One Coolant',158,20,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('N32780','Protex Brake Shoes',60.99,45,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('FR8HDC','Bosch Resistor Spark Plug',12.99,52,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('T23000','Gates Timing Belt',75.99,15,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('CTG009','Protorque Injector Pump',107,12,30);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('EMS697','Exhaust Manifold Gasket',23.45,2,10);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('NKR6T1','NGK Standard Spark Plug',13,80,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('R2132P','Ryco FuelFilter',10.6,28,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('DB1849','Bendix Brake pad set',71.72,10,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('338166','Castrol Power Steering Fluid',12.29,50,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('GEN123','Rear Tail Light set',34.95,10,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('TIM333','Motorkool A/C Compressor',207,5,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('TPS146','Tridon Oil Pressure Sensor',146.95,5,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('478020','Nolathane UBolts',46.29,10,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('KCA415','Whiteline Chamber Bolt Kit',28.76,27,20);
Insert into PART (PART_CODE,PART_DESCRIPTION,PART_UNIT_COST,PART_STOCK,VENDOR_ID) values ('CPC012','CPC Radiator Cap',14.56,10,10);

REM INSERTING into PAY_MODE
SET DEFINE OFF;
Insert into PAY_MODE (PAY_MODE_CODE,PAY_MODE_DESCRIPTION) values ('C','Credit Card');
Insert into PAY_MODE (PAY_MODE_CODE,PAY_MODE_DESCRIPTION) values ('S','Cash');
Insert into PAY_MODE (PAY_MODE_CODE,PAY_MODE_DESCRIPTION) values ('E','Elec Funds Transfer');

commit;