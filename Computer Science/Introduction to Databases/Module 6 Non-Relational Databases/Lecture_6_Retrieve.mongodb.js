
// Insert the First Document into MongoDB
use ("tsim0008")

// // Finds all Documents
db.dronerent.find({
});

// id = 0 means Supress the Object ID and id = 1 means show 
// To Show Child Values type.model = 1
db.dronerent.find({
  "drone_id": 118
}, {"_id": 0, "drone_id": 1, "type.model": 1});

// Lecture Exercise 1: 
// a. Find the Details of the Drone ID
db.dronerent.find({"drone_id": {"$eq": 102}});

// b. Find the Details of the Tpye DIN2
db.dronerent.find({"type.code": {"$eq": "DIN2"}});

// c. Find the Details of Drones with Carrying Capacity GREATER than 4
// Display drone_id, model and cost_per_hour

