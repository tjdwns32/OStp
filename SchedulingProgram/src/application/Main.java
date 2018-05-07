package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
		Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Process Scheduling");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
