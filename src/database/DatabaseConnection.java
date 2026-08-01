package database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    static String URL = "jdbc:mysql://localhost:3306/tms";
    static String USER = "root";
    static String PASSWORD = "";


    public static Connection getConnection() throws Exception{

        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);


//        if(con!=null)
//        {
//            System.out.println("Database Connected Successfully ✅ !");
//        }
//        else {
//            System.out.println("Connection Failed ❌ !");
//        }

        return con;
    }
}