import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Comunicacao {

    public static String receiveMessageTCP(Socket s) {
        try {
            InputStream is = s.getInputStream();
            byte[] lenBytes = new byte[4];
            is.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                    ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            is.read(receivedBytes, 0, len);
            String msg = new String(receivedBytes, 0, len);

            return msg;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public static void sendMessageTCP(Socket s, String msg) {
        try {
            OutputStream os = s.getOutputStream();
            byte[] toSendBytes = msg.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            os.write(toSendLenBytes);
            os.write(toSendBytes);   
            os.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    
    public static DatagramPacket assemblyMessageUDP(String msg, String ip, int porta) {
        try {
            byte[] buffer = msg.getBytes();
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), porta);
            return pacote;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } 
    }

    public static DatagramPacket receiveMessageUDP(DatagramSocket s) {
        try {
            DatagramPacket pacote = new DatagramPacket(new byte[512], 512);
            s.receive(pacote);
            return pacote;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendMessageUDP(DatagramSocket s, DatagramPacket pacote) {
        try {
            s.send(pacote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



