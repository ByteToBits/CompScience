
/*
Applied 11-2: Generate MongoDB JSON Data from SQL Oracle
Institution: Monash University Australia
Subject: ITO 4132 Introduction to Databases
Database Type:: Oracle SQL Database

Sample Provided by the Univeristy of Monash Australia
*/

--SET PAGESIZE 200

SELECT
    JSON_OBJECT ( '_id' VALUE stuid, 'name' VALUE stufname
                || ' '
                || stulname, 
                'contactInfo' VALUE JSON_OBJECT ( 
                    'address' VALUE stuaddress, 
                    'phone'   VALUE rtrim(stuphone), 
                    'email' VALUE stuemail 
                    ), 
                'enrolmentInfo' VALUE JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'unitcode' VALUE unitcode, 
                        'year' VALUE to_char(ofyear, 'yyyy'), 
                        'semester' VALUE ofsemester, 
                        'mark' VALUE enrolmark, 
                        'grade' VALUE enrolgrade
                        )
                    ) FORMAT JSON )
    || ','
FROM
    uni.student
    NATURAL JOIN uni.enrolment  
GROUP BY
    stuid,
    stufname,
    stulname,
    stuaddress,
    stuphone,
    stuemail
ORDER BY
    stuid;