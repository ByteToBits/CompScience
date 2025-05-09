/*****PLEASE ENTER YOUR DETAILS BELOW*****/
-- ITO4132
--T6-ma-json.sql


--Student ID: 30428831
--Student Name: Tristan Sim


/* Comments for your marker:




*/

/* Task 6 (a) SQL to MongoDB JSON
Task Breakdown:
1) Create a JSON document for each vehicle with its service history
2) Format the document with _id as customer_number + underscore + vehicle_vin
3) Create a nested structure with customer details, vehicle information, and service array
4) Calculate derived fields like total cost and number of services

Pseudocode:
1) Main Query: Join Tables (customer -> vehicle -> service)
2) Select Basic Vehicle Information (make, model, year, rego)
3) Create Nested Customer Object (cust_no, name, phone)
4) Calculate Number of Services with Subquery Count

5) Create Nested Array of Services with JSON_ARRAYAGG
6) For Each Service: Create Object with date, costs and total
7) Order Services by Date within Each Vehicle

8) Group Results by Vehicle and Customer Details to Aggregate Services
9) Format Each Document with Trailing Comma for Collection Assembly
*/

SELECT 
    JSON_OBJECT(
        '_id' VALUE c.cust_no || '_' || v.veh_vin,
        'customer' VALUE JSON_OBJECT(
            'custno' VALUE c.cust_no,
            'name' VALUE c.cust_name,
            'phone' VALUE c.cust_phone
        ),
        'rego_number' VALUE v.veh_rego,
        'make' VALUE v.veh_make,
        'model' VALUE v.veh_model,
        'year' VALUE TO_CHAR(v.veh_year, 'YYYY'),
        'noservices' VALUE (
            SELECT COUNT(*) 
            FROM service s 
            WHERE s.veh_vin = v.veh_vin
        ),
        'booked_services' VALUE JSON_ARRAYAGG(
            JSON_OBJECT(
                'servno' VALUE s.serv_no,
                'servdate' VALUE TO_CHAR(s.serv_date, 'DD-Mon-YYYY'),
                'labourcost' VALUE s.serv_labour_cost,
                'partcost' VALUE s.serv_parts_cost,
                'totalcost' VALUE (NVL(s.serv_labour_cost, 0) + NVL(s.serv_parts_cost, 0))
            )
            ORDER BY s.serv_date
        )
    ) || ',' AS vehicle_service_record
FROM 
    customer c
    JOIN vehicle v ON c.cust_no = v.cust_no
    LEFT JOIN service s ON v.veh_vin = s.veh_vin
GROUP BY 
    c.cust_no, c.cust_name, c.cust_phone, 
    v.veh_vin, v.veh_rego, v.veh_make, v.veh_model, v.veh_year;