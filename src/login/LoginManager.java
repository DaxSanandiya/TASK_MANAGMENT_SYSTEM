package login;

import database.DatabaseConnection;
import main.ActivityLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginManager {

    public static int UserId;
    public static int RoleId;

    public static int login() throws Exception{

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────┐");
        System.out.println("|          🌐 TASK MANAGMENT SYSTEM LOGIN                |");
        System.out.println("└────────────────────────────────────────────────────────┘");
        System.out.println();

        System.out.print(" ✉️ Email: ");
        String email = sc.nextLine();
        System.out.print(" 🔑 Password: ");
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

            System.out.println("Database Connected Successfully ✅ !");
            System.out.println("\nLogin Successful 🙌 !");
            ActivityLogger.log("Logged In ");

            return RoleId;
        }
        else
        {
            System.out.println("\nInvalid Email or Password 🤦 ");
            return -1;
        }
    }
}