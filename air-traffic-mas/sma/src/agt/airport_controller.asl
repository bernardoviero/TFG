!start.

+!start <- 
    .print("Controlador do aeroporto iniciado.").

+cfp(airplane, [fuel(Fuel), altitude(Altitude), hasScale(HasScale)]) <- 
    .concat("CFP recebido de ", airplane, Temp1);
    .concat(Temp1, " com combustivel ", Temp2);
    .concat(Temp2, Fuel, Temp3);
    .concat(Temp3, ", altitude ", Temp4);
    .concat(Temp4, Altitude, Temp5);
    .concat(Temp5, ", hasScale ", Mensagem);
    .print(Mensagem);
    c1Id = getArtifactId("c1"); 
    focus(c1Id);
    c1Id.isRunwayAvailable(Disponivel);
    if (Disponivel) {
        !evaluateProposal(airplane, Fuel, Altitude, HasScale);
    } else {
        .send(airplane, reject_proposal, []);
    }.

+!evaluateProposal(airplane, Fuel, Altitude, HasScale) : (Fuel < 4) & (Altitude > 5000) <- 
    .send(airplane, accept_proposal, []);
    c1Id.occupyRunway.

+!evaluateProposal(airplane, Fuel, Altitude, HasScale) <- 
    .send(airplane, reject_proposal, []).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }