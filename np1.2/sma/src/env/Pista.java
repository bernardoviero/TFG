package env;

import cartago.*;

public class Pista extends Artifact {

    private boolean pistaOcupada = false;

    @OPERATION
    public void solicitarPouso(String aviaoId, OpFeedbackParam<Boolean> podePousar) {
        if (!pistaOcupada) {
            pistaOcupada = true;
            podePousar.set(true);
            System.out.println("Pouso autorizado para o avião " + aviaoId);
        } else {
            podePousar.set(false);
            System.out.println("Pouso negado para o avião " + aviaoId + " - Pista ocupada");
        }
    }

    @OPERATION
    public void liberarPouso(String aviaoId) {
        pistaOcupada = false;
        System.out.println("Pista liberada por " + aviaoId);
    }
}
