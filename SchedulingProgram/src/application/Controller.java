package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	ObservableList<String> scheduleList = FXCollections
			.observableArrayList("FCFS","RR","SPN","SRTN","HRRN");
	
	ObservableList<String> processList = FXCollections
			.observableArrayList("Process P1");
	
	@FXML
	private ComboBox comSchedule;
	@FXML
	private ComboBox comPro;
	
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnSet;
	@FXML
	private Button btnStart;
	
	@FXML
	private TextField textTimeQ;
	@FXML
	private TextField textNumP;
	
	@FXML
	private TextField textAT;
	@FXML
	private TextField textBT;
	
	@FXML
	private TextArea textArea;
	
	
	int numP, timequantum; String nameSchedule ="FCFS";
	LinkedList<ProcessP> lp = new LinkedList<ProcessP>();
    ProcessP[] ap;
    
    String textPro;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comSchedule.setValue("FCFS");
		comSchedule.setItems(scheduleList);
		
		
	    String s;
	      
	    int[] aNumbers;
	    int[] bNumbers;
	    //스케쥴링 종류, 프로세스 수를 선택하고 confirm 버튼을 누를 때 액션 
		btnConfirm.setOnAction(event -> {
			numP = Integer.parseInt(textNumP.getText().toString());
			//numP = 입력된 프로세스 수 
			if(nameSchedule.equals("RR"))
				timequantum = Integer.parseInt(textTimeQ.getText().toString());
			//라운드 로빈을 사용할 경우 timequantum값을 가져온다. 
			ap = new ProcessP[numP];
			//프로세스 배열 선언
			comPro.getItems().clear();
			comPro.setValue("Process P1");
			comPro.setItems(processList);
			//하단의 콤보박스에 프로세스 수 만큼의 리스트 생성
			for(int i = 2; i <= numP; i++) {
				comPro.getItems().add("Process P"+Integer.toString(i));
			}
			//콤보박스 행 내용
			for(int i=0;i<numP;i++) {
		         ap[i] = new ProcessP();
		    }//프로세스 객체 생성
		});
		
		//선택된 스케쥴링 기법이 라운드 로빈이 아니면 timequantum칸을 사용하지 못하게 한다.
		comSchedule.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				nameSchedule = t1;				
				if(nameSchedule.equals("RR")) textTimeQ.setDisable(false);
				else textTimeQ.setDisable(true);
			}
		});
		
		//입력된 프로세스 수를 텍스트박스에 기록해둔다.
		comPro.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				textPro = t1;
			}
		});
		
		//Set버튼을 누르면 해당 프로세스에 입력한AT와 BT값이 주어진다.
		btnSet.setOnMouseClicked(event -> {
			Scanner in = new Scanner(textPro).useDelimiter("[^0-9]+");
			int index = in.nextInt() - 1;
			System.out.println(index);
			//선택한 프로세스의 번호에 맞는 객체에 필드값 할당
			ap[index].setAT(Integer.parseInt(textAT.getText().toString()));
			ap[index].setBT(Integer.parseInt(textBT.getText().toString()));
			ap[index].setRT(ap[index].getBT());
			
			//같은 프로세스에 대해서 값을 재입력하면 나중값을 적용
			Predicate<ProcessP> processPredicate = p-> p.getPid() == (index+1);
			lp.removeIf(processPredicate);
			lp.add(ap[index]);
			
			textAT.setText("");
			textBT.setText("");
			in.close();
		});
		//start버튼을 누르면 시작되는 액션 
		btnStart.setOnMouseClicked(event -> {
			textArea.setText(nameSchedule + "START!!");
			
			Scheduling sched = new Scheduling(lp);
		    //스케줄링 시 저장된 프로세스 배열을 인자로 넘겨준다.
		    switch(nameSchedule) {
		      case "FCFS":sched.FCFS();  break;
		      case "RR": sched.RR(timequantum);break;
		      case "SPN": sched.SPN(); break;
		      case "SRTN": sched.SRTN(); break;
		      case "HRRN": sched.HRRN(); break;
		      default:     
		    }
		    textArea.clear();
		    textArea.setText(sched.toString());
		});
	
	}
}
