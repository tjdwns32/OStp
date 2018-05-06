package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.sun.javafx.charts.Legend;
import com.sun.javafx.charts.Legend.LegendItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
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
	private Button btnClear;
	
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
	
	@FXML
	private StackedBarChart chart;
	
	@FXML
	private CategoryAxis yAxis;
	private CategoryAxis xAxis;
	
	ObservableList<String> scheduleList = FXCollections
			.observableArrayList("FCFS","RR","SPN","SRTN","HRRN");
	
	ObservableList<String> processList = FXCollections
			.observableArrayList("Process P1");
	
	int numP, timequantum; String nameSchedule ="FCFS";
	LinkedList<ProcessP> lp = new LinkedList<ProcessP>();
    
	Node nodeColor; String[] rgb;
    Color[] color = {Color.BLUE, Color.RED, Color.LIGHTGREEN, Color.BEIGE, Color.BURLYWOOD};
    
	ProcessP[] ap;
    String textPro;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yAxis.getCategories().addAll("Process");
		ObservableList<XYChart.Series<Number,Category>> list = FXCollections.observableArrayList();

		comSchedule.setValue("FCFS");  //스케줄링 기법 초기 선택 값
		comSchedule.setItems(scheduleList);  //콤보박스에 스케줄링 리스트 생성
		
		/*
	    String s;    
	    int[] aNumbers;
	    int[] bNumbers;
	    */
	    textArea.setText("-> Hello! Scheduling Program!\n\r\n\r");
	    textArea.setText(textArea.getText() + "-> Please Setting Scheduling Information :-)\n\r\n\r");
	    
	    //콤보박스 이벤트 처리 - 스케줄링 선택
	  	comSchedule.valueProperty().addListener(new ChangeListener<String>() {
	  		@Override public void changed(ObservableValue ov, String before, String temp) {
	  			nameSchedule = temp;  //현재 선택한 스케줄링 기법 저장
	  				
	  			//선택한 스케줄링 기법이 RR인 경우 timeQuantum 입력 텍스트필드 활성화
	  			if(nameSchedule.equals("RR")) textTimeQ.setDisable(false);
	  			else textTimeQ.setDisable(true);
	  		}
	  	});
	  		
	  	//콤보박스 이벤트 처리 - 프로세스 선택
	  	comPro.valueProperty().addListener(new ChangeListener<String>() {
	  		@Override public void changed(ObservableValue ov, String before, String temp) {
	  			textPro = temp; //현재 선택한 프로세스 저장 (ex : Process P1)
	  		}
	  	});
	  	
	  	//버튼 이벤트 처리 - Comfirm 버튼
	    btnClear.setOnAction(event -> {
	    	textArea.clear();
	    });
	    
	  	//버튼 이벤트 처리 - Comfirm 버튼
	    btnConfirm.setOnAction(event -> {
	    	//프로세스 수 저장 및 프로세스 생성
	    	if(!textNumP.getText().toString().equals(""))
	    		numP = Integer.parseInt(textNumP.getText().toString());
			
			ap = new ProcessP[numP];
			
			//프로세스 색상배열
			rgb = new String[numP+1];
			
			for(int i=0;i<numP;i++) {
				//프로세스 생성
		        ap[i] = new ProcessP();
		        
		        //프로세스 마다 색상 지정
		        rgb[i+1] = String.format("%d, %d, %d",
				            (int) (color[i%5].getRed() * Math.floor( (255/numP)*(i+1) )),
				            (int) (color[i%5].getGreen() * Math.floor( (255/numP)*(i+1) )),
				            (int) (color[i%5].getBlue() * Math.floor( (255/numP)*(i+1) )));
		    } 
		
			
			//포로세스 기법이 RR인 경우 TimeQuantum값 저장
			if(nameSchedule.equals("RR"))
				timequantum = Integer.parseInt(textTimeQ.getText().toString());
			
			//프로세스 콤보박스 초기값 생성 
			comPro.getItems().clear();
			comPro.setValue("Process P1");
			comPro.setItems(processList);
			
			//프로세스 수 만큼 콤보박스 리스트 추가
			for(int i = 2; i <= numP; i++) {
				comPro.getItems().add("Process P"+Integer.toString(i));
			}
			
			//모니터에 셋팅한 결과 출력
			textArea.clear();
			textArea.setText(textArea.getText() + "-> Process Scheduling : " + nameSchedule + "!\n\r\n\r");
			textArea.setText(textArea.getText() + "-> Create Process " + Integer.toString(numP) + "!\n\r\n\r");
			if(nameSchedule.equals("RR")) textArea.setText(textArea.getText() + "-> Time Quantum " + timequantum + "!\n\r\n\r");
			textArea.setText(textArea.getText() + "-> Please Setting Process Information :-)\n\r\n\r");
			
			textTimeQ.clear();
			textNumP.clear();
		});
		
	    //버튼 이벤트 처리 - Set버튼
		btnSet.setOnMouseClicked(event -> {
			//현재 입력받아온 프로세스에서 숫자부분만 추출
			Scanner in = new Scanner(textPro).useDelimiter("[^0-9]+");
			int index = in.nextInt() - 1;
			System.out.println(index);
			
			//프로세스에  입력받은 AT, BT, RT 초기값 셋팅
			ap[index].setAT(Integer.parseInt(textAT.getText().toString()));
			ap[index].setBT(Integer.parseInt(textBT.getText().toString()));
			ap[index].setRT(ap[index].getBT());
			
		    //같은 프로세스에 대해서 값을 재입력하면 나중 값을 적용
	        Predicate<ProcessP> processPredicate = p-> p.getPid() == (index+1);
	        lp.removeIf(processPredicate);
	        lp.add(ap[index]);
			
			//모니터에 셋팅한 결과 출력
			textArea.setText(textArea.getText() + "-> Set Process " + (index + 1));
			textArea.setText(textArea.getText() + "  AT : " + ap[index].getAT());
			textArea.setText(textArea.getText() + "  BT : " + ap[index].getBT() + "\n\r\n\r");
			
			textAT.clear();
			textBT.clear();
		});
		
		//버튼 이벤트 처리 - Start버튼
		btnStart.setOnMouseClicked(event -> {

			chart.getData().clear();
			
			//프로세스 리스트를 전달하여 스케줄링 시작
			Scheduling sched = new Scheduling(lp);
		    switch(nameSchedule) {
		      case "FCFS":sched.FCFS();  break;
		      case "RR": sched.RR(timequantum);break;
		      case "SPN": sched.SPN(); break;
		      case "SRTN": sched.SRTN(); break;
		      case "HRRN": sched.HRRN(); break;
		      default:     
		    }
		    
		    int size = sched.T.size();
		    System.out.println("size: "+size);
		    //콘솔창에 P배열 T배열값 찍어본거 이거보고 확인하고 수정해도 됨 
		    for(int i = 0; i < size; i++) {
		    	System.out.print(sched.P.get(i)+" ");
		    }
		    System.out.println();
		    for(int i = 0; i < size; i++) {
		    	System.out.print(sched.T.get(i)+" ");
		    }
		    System.out.println();
		    
		    //스케줄링 결과 차트 그리기
		    XYChart.Series<Number,Category>[] CategoryP = Stream.<XYChart.Series<String, Number>>
		    	generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);
		    
		    for(int i = 0; i < size;i++) {
		    	CategoryP[i].getData().add(new XYChart.Data(sched.T.get(i), "Process"));
		    	
		    	if(i<numP) 
		    		CategoryP[i].setName("Process" + (i+1));
		    	else
		    		CategoryP[i].setName("Remove");
		    	
		    	
		    	list.add(CategoryP[i]);
		    	chart.setData(list);
		    }
		    
		    
		    //차트 series 색상 조정
		    for(int i = 0; i < size; i++) { 
		    	for( Node n : CategoryP[i].getChart().lookupAll(".series"+i)) {
				   n.getStyleClass().remove("default-color"+(i%8));
				   n.getStyleClass().add("default-color"+(i%50));
				   n.setStyle("-fx-bar-fill: rgba(" + rgb[sched.P.get(i)] + ", 0.5);");
		    	}	
			}
		    
		    //차트 범례 개수 조정
		    Legend legend = (Legend)CategoryP[0].getChart().lookup(".chart-legend");
		    legend.getItems().removeIf(item->item.getText().equals("Remove"));

		    //차트 범례 색상 조정
		    int i = 0;
		    for( Node n : CategoryP[i].getChart().lookupAll(".chart-legend-item")) {
	    		if (n instanceof Label && ((Label) n).getGraphic() != null && i < numP) {
				   ((Label) n).getGraphic().getStyleClass().remove("default-color" + (i % 8));
		           ((Label) n).getGraphic().getStyleClass().add("default-color" + (i % 50));
		           ((Label) n).getGraphic().setStyle("-fx-background-color: rgba(" + rgb[i+1] + ", 0.5);");
	    		}
	    		i++;
	    	}
		    
		    //모니터에 스케줄링 결과 출력
		    textArea.clear();
		    textArea.setText(textArea.getText() + "-> Complete!!! Process Scheduling : " + nameSchedule + "!\n\r\n\r");
		    textArea.setText(textArea.getText() + sched.toString() + "\n\r\n\r");
		});
	}
}
