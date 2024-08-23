import cartago.*;

public class AircraftArtifact extends Artifact {
    
    void init() {
    }

    @OPERATION
    void adjust_fuel(double fuelNew) {
        signal("fuel_adjusted", fuelNew);
    }

    @OPERATION
    void move_aircraft(double newX, double newY, double altNew) {
        signal("aircraft_moved", newX, newY, altNew);
    }

    @OPERATION
    void adjust_course() {
        signal("course_adjusted");
    }
}
