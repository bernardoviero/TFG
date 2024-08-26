package example;

import cartago.*;
import org.json.JSONObject;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationArtifact extends Artifact {

    private String unityIp = "127.0.0.1";  // IP do Unity
    private int unityPort = 12345;  // Porta que Unity estará escutando

    @OPERATION
    void sendDataToUnity(int fuel, int altitude, int posX, int posY, boolean hasScale) {
        JSONObject json = new JSONObject();
        json.put("fuel", fuel);
        json.put("altitude", altitude);
        json.put("position", new int[]{posX, posY});
        json.put("hasScale", hasScale);

        try (Socket socket = new Socket(unityIp, unityPort)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}