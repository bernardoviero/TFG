+!start
    <- .print("Avião iniciado."),
        !checar_combustivel.

+!checar_combustivel
    <- .my_fuel(NivelCombustivel),
        if (NivelCombustivel = 0) then
            !negociar_pouso
        else
            !continuar_voo.

+!continuar_voo
    <- .send(env, tell, [move(1, 0, -1)]),
        !checar_combustivel.

+!negociar_pouso
    <- .send(all, tell, [propor_pouso(NivelCombustivel)]).

+!propor_pouso(NivelCombustivel) 
    <- .receive([aceitar_proposta(NivelCombustivel)]),
        !pousar.

+!pousar 
    <- .send(env, tell, [pousar]),
        .print("Avião pousando.")

+my_fuel(100).