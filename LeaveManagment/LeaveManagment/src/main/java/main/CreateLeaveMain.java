package main;

import java.util.Scanner;
import model.LeaveDetails;
import model.LeaveType;  
import dao.LeaveDaoImpl;

public class CreateLeaveMain {

    public static void main(String[] args) {
        LeaveDetails leaveDetails = new LeaveDetails();
        Scanner scanner = new Scanner(System.in);
        
 
        System.out.println("Please enter the Employee Number:");
        leaveDetails.setEmpno(scanner.nextInt());
        scanner.nextLine(); 

        System.out.println("Please enter the Employee Name:");
        leaveDetails.setEmpName(scanner.nextLine());

        System.out.println("Please enter the Leave Start Date (yyyy-MM-dd):");
        String startDate = scanner.nextLine();
        leaveDetails.setLeaveStartDate(java.sql.Date.valueOf(startDate)); 

        System.out.println("Please enter the Leave End Date (yyyy-MM-dd):");
        String endDate = scanner.nextLine();
        leaveDetails.setLeaveEndDate(java.sql.Date.valueOf(endDate)); 

       
        System.out.println("Please provide the reason for the leave:");
        leaveDetails.setLeaveReason(scanner.nextLine());

        System.out.println("Please specify the Leave Type (e.g., EL, PL, SL):");
        String leaveType = scanner.nextLine();
        leaveDetails.setLeaveType(LeaveType.valueOf(leaveType.toUpperCase())); 

        LeaveDaoImpl dao = new LeaveDaoImpl();
        try {
            System.out.println(dao.createLeaveDetails(leaveDetails));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
