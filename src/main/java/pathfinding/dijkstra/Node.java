/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pathfinding.dijkstra;


import java.util.ArrayList;


public class Node{
    private int distancefromSource = Integer.MAX_VALUE;
    private boolean visited;
    private ArrayList<Edge> edges= new ArrayList<Edge>();

    public int getDistanceFromSource(){
        return distancefromSource;
    }

    public void setDistanceFromSource(int distanceFromSource){
    this.distancefromSource = distanceFromSource;
    }
    public boolean isVisited(){
    return visited;
    }
    public void setVisited(boolean visited) {
    this.visited = visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    
}