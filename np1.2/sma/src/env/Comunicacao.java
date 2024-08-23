package env;

import cartago.*;

import java.util.ArrayList;
import java.util.List;

public class Comunicacao extends Artifact {

    private List<String> mensagens = new ArrayList<>();

    @OPERATION
    public void enviarMensagem(String mensagem) {
        mensagens.add(mensagem);
        System.out.println("Mensagem enviada: " + mensagem);
    }

    @OPERATION
    public void receberMensagem(OpFeedbackParam<String> mensagemRecebida) {
        if (!mensagens.isEmpty()) {
            mensagemRecebida.set(mensagens.remove(0));
            System.out.println("Mensagem recebida: " + mensagemRecebida.get());
        } else {
            mensagemRecebida.set("Nenhuma mensagem disponível");
        }
    }
}