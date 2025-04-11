
// Insert the First Document into MongoDB
use ("XXXX")

db.dronerent.drop()

db.dronerent.insertOne(
    {
        "drone_id": 100,
        "type": {
          "code": "DMA2",
          "model": "DJI Mavic Air 2 Flymore Combo",
          "manufacturer": "DJI Da-Jiang Innovations"
        },
        "carrying_capacity": 0,
        "pur_date": "2021-01-13",
        "pur_price": 1494,
        "total_flighttime": 100,
        "cost_per_hour": 15,
        "RentalInfo": [
          {
            "rent_no": 1,
            "bond": 100,
            "rent_out": "2021-02-20",
            "rent_in": "2021-02-20",
            "custtrain_id": 1
          },
          {
            "rent_no": 4,
            "bond": 100,
            "rent_out": "2021-02-22",
            "rent_in": "2021-02-25",
            "custtrain_id": 4
          }
        ]
      }      
);