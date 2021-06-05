package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.Direction;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * model for table Direction
 * @author Kuro
 * @version 1.0
 */
public class ControllerDirection {

    public TextField textName;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Direction workItem;
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
    }
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage, Direction workItem){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;
        textName.setText(workItem.getName());
    }
    /**
     * checking input data
     */
    public boolean validation(String process){
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Наименование, либо больше 45 символов");
            return false;
        }
        return true;
    }
    /**
     * set into work item new data
     */
    public void setWorkItem(){
        workItem.setName(textName.getText());
    }
    /**
     * add new record in data base
     */
    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Direction();
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
    /**
     * edit record in data base
     */
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
        else {
            MessageWindow.showError("Изменение", "Неверно введены данные");
        }
    }
    /**
     * delete record from data base
     */
    public void onDelete(ActionEvent actionEvent) {
        if (workItem.getId() >0) {
            try {
                if(!SQLRequests.selectReferences(conn, workItem).isBeforeFirst()) {
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_DIRECTION, workItem.getId()) > 0) {
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
