import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class IntegracaoEnvironment extends Environment {
    private Logger logger = Logger.getLogger("traffic." + IntegracaoEnvironment.class.getName());

    ServerSocket server;
    Socket client;
    DatagramSocket socket;    
    Gson gson;

    @Override
    public void init(String[] args) {
        super.init(args);
        gson = new Gson();

        try {
            addPercept(ASSyntax.parseLiteral("simulation(on)"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.createServerSocketTCP(1234);
        this.waitClientsTCP();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        if (agName.equals("aviao")) {
            try {
                String message = action.getFunctor() + ":" + action.getTerm(0).toString();
                Comunicacao.sendMessageTCP(client, message);
                logger.info(message);
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void createServerSocketTCP(int port) {
        try {
            server = new ServerSocket(port, 10);
            logger.info("Server MAS waiting Unity Simulator at port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitClientsTCP() {
        try {
            client = server.accept();
            logger.info("Server MAS received a connection with Unity Simulator");
            new Thread() {
                public void run() {
                    String jsonStringReceived;
                    StringBuffer perceptListObstaclesSeen = new StringBuffer();
                    logger.info("All threads to handle percepts are online!!");
                    try {
                        while (true) {
                            jsonStringReceived = Comunicacao.receiveMessageTCP(client);
                            if (jsonStringReceived == null || jsonStringReceived.equals("")) {
                                logger.info("Unity simulator did not send anything!");
                                server.close();
                                break;
                            } else {
                                Agente agent = gson.fromJson(jsonStringReceived, Agente.class);
                                java.lang.reflect.Type listType = new TypeToken<LinkedList<Agente>>(){}.getType();
                                
                                LinkedList<Agente> listObstaclesSeen = new LinkedList<>(); // Alterar conforme necessário

                                StringBuffer agentPercept = new StringBuffer();
                                agentPercept.append(agent.id).append("(")
                                    .append(agent.posicao_x).append(",")
                                    .append(agent.posicao_y).append(",")
                                    .append(agent.velocidade).append(",")
                                    .append(agent.altitude).append(",")
                                    .append(agent.combustivel).append(",")
                                    .append(agent.visao).append(")");

                                addPercept(ASSyntax.parseLiteral(agentPercept.toString()));
                                
                                if (listObstaclesSeen.isEmpty()) {
                                    addPercept(ASSyntax.parseLiteral("noObstacles(" + agent.id + ")"));
                                } else {
                                    for (Agente i : listObstaclesSeen) {
                                        perceptListObstaclesSeen.append("seen(")
                                            .append(agent.id).append(",")
                                            .append(i.posicao_x).append(",")
                                            .append(i.posicao_y).append(",")
                                            .append(i.velocidade).append(",")
                                            .append(i.altitude).append(",")
                                            .append(i.combustivel).append(",")
                                            .append(i.visao).append(")");
                                        System.out.println(perceptListObstaclesSeen);
                                        addPercept(ASSyntax.parseLiteral(perceptListObstaclesSeen.toString()));
                                        perceptListObstaclesSeen = new StringBuffer();
                                    }
                                }
                                try {
                                    Thread.sleep(400);
                                    addPercept(ASSyntax.parseLiteral(agentPercept.toString()));
                                    clearPercepts();
                                } catch (Exception e) {
                                    logger.info("Some problems to synchronize!!");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}