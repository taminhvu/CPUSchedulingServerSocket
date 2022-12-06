/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pathfinding.dijkstra;



public class Edge{
    private int fromNodeIndex;
    private int toNodeIndex;
    private int length;

    public Edge(int fromNodeIndex, int toNodeIndex, int length) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.length = length;
    }

    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    public void setFromNodeIndex(int fromNodeIndex) {
        this.fromNodeIndex = fromNodeIndex;
    }

    public int getToNodeIndex() {
        return toNodeIndex;
    }

    public void setToNodeIndex(int toNodeIndex) {
        this.toNodeIndex = toNodeIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
   
    public int getNeighbourIndex(int nodeIndex){
    if (this.fromNodeIndex==nodeIndex){
        return this.toNodeIndex;
    }
    else {
        return this.fromNodeIndex;
    
    }
    }

}
