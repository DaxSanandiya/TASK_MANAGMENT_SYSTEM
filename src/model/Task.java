package model;

public class Task {

    int taskId;
    int projectId;
    int assignedUserId;

    String taskTitle;
    String description;
    String priority;
    String status;
    String deadline;

    public Task(int taskId,
                int projectId,
                int assignedUserId,
                String taskTitle,
                String description,
                String priority,
                String status,
                String deadline) {

        this.taskId = taskId;
        this.projectId = projectId;
        this.assignedUserId = assignedUserId;
        this.taskTitle = taskTitle;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
    }

    @Override
    public String toString() {

        return "Task ID: " + taskId +
                " | Project ID: " + projectId +
                " | User ID: " + assignedUserId +
                " | Title: " + taskTitle +
                " | Priority: " + priority +
                " | Status: " + status +
                " | Deadline: " + deadline;
    }

    public String getPriority() {
        return priority;
    }
    public int getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getStatus() {
        return status;
    }

    public String getDeadline() {
        return deadline;
    }
}
