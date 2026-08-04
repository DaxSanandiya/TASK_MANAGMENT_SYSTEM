package datastructures;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaskHistoryList {

    public static void showHistory() throws Exception {

        Connection con = DatabaseConnection.getConnection();
        String query = "SELECT * FROM activity_log ORDER BY action_time DESC";
        PreparedStatement ps = con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n==============================");
        System.out.println("📜 ACTIVITY HISTORY");
        System.out.println("==============================");

        boolean found = false;

        while(rs.next()) {

            found = true;

            System.out.println("🕒 " + rs.getTimestamp("action_time"));
            System.out.println("👤 User ID : " + rs.getInt("user_id"));
            System.out.println("⚡ " + rs.getString("action"));
            System.out.println("-------------------------------------");
        }

        if(!found) {

            System.out.println("📭 No History Found");
        }

        con.close();
    }
}