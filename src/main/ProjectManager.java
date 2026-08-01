package main;

import database.DatabaseConnection;
import login.LoginManager;
import model.Project;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class ProjectManager {

    public static void viewProjects() throws Exception {

        ArrayList<Project> projects = new ArrayList<>();

            Connection con = DatabaseConnection.getConnection();
            String query ="SELECT * FROM projects";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);


            while(rs.next()) {

                Project project = new Project(
                                rs.getInt("project_id"),
                                rs.getString("project_name"),
                                rs.getString("description"),
                                rs.getString("status"));

                projects.add(project);
            }

            System.out.println("\n=======  📂 PROJECTS  =======\n");

            for(Project project : projects) {

                System.out.println(project);
            }
    }

    public static void createProject() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Project Name: ");
        String projectName = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();


        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while(true) {
            try {

                endDate = sc.nextLine();
                LocalDate date = LocalDate.parse(endDate, formatter);

                if (date.isBefore(LocalDate.now())) {

                    System.out.print("❌ Date cannot be in the past! Enter Again: ");
                    continue;
                }

                break;
            }
            catch (Exception e) {
                System.out.print("❌ Invalid Format ! Right Format is 👉 YYYY-MM-DD: ");
            }
        }

        String status ="Pending";

        int createdBy = LoginManager.UserId;

        Connection con = DatabaseConnection.getConnection();

        String query = "INSERT INTO projects (project_name, description, start_date, end_date, status, created_by) " +
                "VALUES (?, ?, CURDATE(), ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, projectName);
        ps.setString(2, description);
        ps.setString(3, endDate);
        ps.setString(4, status);
        ps.setInt(5, createdBy);

        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("\n✅ Project Created Successfully!");
            ActivityLogger.log("Created Project : " + projectName);

        } else {

            System.out.println("\n❌ Project Creation Failed!");
        }
    }

    public static void deleteProject() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Project ID: ");
        int projectId = sc.nextInt();

        Connection con = DatabaseConnection.getConnection();
        String query = "DELETE FROM projects WHERE project_id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, projectId);
        int rows = ps.executeUpdate();

        if(rows > 0) {

            CallableStatement cs = con.prepareCall("{CALL ResetProjectAutoIncrement()}");
            cs.execute();

            System.out.println("\n✅ Project Deleted Successfully!");
            ActivityLogger.log("Deleted Project ID : " + projectId);

        } else {

            System.out.println("\n❌ Project Not Found!");
        }
    }
}