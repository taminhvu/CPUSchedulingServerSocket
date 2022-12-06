/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pathfinding.dijkstra;

/**
 *
 * @author hieu0
 */
public class Main {
    public static void main(String[] args){
        Edge[] edges={
            new Edge(0, 2, 1),
            new Edge(0, 3, 4),
            new Edge(0, 4, 2),
            new Edge(0, 1, 3),
            new Edge(1, 3, 2),
            new Edge(1, 4, 3),
            new Edge(1, 5, 1),
            new Edge(2, 4, 1),
            new Edge(3, 5, 4),
            new Edge(4, 5, 2),
            new Edge(4, 6, 7),
            new Edge(4, 7, 2),
            new Edge(5, 6, 4),
            new Edge(6, 7, 5)
        };
        
        Graph g=new Graph(edges);
        g.calculateShortestDistances(1);
        g.printResult(3);
    }
}
