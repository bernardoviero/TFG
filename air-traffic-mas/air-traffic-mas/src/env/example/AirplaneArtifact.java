package example;

import cartago.*;

public class AirplaneArtifact extends Artifact {

    void init(String airplaneName, String fuelLevel, int altitude, String possessScale) {
        defineObsProperty("airplane_name", airplaneName);
        defineObsProperty("fuel_level", fuelLevel);
        defineObsProperty("altitude", altitude);
        boolean scale = possessScale.equals("yes");
        defineObsProperty("possess_scale", scale);
    }

    @OPERATION
    public void setFuelLevel(String newLevel) {
        getObsProperty("fuel_level").updateValue(newLevel);
    }

    @OPERATION
    public void setAltitude(int newAltitude) {
        getObsProperty("altitude").updateValue(newAltitude);
    }

    @OPERATION
    public void setPossessScale(String newScale) {
        boolean scale = newScale.equals("yes");
        getObsProperty("possess_scale").updateValue(scale);
    }

    @OPERATION
    public void getStatus(OpFeedbackParam<String> status) {
        String name = getObsProperty("airplane_name").stringValue();
        String fuel = getObsProperty("fuel_level").stringValue();
        int altitude = getObsProperty("altitude").intValue();
        boolean scale = getObsProperty("possess_scale").booleanValue();
        status.set("Airplane " + name + " - Fuel: " + fuel + ", Altitude: " + altitude + ", Scale: " + scale);
    }
}