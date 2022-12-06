package services;

import entities.Process;
import entities.RRB;
import entities.Schedule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static int port = 1234;
    public static int numThread = 10;
    public static Vector<ClientHandler> clientList = new Vector<>();

    public void startServer(){
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            int i =1;
            while(true) {
                Socket socket = server.accept();
                ClientHandler client = new ClientHandler(socket, Integer.toString(i++));
                clientList.add(client);
                executor.execute(client);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        Server server = new Server();
        server.startServer();
//        ArrayList<Process> processes = new ArrayList<>();
//        processes.add(new Process("P1",0,4,5));
//        processes.add(new Process("P2",1,2,6));
//        processes.add(new Process("P3",2,3,7));
//        processes.add(new Process("P4",3,5,5));
//        processes.add(new Process("P5",4,1,6));
//        processes.add(new Process("P6",5,4,7));
//        processes.add(new Process("P7",6,6,7));
//
//        Schedule sc = new RRB(processes,2);
//        System.out.println(sc.toString());
    }
}
