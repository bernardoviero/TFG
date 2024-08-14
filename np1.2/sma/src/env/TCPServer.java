import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor TCP iniciado na porta " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                    String json = "{ \"planes\": [" +
                                  "{ \"id\": \"Plane1\", \"fuelLevel\": \"50\", \"hasScale\": true, \"altitude\": \"1000\", \"coordinates\": { \"x\": 0, \"y\": 0 } }," +
                                  "{ \"id\": \"Plane2\", \"fuelLevel\": \"60\", \"hasScale\": false, \"altitude\": \"1500\", \"coordinates\": { \"x\": -4, \"y\": 4 } }" +
                                  "] }";

                    OutputStream output = clientSocket.getOutputStream();
                    output.write(json.getBytes());
                    output.flush();
                    System.out.println("JSON enviado ao cliente.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}