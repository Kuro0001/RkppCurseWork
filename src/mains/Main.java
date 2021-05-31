package mains;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/sampleMainWindow.fxml"));
        primaryStage.setTitle("Curse work: (TA) Tourist Agency");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        //this.primaryStage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
