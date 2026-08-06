package main;

import database.DatabaseConnection;
import login.LoginManager;

import static login.LoginManager.RoleId;


public class Main {

    public static void main(String[] args) throws Exception {
        DatabaseConnection.getConnection();


        while (true) {
            if (!(LoginManager.login() == -1)) {

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
                            return;
                    }

            }
        }
    }
}