package application;

public class ProcessP implements Cloneable {
	int Pid;
	   static int id=1;
	   int arrivalTime = 0;
	   int burstTime = 0;
	   int remainingTime = 0;
	   int watingTime = 0;
	   int turnaroundTime = 0;
	   float normalizedTime = 0;
	   
	   public ProcessP() {this.Pid= id++;}
	   
	   //getter, setter
	   public int getPid() {return Pid;}
	   public int getAT() {return arrivalTime;}
	   public int getBT() {return burstTime;}
	   public int getRT() {return remainingTime;}
	   public int getWT() {return watingTime;}
	   public int getTT() {return turnaroundTime;}
	   public float getNT() {return normalizedTime;}
	   void setAT(int arrivalTime) {this.arrivalTime =  arrivalTime;}
	   void setBT(int burstTime) {this.burstTime =  burstTime;}
	   void setRT(int remainingTime) {this.remainingTime =  remainingTime;}
	   void setWT(int waitingTime) {this.watingTime = waitingTime;}
	   void setTT(int turnaroundTime) {this.turnaroundTime = turnaroundTime;}
	   void setNT(float normalizedTime) {this.normalizedTime = normalizedTime;}
	   
	   public Object clone(){
	      Object obj = null;
	      try {
	         obj = super.clone();
	      }catch(CloneNotSupportedException e) {}
	      
	      return obj;
	   }
	   public String toString() {
		  String s = String.format("Pid:%2d\tAT: %2d\tBT: %2d\tWT: %2d\tTT: %2d\tNT: %2.3f", getPid(),getAT(),getBT(),getWT(),getTT(),getNT());
	      return s;
		  //getPid(),getAT(),getBT(),getWT(),getTT(),getNT()
	   }
}
