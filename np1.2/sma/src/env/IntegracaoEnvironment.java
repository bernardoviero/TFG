package env;

import cartago.Artifact;
import cartago.OPERATION;
import org.json.JSONObject;
import java.util.HashMap;

public class IntegracaoEnvironment extends Artifact {

    private HashMap<String, Aviao> avioes = new HashMap<>();
    
    @OPERATION
    public void updateAviao(String id, String posicao, String visao, String coordenadas, int altitude, int combustivel) {
        Aviao aviao = avioes.getOrDefault(id, new Aviao(id));
        aviao.setPosicao(posicao);
        aviao.setVisao(visao);
        aviao.setCoordenadas(coordenadas);
        aviao.setAltitude(altitude);
        aviao.setCombustivel(combustivel);
        avioes.put(id, aviao);
        
        enviarDadosParaUnity(aviao);
    }

    @OPERATION
    public void moverAviao(String id, int dx, int dy, int dz) {
        Aviao aviao = avioes.get(id);
        if (aviao != null) {
            aviao.mover(dx, dy, dz);
            enviarDadosParaUnity(aviao);
        }
    }

    private void enviarDadosParaUnity(Aviao aviao) {
        JSONObject json = new JSONObject();
        json.put("id", aviao.getId());
        json.put("posicao", aviao.getPosicao());
        json.put("visao", aviao.getVisao());
        json.put("coordenadas", aviao.getCoordenadas());
        json.put("altitude", aviao.getAltitude());
        json.put("combustivel", aviao.getCombustivel());

        System.out.println("Enviando para Unity: " + json.toString());
    }
}