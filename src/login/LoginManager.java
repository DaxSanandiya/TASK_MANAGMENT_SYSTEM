package login;

import java.sql.*;
import java.util.Scanner;

import database.DatabaseConnection;

public class LoginManager {

    public static int UserId;
    public static int RoleId;

    public static int login() throws Exception{

        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== TASK MANAGMENT SYSTEM LOGIN =====");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();


        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT user_id,role_id FROM users " + "WHERE email=? AND password=?";
        PreparedStatement ps =con.prepareStatement(query);


        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            RoleId = rs.getInt("role_id");
            UserId = rs.getInt("user_id");

            System.out.println("\nLogin Successful!");

            return RoleId;
        }
        else
        {
            System.out.println("\nInvalid Email or Password");
            return -1;
        }
    }
}