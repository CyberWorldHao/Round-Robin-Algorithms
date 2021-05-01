/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author CWH
 */
import java.io.PrintStream;
import java.util.*;

// Java program for implementation of Round-Robin scheduling

public class RR {
    // Method to find the waiting time for all
    // processes
    static void findWaitingTime(int processes[], int n, int bt[], int wt[], int quantum) {
        // Make a copy of burst times bt[] to store remaining
        // burst times.
        int rem_bt[] = new int[n];
        int timewait = 0;
        int count = 1;
        for (int i = 0; i < n; i++)
            rem_bt[i] = bt[i];

        int t = 0; // Current time

        // Keep traversing processes in round robin manner
        // until all of them are not done.
        while (true) {
            boolean done = true;
            // Traverse all processes one by one repeatedly
            for (int i = 0; i < n; i++) {
                // If burst time of a process is greater than 0
                // then only need to process further
                if (rem_bt[i] > 0) {
                    done = false; // There is a pending process
                    System.out.println("### Running " + (count++));
                    System.out.println("Scheduling to Process " + (i + 1));
                    if (rem_bt[i] > quantum) {
                        // Increase the value of t i.e. shows
                        // how much time a process has been processed
                        t += quantum;
                        System.out.println("Time use is " + quantum);
                        System.out.println("Time wait is " + timewait);
                        timewait += quantum;
                        // Decrease the burst_time of current process
                        // by quantum
                        rem_bt[i] -= quantum;
                    }

                    // If burst time is smaller than or equal to
                    // quantum. Last cycle for this process
                    else {
                        // Increase the value of t i.e. shows
                        // how much time a process has been processed
                        t = t + rem_bt[i];

                        // Waiting time is current time minus time
                        // used by this process
                        wt[i] = t - bt[i];
                        System.out.println("Time use is " + rem_bt[i]);
                        System.out.println("Time wait is " + timewait);
                        timewait += rem_bt[i];
                        // As the process gets fully executed
                        // make its remaining burst time = 0
                        rem_bt[i] = 0;
                    }
                    System.out.println("");
                }
            }
            if (rem_bt[0] >= quantum) {
                timewait -= quantum;
            } else {
                timewait -= rem_bt[0];
            }
            // If all processes are done
            if (done == true)
                break;
        }
    }

    // Method to calculate turn around time
    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {
        // calculating turnaround time by adding
        // bt[i] + wt[i]
        for (int i = 0; i < n; i++)
            tat[i] = bt[i] + wt[i];
    }

    // Method to calculate average time
    static void findavgTime(int processes[], int n, int bt[], int quantum) {
        int wt[] = new int[n], tat[] = new int[n];
        double total_wt = 0, total_tat = 0;

        // Function to find waiting time of all processes
        findWaitingTime(processes, n, bt, wt, quantum);

        // Function to find turn around time for all processes
        findTurnAroundTime(processes, n, bt, wt, tat);

        // Display processes along with all details
        System.out.println("### Result");
        System.out.printf("|%10s |%11s |%13s |%16s|\n", "Processes", "Burst time", "Waiting time", "Turn around time");
        System.out.printf("|%10s |%11s |%13s |%16s|\n", "---------", "----------", "------------", "----------------");
        // Calculate total waiting time and total turn
        // around time
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            // System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t " + wt[i] + "\t\t " +
            // tat[i]);
            System.out.printf("|%10d |%11d |%13d |%16d|\n", (i + 1), bt[i], wt[i], tat[i]);
        }

        System.out.printf("Average waiting time = %.3f", (total_wt / n));
        System.out.println("");
        System.out.printf("Average turn around time = %.3f", (total_tat / n));
    }

    // Driver Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Create a Scanner object
        // process id's
        System.out.print("Please enter the numbers of process : ");
        int n = sc.nextInt();
        int processes[] = new int[n];
        // Burst time of all processes
        int burst_time[] = new int[n];

        System.out.println("Please enter the processes' burst-time info accordingly");
        System.out.println("Burst-Time (space) Next Burst-Time ...");
        for (int i = 0; i < n; i++) {
            processes[i] = i;
            burst_time[i] = sc.nextInt();
        }
        sc.close();
        // Time quantum
        int quantum = 5;

        // Print to file
        String outputFileName = ".\\RR_output.md";
        try {
            PrintStream fileStream = new PrintStream(outputFileName);
            System.setOut(fileStream);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        findavgTime(processes, n, burst_time, quantum);

    }
}