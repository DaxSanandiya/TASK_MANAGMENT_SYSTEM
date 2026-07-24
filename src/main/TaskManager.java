package main;

import database.DatabaseConnection;
import datastructures.PriorityTaskQueue;
import model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static login.LoginManager.UserId;

public class TaskManager {

    public static void viewTasks() throws Exception{

        ArrayList<Task> tasks = new ArrayList<>();

        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM tasks";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while(rs.next()) {

            Task task = new Task(

                    rs.getInt("task_id"),
                    rs.getInt("project_id"),
                    rs.getInt("assigned_user_id"),
                    rs.getString("task_title"),
                    rs.getString("description"),
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getString("deadline")
            );

            tasks.add(task);
        }

        System.out.println("\n=======  📃 TASKS  =======\n");

        for(Task task : tasks) {

            System.out.println(task);
        }
    }

    public static void createTask() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Project ID: ");
        int projectId;

        while (true) {

            try {
                projectId = sc.nextInt();
                break;

            }
            catch (Exception e) {
                sc.nextLine();
                System.out.print("❌ Invalid choice! Enter Numbers only !: ");
            }
        }

        System.out.print("Assigned User ID: ");

        int userId;

        while (true) {

            try {
                userId = sc.nextInt();
                break;

            }
            catch (Exception e) {
                sc.nextLine();
                System.out.print("❌ Invalid choice! Enter Numbers only !: ");
            }
        }

        sc.nextLine();

        System.out.print("Task Title: ");
        String title = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        System.out.print("Priority: ");
        String priority;
        while(true) {

            priority = sc.nextLine();

            if(priority.equalsIgnoreCase("High") ||
                    priority.equalsIgnoreCase("Medium") ||
                    priority.equalsIgnoreCase("Low")) {

                break;
            }

            System.out.print("☣️ Enter only High, Medium or Low : "
            );
        }

        String status = "Pending";

        System.out.print("Deadline (YYYY-MM-DD): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String deadline ;
        while(true) {
            try {

                deadline = sc.nextLine();
                LocalDate.parse(deadline, formatter);
                break;
            }
            catch (Exception e) {
//                sc.nextLine();
                System.out.print("❌ Invalid Format ! Right Format is 👉 YYYY-MM-DD: ");
            }
        }


        Connection con = DatabaseConnection.getConnection();

        String query = "INSERT INTO tasks " + "(project_id, assigned_user_id, task_title, description, priority, status, deadline) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, projectId);
        ps.setInt(2, userId);
        ps.setString(3, title);
        ps.setString(4, description);
        ps.setString(5, priority);
        ps.setString(6, status);
        ps.setString(7, deadline);

        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("\n✅ Task Created Successfully!");
            ActivityLogger.log("Created Task : " + title);
        }else {
            System.out.println("\n❌ Task Creation Failed!");
        }
    }

    public static void updateTaskStatus() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Task ID: ");
        int taskId;

        while (true) {

            try {
                taskId = sc.nextInt();
                break;

            }
            catch (Exception e) {
                sc.nextLine();
                System.out.print("❌ Invalid choice! Enter Numbers only !: ");
            }
        }

        sc.nextLine(); //flush

        System.out.print("New Status: ");
        String status;

        while(true) {

            status = sc.nextLine();

            if(status.equalsIgnoreCase("Pending") ||
                    status.equalsIgnoreCase("In Progress") ||
                    status.equalsIgnoreCase("Completed"))
            {
                break;
            }

            System.out.print("👾 Enter Pending, In Progress or Completed: ");
        }




        Connection con = DatabaseConnection.getConnection();
        String query = "UPDATE tasks SET status=? WHERE task_id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, status);
        ps.setInt(2, taskId);


        PreparedStatement ps1 = con.prepareStatement("SELECT status FROM tasks WHERE task_id=?");
        ps1.setInt(1, taskId);

        ResultSet rs = ps1.executeQuery(); // because we don't have oldStatus for undo!

        String oldStatus = "";

        if (rs.next()) {
            oldStatus = rs.getString("status");
        }


        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("✅ Status Updated Successfully!");
            ActivityLogger.log("Updated Task ID : " + taskId);

            System.out.print("↩️ Undo this action? (Y/N): ");
            String choice = sc.nextLine();

            if(choice.equalsIgnoreCase("Y")) {


                CallableStatement cs = con.prepareCall("{CALL UndoTaskStatus(?, ?)}");

                cs.setInt(1, taskId);
                cs.setString(2, oldStatus);

                cs.execute();

                System.out.print("↩️ Undo Action Successfully!");
                ActivityLogger.log("Undo Task Status : " + taskId);
            }else {
                System.out.print("✅ Action Completed !");
            }
        }
        else {
            System.out.println("❌ Status Updated Failed!");
        }
    }

    public static void deleteTask() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Task ID: ");
        int taskId = sc.nextInt();

        Connection con = DatabaseConnection.getConnection();
        String query = "DELETE FROM tasks WHERE task_id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, taskId);

        int rows = ps.executeUpdate();

        if(rows > 0) {

            CallableStatement cs = con.prepareCall("{CALL ResetTaskAutoIncrement()}");
            cs.execute();

            System.out.println("✅ Task Deleted Successfully!");
            ActivityLogger.log("Deleted Task ID : " + taskId);
        }
        else {

            System.out.println("❌ Task Not Found!");
        }
    }


    public static PriorityTaskQueue priorityQueue = new PriorityTaskQueue();

        public static void loadTasksIntoQueue() throws Exception {

            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM tasks where assigned_user_id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,UserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getInt("project_id"),
                        rs.getInt("assigned_user_id"),
                        rs.getString("task_title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getString("deadline")
                );

                priorityQueue.addTask(task);
            }
            priorityQueue.displayTasksByPriority();
        }

    public static void getNextTask() {

        Task task = priorityQueue.getNextTask();

        if(task == null) {

            System.out.println("\n❌ No Tasks Available");
            return;
        }


        System.out.println("======== 🎯 NEXT TASK ========");

        System.out.println("🆔 ID       : " + task.getTaskId());
        System.out.println("📋 Title    : " + task.getTaskTitle());

        System.out.println("🔥 Priority : " + task.getPriority());
        System.out.println("📍 Status   : " + task.getStatus());

        System.out.println("📅 Deadline : " + task.getDeadline());
    }
}