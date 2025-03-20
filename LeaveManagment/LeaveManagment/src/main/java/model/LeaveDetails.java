package model;

import java.sql.Date;
import lombok.Data;

@Data
public class LeaveDetails {
    private int leaveId;
    private int empno;
    private String empName;
    private Date leaveStartDate;
    private Date leaveEndDate;
    private int noOfDays;
    private String leaveReason;
    private LeaveType leaveType;
    private String leaveStatus; 
    }
