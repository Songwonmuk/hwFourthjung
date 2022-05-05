package net.scit.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import net.scit.entity.Department;
import net.scit.entity.Patient;
import net.scit.service.TreatmentPatientService;
import net.scit.service.TreatmentPatientServiceImpl;

public class TreatmentPatientUI {
	Scanner keyin = new Scanner(System.in);
	TreatmentPatientService service = new TreatmentPatientServiceImpl();

	public TreatmentPatientUI() {
		String choice;

		while(true) {
			menu();
			choice = keyin.nextLine();

			switch(choice) {
			case "1" : regist();     		break;
			case "2" : printAll();    		break;
			case "3" : searchPatientById(); break;
			case "4" : searchPatientByName(); break;
			case "0" : 
				System.out.println("** 프로그램을 종료합니다.");
				keyin.close();
				service.setFile();
				return;
			default : System.out.println("err) 메뉴를 다시 선택하세요");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ 입원 환자 관리 프로그램] ===");
		System.out.println("        1. 환자 등록");
		System.out.println("        2. 전체 출력");
		System.out.println("        3. 환자번호로 조회");
		System.out.println("        4. 환자이름으로 조회");
		System.out.println("        0. 종  료");
		System.out.println("---------------------------");
		System.out.print  ("       선택> ");
	}	

	// 환자 번호로 검색
	private void searchPatientById() {
		String patientId = null;
		
		System.out.print("> 환자 번호 : "); patientId = keyin.nextLine();
		
		Patient p = service.findByPatientId(patientId);
		
		if(p != null) {
			System.out.println();
			System.out.println("** 조회 결과");
			System.out.println("> 환자 번호 : " + p.getPatientId());
			System.out.println("> 이    름 : " + p.getName());
			System.out.println("> 나    이 : " + p.getAge());
		}
		else {
			System.out.println("** 환자가 존재하지 않습니다.");
		}
		System.out.println();
	}
	
	// 환자 이름으로 검색
	private void searchPatientByName() {
		String name = null;
		
		System.out.print("> 이   름 : "); name = keyin.nextLine();
		
		
		if(service.findByPatientName(name) != null) {
			System.out.println("\n** 조회 결과");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
			System.out.println("--------------------------------------------------------------------");
			service.findByPatientName(name).forEach(p -> System.out.println(p));
		}
		else {
			System.out.println("** 환자가 존재하지 않습니다.");
		}
		System.out.println();
	}
	
	// 입원 환자 전체 정보 출력
	private void printAll() {
		List<Patient> list = service.selectAll();
		
		if(list == null) {
			System.out.println("등록된 정보가 없습니다");
			System.out.println();
			return;
		}
			
		System.out.println("                      << 병원 관리 프로그램 >> ");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   번호     이름    진찰부서     진찰비     입원일      입원비       총진료비");
		System.out.println("--------------------------------------------------------------------");
	
		int expenses = 0; 			// 총 진찰비 합계
		int hospitalBill = 0 ; 		// 총 입원비 합계
		int total = 0;				// 총 진료비 합계
		
		list.forEach(p -> System.out.println(p));

		for(Patient p : list) {
			expenses += p.getExpenses();
			hospitalBill += p.getHospitalBill();
			total += p.totalBill();
		}
		
		System.out.println("--------------------------------------------------------------------");
		System.out.printf("* 총 진찰비 : %,8d원\n", expenses);
		System.out.printf("* 총 입원비 : %,8d원\n", hospitalBill);
		System.out.printf("* 총 진료비 : %,8d원\n", total);
		
		System.out.println();
	}

	// 입원 환자 정보 등록
	private void regist() {
		// 번호, 진료코드, 입원일수, 나이
		String patientId = null; 		// 환자번호 
		String name=null; 				// 이름
		int age=0; 						// 나이
		Department part=null; 			// 진료코드(진료과목)
		int period = 0; 				// 입원 일수
 
		while(true) {
			System.out.println("\n### 환자 정보 입력 ### (0으로 나가기)");

			System.out.print("> 환자번호 : "); patientId = keyin.nextLine();
			if(patientId.equals("0")) {
				System.out.println("취소되었습니다.");
				System.out.println();
				return;
			}
						
			Patient p = service.findByPatientId(patientId);

			try {
				// 환자 번호에 해당하는 환자가 있으면 진료과목 입력, 입원일수 입력 
				if(p != null) {
					name = p.getName();
					age = p.getAge();
					System.out.println("> 이   름 : " + name);
					System.out.println("> 나   이 : " + age);
				}
				// 환자 번호에 해당하는 환자가 없으면 이름, 나이 입력받고 등록 후 진료과목 입력, 입원일수 입력 
				else {
					System.out.print("> 이   름 : "); name = keyin.nextLine();
					System.out.print("> 나   이 : "); age = keyin.nextInt(); keyin.nextLine();
				}
				System.out.println("> 진료과목 : "); part = inputPart();
				System.out.print("> 입원일수 : "); period = keyin.nextInt(); keyin.nextLine();
			}
			catch(InputMismatchException e) {
				keyin.nextLine();
				System.out.println("잘못된 입력 입니다.");
				continue;
			}

			if(service.regist(new Patient(patientId, name, age, part, period)))		
				System.out.println("** 환자가 등록되었습니다.");
			else
				System.out.println("등록 실패");
		}
		
	}
	
	// 입원할 진료과목 선택
	private Department inputPart() {
		String choice;
		Department part;

		// 진료과목을 잘못 입력하면 제대로 입력할 때까지 반복적으로 입력받는다.
		while(true) {
			System.out.println("** 진료받는 진료과를 선택하세요");
			System.out.print("1) 외과    2) 내과    3) 피부과    4) 소아과    5) 산부인과    6) 비뇨기과 : ");
			choice = keyin.nextLine();
			switch(choice) {
			case "1" :
				part = Department.MI;
				return part;
			case "2" :
				part = Department.NI;
				return part;
			case "3" :
				part = Department.SI;
				return part;
			case "4" :
				part = Department.TI;
				return part;
			case "5" :
				part = Department.VI;
				return part;
			case "6" :
				part = Department.WI;
				return part;
			default :
				System.out.println("잘못된 번호 입니다. 다시 입력해 주세요.");
				break;
			}
		}
	}
}
