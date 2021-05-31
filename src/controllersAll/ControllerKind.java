package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.tables.Kind;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.SQLException;

public class ControllerKind {

    private Stage dialogStage;
    public TextField textName;
    Connection conn;
    DbHandler dbHandler;
    private Kind workItem;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
    }
    public void setDialogStage(Stage dialogStage, Kind kind){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = kind;
        textName.setText(kind.getName());
    }

    public boolean validation(String process){
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверное введено Наименование, либо больше 45 символов");
            return false;
        }
        return true;
    }

    public void setWorkItem(){
        workItem.setName(textName.getText());
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Kind();
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
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_KIND, workItem.getId()) > 0) {
                        MessageWindow.showInformation("Удаление", "Удалена 1 запись");
                        dialogStage.close();
                    }
                }else {
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
