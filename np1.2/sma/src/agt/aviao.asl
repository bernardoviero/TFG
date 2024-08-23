!goal1.
+!goal1 : true <-
    .send_env("adjust_fuel", FuelNew);
    .send_env("move_aircraft", NewX, NewY, AltNew);
    .send_env("adjust_course").

+!move_aircraft(NewX, NewY, AltNew) : true <-
    .print("Moving aircraft to new coordinates");
    .send_env("move_aircraft", NewX, NewY, AltNew).

+!adjust_course : true <-
    .print("Adjusting course");
    .send_env("adjust_course").