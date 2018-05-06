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
	    //�����층 ����, ���μ��� ���� �����ϰ� confirm ��ư�� ���� �� �׼� 
		btnConfirm.setOnAction(event -> {
			numP = Integer.parseInt(textNumP.getText().toString());
			//numP = �Էµ� ���μ��� �� 
			if(nameSchedule.equals("RR"))
				timequantum = Integer.parseInt(textTimeQ.getText().toString());
			//���� �κ��� ����� ��� timequantum���� �����´�. 
			ap = new ProcessP[numP];
			//���μ��� �迭 ����
			comPro.getItems().clear();
			comPro.setValue("Process P1");
			comPro.setItems(processList);
			//�ϴ��� �޺��ڽ��� ���μ��� �� ��ŭ�� ����Ʈ ����
			for(int i = 2; i <= numP; i++) {
				comPro.getItems().add("Process P"+Integer.toString(i));
			}
			//�޺��ڽ� �� ����
			for(int i=0;i<numP;i++) {
		         ap[i] = new ProcessP();
		    }//���μ��� ��ü ����
		});
		
		//���õ� �����층 ����� ���� �κ��� �ƴϸ� timequantumĭ�� ������� ���ϰ� �Ѵ�.
		comSchedule.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				nameSchedule = t1;				
				if(nameSchedule.equals("RR")) textTimeQ.setDisable(false);
				else textTimeQ.setDisable(true);
			}
		});
		
		//�Էµ� ���μ��� ���� �ؽ�Ʈ�ڽ��� ����صд�.
		comPro.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				textPro = t1;
			}
		});
		
		//Set��ư�� ������ �ش� ���μ����� �Է���AT�� BT���� �־�����.
		btnSet.setOnMouseClicked(event -> {
			Scanner in = new Scanner(textPro).useDelimiter("[^0-9]+");
			int index = in.nextInt() - 1;
			System.out.println(index);
			//������ ���μ����� ��ȣ�� �´� ��ü�� �ʵ尪 �Ҵ�
			ap[index].setAT(Integer.parseInt(textAT.getText().toString()));
			ap[index].setBT(Integer.parseInt(textBT.getText().toString()));
			ap[index].setRT(ap[index].getBT());
			
			//���� ���μ����� ���ؼ� ���� ���Է��ϸ� ���߰��� ����
			Predicate<ProcessP> processPredicate = p-> p.getPid() == (index+1);
			lp.removeIf(processPredicate);
			lp.add(ap[index]);
			
			textAT.setText("");
			textBT.setText("");
			in.close();
		});
		//start��ư�� ������ ���۵Ǵ� �׼� 
		btnStart.setOnMouseClicked(event -> {
			textArea.setText(nameSchedule + "START!!");
			
			Scheduling sched = new Scheduling(lp);
		    //�����ٸ� �� ����� ���μ��� �迭�� ���ڷ� �Ѱ��ش�.
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
