package util;

import java.sql.ResultSet;

import model.LeaveDetails;
import model.LeaveType;

public class Helper {
	
	
	/*Leave DAO
	 * String searchLeaveDetails(int leaveid) throws Exception;
      String updateLeaveDetails(LeaveDetails leaveDetails) throws Exception;
      String deleteLeaveDetails(int leaveid)throws Exception;
	 */
	
	
	
	
	//DAO IMP
	
	/*
	 * public String deleteLeaveDetails(int leaveId) throws Exception {
        connection = ConnectionHelper.getConnection();

        // First, retrieve the empId from the leave record
        String getEmpCmd = "SELECT empno FROM leaves WHERE leaveid = ?";
        psmt = connection.prepareStatement(getEmpCmd);
        psmt.setInt(1, leaveId);
        ResultSet rs = psmt.executeQuery();

        if (rs.next()) {
            int empId = rs.getInt("empno");

            // delete the leave record
            String cmd = "DELETE FROM leaves WHERE leaveid = ?";
            psmt = connection.prepareStatement(cmd);
            psmt.setInt(1, leaveId);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                // Log the transaction in LeaveHistory using the empId
                logLeaveHistory(empId, leaveId, "Leave deleted");

                return "Leave details deleted successfully for Leave ID: " + leaveId;
            }
        }

        return "Error: No leave details found for Leave ID: " + leaveId;
    }
	 */
	
	
	
	/*
	 * @Override
	    public String searchLeaveDetails(int leaveid) throws Exception {
	        connection = ConnectionHelper.getConnection();
	        String cmd = "SELECT * FROM leaves WHERE leaveid = ?";
	        psmt = connection.prepareStatement(cmd);
	        psmt.setInt(1, leaveid);
	        ResultSet rs = psmt.executeQuery();
	        
	        if (rs.next()) {
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

	            return "Leave Details: " + leaveDetails.toString();
	        }
	        return "No leave record found for Leave ID: " + leaveid;
	    }
	 */
	
	
	
	/*
	 * @Override
    public String updateLeaveDetails(LeaveDetails leaveDetails) throws Exception {
        connection = ConnectionHelper.getConnection();

        String checkCmd = "SELECT COUNT(*) AS count FROM leaves WHERE leaveid = ?";
        psmt = connection.prepareStatement(checkCmd);
        psmt.setInt(1, leaveDetails.getLeaveId());
        ResultSet rs = psmt.executeQuery();
        if (rs.next() && rs.getInt("count") == 0) {
            return "Error: Leave ID " + leaveDetails.getLeaveId() + " not found.";
        }

        String validationMessage = validateLeaveDates(leaveDetails.getLeaveStartDate(), leaveDetails.getLeaveEndDate());
        if (validationMessage != null) {
            return validationMessage;
        }

        int noOfDays = calculateNumberOfDays(leaveDetails.getLeaveStartDate(), leaveDetails.getLeaveEndDate());
        leaveDetails.setNoOfDays(noOfDays);

        String cmd = "UPDATE leaves SET empno = ?, empname = ?, leavestartdate = ?, leaveenddate = ?, noofdays = ?, "
                   + "leavereason = ?, leavetype = ? WHERE leaveid = ?";
        psmt = connection.prepareStatement(cmd);

        psmt.setInt(1, leaveDetails.getEmpno());
        psmt.setString(2, leaveDetails.getEmpName());
        psmt.setDate(3, new java.sql.Date(leaveDetails.getLeaveStartDate().getTime()));
        psmt.setDate(4, new java.sql.Date(leaveDetails.getLeaveEndDate().getTime()));
        psmt.setInt(5, noOfDays);
        psmt.setString(6, leaveDetails.getLeaveReason());
        psmt.setString(7, leaveDetails.getLeaveType().name());
        psmt.setInt(8, leaveDetails.getLeaveId());

        psmt.executeUpdate();

        // Log the transaction in LeaveHistory with the manager's empId
        logLeaveHistory(leaveDetails.getEmpno(), leaveDetails.getLeaveId(), "Leave updated");

        return "Leave details updated successfully for Leave ID: " + leaveDetails.getLeaveId();
    }
	 */

}
