package application;
	
import java.util.LinkedList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Font.loadFont(getClass().getResourceAsStream("Oswald-bold.ttf"),
                14
        );
		
		Font.loadFont(getClass().getResourceAsStream("Sansation.ttf"),
                14
        );
		
//		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("comparison.png")));
		
		Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setUserAgentStylesheet(STYLESHEET_MODENA);
		primaryStage.setTitle("Process Scheduling");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
