/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Ddnode implements Serializable{
    private String sl_node;
    private String chiphi="0";
    private ArrayList<String> nodes = new ArrayList<String>();

    public Ddnode() {
    }

    public Ddnode(String sl_node) {
        this.sl_node = sl_node;
    }

    public String getChiphi() {
        return chiphi;
    }

    public void setChiphi(String chiphi) {
        this.chiphi = chiphi;
    }

    public String getSl_node() {
        return sl_node;
    }

    public void setSl_node(String sl_node) {
        this.sl_node = sl_node;
    }

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<String> nodes) {
        this.nodes = nodes;
    }
    public void insertNode(String node) {
        this.nodes.add(node);
    }

    @Override
    public String toString() {
        String s=this.getSl_node();
        for (int i=0; i<this.nodes.size(); i++){
            s+="\n"+nodes.get(i);
        }
        return s;
    }
}
