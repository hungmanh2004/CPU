package com.example.cpuscheduler.model;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS extends Scheduler {

    public FCFS(List<Process> processes){
        super(processes);
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule(){
        // Tạo một danh sách ganttChart để lưu trữ kết quả của lịch trình.
        List<Pair<Integer, Pair<Double,Double>>> ganttChart = new ArrayList<>();
        // Đặt thời gian bắt đầu và thời gian hoàn thành của tất cả các tiến trình trong danh sách processes về 0.0.
        for (Process p : processes) {
            p.setCompletionTime(0.0);
            p.setStartTime(0.0);
        }

        // Sắp xếp theo thời gian đến
        processes.sort(Comparator.comparingDouble(Process::getArrivalTime));
        // Khởi tạo biến currentTime bằng 0.0
        double currentTime = 0.0;

        // Duyệt qua từng tiến trình trong danh sách processes
        for (Process p : processes) {
            // Nếu currentTime < arrivalTime, CPU sẽ nhàn rỗi đến lúc process đến => cập nhật currentTime bằng arrivalTime của tiến trình
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }

            // Tính thời gian bắt đầu và thời gian hoàn thành của tiến trình
            double startTime = currentTime;
            double completionTime = startTime + p.getBurstTime();

            p.setStartTime(startTime);
            p.setCompletionTime(completionTime);

            // Thêm thông tin của tiến trình vào Gantt Chart, bao gồm id và cặp thời gian bắt đầu, thời gian hoàn thành
            ganttChart.add(new Pair<>(p.getId(), new Pair<>(startTime, completionTime)));

            // Cập nhật currentTime
            currentTime = completionTime;
        }

        // Phương thức này trả về danh sách ganttChart, chứa các cặp giá trị gồm ID của tiến trình và cặp giá trị thời gian bắt đầu và thời gian hoàn thành của tiến trình
        return ganttChart;
    }
}
