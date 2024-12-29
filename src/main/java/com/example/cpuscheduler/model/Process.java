package com.example.cpuscheduler.model;

public class Process {
    private int id;
    private double arrivalTime;
    private double burstTime;

    private double startTime;
    private double completionTime;

    public Process(){}

    public Process(int id, double arrivalTime, double burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    public int getId() {
        return id;
    }
    public double getArrivalTime() {
        return arrivalTime;
    }
    public double getBurstTime() {
        return burstTime;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }
}
