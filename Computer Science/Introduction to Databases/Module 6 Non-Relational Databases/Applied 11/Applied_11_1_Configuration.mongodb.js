
/*
Applied 11-2: Configure User Password
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Sample Provided by the Univeristy of Monash Australia
*/

use("tsim0008"); 

db.updateUser(
    "tsim0008", {pwd: "XXXXX"}
);

