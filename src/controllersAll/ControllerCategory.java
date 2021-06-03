package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.Category;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.MessageWindow;
import mains.Validation;

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

    private boolean validation(String process){
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Наименование, либо больше 45 символов");
            return false;
        }
        if (Validation.checkContainNotDouble(textAddedValue.getText())) {
            MessageWindow.showError(process, "Неверно введена Добавочная стоимость. Допустимы только числа");
            return false;
        }
        if (Validation.checkContainNotDouble(textDiscount.getText())) {
            MessageWindow.showError(process, "Неверно введена Скидка. Допустимы только числа");
            return false;
        }
        return true;
    }

    public void setWorkItem(){
        workItem.setName(textName.getText());
        workItem.setAddedValue(Double.parseDouble(textAddedValue.getText()));
        workItem.setDiscount(Double.parseDouble(textDiscount.getText()));
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Category();
                setWorkItem();
                if (SQLRequests.addOneRow(conn, workItem) > 0) {
                    MessageWindow.showInformation("Добавление", "Добавлена 1 запись");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void onEdit(ActionEvent actionEvent) {
        if (validation("Изменение") && workItem.getId() >0) {
            try {
                setWorkItem();
                if (SQLRequests.editOneRow(conn, workItem) > 0) {
                    MessageWindow.showInformation("Изменение", "Изменена 1 запись");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        if (workItem.getId() > 0) {
            try {
                if (!SQLRequests.selectReferences(conn, workItem).isBeforeFirst()) {
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_CATEGORY, workItem.getId()) > 0) {
                        MessageWindow.showInformation("Удаление", "Удалена 1 запись");
                        dialogStage.close();
                    }
                } else {
                    MessageWindow.showError("Удаление", "Невозможно удалить запись,так как на нее ссылаются в других таблицах");
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
