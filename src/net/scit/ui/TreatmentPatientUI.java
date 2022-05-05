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
				System.out.println("** ���α׷��� �����մϴ�.");
				keyin.close();
				service.setFile();
				return;
			default : System.out.println("err) �޴��� �ٽ� �����ϼ���");
			}
		}
	}

	// Main Menu
	private void menu() {
		System.out.println("=== [ �Կ� ȯ�� ���� ���α׷�] ===");
		System.out.println("        1. ȯ�� ���");
		System.out.println("        2. ��ü ���");
		System.out.println("        3. ȯ�ڹ�ȣ�� ��ȸ");
		System.out.println("        4. ȯ���̸����� ��ȸ");
		System.out.println("        0. ��  ��");
		System.out.println("---------------------------");
		System.out.print  ("       ����> ");
	}	

	// ȯ�� ��ȣ�� �˻�
	private void searchPatientById() {
		String patientId = null;
		
		System.out.print("> ȯ�� ��ȣ : "); patientId = keyin.nextLine();
		
		Patient p = service.findByPatientId(patientId);
		
		if(p != null) {
			System.out.println();
			System.out.println("** ��ȸ ���");
			System.out.println("> ȯ�� ��ȣ : " + p.getPatientId());
			System.out.println("> ��    �� : " + p.getName());
			System.out.println("> ��    �� : " + p.getAge());
		}
		else {
			System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");
		}
		System.out.println();
	}
	
	// ȯ�� �̸����� �˻�
	private void searchPatientByName() {
		String name = null;
		
		System.out.print("> ��   �� : "); name = keyin.nextLine();
		
		
		if(service.findByPatientName(name) != null) {
			System.out.println("\n** ��ȸ ���");
			System.out.println("--------------------------------------------------------------------");
			System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
			System.out.println("--------------------------------------------------------------------");
			service.findByPatientName(name).forEach(p -> System.out.println(p));
		}
		else {
			System.out.println("** ȯ�ڰ� �������� �ʽ��ϴ�.");
		}
		System.out.println();
	}
	
	// �Կ� ȯ�� ��ü ���� ���
	private void printAll() {
		List<Patient> list = service.selectAll();
		
		if(list == null) {
			System.out.println("��ϵ� ������ �����ϴ�");
			System.out.println();
			return;
		}
			
		System.out.println("                      << ���� ���� ���α׷� >> ");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("   ��ȣ     �̸�    �����μ�     ������     �Կ���      �Կ���       �������");
		System.out.println("--------------------------------------------------------------------");
	
		int expenses = 0; 			// �� ������ �հ�
		int hospitalBill = 0 ; 		// �� �Կ��� �հ�
		int total = 0;				// �� ����� �հ�
		
		list.forEach(p -> System.out.println(p));

		for(Patient p : list) {
			expenses += p.getExpenses();
			hospitalBill += p.getHospitalBill();
			total += p.totalBill();
		}
		
		System.out.println("--------------------------------------------------------------------");
		System.out.printf("* �� ������ : %,8d��\n", expenses);
		System.out.printf("* �� �Կ��� : %,8d��\n", hospitalBill);
		System.out.printf("* �� ����� : %,8d��\n", total);
		
		System.out.println();
	}

	// �Կ� ȯ�� ���� ���
	private void regist() {
		// ��ȣ, �����ڵ�, �Կ��ϼ�, ����
		String patientId = null; 		// ȯ�ڹ�ȣ 
		String name=null; 				// �̸�
		int age=0; 						// ����
		Department part=null; 			// �����ڵ�(�������)
		int period = 0; 				// �Կ� �ϼ�
 
		while(true) {
			System.out.println("\n### ȯ�� ���� �Է� ### (0���� ������)");

			System.out.print("> ȯ�ڹ�ȣ : "); patientId = keyin.nextLine();
			if(patientId.equals("0")) {
				System.out.println("��ҵǾ����ϴ�.");
				System.out.println();
				return;
			}
						
			Patient p = service.findByPatientId(patientId);

			try {
				// ȯ�� ��ȣ�� �ش��ϴ� ȯ�ڰ� ������ ������� �Է�, �Կ��ϼ� �Է� 
				if(p != null) {
					name = p.getName();
					age = p.getAge();
					System.out.println("> ��   �� : " + name);
					System.out.println("> ��   �� : " + age);
				}
				// ȯ�� ��ȣ�� �ش��ϴ� ȯ�ڰ� ������ �̸�, ���� �Է¹ް� ��� �� ������� �Է�, �Կ��ϼ� �Է� 
				else {
					System.out.print("> ��   �� : "); name = keyin.nextLine();
					System.out.print("> ��   �� : "); age = keyin.nextInt(); keyin.nextLine();
				}
				System.out.println("> ������� : "); part = inputPart();
				System.out.print("> �Կ��ϼ� : "); period = keyin.nextInt(); keyin.nextLine();
			}
			catch(InputMismatchException e) {
				keyin.nextLine();
				System.out.println("�߸��� �Է� �Դϴ�.");
				continue;
			}

			if(service.regist(new Patient(patientId, name, age, part, period)))		
				System.out.println("** ȯ�ڰ� ��ϵǾ����ϴ�.");
			else
				System.out.println("��� ����");
		}
		
	}
	
	// �Կ��� ������� ����
	private Department inputPart() {
		String choice;
		Department part;

		// ��������� �߸� �Է��ϸ� ����� �Է��� ������ �ݺ������� �Է¹޴´�.
		while(true) {
			System.out.println("** ����޴� ������� �����ϼ���");
			System.out.print("1) �ܰ�    2) ����    3) �Ǻΰ�    4) �Ҿư�    5) ����ΰ�    6) �񴢱�� : ");
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
				System.out.println("�߸��� ��ȣ �Դϴ�. �ٽ� �Է��� �ּ���.");
				break;
			}
		}
	}
}
