package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.tables.Direction;
import dbClasses.tables.Hotel;
import dbClasses.tables.Result;
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

    public void setDialogStage(Stage dialogStage){
        setDialogStage(dialogStage, new  Hotel("новая запись"));
    }
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

    private void setTableDirections(){
        columnDirectionsName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        conn = dbHandler.getConnection();
        fillTableDirections();
        tableDirections.setItems(directions);
    }
    private void fillTableDirections(){
        try {
            fillTableDirections(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_DIRECTION));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void fillTableDirections(ResultSet localResultSet) throws SQLException{
        tableDirections.getItems().clear();
        while (localResultSet.next()) {
            Direction direction = new Direction();
            direction.setId(localResultSet.getInt(Direction.FIELD_ID));
            direction.setName(localResultSet.getString(Direction.FIELD_NAME));
            directions.add(direction);
        }
    }

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

    private void setWorkItem() {
        workItem.setName(textName.getText());
        workItem.setAddress(textAddress.getText());
        if (tableDirections.getSelectionModel().getSelectedItem() != null) {
            workItem.setIdDirection(tableDirections.getSelectionModel().getSelectedItem().getId());
            workItem.setDirection(tableDirections.getSelectionModel().getSelectedItem().getName());
        }
        labelDirection.setText(workItem.getDirection());
    }

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
                if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_HOTELS, workItem.getId()) > 0) {
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

    public void onDirectionClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1)
            setWorkItem();
    }

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
