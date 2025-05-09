// *****PLEASE ENTER YOUR DETAILS BELOW*****
// ITO4132
// T6-ma-mongo.mongodb.js

// Student ID: 30428831
// Student Name: Tristan Sim

// Comments for your marker:

// ===================================================================================
// Do not modify or remove any of the comments below (items marked with //)
// ===================================================================================

//Use (connect to) your database - you MUST update xyz001
//with your authcate username

use("tsim0008");

// (b)
// PLEASE PLACE REQUIRED MONGODB COMMAND TO CREATE THE COLLECTION HERE
// YOU MAY PICK ANY COLLECTION NAME
// ENSURE that your query is formatted and has a semicolon
// (;) at the end of this answer

// Drop collection
db.services.drop();

// Create collection and insert documents
db.services.insertMany([
    {"_id":"1000_WBAWR33598P984354","customer":{"custno":1000,"name":"Andres Syphas","phone":"9571953915"},"rego_number":"I6W872","make":"Toyota","model":"Hilux","year":"2018","noservices":3,"booked_services":[{"servno":100,"servdate":"15-Jan-2023","labourcost":250,"partcost":125.5,"totalcost":375.5},{"servno":110,"servdate":"04-Jun-2023","labourcost":275.5,"partcost":198.45,"totalcost":473.95},{"servno":114,"servdate":"30-Jun-2023","labourcost":290,"partcost":185.99,"totalcost":475.99}]},
    {"_id":"1010_WAUVFAFH4AN857561","customer":{"custno":1010,"name":"Charlotta Schimke","phone":"6053395648"},"rego_number":"Z7K189","make":"Mazda","model":"3","year":"2020","noservices":2,"booked_services":[{"servno":101,"servdate":"25-Jan-2023","labourcost":320,"partcost":198.75,"totalcost":518.75},{"servno":113,"servdate":"25-Jun-2023","labourcost":175,"partcost":103.55,"totalcost":278.55}]},
    {"_id":"1020_WAUGFAFC8FN843179","customer":{"custno":1020,"name":"Cathrine Lynes","phone":"9678403727"},"rego_number":"R9X386","make":"Hyundai","model":"i30","year":"2017","noservices":1,"booked_services":[{"servno":102,"servdate":"10-Feb-2023","labourcost":180,"partcost":89.99,"totalcost":269.99}]},
    {"_id":"1030_JN1CV6EK6FM569421","customer":{"custno":1030,"name":"Farrel Grazier","phone":"3504231495"},"rego_number":"C3Y667","make":"Mazda","model":"CX-5","year":"2021","noservices":2,"booked_services":[{"servno":103,"servdate":"22-Feb-2023","labourcost":420,"partcost":312.45,"totalcost":732.45},{"servno":1000,"servdate":"21-Mar-2024","labourcost":0,"partcost":0,"totalcost":0}]},
    {"_id":"1040_3GTU1YEJ1BG975085","customer":{"custno":1040,"name":"Angie Eouzan","phone":"7355393095"},"rego_number":"D1E595","make":"Audi","model":"A4","year":"2016","noservices":1,"booked_services":[{"servno":111,"servdate":"12-Jun-2023","labourcost":390,"partcost":220.85,"totalcost":610.85}]},
    {"_id":"1040_5XXGM4A74DG668202","customer":{"custno":1040,"name":"Angie Eouzan","phone":"7355393095"},"rego_number":"Y9B216","make":"Jaguar","model":"XJ6","year":"2015","noservices":1,"booked_services":[{"servno":104,"servdate":"05-Mar-2023","labourcost":350,"partcost":275.6,"totalcost":625.6}]},
    {"_id":"1040_WA1CGCFE6BD111395","customer":{"custno":1040,"name":"Angie Eouzan","phone":"7355393095"},"rego_number":"Q2V771","make":"BMW","model":"3 Series","year":"2020","noservices":1,"booked_services":[{"servno":106,"servdate":"07-Apr-2023","labourcost":190,"partcost":87.25,"totalcost":277.25}]},
    {"_id":"1050_4T1BK3DB4CU320186","customer":{"custno":1050,"name":"Butch Japp","phone":"6715573197"},"rego_number":"GDD132","make":"Mitsubishi","model":"Lancer","year":"2021","noservices":1,"booked_services":[{"servno":105,"servdate":"18-Mar-2023","labourcost":280,"partcost":145.8,"totalcost":425.8}]},
    {"_id":"1060_19UYA42792A297253","customer":{"custno":1060,"name":"Tasha Obispo","phone":"1803631411"},"rego_number":"K9I306","make":"Volkswagen","model":"Tiguan","year":"2016","noservices":1,"booked_services":[{"servno":107,"servdate":"22-Apr-2023","labourcost":300,"partcost":215.4,"totalcost":515.4}]},
    {"_id":"1070_19XFB2E59DE245929","customer":{"custno":1070,"name":"Suzi Buxsy","phone":"2338078149"},"rego_number":"J9Z085","make":"Nissan","model":"Maxima","year":"2019","noservices":1,"booked_services":[{"servno":108,"servdate":"12-May-2023","labourcost":350,"partcost":71.28,"totalcost":421.28}]},
    {"_id":"1080_TRUWT28N921709039","customer":{"custno":1080,"name":"Fredra Doulton","phone":"5625108047"},"rego_number":"H6D767","make":"Ford","model":"Focus","year":"2017","noservices":1,"booked_services":[{"servno":112,"servdate":"19-Jun-2023","labourcost":220,"partcost":150.75,"totalcost":370.75}]},
    {"_id":"1080_WA1DGAFE9CD490452","customer":{"custno":1080,"name":"Fredra Doulton","phone":"5625108047"},"rego_number":"F4I963","make":"Ford","model":"Falcon","year":"2014","noservices":1,"booked_services":[{"servno":109,"servdate":"28-May-2023","labourcost":400,"partcost":325.95,"totalcost":725.95}]}
]);

// List all documents you added
db.services.find();


// (c)
// PLEASE PLACE REQUIRED MONGODB COMMAND/S FOR THIS PART HERE
// ENSURE that your query is formatted and has a semicolon
// (;) at the end of this answer
db.services.find(
   {
      "make": {"$eq": 'Mazda'},
      "noservices": {"$gt": 1}
   }, 
   {
      "_id": 0, "customer.custno": 1, "customer.name": 1, "rego_number": 1, "make": 1, "noservices": 1
   }
);


// (d)
// PLEASE PLACE REQUIRED MONGODB COMMAND/S FOR THIS PART HERE
// ENSURE that your query is formatted and has a semicolon
// (;) at the end of this answer

// Show document before service record is added
db.services.find(
    {
        "_id": {"$eq": '1040_5XXGM4A74DG668202'}
    },
    {
        "_id": 1, "customer.custno": 1, "customer.name": 1, "booked_services": 1, "noservices": 1, "make": 1
    }
);

// Add new service 
db.services.updateOne(
    {
        "_id": {"$eq": '1040_5XXGM4A74DG668202'}
    },
    {
        "$push": {
            "booked_services":
            {
                "servno": 2000,
                "servdate": "12-Mar-2024",
                "labourcost": 501.10,
                "partcost": 123.45,
                "totalcost": 624.55
            }
        },
        "$inc": {
            "noservices": 1
        }
    }
);


// Illustrate/confirm changes made
db.services.find(
    {
        "_id": {"$eq": '1040_5XXGM4A74DG668202'}
    },
    {
        "_id": 1, "customer.custno": 1, "customer.name": 1, "booked_services": 1, "noservices": 1, "make": 1
    }
);

