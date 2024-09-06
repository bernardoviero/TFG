+!start_negotiation <- 
    .broadcast(cfp, "env", "request_landing_proposal").

+propose(Sender, [Fuel, Altitude]) <- 
    .println("Received proposal from ", Sender, " with fuel: ", Fuel, " and altitude: ", Altitude);
    !evaluate_proposals.

+!evaluate_proposals <- 
    .findall([Fuel, Altitude], propose(Sender, [Fuel, Altitude]), Proposals);
    .send(Sender, accept_proposal, [landing_granted]).