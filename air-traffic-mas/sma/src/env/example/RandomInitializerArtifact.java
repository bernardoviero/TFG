package example;

import cartago.*;

import java.util.Random;

public class RandomInitializerArtifact extends Artifact {
    
    void init() {
        Random rand = new Random();

        // Gera valores aleatórios
        int fuel = rand.nextInt(10) + 1; // Entre 1 e 10
        int altitude = rand.nextInt(10000) + 5000; // Entre 5000 e 15000
        int posX = rand.nextInt(100); // Entre 0 e 100
        int posY = rand.nextInt(100); // Entre 0 e 100
        int hasScale = rand.nextInt(2); // 0 ou 1

        // Define essas crenças diretamente no agente
        defineObsProperty("fuel", fuel);
        defineObsProperty("altitude", altitude);
        defineObsProperty("position", posX, posY);
        defineObsProperty("hasScale", hasScale);
        defineObsProperty("runway_available", true);
    }
}