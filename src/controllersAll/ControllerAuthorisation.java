package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mains.Main;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * model for sample sampleAuthorisation
 * @author Kuro
 * @version 1.0
 */
public class ControllerAuthorisation {

    public TextField textSurname;
    public TextField textEmail;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Employee workItem;

    /**
     * constructor - creating a new object
     */
    public void setDialogStage(Stage dialogStage) {
        setDialogStage(dialogStage, new Employee("новая запись"));
    }
    /**
     * constructor - creating a new object
     */
    public void setDialogStage(Stage dialogStage, Employee workItem) {
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        textSurname.setText(workItem.getSurname());
        textEmail.setText(workItem.getEmail());
    }

    /**
     * checking input data
     */
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
    /**
     * set into work item new data
     */
    private void setWorkItem() {
        workItem.setSurname(textSurname.getText());
        workItem.setEmail(textEmail.getText());
    }
    /**
     * make authorisation
     */
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