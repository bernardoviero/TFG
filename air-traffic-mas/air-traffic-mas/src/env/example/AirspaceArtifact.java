
package example;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class AirspaceArtifact extends Artifact {

    void init() {
        defineObsProperty("plane_status", "waiting");
    }

    @OPERATION
    public void negotiate_landing(int altitude, String fuel_level, OpFeedbackParam<String> result) {
        if (fuel_level.equals("low")) {
            result.set("priority");
        } else if (fuel_level.equals("medium") && altitude < 1000) {
            result.set("priority");
        } else {
            result.set("wait");
        }
    }
}