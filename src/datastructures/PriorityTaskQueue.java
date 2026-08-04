package datastructures;

import model.Task;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityTaskQueue {

    PriorityQueue<Task> queue = new PriorityQueue<>(new Comparator<Task>() {

        public int compare(Task t1, Task t2) {
            return getPriorityValue(t2.getPriority()) - getPriorityValue(t1.getPriority());
        }
    });

    private int getPriorityValue(String priority) {

        return switch (priority.toUpperCase()) {
            case "HIGH" -> 3;
            case "MEDIUM" -> 2;
            case "LOW" -> 1;
            default -> 0;
        };
    }

    public void addTask(Task task) {
        queue.add(task);
    }

    public void displayTasksByPriority() {

        System.out.println("\n===== 📌 MY TASKS (BY PRIORITY) =====\n");
        for (Task task : queue) {

            System.out.println(task);
        }
    }

    public Task getNextTask() {

        for (Task task : queue) {

            if (!task.getStatus().equalsIgnoreCase("Completed")) {
                return task;
            }
        }

        return null;
    }

}