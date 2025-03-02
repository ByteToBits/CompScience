
// Databases Applied 1
// Applied 1: Student MongoDB Sample

// student id: 30428831
// student name: Tristan Sim
// last modified date: 2/3/2025

use ("tsim0008")

// MongoDB is a Set of Collections;
// A Collection is a Set of Documents
// ".drop" drops a collection if it exist
db.student.drop();

// Insert Multiple Docunments into a Collection 
db.student.insertMany([
    {
        "_id": 12489379,
        "firstName": "Gilberto",
        "lastName": "Bwy",
        "address": "5664 Loomis Parkway, Melbourne",
        "phone": "7037621034",
        "email":"Gilberto.Bwy@student.monash.edu"
    },
    {
        "_id": 12511467,
        "firstName": "Francyne",
        "lastName": "Rigney",
        "address": "75 Buhler Street, Mulgrave",
        "phone": "6994152403",
        "email":"Francyne.Rigney@student.monash.edu"
    },
    {
        "_id": 12609485,
        "firstName": "Cassondra",
        "lastName": "Sedcole",
        "address": "6507 Tennessee Alley, Melbourne",
        "phone": "8343944500",
        "email":"Cassondra.Sedcole@student.monash.edu"
    },
    {
        "_id": 12802225,
        "firstName": "Friedrick",
        "lastName": "Geist",
        "address": "99271 Eliot Pass, Dingley",
        "phone": "6787553656",
        "email":"Friedrick.Geist@student.monash.edu"
    },
    {
        "_id": 12842838,
        "firstName": "Herminia",
        "lastName": "Mendus",
        "address": "64186 East Lane, Moorabbin",
        "phone": "4896374903",
        "email":"Herminia.Mendus@student.monash.edu"
    },
    {
        "_id": 13028303,
        "firstName": "Herculie",
        "lastName": "Mendus",
        "address": "44 Becker Street, Mulgrave",
        "phone": "2309618710",
        "email":"Herculie.Mendus@student.monash.edu"
    },
    {
        "_id": 13119134,
        "firstName": "Shandra",
        "lastName": "Lindblom",
        "address": "9241 Rieder Parkway, Chelsea",
        "phone": "4384142213",
        "email":"Shandra.Lindblom@student.monash.edu"
    },
    {
        "_id": 13390148,
        "firstName": "Brier",
        "lastName": "Kilgour",
        "address": "79776 Dryden Plaza, Moorabbin",
        "phone": "6981280319",
        "email":"Brier.Kilgour@student.monash.edu"
    }
])

// Step 1: Retrieve the Student Details
db.student.find()

// Step 2: Find Students Living in Moorabbin
db.student.find({"address":/.*Moorabbin*./});

// Step 3: Display the Student ID, First Name and Last Name who Live in Moorabbin
db.student.find({"address":/.*Moorabbin*./}, {"_id":0, "firstName":1, "lastName":1});