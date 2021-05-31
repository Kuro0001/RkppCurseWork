package mains;

import javafx.scene.control.Alert;

public class MessageWindow {

    public static void showInformation(String process, String result){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(process); //title
        alert.setHeaderText(null);
        alert.setContentText(result); // content
        alert.showAndWait();
    }
    public static void showError(String process, String result){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(process); //title
        alert.setHeaderText(null);
        alert.setContentText(result); // content
        alert.showAndWait();
    }
}
