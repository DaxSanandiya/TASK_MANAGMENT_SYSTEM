package main;

import java.util.Scanner;


/// search user
/// deadline --------team member view  & manager can update
/// salary
/// implement ds classes

public class    Menu {

    public static void showAdminMenu() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== ADMIN MENU =====");

        System.out.println("1. View Users");
        System.out.println("2. Create User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");

        System.out.println("5. View Projects list");
        System.out.println("6. View Tasks list");

        System.out.println("7. Get Report");
        System.out.println("8. Logout");

        while (true) {

//            Menu.showAdminMenu();

            int choice = sc.nextInt(); //validate
            while ((choice < 1) || (choice > 8)) {
                System.out.println("Invalid choice, please try again: ");
            }

            switch (choice) {

                case 1:
                    System.out.println("1. View All Users");
                    System.out.println("2. View Managers ");
                    System.out.println("3. View Team Members ");
                    System.out.println("Enter choice: ");
                    int c = sc.nextInt();
                    if (c == 1) {UserManager.viewALLUsers();}
                    else if (c == 2) {UserManager.viewManagers();}
                    else if (c == 3) {UserManager.viewTeamMembers();}
                    else {System.out.println("Invalid choice, please try again: ");}
                    break;

                case 2:
                    UserManager.createUser();
                    break;

                case 3:
                    UserManager.updateUser();
                    break;

                case 4:
                    UserManager.deleteUser();
                    break;

                case 5:
                    ProjectManager.viewProjects();
                    break;

                case 6:
                    TaskManager.viewTasks();
                    break;


                case 7:
                    ReportManager.showStatistics();
                    break;

                case 8:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void showManagerMenu() throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== PROJECT MANAGER MENU =====");

        System.out.println("1. View Projects");
        System.out.println("2. Create Project");
        System.out.println("3. Delete Project");

        System.out.println("4. View Tasks");
        System.out.println("5. Create Task");
        System.out.println("6. Update Task Status");
        System.out.println("7. Delete Task");

        System.out.println("8. Get Report");

        System.out.println("9. Logout");

        while(true) {

            System.out.println("Enter Choice: ");
            int choice = sc.nextInt();
            while ((choice < 1) || (choice > 9)) {
                System.out.println("Invalid choice, please try again: ");
            }

            switch(choice) {

                case 1:
                    ProjectManager.viewProjects();
                    break;

                case 2:
                    ProjectManager.createProject();
                    break;
                case 3:
                    ProjectManager.deleteProject();
                    break;

                case 4:
                    TaskManager.viewTasks();
                    break;

                case 5:
                    TaskManager.createTask();
                    break;

                case 6:
                    TaskManager.updateTaskStatus();
                    break;

                case 7:
                    TaskManager.deleteTask();
                    break;
                case 8:
                    ReportManager.showStatistics();
                    break;

                case 9:
                    System.out.println("\nLogged Out!");
                    return;

                default:
                    System.out.println("\nInvalid Choice!");
                    return;
            }
        }

    }

    public static void showMemberMenu() throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== TEAM MEMBER MENU =====");

        System.out.println("1. View My Tasks");
        System.out.println("2. Update Task Status");
        System.out.println("3. View Task in Priority ");

        System.out.println("4. Logout");

        while(true) {

            System.out.println("Enter Choice: ");
            int choice = sc.nextInt(); //validate
            while ((choice < 1) || (choice > 4)) {
                System.out.println("Invalid choice, please try again: ");
            }

            switch(choice) {

                case 1:
                    TaskManager.viewTasks();
                    break;

                case 2:
                    TaskManager.updateTaskStatus();
                    break;

                case 3:
                    TaskManager.loadTasksIntoQueue();
                    break;

                case 4:
                    System.out.println("\nLogged Out!");
                    return;

                default:
                    System.out.println("\nInvalid Choice!");
            }
        }
    }
}