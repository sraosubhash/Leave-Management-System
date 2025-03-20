package model;

import lombok.Data;

@Data
public class LeaveHistory {

    private int historyId;      
    private int empId;         
    private int mgr;            
    private int leaveId;       
    private String mgrComments;
}