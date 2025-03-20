package brillio.LeaveManagment;

import java.sql.Date;
import java.util.Scanner;

import dao.LeaveDaoImpl;
import model.LeaveDetails;
import model.LeaveType;

public class UpdateLeaveMain {
	
	
	/*
	 * public class UpdateLeaveMain {
    public static void main(String[] args) {
        LeaveDaoImpl dao = new LeaveDaoImpl();
        Scanner scanner = new Scanner(System.in);

        try {
            // Collecting input details
            System.out.print("Enter Leave ID to update: ");
            int leaveId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            // Check if Leave ID exists using the searchLeaveDetails method
            String searchResult = dao.searchLeaveDetails(leaveId);
            if (searchResult.startsWith("No leave record found")) {
                System.out.println(searchResult); // Print the error and return
                return;
            }

            // Proceed with update since Leave ID exists
            LeaveDetails leaveDetails = new LeaveDetails();
            leaveDetails.setLeaveId(leaveId);

            System.out.print("Enter Employee Number: ");
            leaveDetails.setEmpno(scanner.nextInt());
            scanner.nextLine(); // consume newline
            System.out.print("Enter Employee Name: ");
            leaveDetails.setEmpName(scanner.nextLine());
            System.out.print("Enter Leave Start Date (yyyy-mm-dd): ");
            leaveDetails.setLeaveStartDate(Date.valueOf(scanner.nextLine()));
            System.out.print("Enter Leave End Date (yyyy-mm-dd): ");
            leaveDetails.setLeaveEndDate(Date.valueOf(scanner.nextLine()));
            System.out.print("Enter Leave Reason: ");
            leaveDetails.setLeaveReason(scanner.nextLine());
            System.out.print("Enter Leave Type (EL,SL,PL): ");
            leaveDetails.setLeaveType(LeaveType.valueOf(scanner.nextLine().toUpperCase()));

           
            String updateResult = dao.updateLeaveDetails(leaveDetails);

            // Display the result
            System.out.println(updateResult);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid input. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } 
    }
}

	 */

}
