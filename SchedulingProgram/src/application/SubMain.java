package application;
/////////////////////////////////////////////////////////////////////
//이 코드는 javafx를 사용하지 않고 콘솔에서만 프로그램을 실행하고 싶을 때 사용하시면 됩니다.//
//이 코드를 복사해서 Main.java에 붙여넣기 하시면 됩니다.                        //
//도착시간, 실행시간 입력은 스페이스바를 기준으로 한줄에 입력하시면 됩니다.             //
//이 코드를 실행하기 위해 필요한 다른 코드들은 주석처리 되어있습니다. 주석을 해제하세요         //
//Scheduling.java에서 각 스케쥴링 기법에 쓰이느 pArrO를 pArrD로 바꾸세요
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
		
		//사용자 입력값에 따라 lp초기화 시작
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
	    //초기화한 lp를 sched의 매개변수로 건네줌
		Scheduling sched = new Scheduling(lp);
		//스케줄링 시작
		while(true) {
			System.out.println("진행할 스케쥴링 기법을 고르세요");
			System.out.println("1: FCFS\t2: RR\t3: SPN\t4: SRTN\t5: HRRN\t-1: 종료");
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
					System.out.println("입력이 올바르지 않습니다.");
					System.out.println();
			}
		}
		sc.close();
	}
}
