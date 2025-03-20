package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.LeaveDetails;
import model.LeaveType;
import model.LeaveHistory;
import util.ConnectionHelper;

public class LeaveDaoImpl implements LeaveDao {
    Connection connection;
    PreparedStatement psmt;

  

    public int generateLeaveId() throws Exception {
        connection = ConnectionHelper.getConnection();
        String cmd = "SELECT CASE WHEN MAX(leaveid) IS NULL THEN 1 ELSE MAX(leaveid) + 1 END AS leaveId FROM leaves";
        psmt = connection.prepareStatement(cmd);
        ResultSet rs = psmt.executeQuery();
        rs.next();
        int leaveId = rs.getInt("leaveId");
        return leaveId;
    }

    public int calculateNumberOfDays(java.sql.Date startDate, java.sql.Date endDate) {
        long startDateMillis = startDate.getTime();
        long endDateMillis = endDate.getTime();
        long diffMillis = endDateMillis - startDateMillis;
        return (int) (diffMillis / (24 * 60 * 60 * 1000)) + 1;
    }

    public String validateLeaveDates(java.sql.Date startDate, java.sql.Date endDate) {
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        if (startDate.before(currentDate) || endDate.before(currentDate)) {
            return "Error: Leave dates cannot be in the past (yesterday or earlier).";
        }

        if (startDate.after(endDate)) {
            return "Error: Leave start date cannot be later than leave end date.";
        }

        return null;
    }

    public int getLeaveBalance(int empno) throws Exception {
        connection = ConnectionHelper.getConnection();
        String cmd = "SELECT leave_balance FROM employ WHERE empno = ?";
        psmt = connection.prepareStatement(cmd);
        psmt.setInt(1, empno);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("leave_balance");
        }
        throw new Exception("Error: leave balance not sufficient.");
    }

    public void logLeaveHistory(int empId, int leaveId, String mgrComments) throws Exception {
        connection = ConnectionHelper.getConnection();
        
        // Get the manager ID from the employee table
        String getMgrCmd = "SELECT mgr FROM employ WHERE empno = ?";
        psmt = connection.prepareStatement(getMgrCmd);
        psmt.setInt(1, empId);
        ResultSet rs = psmt.executeQuery();
        
        if (rs.next()) {
            int mgrId = rs.getInt("mgr");
            
            // Insert the leave history record
            String cmd = "INSERT INTO LeaveHistory (empId, mgr, leaveId, mgrComments) VALUES (?, ?, ?, ?)";
            psmt = connection.prepareStatement(cmd);
            psmt.setInt(1, empId);
            psmt.setInt(2, mgrId);  // Use the manager's ID here
            psmt.setInt(3, leaveId);
            psmt.setString(4, mgrComments);
            psmt.executeUpdate();
        } else {
            throw new Exception("Error: Manager not found for employee ID " + empId);
        }
    }

    @Override
    public String createLeaveDetails(LeaveDetails leaveDetails) throws Exception {
        connection = ConnectionHelper.getConnection();

        String validationMessage = validateLeaveDates(leaveDetails.getLeaveStartDate(), leaveDetails.getLeaveEndDate());
        if (validationMessage != null) {
            return validationMessage;
        }

        int noOfDays = calculateNumberOfDays(leaveDetails.getLeaveStartDate(), leaveDetails.getLeaveEndDate());
        leaveDetails.setNoOfDays(noOfDays);

        int leaveBalance = getLeaveBalance(leaveDetails.getEmpno());
        if (noOfDays > leaveBalance) {
            return "Error: Requested leave days (" + noOfDays + ") exceed available leave balance (" + leaveBalance + ").";
        }

        int leaveId = generateLeaveId();
        leaveDetails.setLeaveId(leaveId);

        String cmd = "INSERT INTO leaves (leaveid, empno, empname, leavestartdate, leaveenddate, noofdays, leavereason, leavetype, leave_status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        psmt = connection.prepareStatement(cmd);

        psmt.setInt(1, leaveId);
        psmt.setInt(2, leaveDetails.getEmpno());
        psmt.setString(3, leaveDetails.getEmpName());
        psmt.setDate(4, new java.sql.Date(leaveDetails.getLeaveStartDate().getTime()));
        psmt.setDate(5, new java.sql.Date(leaveDetails.getLeaveEndDate().getTime()));
        psmt.setInt(6, leaveDetails.getNoOfDays());
        psmt.setString(7, leaveDetails.getLeaveReason());
        psmt.setString(8, leaveDetails.getLeaveType().name());
        psmt.setString(9, "inactive");

        psmt.executeUpdate();

        // Log the transaction in LeaveHistory with the manager's empId
        logLeaveHistory(leaveDetails.getEmpno(), leaveId, "Leave created");

        return "Leave created with ID " + leaveId;
    }

     


    @Override
    public List<LeaveDetails> getAllLeaveDetails() throws Exception {
        List<LeaveDetails> leaveList = new ArrayList<>();
        connection = ConnectionHelper.getConnection();
        String cmd = "SELECT * FROM leaves";
        try (PreparedStatement psmt = connection.prepareStatement(cmd)) {
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    LeaveDetails leaveDetails = new LeaveDetails();
                    leaveDetails.setLeaveId(rs.getInt("leaveid"));
                    leaveDetails.setEmpno(rs.getInt("empno"));
                    leaveDetails.setEmpName(rs.getString("empname"));
                    leaveDetails.setLeaveStartDate(rs.getDate("leavestartdate"));
                    leaveDetails.setLeaveEndDate(rs.getDate("leaveenddate"));
                    leaveDetails.setNoOfDays(rs.getInt("noofdays"));
                    leaveDetails.setLeaveReason(rs.getString("leavereason"));
                    leaveDetails.setLeaveType(LeaveType.valueOf(rs.getString("leavetype").toUpperCase()));
                    leaveDetails.setLeaveStatus(rs.getString("leave_status"));
                    leaveList.add(leaveDetails);
                }
            }
        }
        return leaveList;
    }

	 
}
