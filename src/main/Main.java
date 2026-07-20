package main;
import login.LoginManager;
import database.DatabaseConnection;
import static login.LoginManager.RoleId;


public class Main {

    public static void main(String[] args) throws Exception {
        DatabaseConnection.getConnection();

        while (true) {
            if (!(LoginManager.login() == -1)) {


                while (true) {

                    if (RoleId == 1 || RoleId == 2 || RoleId == 3) {
                        break;
                    }

                    System.out.print("🦖 Role ID must be 1, 2 or 3: ");
                    break;
                }


                    switch (RoleId) {

                        case 1:
                            Menu.showAdminMenu();
                            break;


                        case 2:
                            Menu.showManagerMenu();
                            break;

                        case 3:
                            Menu.showMemberMenu();
                            break;

                        default:
                            System.out.println("🦖 Login Failed");
                    }

                return;
            }
        }
    }
}