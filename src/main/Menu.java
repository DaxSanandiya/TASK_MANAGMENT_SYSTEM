package main;

import datastructures.TaskHistoryList;

import java.util.Scanner;



public class    Menu {

    public static void showAdminMenu() throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("──────────────────────────────────────────────");
            System.out.println("               👑 ADMIN  MENU                 ");
            System.out.println("──────────────────────────────────────────────");

            System.out.println("1. 👁️👁️ View Users");
            System.out.println("2. 👤 Create User");
            System.out.println("3. ✏️ Update User");
            System.out.println("4. ❌ Delete User");

            System.out.println("5. 👀 View Projects list");
            System.out.println("6. 📃 View Tasks list");

            System.out.println("7. 📋 Get Report");
            System.out.println("8. 📜 View Activity History");

            System.out.println("9. 🚪🏃‍  ️Logout");

            System.out.println("Enter Your Choice : ");
            int choice ;

            while (true) {

                try {
                    choice = sc.nextInt(); //validate
                    if(choice >= 1 && choice <= 9) {
                        break;
                    }
                    System.out.print("❌ Invalid choice! Enter 1-9: ");
                }
                catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid choice 🤦, please try again 🥹 : ");
                }
            }


            switch (choice) {

                case 1:
                    System.out.println("1.👀 View All Users");
                    System.out.println("2.👀 View Managers ");
                    System.out.println("3.👀 View Team Members ");
                    System.out.println("Enter choice: ");

                    int c;
                    while (true) {

                        try {
                            c = sc.nextInt(); //validate
                            if(c >= 1 && c <= 3) {
                                break;
                            }
                            System.out.print("❌ Invalid choice! Enter 1-9: ");
                        }
                        catch (Exception e) {
                            sc.nextLine();
                            System.out.println("Invalid choice 🤦, please try again 🥹 : ");
                        }
                    }


                    if (c == 1) {UserManager.viewALLUsers();}
                    else if (c == 2) {UserManager.viewManagers();}
                    else {UserManager.viewTeamMembers();}
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
                    TaskHistoryList.showHistory();
                    break;

                case 9:
                    System.out.println("\n🚪🏃‍♂️ Logged Out!");
                    ActivityLogger.log("Logged Out ");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void showManagerMenu() throws Exception {

        Scanner sc = new Scanner(System.in);


        while(true) {


            System.out.println("──────────────────────────────────────────────");
            System.out.println("         👨‍💼  PROJECT MANAGER MENU             ");
            System.out.println("──────────────────────────────────────────────");

            System.out.println("1. 👀 View Projects");
            System.out.println("2. 📁 Create Project");
            System.out.println("3. ❌ Delete Project");

            System.out.println("4. 👀 View Tasks");
            System.out.println("5. 📋 Create Task");
            System.out.println("6. ✏️ Update Task Status");
            System.out.println("7. ❌ Delete Task");

            System.out.println("8. 📊 Generate Report");
            System.out.println("9. 📜 View Activity History");

            System.out.println("10. 🚪🏃‍♂️ Logout");

            System.out.println("Enter Choice: ");

            int choice;
            while (true) {

                try {
                    choice = sc.nextInt(); //validate
                    if(choice >= 1 && choice <= 10) {
                        break;
                    }
                    System.out.print("❌ Invalid choice! Enter 1-9: ");
                }
                catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid choice 🤦, please try again 🥹 : ");
                }
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
                    TaskHistoryList.showHistory();
                    break;

                case 10:
                    System.out.println("\n🚪🏃‍♂️ Logged Out!");
                    ActivityLogger.log("Logged Out ");
                    return;

                default:
                    System.out.println("\nInvalid Choice 🤦 !");
                    return;
            }
        }

    }

    public static void showMemberMenu() throws Exception {

        Scanner sc = new Scanner(System.in);



        while(true) {


            System.out.println("──────────────────────────────────────────────");
            System.out.println("            👨‍💻  TEAM MEMBER MENU              ");
            System.out.println("──────────────────────────────────────────────");


            System.out.println("1. 📋 View My Tasks");
            System.out.println("2. ✏️ Update Task Status");

            System.out.println("3. 🔥 View Tasks By Priority");
            System.out.println("4. ⏭️ Get Next Task");

            System.out.println("5. 🚪 Logout");

            System.out.println("Enter Choice: ");

            int choice;
            while (true) {

                try {
                    choice = sc.nextInt(); //validate
                    if(choice >= 1 && choice <= 5) {
                        break;
                    }
                    System.out.print("❌ Invalid choice! Enter 1-9: ");
                }
                catch (Exception e) {
                    sc.nextLine();
                    System.out.println("Invalid choice 🤦, please try again 🥹 : ");
                }
            }

            switch(choice) {

                case 1:
                    TaskManager.viewMyTasks();
                    break;

                case 2:
                    TaskManager.updateTaskStatus();
                    break;

                case 3:
                    TaskManager.loadTasksIntoQueue();
                    break;

                case 4:
                    TaskManager.getNextTask();
                    break;
                case 5:
                    System.out.println("\n🚪🏃‍♂️ Logged Out!");
                    ActivityLogger.log("Logged Out ");
                    return;

                default:
                    System.out.println("\nInvalid Choice 🤦 !");
            }
        }
    }
}