package example;

import cartago.*;

public class RunwayArtifact extends Artifact {
    public void init() {
        defineObsProperty("available", true);
    }

    @OPERATION
    public void requestLanding() {
        if (getObsProperty("available").booleanValue()) {
            getObsProperty("available").updateValue(false);
        } else {
            failed("Runway not available");
        }
    }

    @OPERATION
    public void releaseRunway() {
        getObsProperty("available").updateValue(true);
    }
}