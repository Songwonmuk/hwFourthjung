package net.scit.entity;

public enum Department {
	MI("외과"), NI("내과"), SI("피부과"), TI("소아과"), VI("산부인과"), WI("비뇨기과");
	
	private String departmentName;
	
	Department(String string) {
		this.departmentName = string;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
}
