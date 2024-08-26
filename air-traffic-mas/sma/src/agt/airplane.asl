!start.

+!start <- 
    +fuel(2);
    +altitude(10000);
    +position(50, 50);
    +hasScale(0);
    !negotiateLanding.

+!negotiateLanding <- 
    .send(airport_controller, cfp, [fuel, altitude, hasScale]).

+!kqml_received(airport_controller, cfp, [runway_available], _) <- 
    .print("Received Call for Proposal for landing.");
    .send(airport_controller, propose, [fuel, altitude, hasScale]).

+!kqml_received(airport_controller, accept_proposal, [], _) <- 
    .print("Proposal accepted. Preparing to land.").