package mains;

import dbClasses.models.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static Employee logedInUser;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static Employee getUser() {
        return logedInUser;
    }
    public static void setUser(Employee user) { logedInUser = user; }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/sampleMainWindow.fxml"));
        primaryStage.setTitle("Curse work: (TA) Tourist Agency");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(600);
        this.primaryStage = primaryStage;
        this.logedInUser = null;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
