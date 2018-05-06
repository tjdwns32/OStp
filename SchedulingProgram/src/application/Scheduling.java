package application;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class Scheduling  {
	LinkedList<ProcessP> pArrO;
	LinkedList<ProcessP> pArrD = new LinkedList<ProcessP>();
	LinkedList<ProcessP> pArrT = new LinkedList<ProcessP>();
	
	LinkedList<Integer> P = new LinkedList<Integer>();
	LinkedList<Integer> T = new LinkedList<Integer>();
	
	

	Scheduling(LinkedList<ProcessP> pArr){
	   Collections.sort(pArr,new SortbyPid());
	   this.pArrO = pArr;
	}
	   public void FCFS() {
		   pArrD.clear();
		   cloneP();
		   
		   int t=0;
	       int previous=0;
	       int count =0;
	       ProcessP temp;
	       while(true) {
	          if(pArrD.isEmpty()) {
	             T.add(count);
	             break;     
	          }
	          if(pArrD.get(0).getAT()<=t) {
	             temp = pArrD.pollFirst();
	             int pid = temp.getPid();
	             int burstTime = temp.getBT();
	             for(int i = 0; i < burstTime;i++){
	                t++;
	                temp.setRT(temp.getRT()-1);
	                //System.out.println("p"+pid);
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
		   pArrD.clear();
		   cloneP();
		   
		   int t=0;
		      int previous=0;
		      int count =0;
		      boolean isfinished;
		      ProcessP temp;
		      
		      while(!pArrD.isEmpty()) {
		         
		         if(pArrD.get(0).getAT()<=t) {
		            isfinished = false;
		            temp = pArrD.pollFirst();
		            int pid = temp.getPid();
		            for(int i =0;i<timequantum;i++) {
		               t++;
		               temp.setRT(temp.getRT()-1);
		               //System.out.println("p"+pid);
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
		               for(int i = 0;i<pArrD.size();i++) {
		                  if(pArrD.get(i).getAT()>t) {//프로세스 AT가 t보다 큰 경우면 아직 도착안한거니까 그 앞에 add
		                     pArrD.add(i,temp); 
		                     isNAexist = true;
		                     break;
		                  }
		               }
		               if(!isNAexist) pArrD.addLast(temp);
		            }
		         }else{t++;}
		      }T.add(count);
	   }

	   public void SPN() {
		   pArrD.clear();
		   cloneP();
		   
		   int t=0;
	       int previous = 0;
	       int count = 0;
	       ProcessP temp;
	        
	       while(true) {
	          if(pArrD.isEmpty())break;       
	          if(pArrO.get(0).getAT()<=t) {
	             int shortest = 0;
	             int burstTime = pArrD.get(0).getBT();
	             
	             for(int i = 0; i < pArrD.size();i++) {
	                if(pArrD.get(i).getAT() <= t && burstTime > pArrD.get(i).getBT()) {
	                   burstTime = pArrD.get(i).getBT();
	                   shortest = i;
	                }
	             }         
	             temp = pArrD.get(shortest);
	             int pid = temp.getPid();
	             pArrD.remove(shortest);
	             for(int i = 0; i < burstTime;i++){
	                t++;
	                temp.setRT(temp.getRT()-1);
	                System.out.println("p"+temp.getPid());
	                if(previous == pid) {
	                   count++;
	                }else {
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
		   pArrD.clear();
		   cloneP();
		   
		   int min=0,t=0;
		      int RT;
		      int previous = 0;
		      int count = 0;
		      ProcessP temp;
		      
		      while(!pArrD.isEmpty()) {
		         RT = Integer.MAX_VALUE;
		         for(int i=0;i<pArrD.size();i++) {
		            if(pArrD.get(i).getAT()<=t && pArrD.get(i).getRT() < RT ) {
		               RT = pArrD.get(i).getRT();
		               min = i;
		            }
		         }//남은 실행시간이 가장적은 프로세스 선별
		         temp = pArrD.get(min);
		         int pid = temp.getPid();
		         pArrD.remove(min);
		         temp.setRT(temp.getRT()-1);
		         t++;
		         System.out.println("p"+temp.getPid());
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
		            pArrD.add(temp);
		         }   
		      }T.add(count);

	   }

	   public void HRRN() {
		   pArrD.clear();
		   cloneP();
		   
		   int t=0,p=0;
		      int previous = 0;
		      int count = 0;
		      float ratio;
		      float maxR;
		      ProcessP temp;
		      
		      while(!pArrD.isEmpty()) {   
		         if(pArrD.get(p).getAT()<=t) {
		            temp = pArrD.get(p);
		            int pid = temp.getPid();
		            pArrD.remove(p);
		            int RT = temp.getRT();
		            for(int i =0;i<RT;i++) {
		               temp.setRT(temp.getRT()-1);
		               for(int j =0;j<pArrD.size();j++) {
		                  if(pArrD.get(j).getAT()<=t)
		                     pArrD.get(j).setWT(pArrD.get(j).getWT()+1);
		               }
		               t++;
		               System.out.println("p"+temp.getPid());
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
		            for(int i =0;i<pArrD.size();i++) {
		               temp = pArrD.get(i);
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
	   
	   public void cloneP() {
	      ProcessP p;
	      for(int i =0;i<pArrO.size();i++) {
	         p = (ProcessP)pArrO.get(i).clone();
	            pArrD.add(p);
	      }
	   }
	   
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
		      ProcessP.id = 1;
		      return s;
	   }
	   
	   public void finishP(ProcessP p,int t) {
	      p.setTT(t-p.getAT());
	      p.setWT(p.getTT()-p.getBT());
	      p.setNT((float)(p.getTT())/p.getBT());
	      pArrT.add(p);
	      System.out.println("finishe, time: " + t);
	   }
	}

	class SortbyPid implements Comparator<ProcessP>
	{
	    public int compare(ProcessP a, ProcessP b)
	    {
	        return a.getPid() - b.getPid();
	    }
	}

