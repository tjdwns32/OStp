package application;
/////////////////////////////////////////////////////////////////////
//�� �ڵ�� javafx�� ������� �ʰ� �ֿܼ����� ���α׷��� �����ϰ� ���� �� ����Ͻø� �˴ϴ�.//
//�� �ڵ带 �����ؼ� Main.java�� �ٿ��ֱ� �Ͻø� �˴ϴ�.                        //
//�����ð�, ����ð� �Է��� �����̽��ٸ� �������� ���ٿ� �Է��Ͻø� �˴ϴ�.             //
//�� �ڵ带 �����ϱ� ���� �ʿ��� �ٸ� �ڵ���� �ּ�ó�� �Ǿ��ֽ��ϴ�. �ּ��� �����ϼ���         //
//Scheduling.java���� �� �����층 ����� ���̴� pArrO�� pArrD�� �ٲټ���
/////////////////////////////////////////////////////////////////////
import java.util.*;
import java.util.Scanner;

public class SubMain {
	static int[] parseIntegerFromString(String s) {
		String[] ss = s.split(" ");
		int[] numbers = new int[ss.length];
		for(int i= 0;i<ss.length;i++) {
			numbers[i] = Integer.parseInt(ss[i]);
		}
		return numbers;
	}
	public static void main(String args[]) {
		int numP, timequantum, processNum;
		String s;
		
		int[] aNumbers;
		int[] bNumbers;
		
		LinkedList<ProcessP> lp = new LinkedList<ProcessP>();
		ProcessP[] ap;
		Scanner sc = new Scanner(System.in);
		
		//����� �Է°��� ���� lp�ʱ�ȭ ����
		System.out.print("Enter Number of Processses: ");
		numP = sc.nextInt();
		ap = new ProcessP[numP];
		
		sc.nextLine();
		System.out.print("Enter Arrival times: ");
		s = sc.nextLine();
		aNumbers = parseIntegerFromString(s);
		
		System.out.print("Enter Burst times: ");
		s = sc.nextLine();
		bNumbers = parseIntegerFromString(s);

		for(int i=0;i<numP;i++) {
			ap[i] = new ProcessP();
			ap[i].setAT(aNumbers[i]);
			ap[i].setBT(bNumbers[i]);
			ap[i].setRT(bNumbers[i]);
			lp.add(ap[i]);
		}
	    //�ʱ�ȭ�� lp�� sched�� �Ű������� �ǳ���
		Scheduling sched = new Scheduling(lp);
		//�����ٸ� ����
		while(true) {
			System.out.println("������ �����층 ����� ������");
			System.out.println("1: FCFS\t2: RR\t3: SPN\t4: SRTN\t5: HRRN\t-1: ����");
			System.out.print("Input: ");
			processNum = sc.nextInt();
			if(processNum == -1) break;
			switch(processNum) {
				case 1:
					sched.FCFS();
					System.out.println(sched);
					break;
				case 2:
					System.out.print("Enter timequantum: ");
					timequantum = sc.nextInt();
					sched.RR(timequantum);
					System.out.println(sched);
					break;
				case 3:
					sched.SPN();
					System.out.println(sched);
					break;
				case 4:
					sched.SRTN();
					System.out.println(sched);
					break;
				case 5:
					sched.HRRN();
					System.out.println(sched);
					break;
				default:
					System.out.println("�Է��� �ùٸ��� �ʽ��ϴ�.");
					System.out.println();
			}
		}
		sc.close();
	}
}
