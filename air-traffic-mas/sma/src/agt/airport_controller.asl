!start.

+!start <- 
    ?fuel(Fuel);
    ?altitude(Altitude);
    ?position(PosX, PosY);
    ?hasScale(HasScale);
    ?runway_available(true);

    +fuel(Fuel);
    +altitude(Altitude);
    +position(PosX, PosY);
    +hasScale(HasScale);

    !controlTraffic.

+!controlTraffic <- 
    .print("Iniciando controle de tráfego...");
    !negotiateLanding.

+!negotiateLanding <- 
    .print("Negociando pouso com base no combustível, altitude e escala atuais.");
    ?runway_available(true);
    !evaluateProposal.

+!evaluateProposal : fuel(F) & altitude(A) <- 
    .print("Proposta aceita. Preparando para pousar.");
    !land.

+!evaluateProposal : fuel(0) | altitude(0) <- 
    .print("Proposta rejeitada. Recursos insuficientes para pousar.");
    !retryNegotiation.

+!land <- 
    .print("Procedimento de pouso iniciado.");
    -runway_available(true);  // Pista ocupada
    !releaseRunway.

+!releaseRunway <- 
    .print("Liberando a pista.");
    +runway_available(true).

+!retryNegotiation <- 
    .print("Tentando renegociar...");
    !negotiateLanding.