package dao;


import java.util.List;

import model.LeaveDetails;

public interface LeaveDao {
	String createLeaveDetails(LeaveDetails leaveDetails) throws Exception;
	List<LeaveDetails> getAllLeaveDetails() throws Exception;
      
	
}
