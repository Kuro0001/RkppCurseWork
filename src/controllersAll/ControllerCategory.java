package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.tables.Category;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.SQLException;

public class ControllerCategory {
    public TextField textName;
    public TextField textAddedValue;
    public TextField textDiscount;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Category workItem;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
    }
    public void setDialogStage(Stage dialogStage, Category workItem){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;
        textName.setText(workItem.getName());
        textAddedValue.setText(String.valueOf(workItem.getAddedValue()));
        textDiscount.setText(String.valueOf(workItem.getDiscount()));
    }

    public boolean validation() {
        if (textName.getText() != "" && textAddedValue.getText() != "" && textDiscount.getText() != "")
            return true;
        return false;
    }

    public void setWorkItem(){
        workItem.setName(textName.getText());
        workItem.setAddedValue(Double.parseDouble(textAddedValue.getText()));
        workItem.setDiscount(Double.parseDouble(textDiscount.getText()));
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation()) {
            try {
                workItem = new Category();
                setWorkItem();
                if (SQLRequests.addCategory(conn, workItem) > 0) {
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
                if (SQLRequests.editCategory(conn, workItem) > 0) {
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
                if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_CATEGORY, workItem.getId()) > 0) {
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
