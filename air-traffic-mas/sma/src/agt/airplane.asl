!start.

+!start <- 
    .print("Avião iniciado.");
    makeArtifact("c1", "example.RunwayArtifact", [], ArtId);
    focus(ArtId);
    !controlar_trafego.

+controlar_trafego <- 
    .print("Iniciando controle de tráfego...");
    !negociar_pouso.

+negociar_pouso <- 
    .print("Negociando pouso com base no combustível, altitude e escala atuais.");
    c1Id.isRunwayAvailable(Disponivel);
    if (Disponivel) {
        !avaliar_proposta;
    } else {
        !renegociar_pouso;
    }.

+avaliar_proposta : fuel(2) & altitude(10000) <- 
    .print("Proposta aceita. Preparando para pousar.");
    !pousar.

+avaliar_proposta : fuel(0) | altitude(0) <- 
    .print("Proposta rejeitada. Recursos insuficientes para pousar.");
    !renegociar_pouso.

+pousar <- 
    .print("Procedimento de pouso iniciado.");
    c1Id.occupyRunway;
    !liberar_pista.

+liberar_pista <- 
    .print("Liberando a pista.");
    c1Id.releaseRunway.

+renegociar_pouso <- 
    .print("Tentando renegociar...");
    !negociar_pouso.

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }