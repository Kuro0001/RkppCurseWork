package mains;

import dbClasses.models.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * main executable file
 * @author Kuro
 * @version 1.0
 */
public class Main extends Application {
    /** field scene */
    private static Stage primaryStage;
    /** field current user */
    private static Employee logedInUser;

    /**
     * to get parent scene
     * @return parent stage(Stage)
     */
    public static Stage getPrimaryStage() { return primaryStage; }
    /**
     * to get current user
     * @return current user like model(Employee)
     */
    public static Employee getUser() { return logedInUser; }

    /**
     * to get current user
     * @param user - model(Employee)
     */
    public static void setUser(Employee user) { logedInUser = user; }

    /**
     * to start the application
     * @param primaryStage - main scene
     * @throws Exception in situation where scene was not founded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/views/sampleMainWindow.fxml"));
        primaryStage.setTitle("Curse work: (TA) Tourist Agency");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(600);
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    /**
     * standard constructor to launch program
     * @param args standard args to launch program
     */
    public static void main(String[] args) { launch(args); }
}
