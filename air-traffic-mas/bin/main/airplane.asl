!start.

+!start
    <- joinWorkspace(w); // agente entra no espaço de trabalho chamado de w no jcm
       lookupArtifact(jsonReceiver, ID); // agente procura o artefato jsonReceiver citado no jcm, e pega o "ID" do artefato
       focus(ID); // foca no artefato pelo seu "ID"
       !obterDados.

+!obterDados
    <- .my_name(AgentName); //acao interna
       .println("agente : ", AgentName);
       .println("obtendo dados do artefato...");
       getData(AgentName, Dados);
       .println("dados recebidos: ", Dados);
       Dados = dados(Id, Altitude, Posicao, Escala, Gasolina); //Dados retorno de getData. dados():representa a estrutura esperada dos dado
       +id(Id);
       +altitude(Altitude);
       +posicao(Posicao);
       +escala(Escala);
       +gasolina(Gasolina);
       .println("crencas atualizadas: id(", Id, "), altitude(", Altitude, "), posicao(", Posicao, "), escala(", Escala, "), gasolina(", Gasolina, ").").