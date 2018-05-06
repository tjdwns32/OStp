package application;

import java.util.*;

class Scheduling{
	LinkedList<ProcessP> pArrO;
	//LinkedList<ProcessP> pArrD = new LinkedList<ProcessP>();
	LinkedList<ProcessP> pArrT = new LinkedList<ProcessP>();
	LinkedList<Tuple> qTup = new LinkedList<Tuple>();
	LinkedList<Integer> P = new LinkedList<Integer>();
	LinkedList<Integer> T = new LinkedList<Integer>();
	Scheduling(LinkedList<ProcessP> pArr){
		this.pArrO = pArr;
	}

	public void FCFS() {
		/*
		pArrO.clear();
	    cloneP();
	    */    
	    int t=0;
	    int previous=0;
	    int count =0;
	    ProcessP temp;
	    while(true) {
	       if(pArrO.isEmpty()) {
	    	   T.add(count);
	    	   break;     
	       }
	       if(pArrO.get(0).getAT()<=t) {
	          temp = pArrO.pollFirst();
	          int pid = temp.getPid();
	          int burstTime = temp.getBT();
	          for(int i = 0; i < burstTime;i++){
	             t++;
	             temp.setRT(temp.getRT()-1);
	             System.out.print("p"+pid+" ");
	             if(previous == pid) {
	            	 count++;
	             }else {
	            	 System.out.println("added");
	            	 if(previous != 0) T.add(count);
	           		 P.add(pid);
	           		 previous = pid;
	           		 count=1;
	             }
	             if(temp.getRT() <= 0){
	                finishP(temp,t);
	                break;
	             }
	          }
	       }else { t++;}
	    }
	}

	public void RR(int timequantum) {
		/*
		pArrO.clear();
		cloneP();
		*/
		
		int t=0;
		int previous=0;
		int count =0;
		boolean isfinished;
		ProcessP temp;
		
		while(!pArrO.isEmpty()) {
			
			if(pArrO.get(0).getAT()<=t) {
				isfinished = false;
				temp = pArrO.pollFirst();
				int pid = temp.getPid();
				for(int i =0;i<timequantum;i++) {
					t++;
					temp.setRT(temp.getRT()-1);
		            System.out.print("p"+pid+" ");
					if(previous == pid) {
		            	 count++;
		             }else {
		            	 System.out.println("added");
		            	 if(previous != 0) T.add(count);
		           		 P.add(pid);
		           		 previous = pid;
		           		 count=1;
		             }
					if(temp.getRT()<=0) {
						finishP(temp,t);
						isfinished=true;
						break;
					}
				}
				if(!isfinished) { 
					boolean isNAexist = false;
					for(int i = 0;i<pArrO.size();i++) {
						if(pArrO.get(i).getAT()>t) {//프로세스 AT가 t보다 큰 경우면 아직 도착안한거니까 그 앞에 add
							pArrO.add(i,temp); 
							isNAexist = true;
							break;
						}
					}
					if(!isNAexist) pArrO.addLast(temp);
				}
			}else{t++;}
		}T.add(count);
	}

	public void SPN() {
		/*
		pArrO.clear();
	    cloneP();
	    */  
	      
	    int t=0;
	    int previous = 0;
	    int count = 0;
	    ProcessP temp;
	     
	    while(true) {
	       if(pArrO.isEmpty())break;       
	       if(pArrO.get(0).getAT()<=t) {
	          int shortest = 0;
	          int burstTime = pArrO.get(0).getBT();
	          
	          for(int i = 0; i < pArrO.size();i++) {
	             if(pArrO.get(i).getAT() <= t && burstTime > pArrO.get(i).getBT()) {
	                burstTime = pArrO.get(i).getBT();
	                shortest = i;
	             }
	          }         
	          temp = pArrO.get(shortest);
	          int pid = temp.getPid();
	          pArrO.remove(shortest);
	          for(int i = 0; i < burstTime;i++){
	             t++;
	             temp.setRT(temp.getRT()-1);
	             System.out.print("p"+pid+" ");
	             if(previous == pid) {
	            	 count++;
	             }else {
	            	 System.out.println("added");
	            	 if(previous != 0) T.add(count);
	           		 P.add(pid);
	           		 previous = pid;
	           		 count=1;
	             }
	             if(temp.getRT() <= 0) {
	                finishP(temp,t);
	                break;
	             }
	          }    
	       }else{ t++;}
	    }T.add(count);
		
	}

	public void SRTN() {
		/*
		pArrO.clear();
		cloneP();
		*/
		
		int min=0,t=0;
		int RT;
		int previous = 0;
		int count = 0;
		ProcessP temp;
		
		while(!pArrO.isEmpty()) {
			RT = Integer.MAX_VALUE;
			for(int i=0;i<pArrO.size();i++) {
				if(pArrO.get(i).getAT()<=t && pArrO.get(i).getRT() < RT ) {
					RT = pArrO.get(i).getRT();
					min = i;
				}
			}//남은 실행시간이 가장적은 프로세스 선별
			temp = pArrO.get(min);
			int pid = temp.getPid();
			pArrO.remove(min);
			temp.setRT(temp.getRT()-1);
			t++;
            System.out.print("p"+pid+" ");
			 if(previous == pid) {
            	 count++;
             }else {
            	 System.out.println("added");
            	 if(previous != 0) T.add(count);
           		 P.add(pid);
           		 previous = pid;
           		 count=1;
             }
			if(temp.getRT()<=0) {
				finishP(temp,t);
			}else{
				pArrO.add(temp);
			}	
		}T.add(count);
	}

	public void HRRN() {
		/*
		pArrO.clear();
		cloneP();
		*/
		
		int t=0,p=0;
		int previous = 0;
		int count = 0;
		float ratio;
		float maxR;
		ProcessP temp;
		
		while(!pArrO.isEmpty()) {	
			if(pArrO.get(p).getAT()<=t) {
				temp = pArrO.get(p);
				int pid = temp.getPid();
				pArrO.remove(p);
				int RT = temp.getRT();
				for(int i =0;i<RT;i++) {
					temp.setRT(temp.getRT()-1);
					for(int j =0;j<pArrO.size();j++) {
						if(pArrO.get(j).getAT()<=t)
							pArrO.get(j).setWT(pArrO.get(j).getWT()+1);
					}
					t++;
		            System.out.print("p"+pid+" ");
					if(previous == pid) {
		            	 count++;
		             }else {
		            	 System.out.println("added");
		            	 if(previous != 0) T.add(count);
		           		 P.add(pid);
		           		 previous = pid;
		           		 count=1;
		             }
					if(temp.getRT()<=0) {
						finishP(temp,t);
						break;
					}
				}
				maxR=0;
				for(int i =0;i<pArrO.size();i++) {
					temp = pArrO.get(i);
					if(temp.getAT()<=t) {
						ratio = (float)(temp.getWT()+temp.getBT())/temp.getBT();
						if(ratio > maxR) {
							maxR = ratio;
							p = i;
							System.out.println("next: "+p+", ratio: "+maxR);
						}
					}
				}
				
			}else{t++;}
		}T.add(count);
		
	}
	
	/*//객체배열 복사
	public void cloneP() {
		ProcessP p;
		for(int i =0;i<pArrO.size();i++) {
			p = (ProcessP)pArrO.get(i).clone();//객체의 속성값 복사
			pArrO.add(p);
		}
	}*/
	//배열의 각 객체에 대한 정보출력
	public String toString() {
		Collections.sort(pArrT,new SortbyPid());//Pid에 따라서 정렬
		String s = "";
		for(int i = 0;i<pArrT.size();i++) {
			s += pArrT.get(i).toString()+"\n";
		}
		for(int i=0;i<P.size();i++) {
			System.out.println("P:"+P.get(i) +" T:"+T.get(i));
		}
		pArrT.clear();//출력 후 다음 입력을 위해 비움
		P.clear();
		T.clear();
		return s;
		
	}
	//작업이 끝난 프로세스 후처리
	public void finishP(ProcessP p,int t) {
		p.setTT(t-p.getAT());
		p.setWT(p.getTT()-p.getBT());
		p.setNT((float)(p.getTT())/p.getBT());
		pArrT.add(p);
		System.out.println("\n(p"+p.getPid()")finished, time: "+t);
	}
}

//객체의 Pid를 기준으로 정렬하기위해 구현
class SortbyPid implements Comparator<ProcessP>
{
    public int compare(ProcessP a, ProcessP b)
    {
        return a.getPid() - b.getPid();
    }
}
 
class Tuple{
	int np;
	int nl;
	Tuple(int np,int nl){
		this.np = np;
		this.nl =nl;
	}
	
	public String toString() {
		return "Process"+np+": "+nl;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}