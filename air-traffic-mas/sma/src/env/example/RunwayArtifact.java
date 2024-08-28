package example;

import cartago.*;

public class RunwayArtifact extends Artifact {
    private boolean runwayAvailable;

    void init() {
        runwayAvailable = true;
    }

    @OPERATION
    void isRunwayAvailable(OpFeedbackParam<Boolean> available) {
        available.set(runwayAvailable);
    }

    @OPERATION
    void occupyRunway() {
        runwayAvailable = false;
    }

    @OPERATION
    void releaseRunway() {
        runwayAvailable = true;
    }
}