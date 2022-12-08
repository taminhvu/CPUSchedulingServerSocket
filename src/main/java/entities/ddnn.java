package entities;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.io.File;
import pathfinding.dijkstra.DijkstraWithPriorityQueue;
import pathfinding.dijkstra.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"squid:S106", "PMD.SystemPrintln"})
public class ddnn {
    public static Ddnode nodes=new Ddnode();
    static String str2="";
    ArrayList<Edge> edges = new ArrayList<Edge>();
    HashMap<String, String> canh = new HashMap<String, String>();

    public ddnn(Ddnode nodes) {
        this.nodes=nodes;
    }
    public ddnn() {

    }




    public static Ddnode findAndPrintShortestPath(
            ValueGraph<String, Integer> graph, String source, String target) {
        List<String> shortestPath = DijkstraWithPriorityQueue.findShortestPath(graph, source, target);
        System.out.printf("shortestPath from %s to %s = %s%n", source, target, shortestPath);
        ArrayList<String> tam2 = new ArrayList<String>();
        Ddnode nodes_tam =new Ddnode();
        for(int i=0;i<shortestPath.size()-1;i++){
            tam2.add(String.valueOf(shortestPath.get(i))+";"+String.valueOf(shortestPath.get(i+1)));
        }
        nodes_tam.setNodes(tam2);
        return nodes_tam;
    }

    public   ValueGraph<String, Integer> createSampleGraph(Ddnode nodes) {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected().build();
        String str="";


        // List of graph edges as per the above diagram
        for (int i=0; i<nodes.getNodes().size(); i++){
            str=nodes.getNodes().get(i).toLowerCase();
            //System.out.println(i);
            try{
                String[] arrSplit = str.split(";");
                String tam1= arrSplit[0].toString();
                String tam2= arrSplit[1].toString();
                int tam3= Integer.parseInt(arrSplit[2]);
                graph.putEdgeValue(tam1, tam2, tam3);
                if(kiemtracanhtrung(tam1,tam2)==false){
                    break;
                }
                canh.put(tam1, tam2);
                edges.add(new Edge(Integer.parseInt(tam1), Integer.parseInt(tam2), tam3));
            }catch(Exception e){
                return graph;
            }
        }

        return graph;

    }

    public int calculateDistance(int source, int dest){
        Edge[] arr = new Edge[edges.size()];
        arr = edges.toArray(arr);
        pathfinding.dijkstra.Graph g=new pathfinding.dijkstra.Graph(arr);
        g.calculateShortestDistances(source);
        return g.printResult(dest);
    }
    public boolean kiemtracanhtrung(String node1, String node2){
        for(Map.Entry entry:canh.entrySet()){
            if(Integer.parseInt(entry.getValue().toString())==Integer.parseInt(node1)){
                if(Integer.parseInt(entry.getKey().toString())==Integer.parseInt(node1)){
                    return false;
                }
                if(Integer.parseInt(entry.getKey().toString())==Integer.parseInt(node2)){
                    //System.out.println("Chay");
                    return false;
                }

            }
            if(Integer.parseInt(entry.getValue().toString())==Integer.parseInt(node2)){
                if(Integer.parseInt(entry.getKey().toString())==Integer.parseInt(node1)){
                    return false;
                }
                if(Integer.parseInt(entry.getKey().toString())==Integer.parseInt(node2)){
                    //System.out.println("Chay");
                    return false;
                }
            }
        }
        return true;
    }

}