package mains;

import javafx.scene.control.Alert;

/**
 * class for message window
 * @author Kuro
 * @version 1.0
 */
public class MessageWindow {

    /**
     * method to open information window
     * @param process title for window
     * @param result content for window
     */
    public static void showInformation(String process, String result){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(process); //title
        alert.setHeaderText(null);
        alert.setContentText(result); // content
        alert.showAndWait();
    }
    /**
     * method to open error window
     * @param process title for window
     * @param result content for window
     */
    public static void showError(String process, String result){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(process); //title
        alert.setHeaderText(null);
        alert.setContentText(result); // content
        alert.showAndWait();
    }
}
