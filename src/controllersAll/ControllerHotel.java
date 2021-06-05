package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.Direction;
import dbClasses.models.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mains.MessageWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * model for table Hotel
 * @author Kuro
 * @version 1.0
 */
public class ControllerHotel {

    public TextField textName;
    public TextField textAddress;
    public Label labelDirection;
    public TableView<Direction> tableDirections;
    public TableColumn<Direction, String> columnDirectionsName;
    public TextField textHotelDirection;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Hotel workItem;
    private ObservableList<Direction> directions = FXCollections.observableArrayList();
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage){
        setDialogStage(dialogStage, new  Hotel("новая запись"));
    }
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage, Hotel workItem){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;
        textName.setText(workItem.getName());
        textAddress.setText(workItem.getAddress());
        labelDirection.setText(workItem.getDirection());
        setTableDirections();
    }
    /**
     * set data into table
     */
    private void setTableDirections(){
        columnDirectionsName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        conn = dbHandler.getConnection();
        fillTableDirections();
        tableDirections.setItems(directions);
    }
    /**
     * fill table
     */
    private void fillTableDirections(){
        try {
            fillTableDirections(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_DIRECTION));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableDirections(ResultSet localResultSet) throws SQLException{
        tableDirections.getItems().clear();
        while (localResultSet.next()) {
            Direction direction = new Direction();
            direction.setId(localResultSet.getInt(Direction.FIELD_ID));
            direction.setName(localResultSet.getString(Direction.FIELD_NAME));
            directions.add(direction);
        }
    }
    /**
     * checking input data
     */
    private boolean validation(String process){
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Наименование, либо больше 45 символов");
            return false;
        }
        if (textAddress.getText().equals("") || textAddress.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введен Адрес, либо больше 45 символов");
            return false;
        }
        if (workItem.getIdDirection() <= 0) {
            MessageWindow.showError(process, "Не выбрано Направление");
            return false;
        }
        return true;
    }
    /**
     * set into work item new data
     */
    private void setWorkItem() {
        workItem.setName(textName.getText());
        workItem.setAddress(textAddress.getText());
        if (tableDirections.getSelectionModel().getSelectedItem() != null) {
            workItem.setIdDirection(tableDirections.getSelectionModel().getSelectedItem().getId());
            workItem.setDirection(tableDirections.getSelectionModel().getSelectedItem().getName());
        }
        labelDirection.setText(workItem.getDirection());
    }
    /**
     * add new record in data base
     */
    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Hotel();
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
    }
    /**
     * delete record from data base
     */
    public void onDelete(ActionEvent actionEvent) {
        if (workItem.getId() > 0) {
            try {
                if (!SQLRequests.selectReferences(conn, workItem).isBeforeFirst()) {
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_HOTELS, workItem.getId()) > 0) {
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

    /**
     * set work item from table
     */
    public void onDirectionClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1)
            setWorkItem();
    }
    /**
     * search in table
     */
    public void onSerchDirection(ActionEvent actionEvent) {
        try {
            Direction item = new Direction();
            String name = textHotelDirection.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableDirections(result);
            else MessageWindow.showInformation("Поиск направлений туров", "направлений туров с наименованием содержащим (" +
                    name + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
