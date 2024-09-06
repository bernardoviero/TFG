import jacamo.infra.JaCaMoLauncher;

public class LaunchMAS {
    public static void main(String[] args) {
        try {
            JaCaMoLauncher.main(new String[] { "air_traffic_mas.jcm" });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
