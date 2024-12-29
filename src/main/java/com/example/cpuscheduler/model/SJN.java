package com.example.cpuscheduler.model;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJN extends Scheduler {

    public SJN(List<Process> processes) {
        super(processes);
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule() {
        List<Pair<Integer, Pair<Double, Double>>> ganttChart = new ArrayList<>();
        // Kiểm tra nếu danh sách processes rỗng thì trả về ganttChart rỗng
        if (processes.isEmpty()) {
            return ganttChart;
        }

        // Sắp xếp processes theo arrivalTime
        processes.sort(Comparator.comparingDouble(Process::getArrivalTime));

        double currentTime = 0.0; // Thời gian hiện tại
        int completed = 0;       // Số tiến trình đã hoàn thành
        int n = processes.size();

        // Danh sách lưu remainingBurstTime (thời gian thực thi còn lại của từng tiến trình)
        double[] remainingBurstTime = new double[n];
        for (int i = 0; i < n; i++) {
            remainingBurstTime[i] = processes.get(i).getBurstTime();
        }

        Process currentProcess = null; // Tiến trình đang chạy

        while (completed < n) {
            // Trong vòng lặp, tìm tiến trình có thời gian thực thi nhỏ nhất đã đến và chưa hoàn thành
            int shortestJobIndex = -1;
            double shortestBurstTime = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Process process = processes.get(i);

                if (process.getArrivalTime() <= currentTime && process.getCompletionTime() == 0
                        && remainingBurstTime[i] < shortestBurstTime && remainingBurstTime[i] > 0) {
                    shortestJobIndex = i;
                    shortestBurstTime = remainingBurstTime[i];
                }
            }

            // Nếu không có tiến trình nào sẵn sàng
            if (shortestJobIndex == -1) {
                currentTime++; // Nhảy thời gian
                continue;
            }

            Process shortestJob = processes.get(shortestJobIndex);

            // Nếu có tiến trình sẵn sàng
            if (currentProcess != shortestJob) {
                // cập nhật ganttChart nếu tiến trình đang chạy thay đổi (preemption)
                if (currentProcess != null) {
                    ganttChart.add(new Pair<>(currentProcess.getId(),
                            new Pair<>(currentTime - remainingBurstTime[shortestJobIndex], currentTime)));
                }
                currentProcess = shortestJob;
            }

            // Giảm thời gian thực thi còn lại của tiến trình đang chạy và tăng currentTime
            remainingBurstTime[shortestJobIndex] -= 1;
            currentTime += 1;

            // Nếu tiến trình hoàn thành, cập nhật thời gian hoàn thành và thêm vào ganttChart
            if (remainingBurstTime[shortestJobIndex] <= 0) {
                currentProcess = null; // Giải phóng tiến trình hiện tại
                shortestJob.setCompletionTime(currentTime);
                ganttChart.add(new Pair<>(shortestJob.getId(),
                        new Pair<>(currentTime - shortestJob.getBurstTime(), currentTime)));
                completed++;
            }
        }

        return ganttChart;
    }
}
