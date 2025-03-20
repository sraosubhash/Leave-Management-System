package manager.dao;

import java.util.List;

import model.LeaveDetails;

public interface ManagerDao {

	List<LeaveDetails> showEmpLeaveDetail(int mgrId) throws Exception;	
	String approveLeave(int mgr) throws Exception;
	
}
