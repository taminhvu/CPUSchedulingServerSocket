package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract  class Schedule {

    private int numberOfProcess;
    private float averageCompletionTime;
    private float averageWaitingTime;
    private float averageTurnAroundTime;
    private ArrayList<Process> processes;
    public Schedule(ArrayList<Process> processes) {
        this.processes = processes;
        this.numberOfProcess = processes.size();
    }
    public abstract void calculateCompletionTime();
    public abstract void calculate();
    public void calculateWaitingTime(){
        int time;
        for(int i = 0; i < this.numberOfProcess; i++){
            time = processes.get(i).getTurnaroundTime() - this.processes.get(i).getBurstTime();
            this.processes.get(i).setWaitingTime(time);
        }
    }
    public void calculateTurnAroundTime(){
        int time = 0;
        for(int i = 0; i < this.numberOfProcess; i++){
            time = this.processes.get(i).getCompletionTime()-this.processes.get(i).getArrivalTime();
            this.processes.get(i).setTurnaroundTime(time);
        }
    }

    public float getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime() {
        float sum = 0;
        for(int i = 0; i < this.numberOfProcess; i++){
            sum = sum + this.processes.get(i).getWaitingTime();
        }
        this.averageWaitingTime = sum / (float)this.numberOfProcess;
    }
    public void setAverageTurnAroundTime() {
        float sum = 0;
        for(int i = 0; i < this.numberOfProcess; i++){
            sum = sum + this.processes.get(i).getTurnaroundTime();
        }
        this.averageTurnAroundTime = sum / (float)this.numberOfProcess;
    }
    public float getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public void setAverageCompletionTime() {
        float sum = 0;
        for(int i = 0; i < this.numberOfProcess; i++){
            sum = sum + this.processes.get(i).getCompletionTime();
        }
        this.averageCompletionTime = sum / (float)this.numberOfProcess;
    }
    public float getAverageCompletionTime() {
        return averageCompletionTime;
    }

    public int getNumberOfProcess() {
        return numberOfProcess;
    }

    public void setNumberOfProcess(int numberOfProcess) {
        this.numberOfProcess = numberOfProcess;
    }
    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(ArrayList<Process> processes) {
        this.processes = processes;
    }
    public void sortByArrivalTime(ArrayList<Process> processes){
        Comparator<Process> arrivalTime = new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o1.getArrivalTime()-o2.getArrivalTime();
            }
        };
        Collections.sort(processes,arrivalTime);
    }
    @Override
    public String toString() {
        calculateCompletionTime();
        calculateTurnAroundTime();
        calculateWaitingTime();
        setAverageWaitingTime();
        setAverageTurnAroundTime();
        String result = "P.No \tArrvalTime\tBurstTime\tCompletionTime\tWaitingTime\tTurnAroundTime\n";

        for(int i = 0; i <numberOfProcess; i++){
            result += processes.get(i).getNameProcess()+"\t\t\t"+processes.get(i).getArrivalTime()+"\t\t\t"+processes.get(i).getBurstTime()+"\t\t\t"+processes.get(i).getCompletionTime()+"\t\t\t"+processes.get(i).getWaitingTime()+"\t\t\t"+processes.get(i).getTurnaroundTime()+"\n";
        }
        result += "Average waiting time =" + this.getAverageWaitingTime()+ "\n";
        result += "Average Turn around time =" + this.getAverageTurnAroundTime();
        return result;
    }
}
