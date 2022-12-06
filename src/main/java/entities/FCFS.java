package entities;

import java.util.ArrayList;
public class FCFS extends Schedule {

    public FCFS(ArrayList<Process> processes) {
        super(processes);
    }
    public  void calculateCompletionTime(){
        sortByArrivalTime(getProcesses());
        getProcesses().get(0).setCompletionTime(getProcesses().get(0).getBurstTime() + getProcesses().get(0).getArrivalTime());
        for(int i = 1; i <getNumberOfProcess(); i++){
            getProcesses().get(i).setCompletionTime(getProcesses().get(i).getBurstTime()+getProcesses().get(i-1).getCompletionTime());
        }
    }
    public void calculate(){
        calculateCompletionTime();
        calculateTurnAroundTime();
        calculateWaitingTime();
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
                    +"\t\t\t"+getProcesses().get(i).getTurnaroundTime()
                    +"\n";
        }
        result += "Average waiting time =" + this.getAverageWaitingTime()+ "\n";
        result += "Average Turn around time =" + this.getAverageTurnAroundTime();
        return result;
    }
}
