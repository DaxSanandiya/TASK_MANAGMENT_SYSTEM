package main;

import database.DatabaseConnection;
import login.LoginManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityLogger {

    public static void log(String action)
            throws Exception {

        Connection con = DatabaseConnection.getConnection();
        String query = "INSERT INTO activity_log(user_id, action) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);


        ps.setInt(1, LoginManager.UserId);
        ps.setString(2, action);

        ps.executeUpdate();
    }


}