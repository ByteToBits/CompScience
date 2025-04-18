
/*
Applied 11-4: MongoDB Applied Exercise 2
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: MongoDB Database

Student Name: Tristan Sim 
Last Modified Date: 8th April 2025
*/

use("XXXXX");

/* 1. Retrieve the document for student id = 12802225 */ 
db.enrolment.find(
    {"_id":{"$eq":12802225}}
);

/* 2. Show the id and name of students who have any mark greater than 95 in any enrolment (hint: use $gt:95) */
db.enrolment.find(
    {"enrolment.mark":{"$gt":95}},
    {"_id": 1, "name": 1}
);

/* 3. Retrieve the name and contact info of students who enrolled in any unit which has "web design" as part of its name */
db.enrolment.find(
    {"enrolmentInfo:unitname": /.*web design.*/},
    {"name":1,"contactInfo":1}
);

/* 4. Retrieve the id and name of any students who have grades WH or N */ 
db.enrolment.find(
    {"enrolmentInfo.grade":{"$in":["WH","N"]}},
    {"_id":1,"name":1}
);

db.enrolment.find(
    {"$or":[{"enrolmentInfo.grade":"WH"},{"enrolmentInfo.grade":"N"}]},
    {"_id":1,"name":1}
);