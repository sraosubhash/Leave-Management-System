package main;

import manager.dao.ManagerDaoImpl;

import java.util.Scanner;

public class ApprovLeaveMain {

    public static void main(String[] args) {
        // Create an instance of ManagerDaoImpl
        ManagerDaoImpl managerDao = new ManagerDaoImpl();

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        try {
            // Ask for the manager's ID
            System.out.print("Enter the Manager ID: ");
            int mgrId = scanner.nextInt();

            // Call the approveLeave method with the manager's ID
            String result = managerDao.approveLeave(mgrId);

            // Display the result
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();  // Handle exception properly
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
