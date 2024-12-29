package com.example.cpuscheduler.testScheduler;

import com.example.cpuscheduler.model.RoundRobin;
import com.example.cpuscheduler.model.Scheduler;
import com.example.cpuscheduler.model.Process;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.util.Pair;


import java.util.ArrayList;
import java.util.List;

public class testRoundRobin {
    public static void main(String[] args) {
        Scheduler scheduler;
        List<Process> list = new ArrayList<>();
        Process process1 = new Process(1, 0.0, 5.0);
        Process process2 = new Process(2, 0.0, 6.0);
        Process process3 = new Process(3, 0.0, 7.0);
        Process process4 = new Process(4, 0.0, 8.0);
        Process process5 = new Process(5, 0.0, 9.0);
        Process process6 = new Process(6, 0.0, 10.0);
        list.add(process1);
        list.add(process2);
        list.add(process3);
        list.add(process4);
        list.add(process5);
//        list.add(process6);
        scheduler = new RoundRobin(list, 3.0);
        List<Pair<Integer, Pair<Double, Double>>> result = scheduler.schedule();
        for(Pair<Integer, Pair<Double, Double>> pair : result){
            System.out.println(pair.getKey() + " " + pair.getValue().getKey() + " " + pair.getValue().getValue());
        }
//        System.out.println();
//        System.out.println();

        List<Double> timestamps = new ArrayList<Double>();
        List<Integer> currentProcess = new ArrayList<Integer>();
        List<Double> se1 = new ArrayList<Double>();
        List<Double> se2 = new ArrayList<Double>();
        List<Pair<Double, Pair<Double, Double>>> color = new ArrayList<Pair<Double, Pair<Double, Double>>>();

        for (Pair<Integer, Pair<Double, Double>> p : result) {
            currentProcess.add(p.getKey());
        }
        for(int i = 0; i < result.size(); i++){
            Double colorRed = Math.random();
            Double colorGreen = Math.random();
            Double colorYellow = Math.random();
            color.add(new Pair<>(colorRed, new Pair<>(colorGreen, colorYellow)));
        }

        for (Pair<Integer, Pair<Double, Double>> process : result) {
            timestamps.add(process.getValue().getValue() - process.getValue().getKey());
            System.out.println(timestamps.size());
            System.out.println(process.getValue().getValue() - process.getValue().getKey());
            currentProcess.add(process.getKey());
            se1.add(process.getValue().getKey());
            se2.add(process.getValue().getValue());
        }

        int index = 0;
        double time = 0, cnt = 0;
        System.out.println(timestamps.size());
        for (double timestamp : timestamps) {
            double waitTime;
            if (index == 0) waitTime = 0;
            else waitTime = timestamps.get(index - 1);
            double x, y;
            if ((time + timestamp) * 60 > 500) {
                time = 0;
                cnt++;
            }
            x = time * 60;
            y = cnt * 80;
            time += timestamp;
            String stringSecond1 = String.valueOf(se1.get(index));
            String stringSecond2 = String.valueOf(se2.get(index));
            String curProcess = String.valueOf(currentProcess.get(index));
            Pair<Double, Pair<Double, Double>> currentColor = color.get(currentProcess.get(index) - 1);
//            System.out.println(stringSecond1 + stringSecond2 + curProcess + currentColor);
            System.out.println(waitTime);
            index++;
        }
    }
}