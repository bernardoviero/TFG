!start.

+!start <- 
    +pousos_restantes(3);
    !iniciar_pouso.

+!iniciar_pouso : pousos_restantes(N) & N > 0 <- 
    .broadcast(cfp, "quem_quer_pousar");  //envia uma mensagem CFP (Call for Proposals) para todos os aviões disponíveis, perguntando quem quer pousar
    .wait(5000);
    !encontrar_melhor_aviao;  //busca o avião com o menor nivel de combustivel
    ?melhor_aviao(Aviao);
    .send(Aviao, tell, pouso_aprovado);
    .println(Aviao, " recebeu autorizacao para pousar");
    -pousos_restantes(N);  //remove a crença antiga
    +pousos_restantes(N-1);  //atualiza a crença com o novo número de aviões
    -proposta(Aviao, _);  //remove a proposta do avião que já foi selecionado
    !verificar_fim.  //verifica se ainda há aviões que precisam pousar

+!verificar_fim : pousos_restantes(N) & N > 0 <- 
    !iniciar_pouso.  //se ainda houver avioes

+!verificar_fim : pousos_restantes(0) <- 
    .println("Todos os avioes ja pousaram. Finalizando...").

+!kqml_received(Aviao, propose, [C], _) <-  //é disparada quando o agente controlador recebe uma mensagem propose de um avião. No protocolo Contract Net Protocol (CNP), após o controlador enviar um CFP (Call for Proposal), os aviões respondem com suas propostas usando o tipo de mensagem propose. Neste caso, os aviões estão enviando suas propostas com o valor de C (que representa o nível de combustível).
    +proposta(Aviao, C);  //armazena a proposta enviada pelo avião, que inclui o valor de combustível (C)
    .println("Recebida proposta de ", Aviao, " com gasolina: ", C).

+!encontrar_melhor_aviao <- 
    ?proposta(MelhorAviao, MenorC);  //busca o avião com o menor nível de combustível na lista de propostas
    !comparar_propostas(MelhorAviao, MenorC).  //compara as propostas para encontrar o avio com o menor combustivel

+!comparar_propostas(AtualAviao, AtualC) : proposta(Aviao, C) & C < AtualC <- 
    !comparar_propostas(Aviao, C).  //caso a proposta atual tenha cmbustivel maior que o proximo, atualiza a comparação

+!comparar_propostas(MelhorAviao, MenorC) : not (proposta(_, C) & C < MenorC) <- 
    +melhor_aviao(MelhorAviao);  //ddefine o melhor avião com base nas comparações anteriores
    .println("Aviao selecionado para pousar: ", MelhorAviao, " com gasolina: ", MenorC).