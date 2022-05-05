package net.scit.entity;

import java.io.Serializable;

public class Patient implements Serializable {
	private static final long serialVersionUID = -2587660146897736450L;

	private String patientId; // 환자번호
	private String name; // 이름
	private int age; // 나이
	private Department part; // 진료코드(진료과목)
	private int period; // 입원기간
	private int expenses; // 진찰비
	private int hospitalBill; // 입원비

	private final int HOSBILL = 25000;
	private final int HOSBILL_UNDER_THREE_DAYS = 30000;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(String patientId, String name, int age, Department part, int period) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.part = part;
		this.period = period;

		caclExpenses();
		calcHospitalBill();
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Department getPart() {
		return part;
	}

	public void setPart(Department part) {
		this.part = part;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getExpenses() {
		return expenses;
	}

	private void caclExpenses() {
		if (age < 10) {
			expenses = 7000;
		} else if (age < 20) {
			expenses = 5000;
		} else if (age < 30) {
			expenses = 8000;
		} else if (age < 40) {
			expenses = 7000;
		} else if (age < 50) {
			expenses = 4500;
		} else {
			expenses = 2300;
		}
	}

	public int getHospitalBill() {
		return hospitalBill;
	}

	private void calcHospitalBill() {
		if (period <= 3) {
			hospitalBill = HOSBILL_UNDER_THREE_DAYS * period;
		} else if (period < 10) {
			hospitalBill = HOSBILL * period;
		} else if (period < 15) {
			hospitalBill = HOSBILL * period * 85 / 100;
		} else if (period < 20) {
			hospitalBill = HOSBILL * period * 80 / 100;
		} else if (period < 30) {
			hospitalBill = HOSBILL * period * 77 / 100;
		} else if (period < 100) {
			hospitalBill = HOSBILL * period * 72 / 100;
		} else {
			hospitalBill = HOSBILL * period * 68 / 100;
		}
	}

	public int totalBill() {
		return expenses + hospitalBill;
	}

	@Override
	public String toString() {
		return String.format("    %s\t  %4s\t%4s\t\t%,4d원\t%2d일\t%,7d원\t%,7d원", patientId, name,
				part.getDepartmentName(), expenses, period, hospitalBill, totalBill());

		// return "Patient [patientId=" + patientId + ", name=" + name + ", age=" + age
		// + ", part=" + part + ", period="
		// + period + ", expenses=" + expenses + ", hospitalBill=" + hospitalBill + "]";
	}

}
