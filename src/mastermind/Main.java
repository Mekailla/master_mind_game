package mastermind;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    public void start(Stage primaryStage) {
        mastermind.GameController Controller = new mastermind.GameController();
        primaryStage.setTitle("MasterMind");
        primaryStage.setResizable(true);
        Controller.StartTime();
        VBox layout=Controller.CreateGameLayout();
        Scene scene=new Scene(layout,800,600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/master/style.css").toExternalForm()) );
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/master/BG.jpg"))));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}