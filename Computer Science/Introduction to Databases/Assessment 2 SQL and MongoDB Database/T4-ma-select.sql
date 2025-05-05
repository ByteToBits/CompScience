/*****PLEASE ENTER YOUR DETAILS BELOW*****/
-- ITO4132
--T4-ma-select.sql

--Student ID: 30428831
--Student Name: Tristan Sim

/* Comments for your marker:




*/

-- PLEASE PLACE REQUIRED SQL SELECT STATEMENT FOR THIS PART HERE
-- ENSURE that your query is formatted and has a semicolon
-- (;) at the end of this answer

/* (a) Report the service number, vehicle vin, registration number, make, the service job numbers and service 
job descriptions for all services which have been completed. The output should be in service number order, and 
within service number ordered by service job number. The most recent service number should be shown first. 
[5 marks]*/

SELECT 
   s.serv_no AS Service_Number, 
   v.veh_vin AS Vehicle_VIN, 
   v.veh_rego AS Registration_Number, 
   v.veh_make AS Vehicle_Make, 
   sj.sj_job_no AS Service_Job_Number, 
   sj.sj_task_description AS Service_Job_Description
FROM
   service s
   JOIN vehicle v ON s.veh_vin = v.veh_vin
   JOIN service_job sj ON s.serv_no = sj.serv_no
WHERE
   s.serv_ready_pickup IS NOT NULL
ORDER BY
   s.serv_no DESC, sj.sj_job_no ASC;



/* (b) For every part stocked by Monash Automotive list the part code, description, vendor ID and vendor name in a 
single VENDOR column separated by (see below), the current stock held by MA and the total value of the parts 
stock currently held. The output should be in part code order. The stock value should be output in the form $123.45. 
[5 marks]*/ 

SELECT 
   p.part_code, 
   p.part_description AS part_description,
   v.vendor_id || '-' || v.vendor_name AS vendor,
   LPAD(TO_CHAR(p.part_stock), 5, ' ') AS part_stock, 
   LPAD(TO_CHAR(p.part_stock * p.part_unit_cost, '$99,990.99'), 15, ' ') AS stock_value
FROM
   part p
   JOIN vendor v ON p.vendor_id = v.vendor_id
ORDER BY
   p.part_code ASC;



/* (c) For every part stocked by Monash Automotive list the part code, the part description, the name of the vendor 
who supplies the part and an indicator if the part has been used (or not used) in a service. The indicator should say 
'Used in at least one service' or 'Not used in any service'. The output should be in part code order. 
[8 marks]*/

SELECT 
   p.part_code,
   p.part_description,
   v.vendor_name,
   DECODE(
      (SELECT COUNT(*) FROM part_charge pc WHERE pc.part_code = p.part_code),
      0, 'Not used in any service',
      'Used in at least one service'
   ) AS partusage
FROM
   part p
   JOIN vendor v ON p.vendor_id = v.vendor_id
ORDER BY
   p.part_code ASC;



/* (d) For every part stocked by Monash Automotive list the part code, part description, the quantity of these items which 
have been charged out via a part charge and the total amount of such charges. In arriving at your solution, it is important 
to note that the current unit cost listed in the part table may not be the price the item was charged out at due to part 
price variations. Your output should be listed with the part which has been used the most times first. 
Typical output would have the form: [8 marks] 

Comments: Part Charge Line Cost is the final price charged, not to reference Part Unit Price.
Total Parts used and charged should reference Part Charge Table.
*/

SELECT
   p.part_code,
   p.part_description, 
   LPAD(NVL(TO_CHAR(SUM(pc.pc_quantity)), '0'), 3, ' ') AS Quantity_Used,
   LPAD(TRIM(TO_CHAR(NVL(SUM(pc.pc_linecost), 0.00), '$9,990.99')), 12, ' ') AS Total_Charges
FROM
   part p
   LEFT OUTER JOIN part_charge pc ON p.part_code = pc.part_code
GROUP BY
   p.part_code,
   p.part_description
ORDER BY
   NVL(SUM(pc.pc_quantity), 0) DESC;



/* (e) For all completed services in which the vehicle was ready for pickup later than the customer's requested pickup time, 
list the customer number, customer name, the service number, the required pickup time, the time the vehicle was ready for pickup 
and how late the delivery was in hours and minutes in the form 1 hr 15 mins. The output should show the longest delayed delivery first.
[8 marks] 

Comments: Sub-tracting 2 Dates in Oracles Returns a Day in decimals 0.65 Days = 15.6 hours (0.65 × 24 = 15.6).
To find Minute, 0.6 hours × 60 = 36 minutes. Hence, it should be 15 hours and 36 minutes.
FLOOR returns an Integer Value and no decimal places. MOD (Modulus) Retrieves only the Decimal Values.
*/
SELECT
   c.cust_no,
   c.cust_name AS customer_name,
   s.serv_no,
   TO_CHAR(s.serv_req_pickup, 'HH:MI AM') AS required_pickup_time, 
   TO_CHAR(s.serv_ready_pickup, 'HH:MI AM') AS ready_for_pickup_time,
   FLOOR((s.serv_ready_pickup - s.serv_req_pickup) * 24) || ' hr ' || 
   ROUND(MOD((s.serv_ready_pickup - s.serv_req_pickup) * 24 * 60, 60)) || ' mins'
   AS late_delivery
FROM
   customer c
   JOIN service s ON c.cust_no = s.cust_no
WHERE
   s.serv_ready_pickup > s.serv_req_pickup
   AND s.serv_ready_pickup IS NOT NULL
ORDER BY
   (s.serv_ready_pickup - s.serv_req_pickup) DESC;



/* (f) List the customer number, customer name and total amount they have been charged for parts across all their completed services 
where the amount they have been charged is greater than the average amount charged for parts across all completed services for all customers.
For example, on average customers may have been charged $234.56 for all part charges across all their services.
This report will then list those customers whose total part charges across all their services exceeds $234.56 (note these figures are quite 
small here due to the small amount of data in our model system). The customer with the highest part charges should be listed first. 
Where two customers have been charged the same total parts charge, order them by the customer's name. [12 marks]

Pseudo Code: 
1) Main Query: Join Tables (customer -> service -> service_job -> part_charge)
2) Main Query: ".serv_ready_pickup IS NOT NULL" is determine if the service has been completed
3) Main Query: Calculate the total parts charged per customer based on "pc_linecost"
4) Main Query: Filter Customers using (Having) total parts charged greater than average total Grouped by Cust No & Name
5) Sub Query: Calculate the Average Total Parts Cost from all Customers 
6) Sub Query: Join Tables (customer -> service -> service_job -> part_charge)
7) Sub Query: Find the Average of the Customer Total Attribute
*/

SELECT
   c1.cust_no,
   c1.cust_name,
   LPAD(TO_CHAR(SUM(pc1.pc_linecost), '$9,990.99'), 12, ' ') AS Total_Part_Payment
FROM
   customer c1
   JOIN service s1 ON c1.cust_no = s1.cust_no
   JOIN service_job sj1 ON s1.serv_no = sj1.serv_no
   JOIN part_charge pc1 ON sj1.serv_no = pc1.serv_no AND sj1.sj_job_no = pc1.sj_job_no
WHERE
   s1.serv_ready_pickup IS NOT NULL
GROUP BY
   c1.cust_no, c1.cust_name
HAVING 
   SUM(pc1.pc_linecost) > (
      SELECT 
         AVG(customer_total)
      FROM
         (
            SELECT
              c2.cust_no,
              SUM(pc2.pc_linecost) AS customer_total
            FROM
              customer c2
              JOIN service s2 ON c2.cust_no = s2.cust_no
              JOIN service_job sj2 ON s2.serv_no = sj2.serv_no
              JOIN part_charge pc2 ON sj2.serv_no = pc2.serv_no AND sj2.sj_job_no = pc2.sj_job_no
            WHERE
              s2.serv_ready_pickup IS NOT NULL  
            GROUP BY
              c2.cust_no
         )
   )
ORDER BY
   Total_Part_Payment DESC, c1.cust_name ASC;
   
