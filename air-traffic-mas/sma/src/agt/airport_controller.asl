!start.

+!start <- 
    !callForProposals.

+!callForProposals <- 
    .broadcast(cfp, [runway_available]).

+!kqml_received(airplane, cfp, [fuel, altitude, hasScale], _) <- 
    .print("Received CFP from airplane.");
    .send(airplane, propose, [runway_available]).