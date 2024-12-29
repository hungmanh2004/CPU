package com.example.cpuscheduler.model;

import javafx.util.Pair;
import java.util.*;

public class RoundRobin extends Scheduler {
    private double quantumTime;

    public RoundRobin(List<Process> processes, double quantumTime) {
        super(processes);
        this.quantumTime = quantumTime;
    }

    public double getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(double quantumTime) {
        this.quantumTime = quantumTime;
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule(){
        for (Process p : processes) {
            p.setCompletionTime(0.0);
            p.setStartTime(0.0);
        }
        List<Pair<Integer, Pair<Double,Double>>> ganttchart = new ArrayList<>();
        if (processes.isEmpty()) {
            return ganttchart;
        }

        // Sắp xếp theo arrival time
        processes.sort(Comparator.comparingDouble(Process::getArrivalTime));

        // Tạo một bản đồ (remainingTimes) để lưu thời gian còn lại của mỗi tiến trình
        Map<Integer, Double> remainingTimes = new HashMap<>();
        for (Process p : processes) {
            remainingTimes.put(p.getId(), p.getBurstTime());
        }

        // Sử dụng một hàng đợi (readyQueue) để lưu các tiến trình sẵn sàng chạy
        Queue<Process> readyQueue = new LinkedList<>();
        // Sử dụng biến currentTime để theo dõi thời gian hiện tại
        double currentTime = 0.0;
        // Sử dụng biến completed để đếm số tiến trình đã hoàn thành
        int completed = 0;
        // Sử dụng biến n để lưu số lượng tiến trình
        int n = processes.size();

        // Trong khi số tiến trình hoàn thành nhỏ hơn tổng số tiến trình
        while (completed < n) {
            // Thêm process vào hàng đợi nếu đã đến
            for (Process p : processes) {
                if (p.getArrivalTime() <= currentTime && p.getCompletionTime() == 0 && !readyQueue.contains(p) && remainingTimes.get(p.getId()) > 0) {
                    readyQueue.offer(p);
                }
            }

            // Nếu hàng đợi không rỗng
            if (!readyQueue.isEmpty()) {
                // Lấy process đầu tiên ra khỏi hàng đợi
                Process current = readyQueue.poll();

                // Tính thời gian bắt đầu, thời gian thực thi và thời gian hoàn thành
                double startTime = currentTime;
                double execTime = Math.min(quantumTime, remainingTimes.get(current.getId()));
                double completionTime = startTime + execTime;

                // Chạy trong ganttchart
                ganttchart.add(new Pair<>(current.getId(), new Pair<>(startTime, completionTime)));

                currentTime = completionTime;
                // Cập nhật thời gian còn lại của tiến trình
                double newRemaining = remainingTimes.get(current.getId()) - execTime;
                remainingTimes.put(current.getId(), newRemaining);

                if (newRemaining == 0) {
                    // Process hoàn thành
                    current.setCompletionTime(currentTime); // Vì curentTime = completionTime
                    // Gán startTime lần đầu nếu chưa có vì ArrivalTime != startTime
                    if (current.getStartTime() == 0) {
                        current.setStartTime(startTime);
                    }
                    completed++;
                } else {
                    // Chưa hoàn thành, cho quay lại hàng đợi
                    // Trước khi cho quay lại, kiểm tra xem có process mới nào đến
                    for (Process p : processes) {
                        if (p.getArrivalTime() <= currentTime && p.getCompletionTime() == 0 && !readyQueue.contains(p) && remainingTimes.get(p.getId()) > 0 && p != current) {
                            readyQueue.offer(p);
                        }
                    }
                    // Trả lại current vào queue
                    if (current.getStartTime() == 0) {
                        current.setStartTime(startTime);
                    }
                    readyQueue.offer(current);
                }
            } else {
                // Không có process trong queue, nhích thời gian lên
                currentTime += 0.5;
            }
        }

        return ganttchart;
    }
}
