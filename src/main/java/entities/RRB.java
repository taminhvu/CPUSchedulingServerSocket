package entities;

import java.util.ArrayList;

public class RRB extends Schedule {
    private int quantum;

    public RRB(ArrayList<Process> processes, int quantum) {
        super(processes);
        this.quantum = quantum;
    }

    @Override
    public void calculateCompletionTime() {
        ArrayList<Process> listProcess = new ArrayList<>(getProcesses());
        sortByArrivalTime(listProcess);
        int[] remBtArrays = new int[getNumberOfProcess()];
        int t = 0; //current time
        for(int i = 0; i< getNumberOfProcess(); i++){
            remBtArrays[i] = listProcess.get(i).getBurstTime();
        }
        while(true){
            boolean done = true;
            for(int i = 0; i < getNumberOfProcess(); i++){
                if(remBtArrays[i] > 0){
                    done = false;
                    if(remBtArrays[i] > getQuantum()){
                        t += quantum;
                        remBtArrays[i] -= getQuantum();
                    }else{
                        t = t + remBtArrays[i];
                        listProcess.get(i).setCompletionTime(t);
                        remBtArrays[i] = 0;
                    }
                }
            }
            if(done == true){
                break;
            }
        }
        setProcesses(listProcess);
    }
    public void calculate(){
        calculateCompletionTime();
        calculateTurnAroundTime();
        calculateWaitingTime();
    }
    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    @Override
    public String toString() {
        calculateCompletionTime();
        calculateTurnAroundTime();
        calculateWaitingTime();
        setAverageWaitingTime();
        setAverageTurnAroundTime();
        String result = "P.No \tArrvalTime\tBurstTime\tCompletionTime\tWaitingTime\tTurnAroundTime\n";

        for(int i = 0; i <getNumberOfProcess(); i++){
            result += getProcesses().get(i).getNameProcess()
                    +"\t\t\t"+getProcesses().get(i).getArrivalTime()
                    +"\t\t\t"+getProcesses().get(i).getBurstTime()
                    +"\t\t\t"+getProcesses().get(i).getCompletionTime()
                    +"\t\t\t"+getProcesses().get(i).getWaitingTime()
                    +"\t\t\t"+getProcesses().get(i).getTurnaroundTime()+"\n";
        }
        result += "Average waiting time =" + this.getAverageWaitingTime()+ "\n";
        result += "Average Turn around time =" + this.getAverageTurnAroundTime();
        return result;
    }
}
