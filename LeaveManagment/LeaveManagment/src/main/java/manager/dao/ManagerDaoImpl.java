package manager.dao;

import model.LeaveDetails;
import util.ConnectionHelper;
import model.LeaveType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerDaoImpl implements ManagerDao {

    @Override
    public List<LeaveDetails> showEmpLeaveDetail(int mgrId) throws Exception {
        List<LeaveDetails> leaveDetailsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ResultSet leaveRs = null;

        try {
            connection = ConnectionHelper.getConnection();

            // Get the list of employees under the manager from the 'employ' table
            String getEmpIdsCmd = "SELECT empno FROM employ WHERE mgr = ?";
            psmt = connection.prepareStatement(getEmpIdsCmd);
            psmt.setInt(1, mgrId);
            rs = psmt.executeQuery();

            // Loop through each employee under the manager and fetch their leave details
            while (rs.next()) {
                int empno = rs.getInt("empno");

                // Fetch leave details for the employee from the 'leaves' table
                String getLeaveDetailsCmd = "SELECT * FROM leaves WHERE empno = ?";
                psmt = connection.prepareStatement(getLeaveDetailsCmd);
                psmt.setInt(1, empno);
                leaveRs = psmt.executeQuery();

                // Loop through the result set for leave details
                while (leaveRs.next()) {
                    LeaveDetails leaveDetails = new LeaveDetails();
                    leaveDetails.setLeaveId(leaveRs.getInt("leaveid"));
                    leaveDetails.setEmpno(leaveRs.getInt("empno"));
                    leaveDetails.setEmpName(leaveRs.getString("empname"));
                    leaveDetails.setLeaveStartDate(leaveRs.getDate("leavestartdate"));
                    leaveDetails.setLeaveEndDate(leaveRs.getDate("leaveenddate"));
                    leaveDetails.setNoOfDays(leaveRs.getInt("noofdays"));
                    leaveDetails.setLeaveReason(leaveRs.getString("leavereason"));
                    leaveDetails.setLeaveType(LeaveType.valueOf(leaveRs.getString("leavetype").toUpperCase()));
                    leaveDetails.setLeaveStatus(leaveRs.getString("leave_status"));

                    leaveDetailsList.add(leaveDetails);  // Add the leave details to the list
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception (you can also use a logging framework)
            throw new Exception("Error fetching leave details for employees under manager ID " + mgrId, e);
        } 
        return leaveDetailsList;  // Return the list of leave details for all employees under the manager
    }

    @Override
    public String approveLeave(int mgr) throws Exception {
        Connection connection = ConnectionHelper.getConnection();

        // Fetch all leave details for employees under the given manager
        List<LeaveDetails> leaveDetailsList = showEmpLeaveDetail(mgr);
        
        if (leaveDetailsList.isEmpty()) {
            return "No leave details found for employees under manager ID " + mgr;
        }
        
        // Display leave details for the manager to choose from
        System.out.println("Please select a Leave ID to approve/decline:");
        for (LeaveDetails leaveDetails : leaveDetailsList) {
            System.out.println("Leave ID: " + leaveDetails.getLeaveId() + ", Employee Name: " + leaveDetails.getEmpName());
        }

        // Ask the manager to input a specific leave ID to approve/decline
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Leave ID to approve/decline: ");
        int leaveId = scanner.nextInt();

        // Find the selected leave details
        LeaveDetails selectedLeave = null;
        for (LeaveDetails leaveDetails : leaveDetailsList) {
            if (leaveDetails.getLeaveId() == leaveId) {
                selectedLeave = leaveDetails;
                break;
            }
        }

        if (selectedLeave == null) {
            return "Error: Leave ID " + leaveId + " not found.";
        }

        // Ask if the leave should be approved or declined
        System.out.print("Approve leave for " + selectedLeave.getEmpName() + " (Yes/No): ");
        String approval = scanner.next();

        if (approval.equalsIgnoreCase("Yes")) {
            // Approve the leave and update the status
            String updateLeaveStatusCmd = "UPDATE leaves SET leave_status = 'approved' WHERE leaveid = ?";
            PreparedStatement psmt = connection.prepareStatement(updateLeaveStatusCmd);
            psmt.setInt(1, leaveId);
            psmt.executeUpdate();

            // Deduct the leave balance from the employee's balance
            String getLeaveBalanceCmd = "SELECT leave_balance FROM employ WHERE empno = ?";
            psmt = connection.prepareStatement(getLeaveBalanceCmd);
            psmt.setInt(1, selectedLeave.getEmpno());
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("leave_balance");
                int updatedBalance = currentBalance - selectedLeave.getNoOfDays();

                // Update the employee's leave balance
                String updateLeaveBalanceCmd = "UPDATE employ SET leave_balance = ? WHERE empno = ?";
                psmt = connection.prepareStatement(updateLeaveBalanceCmd);
                psmt.setInt(1, updatedBalance);
                psmt.setInt(2, selectedLeave.getEmpno());
                psmt.executeUpdate();
            }

            // Record the transaction in the LeaveHistory table
            String logLeaveHistoryCmd = "INSERT INTO LeaveHistory (empId, mgr, leaveId, mgrComments) VALUES (?, ?, ?, ?)";
            psmt = connection.prepareStatement(logLeaveHistoryCmd);
            psmt.setInt(1, selectedLeave.getEmpno());
            psmt.setInt(2, mgr);
            psmt.setInt(3, leaveId);
            psmt.setString(4, "Leave approved");
            psmt.executeUpdate();

            return "Leave ID " + leaveId + " approved and leave balance updated.";
        } else if (approval.equalsIgnoreCase("No")) {
            // Leave declined, no changes to balance
            // Record the transaction in the LeaveHistory table
            String logDeclineHistoryCmd = "INSERT INTO LeaveHistory (empId, mgr, leaveId, mgrComments) VALUES (?, ?, ?, ?)";
            PreparedStatement psmt = connection.prepareStatement(logDeclineHistoryCmd);
            psmt.setInt(1, selectedLeave.getEmpno());
            psmt.setInt(2, mgr);
            psmt.setInt(3, leaveId);
            psmt.setString(4, "Leave declined");
            psmt.executeUpdate();

            return "Leave ID " + leaveId + " declined.";
        } else {
            return "Invalid input. Please enter 'Yes' or 'No'.";
        }
    }

}
