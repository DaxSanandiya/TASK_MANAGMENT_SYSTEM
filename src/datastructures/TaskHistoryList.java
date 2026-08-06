package datastructures;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Stack;

public class TaskHistoryList {

    public static void showHistory() throws Exception {

        Connection con = DatabaseConnection.getConnection();

        String query = "SELECT * FROM activity_log ORDER BY action_time ASC";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        Stack<String> history = new Stack<>();

        while (rs.next()) {

            String record =
                    "🕒 " + rs.getTimestamp("action_time") + "\n" +
                            "👤 User ID : " + rs.getInt("user_id") + "\n" +
                            "⚡ " + rs.getString("action") + "\n" +
                            "-------------------------------------";

            history.push(record);

        }

        System.out.println("\n==============================");
        System.out.println("      📜 ACTIVITY HISTORY       ");
        System.out.println("================================");

        if (history.isEmpty()) {

            System.out.println("📭 No History Found");

        } else {

            while (!history.isEmpty()) {

                System.out.println(history.pop());

            }

        }

    }
}