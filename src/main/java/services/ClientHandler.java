package services;

import com.google.common.graph.ValueGraph;
import entities.Process;
import entities.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String name;

    private PrivateKey serverPrivateKey;
    private PublicKey serverPublicKey;
    private SecretKey secretKey;
    private PublicKey clientPublicKey;
    private
    ArrayList<entities.Process> processes;
    Ddnode nodes;
    ObjectInputStream in;
    ObjectOutputStream out;
    String message_return = "ok";

    public ClientHandler(Socket s, String n) throws IOException, NoSuchAlgorithmException {
        this.name = n;
        this.socket = s;
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
        processes = new ArrayList<>();
        nodes = new Ddnode();
        generateKey();
    }

    public void generateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        this.serverPrivateKey = privateKey;
        this.serverPublicKey = publicKey;
        this.secretKey  = KeyGenerator.getInstance("AES").generateKey();
    }
    public void sendAndReceivedKey() throws Exception {
        out.writeObject(serverPublicKey);
        setClientPublicKey((PublicKey) in.readObject());
        out.writeObject(HelperService.encryptSymmetricKey(getSecretKey(),getClientPublicKey()));
    }
    public void run() {
        try {
            System.out.println("Client " + socket.toString() + " accepted");
            generateKey();
            sendAndReceivedKey();

            while (true) {
                DataPackage dataPackage = (DataPackage) in.readObject();
                System.out.println("Server received '" + dataPackage + "' from Client " + this.name);
                SealedObject obj = dataPackage.getSealedObject();
                String message = HelperService.decryptInput(dataPackage.getMessage(), getSecretKey());
                Schedule algorithm;
                switch (message) {
                    case "fcfs": {
                        this.setProcesses((ArrayList<Process>) obj.getObject(getSecretKey()));
                        algorithm = new FCFS(processes);
                        algorithm.calculate();
                        obj = HelperService.encryptObject(algorithm.getProcesses(),getSecretKey());
                        String str = HelperService.encryptInput("fcfs",getSecretKey());
                        dataPackage = new DataPackage(obj,str);
                    }
                    break;
                    case "priority": {
                        this.setProcesses((ArrayList<Process>) obj.getObject(getSecretKey()));
                        algorithm = new Priority(processes);
                        algorithm.calculate();
                        obj = HelperService.encryptObject(algorithm.getProcesses(),getSecretKey());
                        String str = HelperService.encryptInput("priority",getSecretKey());
                        dataPackage = new DataPackage(obj,str);
                    }
                    break;
                    case "rrb": {
                        this.setProcesses((ArrayList<Process>) obj.getObject(getSecretKey()));
                        algorithm = new RRB(processes, 2);
                        algorithm.calculate();
                        obj = HelperService.encryptObject(algorithm.getProcesses(),getSecretKey());
                        String str = HelperService.encryptInput("rrb",getSecretKey());
                        dataPackage = new DataPackage(obj,str);
                    }
                    break;
                    case "sjf": {
                        this.setProcesses((ArrayList<Process>) obj.getObject(getSecretKey()));
                        algorithm = new SJF(processes);
                        algorithm.calculate();
                        obj = HelperService.encryptObject(algorithm.getProcesses(),getSecretKey());
                        String str = HelperService.encryptInput("sjf",getSecretKey());
                        dataPackage = new DataPackage(obj,str);
                    }
                    break;
                    case "ddnn": {
                        this.setNodes((Ddnode) obj.getObject(getSecretKey()));
                        ddnn ddnn = new ddnn();
                        String str2 = "";
                        Ddnode nodes_tam = nodes;
                        System.out.println(nodes.getNodes().get(0));
                        ValueGraph<String, Integer> graph = ddnn.createSampleGraph(nodes_tam);
                        System.out.println("graph = " + graph);

                        try {
                            for (int i = 0; i < nodes_tam.getNodes().size(); i++) {
                                str2 = nodes_tam.getNodes().get(i).toLowerCase();
                                try {
                                    String[] arrSplit = str2.split(";");
                                    String tam1 = arrSplit[0];
                                    String tam2 = arrSplit[1];
                                } catch (Exception e) {
                                    String[] arrSplit = str2.split(":");
                                    String source = arrSplit[0];
                                    String dest = arrSplit[1];
                                    nodes = ddnn.findAndPrintShortestPath(graph, source, dest);
                                    nodes.setChiphi(String.valueOf(ddnn.calculateDistance(Integer.parseInt(source), Integer.parseInt(dest))));
                                }
                            }
                        } catch (Exception e) {
                            message_return = "not ok";
                            nodes = nodes_tam;
                        }
                        System.out.println(nodes);
                        System.out.println(nodes.getChiphi());
                        obj = HelperService.encryptObject(nodes,getSecretKey());
                        String str = HelperService.encryptInput(message_return,getSecretKey());
                        dataPackage = new DataPackage(obj,str);
                    }
                    break;
                }

                for (ClientHandler client : Server.clientList)
                    if (name.equals(client.name)) {
                        client.out.writeObject(dataPackage);
                        System.out.println("Server sent '" + message + "--> Client " + client.name);
                    }

                if (message.equals("bye")) {
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrivateKey getServerPrivateKey() {
        return serverPrivateKey;
    }

    public void setServerPrivateKey(PrivateKey serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }

    public PublicKey getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(PublicKey clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public Ddnode getNodes() {
        return nodes;
    }

    public void setNodes(Ddnode nodes) {
        this.nodes = nodes;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
