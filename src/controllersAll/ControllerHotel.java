package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.tables.Direction;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.SQLException;

public class ControllerHotel {

    public TextField textName;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Direction workItem;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
    }
    public void setDialogStage(Stage dialogStage, Direction workItem){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;
        textName.setText(workItem.getName());
    }

    public boolean validation() {
        if (textName.getText() != "")
            return true;
        return false;
    }

    public void setWorkItem(){
        workItem.setName(textName.getText());
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation()) {
            try {
                workItem = new Direction();
                setWorkItem();
                if (SQLRequests.addDirection(conn, workItem) > 0) {
                    MessageWindow.showInformation("Добавление", "Добавлена 1 запись");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            MessageWindow.showError("Добавление", "Неверно введены данные");
        }
    }

    public void onEdit(ActionEvent actionEvent) {
        if (validation() && workItem.getId() >0) {
            try {
                setWorkItem();
                if (SQLRequests.editDirection(conn, workItem) > 0) {
                    MessageWindow.showInformation("Изменение", "Изменена 1 запись");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            MessageWindow.showError("Изменение", "Неверно введены данные");
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        if (workItem.getId() >0) {
            try {
                if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_DIRECTION, workItem.getId()) > 0) {
                    MessageWindow.showInformation("Удаление", "Удалена 1 запись");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            MessageWindow.showError("Удаление", "Отсутсвует запись для удалениея");
        }
    }
}
