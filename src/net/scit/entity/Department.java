package net.scit.entity;

public enum Department {
	MI("�ܰ�"), NI("����"), SI("�Ǻΰ�"), TI("�Ҿư�"), VI("����ΰ�"), WI("�񴢱��");
	
	private String departmentName;
	
	Department(String string) {
		this.departmentName = string;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
}
