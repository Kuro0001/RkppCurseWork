package controllersAll;

import com.mysql.cj.protocol.Resultset;
import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.tables.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mains.Main;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerAuthorisation {

    public TextField textSurname;
    public TextField textEmail;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Employee workItem;

    public void setDialogStage(Stage dialogStage) {
        setDialogStage(dialogStage, new Employee("новая запись"));
    }

    public void setDialogStage(Stage dialogStage, Employee workItem) {
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        textSurname.setText(workItem.getSurname());
        textEmail.setText(workItem.getEmail());
    }


    private boolean validation(String process) {
        if (textSurname.getText().equals("") || textSurname.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введена Фамилия, либо больше 45 символов");
            return false;
        }
        if (textEmail.getText().equals("") || textEmail.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введен Адрес электронной почты, либо больше 45 символов");
            return false;
        }
        return true;
    }

    private void setWorkItem() {
        workItem.setSurname(textSurname.getText());
        workItem.setEmail(textEmail.getText());
    }

    public void onEnter(ActionEvent actionEvent) {
        if (validation("Попытка авторизации")) {
            try {
                workItem = new Employee();
                setWorkItem();
                ResultSet set = SQLRequests.selectOneRow(conn, workItem);
                if (set.isBeforeFirst()) {
                    set.next();
                    workItem.setId(set.getInt(Employee.FIELD_ID));
                    workItem.setName(set.getString(Employee.FIELD_NAME));
                    workItem.setSurname(set.getString(Employee.FIELD_SURNAME));
                    workItem.setPatronymic(set.getString(Employee.FIELD_PATRONYMIC));
                    workItem.setEmail(set.getString(Employee.FIELD_EMAIL));
                    MessageWindow.showInformation("Попытка авторизации", "Успешно");
                    Main.setUser(workItem);
                    dialogStage.close();
                }
                else {
                    MessageWindow.showInformation("Попытка авторизации", "Пользователь не найден");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}