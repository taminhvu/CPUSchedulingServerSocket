package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF extends Schedule {

    public SJF(ArrayList<Process> processes) {
        super(processes);
    }
    public  void calculateCompletionTime(){
        ArrayList<Process> queue = new ArrayList<>(getProcesses());
        ArrayList<Process> result = new ArrayList<>();
        Comparator<Process> arrivalAndBurstTime = new Comparator<>() {
            @Override
            public int compare(Process o1, Process o2) {
                int kt = o1.getArrivalTime() - o2.getArrivalTime();
                if (kt != 0) {
                    return kt;
                }
                return o1.getBurstTime() - o2.getBurstTime();
            }
        };
        Collections.sort(queue,arrivalAndBurstTime);
        Process currentProcess = queue.get(0);
        currentProcess.setCompletionTime(currentProcess.getBurstTime() + currentProcess.getArrivalTime());
        result.add(currentProcess);
        queue.remove(currentProcess);
        while(queue.size()!=0){
            int lastProcess = result.size()-1;
            Process process = queue.get(0);
            for(int i = 1; i < queue.size();i++){
                int check =result.get(lastProcess).getCompletionTime() - queue.get(i).getArrivalTime();
                if(check >= 0){
                    if(process.getBurstTime() > queue.get(i).getBurstTime()){
                        process = queue.get(i);
                    }
                }
            }
            process.setCompletionTime(result.get(lastProcess).getCompletionTime()+process.getBurstTime());
            result.add(process);
            queue.remove(process);
        }
        setProcesses(result);
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
                    +"\t\t\t"+getProcesses().get(i).getTurnaroundTime()+"\n";
        }
        result += "Average waiting time =" + this.getAverageWaitingTime()+ "\n";
        result += "Average Turn around time =" + this.getAverageTurnAroundTime();
        return result;
    }
}
