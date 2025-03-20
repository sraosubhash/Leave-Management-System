package main;

import java.util.List;
import java.util.Scanner;
import model.LeaveDetails;
import dao.LeaveDaoImpl;

public class ShowLeavedetailsMain {

    public static void main(String[] args) {
        LeaveDaoImpl dao = new LeaveDaoImpl();

        try {
            System.out.println("Fetching all leave records...");
            List<LeaveDetails> leaveList = dao.getAllLeaveDetails();

            if (leaveList.isEmpty()) {
                System.out.println("No leave records found.");
            } else {
                System.out.println("Leave Records:");
                for (LeaveDetails leaveDetails : leaveList) {
                    
                    System.out.println("Leave ID: " + leaveDetails.getLeaveId());
                    System.out.println("Employee ID: " + leaveDetails.getEmpno());
                    System.out.println("Employee Name: " + leaveDetails.getEmpName());
                    System.out.println("Leave Start Date: " + leaveDetails.getLeaveStartDate());
                    System.out.println("Leave End Date: " + leaveDetails.getLeaveEndDate());
                    System.out.println("Number of Days: " + leaveDetails.getNoOfDays());
                    System.out.println("Leave Reason: " + leaveDetails.getLeaveReason());
                    System.out.println("Leave Type: " + leaveDetails.getLeaveType());
                    System.out.println("Leave Status: " + leaveDetails.getLeaveStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching leave records: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
