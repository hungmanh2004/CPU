package com.example.cpuscheduler.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    protected List<Process> processes;

    public Scheduler(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
    }

    /**
     * Phương thức abstract để lập lịch, trả về danh sách thể hiện
     * Gantt Chart dưới dạng:
     * List<Pair<ProcessID, Pair<StartTime, CompletionTime>>>
     */
    public abstract List<Pair<Integer, Pair<Double, Double>>> schedule();

    /**
     * Tính thời gian Turnaround trung bình
     * Turnaround = CompletionTime - ArrivalTime
     */
    public double calTurnaroundTime() {
        double total = 0.0;
        for (Process p : processes) {
            double turnaround = p.getCompletionTime() - p.getArrivalTime();
            total += turnaround;
        }
        return processes.isEmpty() ? 0.0 : total / processes.size();
    }

    /**
     * Tính thời gian chờ trung bình
     * WaitingTime = TurnaroundTime - BurstTime
     */
    public double calWaitingTime() {
        double total = 0.0;
        for (Process p : processes) {
            double turnaround = p.getCompletionTime() - p.getArrivalTime();
            double waiting = turnaround - p.getBurstTime();
            total += waiting;
        }
        return processes.isEmpty() ? 0.0 : total / processes.size();
    }

    /**
     * Tính CPU Utilization
     * CPU Utilization = (Tổng BurstTime / Thời gian từ start đến completion cuối cùng) * 100%
     */
    public double calCPUUtilization() {
        if (processes.isEmpty()) return 0.0;

        double totalBurst = 0.0;
        double lastCompletion = 0.0;

        for (Process p : processes) {
            totalBurst += p.getBurstTime();
            if (p.getCompletionTime() > lastCompletion) {
                lastCompletion = p.getCompletionTime();
            }
        }

        return lastCompletion > 0 ? (totalBurst / lastCompletion) * 100.0 : 0.0;
    }
}
