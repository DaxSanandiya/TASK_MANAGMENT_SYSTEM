package main;

import database.DatabaseConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class ReportManager {

    public static void showStatistics() throws Exception {

        Connection con = DatabaseConnection.getConnection();
        Statement stmt = con.createStatement();
        
        ResultSet users = stmt.executeQuery("SELECT COUNT(*) FROM users");
        users.next(); // initially ResultSet pointer is null
        int totalUsers = users.getInt(1);

        ResultSet projects = stmt.executeQuery("SELECT COUNT(*) FROM projects");
        projects.next();
        int totalProjects = projects.getInt(1);

        ResultSet tasks = stmt.executeQuery("SELECT COUNT(*) FROM tasks");
        tasks.next();
        int totalTasks = tasks.getInt(1);

        ResultSet completed = stmt.executeQuery("SELECT COUNT(*) FROM tasks WHERE status='Completed'");
        completed.next();
        int completedTasks = completed.getInt(1);

        System.out.println("\n===== SYSTEM REPORT =====");
        System.out.println("Total Users     : " + totalUsers);
        System.out.println("Total Projects  : " + totalProjects);
        System.out.println("Total Tasks     : " + totalTasks);
        System.out.println("Completed Tasks : " + completedTasks);
        System.out.println("Pending Tasks   : " + (totalTasks - completedTasks));


        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\DJD\\X-Report\\MiniJiraReport.txt"));

        bw.write("==============================================");
        bw.newLine();
        bw.write("         🤖   MINI JIRA REPORT");
        bw.newLine();
        bw.write("==============================================");
        bw.newLine();
        bw.newLine();

        // Overall details


        bw.write("👥 Total Users      : " + totalUsers);
        bw.newLine();

        bw.write("📂 Total Projects   : " + totalProjects);
        bw.newLine();

        bw.write("📃 Total Tasks      : " + totalTasks);
        bw.newLine();

        bw.write("✅ Completed Tasks  : " + completedTasks);
        bw.newLine();

        bw.write("❗Pending Tasks    : " + (totalTasks - completedTasks));
        bw.newLine();


        bw.newLine();
        bw.write("====================================");
        bw.newLine();
        bw.write(" 📊 PROJECT DETAILS");
        bw.newLine();
        bw.write("====================================");
        bw.newLine();
        bw.newLine();

        String projectQuery = "SELECT * FROM projects";

        ResultSet projectRs = stmt.executeQuery(projectQuery);

        while(projectRs.next()) {

            int projectId = projectRs.getInt("project_id");
            String projectName = projectRs.getString("project_name");
            String projectStatus = projectRs.getString("status");

            bw.newLine();
            bw.write("🆔 Project ID       : " + projectId);
            bw.newLine();

            bw.write("🏷️ Project Name    : " + projectName);
            bw.newLine();

            bw.write("⭕ Status          : " + projectStatus);
            bw.newLine();


            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM tasks WHERE project_id=?");
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            int projectTasks=0 ;
            int projectCompleted = 0;


            if(rs.next()) {

                projectTasks = rs.getInt(1);
            }

            PreparedStatement completedPs = con.prepareStatement("SELECT COUNT(*) FROM tasks WHERE project_id=? AND status='Completed'");
            completedPs.setInt(1, projectId);
            ResultSet completedRs = completedPs.executeQuery();


            if(completedRs.next()) {

                projectCompleted = completedRs.getInt(1);
            }

            int projectPending = projectTasks - projectCompleted;

            bw.write("🎯 Total Tasks     : " + projectTasks);
            bw.newLine();

            bw.write("✅ Completed Tasks : " + projectCompleted);
            bw.newLine();

            bw.write("❗ Pending Tasks   : " + projectPending);
            bw.newLine();
            bw.newLine();
            bw.newLine();
            bw.write("Tasks:");
            bw.newLine();

            PreparedStatement taskPs = con.prepareStatement("SELECT * FROM tasks WHERE project_id=?");
            taskPs.setInt(1, projectId);
            ResultSet taskRs =
                    taskPs.executeQuery();

            while(taskRs.next()) {

                bw.write("--------------------------------");
                bw.newLine();

                bw.write("🆔 Task ID       : " + taskRs.getInt("task_id"));
                bw.newLine();

                bw.write("🏷️ Task Name    : " + taskRs.getString("task_title"));
                bw.newLine();

                bw.write("📌 Priority     : " + taskRs.getString("priority"));
                bw.newLine();

                bw.write("🟢 Status       : " + taskRs.getString("status"));
                bw.newLine();

                bw.write("🤵 Assigned To  : " + taskRs.getInt("assigned_user_id"));
                bw.newLine();

                bw.write("⌛ Deadline     : " + taskRs.getString("deadline"));
                bw.newLine();
            }

            bw.newLine();
            bw.write("==================================================");
            bw.newLine();
            bw.newLine();
        }

        bw.close();

        System.out.println("\nReport Generated Successfully!");
        ActivityLogger.log("Generated Report");
    }
}