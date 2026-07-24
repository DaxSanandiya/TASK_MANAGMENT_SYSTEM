package main;
import java.sql.*;
import java.util.*;
import model.User;
import database.DatabaseConnection;

import java.util.ArrayList;


public class UserManager {

    public static void viewALLUsers() throws Exception {

        ArrayList<User> users = new ArrayList<>();

        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next())
        {
            User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getInt("role_id"),
                    rs.getInt("team_id"));
            users.add(user);
        }

        System.out.println("\n================ 👥 USERS ================");

        for (User user : users) {

            System.out.println(user);
            System.out.println("------------------------------------------");
        }
    }

    public static void viewManagers() throws Exception {

        Connection con = DatabaseConnection.getConnection();

        String query = "SELECT * FROM users WHERE role_id=2";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("======== 👨‍💼 MANAGERS ========");

        while(rs.next()) {

            System.out.println(rs.getInt("user_id") +
                    " | " + rs.getString("full_name"));
        }
    }

    public static void viewTeamMembers() throws Exception {

        Connection con = DatabaseConnection.getConnection();

        String query = "SELECT * FROM users WHERE role_id=3";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("======== 👨‍💻 TEAM MEMBERS ========");

        while(rs.next()) {

            System.out.println(rs.getInt("user_id") +
                    " | " + rs.getString("full_name"));
        }
    }


    public static void createUser() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Full Name: ");
        String fullName = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password =sc.nextLine();
        while(password.length() < 4) {

            System.out.print(
                    "Password Must Be 4+ Characters or Digits 🦖 !!  "
            );

            password = sc.nextLine();
        }
        System.out.print("User ID: ");

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


        System.out.print("Role ID (1-Admin, 2-Manager, 3-Team Member): ");

        int roleId;

        while (true) {

            try {
                roleId = sc.nextInt();
                if(roleId >= 1 && roleId <= 3) {
                    break;
                }
                System.out.print("🦖 Invalid choice! Enter 1-3: ");
                break;

            }
            catch (Exception e) {
                sc.nextLine();
                System.out.print("❌ Invalid choice! Enter Numbers only !: ");
            }
        }


        System.out.print("Team ID: ");

        int teamId;

        while (true) {

            try {
                teamId = sc.nextInt();
                break;

            }
            catch (Exception e) {
                sc.nextLine();
                System.out.print("❌ Invalid choice! Enter Numbers only !: ");
            }
        }


        Connection con = DatabaseConnection.getConnection();

        String query = "INSERT INTO users " +
                        "(user_id, full_name, email, password, role_id, team_id, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, userId);
        ps.setString(2, fullName);
        ps.setString(3, email);
        ps.setString(4, password);
        ps.setInt(5, roleId);
        ps.setInt(6, teamId);

        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("\n✅ User Created Successfully!");
            ActivityLogger.log("Created User : " + fullName);
            System.out.print("↩️ Undo this action? (Y/N): ");

            sc.nextLine() ;
            String choice = sc.nextLine();

            if(choice.equalsIgnoreCase("Y")) {

                CallableStatement cs = con.prepareCall("{CALL UndoCreateUser(?)}");
                cs.setInt(1, userId);

                cs.execute();

                System.out.print("↩️ Undo Action Successfully!");
                ActivityLogger.log("Undo Created User : " + fullName);
            }else {
                System.out.print("✅ Action Completed !");
            }
        }
        else {

            System.out.println("\n❌ User Creation Failed!");
        }
    }

    public static void updateUser() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();
        sc.nextLine();
        System.out.print("New Full Name: ");
        String fullName = sc.nextLine();
        System.out.print("New Email: ");
        String email = sc.nextLine();
        System.out.print("New Team ID: ");
        int teamId = sc.nextInt();

        Connection con = DatabaseConnection.getConnection();

        String query = "UPDATE users SET full_name=?, email=?, team_id=? WHERE user_id=?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, fullName);
        ps.setString(2, email);
        ps.setInt(3, teamId);
        ps.setInt(4, userId);

        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("\n✅ User Updated Successfully !");

            ActivityLogger.log("Updated User ID : " + userId);
        }
        else {

            System.out.println("\n❌ User Not Found !");
        }
    }


    public static void deleteUser() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        Connection con = DatabaseConnection.getConnection();

        String query = "DELETE FROM users WHERE user_id=?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, userId);
        int rows = ps.executeUpdate();

        if(rows > 0) {

            System.out.println("\n✅ User Deleted Successfully!");
            ActivityLogger.log("Deleted User ID : " + userId);
        }
        else {

            System.out.println("\n❌ User Not Found!");
        }
    }
}