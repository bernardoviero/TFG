package example;

import cartago.*;

public class AgentDataArtifact extends Artifact {
    void init() {
        defineObsProperty("fuel", 0);
        defineObsProperty("altitude", 0);
    }

    @OPERATION
    public void setFuel(int fuel) {
        getObsProperty("fuel").updateValue(fuel);
    }

    @OPERATION
    public void setAltitude(int altitude) {
        getObsProperty("altitude").updateValue(altitude);
    }
}