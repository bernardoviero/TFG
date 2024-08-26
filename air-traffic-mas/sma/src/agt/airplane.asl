!start.

+!start <- 
    +fuel(2);
    +altitude(10000);
    +position(50, 50);
    +hasScale(0);
    !negotiateLanding.

+!negotiateLanding <- 
    .send(airport_controller, cfp, [fuel(2), altitude(10000), hasScale(0)]).

+cfp(airport_controller, [runway_available(true)], _) <- 
    .print("Received CFP from airport_controller with runway available.");
    .send(airport_controller, propose, [fuel(2), altitude(10000), hasScale(0)]).

+accept_proposal(airport_controller, [], _) <- 
    .print("Proposal accepted. Preparing to land.").

+reject_proposal(airport_controller, [], _) <- 
    .print("Proposal rejected. Retrying...");
    !negotiateLanding.