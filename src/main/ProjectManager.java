package main;

import database.DatabaseConnection;
import login.LoginManager;
import model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

                Project project =
                        new Project(

                                rs.getInt("project_id"),

                                rs.getString("project_name"),

                                rs.getString("description"),

                                rs.getString("status")
                        );

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
        String endDate = sc.nextLine();
        System.out.print("Status: ");
        String status = sc.nextLine();

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

            System.out.println("\nProject Created Successfully!");
            ActivityLogger.log("Created Project : " + projectName);

        } else {

            System.out.println("\nProject Creation Failed!");
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

            System.out.println("\nProject Deleted Successfully!");
            ActivityLogger.log("Deleted Project ID : " + projectId);

        } else {

            System.out.println("\nProject Not Found!");
        }
    }
}