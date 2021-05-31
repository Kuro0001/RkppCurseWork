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

    public boolean validation(){
        if (textName.getText() != "")
            return true;
        return false;
        /*
        String numberMatcher = "^-?\\d+$";
        //t1 - новый текст, s - старый текст.
        textName.textProperty().addListener((observableValue, s, t1) - > {
        if (!t1.isEmpty()) {
            if (!t1.matches(numberMatcher)) {
                textBox.setText(s);
            } else {
                try {
                    // тут можете парсить строку как захотите
                    int value = Integer.parseInt(t1);
                    textBox.setText(value);
                } catch (NumberFormatException e) {
                    this.setText(s);
                }
            }
        }
 });*/
    }

    public void setWorkItem(){
        workItem.setName(textName.getText());
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation()) {
            try {
                workItem = new Kind();
                setWorkItem();
                if (SQLRequests.addKind(conn, workItem) > 0) {
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
                if (SQLRequests.editKind(conn, workItem) > 0) {
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
                if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_KIND, workItem.getId()) > 0) {
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
