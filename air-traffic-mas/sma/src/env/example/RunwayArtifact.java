package example;

import cartago.*;

public class RunwayArtifact extends Artifact {
    void init() {
        defineObsProperty("available", true);
    }

    @OPERATION
    void requestLanding(OpFeedbackParam<Boolean> success) {
        ObsProperty prop = getObsProperty("available");
        if (prop.booleanValue()) {
            prop.updateValue(false);
            success.set(true);
        } else {
            success.set(false);
        }
    }

    @OPERATION
    void releaseRunway() {
        getObsProperty("available").updateValue(true);
    }
}