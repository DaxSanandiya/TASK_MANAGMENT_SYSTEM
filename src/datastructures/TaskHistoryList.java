package datastructures;

import java.util.LinkedList;
import model.Task;

public class TaskHistoryList {

    LinkedList<Task> history = new LinkedList<>();

    public void addCompletedTask(Task task) {

        history.add(task);
    }

    public void showHistory() {

        for(Task task : history) {

            System.out.println(task);
        }
    }
}