package model;

import lombok.Data;

@Data
public class Employ {

	private int empno;
	private String name;
	private Gender gender;
	private String dept;
	private String desig;
	private double basic;
	private int leave_balance;
}