package main;

import manager.dao.ManagerDaoImpl;
import model.LeaveDetails;

import java.util.List;
import java.util.Scanner;

public class ManagerLeaveShowMain {

    public static void main(String[] args) {
        // Create a scanner object to take user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the manager ID
        System.out.print("Enter Manager ID: ");
        int mgrId = scanner.nextInt();  // Read the manager ID from user input

        // Create an instance of ManagerDaoImpl
        ManagerDaoImpl managerDao = new ManagerDaoImpl();

        try {
            // Fetch leave details for employees under the given manager
            List<LeaveDetails> leaveDetailsList = managerDao.showEmpLeaveDetail(mgrId);

            // Display the leave details
            if (leaveDetailsList.isEmpty()) {
                System.out.println("No leave details found for employees under manager ID " + mgrId);
            } else {
                for (LeaveDetails leaveDetails : leaveDetailsList) {
                    System.out.println("Leave ID: " + leaveDetails.getLeaveId());
                    System.out.println("Employee Name: " + leaveDetails.getEmpName());
                    System.out.println("Leave Start Date: " + leaveDetails.getLeaveStartDate());
                    System.out.println("Leave End Date: " + leaveDetails.getLeaveEndDate());
                    System.out.println("Leave Reason: " + leaveDetails.getLeaveReason());
                    System.out.println("Leave Type: " + leaveDetails.getLeaveType());
                    System.out.println("Leave Status: " + leaveDetails.getLeaveStatus());
                    System.out.println("No of Days: " + leaveDetails.getNoOfDays());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
