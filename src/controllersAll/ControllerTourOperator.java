package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mains.Main;
import mains.MessageWindow;
import mains.Validation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
/**
 * model for table Category
 * @author Kuro
 * @version 1.0
 */
public class ControllerTourOperator {

    public TextField textName;
    public TextField textEmail;
    public TextField textPhone;
    public TextField textUniqueNumber;

    public TableView<Tour> tableTours;
    public TableColumn<Tour, String> columnTourName;
    public TableColumn<Tour, String> columnTureTourOperator;
    public TableColumn<Tour, Integer> columnTureOffersCount;
    public TableColumn<Tour, Integer> columnTureVouchersCount;
    public TableColumn<Tour, String> columnTureHotel;
    public TableColumn<Tour, String> columnTureDirection;
    public TableColumn<Tour, Double> columnTurePrice;
    public TableColumn<Tour, LocalDate> columnDateStart;
    public TableColumn<Tour, Integer> columnTureDaysCount;
    public TableColumn<Tour, String> columnTureKind;
    public TableColumn<Tour, String> columnTureCategory;
    public TextField textTourDirection;
    public TextField textTourKind;
    public TextField textTourPrice;
    public DatePicker dateTourDate;


    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private TourOperator workItem;
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage){
        setDialogStage(dialogStage, new TourOperator("новая запись"));
    }
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage, TourOperator workItem) {
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        textName.setText(workItem.getName());
        textEmail.setText(workItem.getEmail());
        textPhone.setText(workItem.getPhone());
        textUniqueNumber.setText(workItem.getUniqueNumber());
        setTableTour();
    }
    /**
     * set data into table
     */
    public void setTableTour() {
        columnTourName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnTureTourOperator.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().tourOperatorProperty());
        columnTureOffersCount.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().offersCountProperty().asObject());
        columnTureVouchersCount.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().vouchersCountProperty().asObject());
        columnTureHotel.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().hotelProperty());
        columnTureDirection.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().directionProperty());
        columnTurePrice.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().priceProperty().asObject());
        columnDateStart.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().dateStartProperty());
        columnTureDaysCount.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().daysCountProperty().asObject());
        columnTureKind.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().kindProperty());
        columnTureCategory.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().categoryProperty());
        conn = dbHandler.getConnection();
        if (workItem.getId() > 0) fillTableTour();
        tableTours.setItems(tours);
        tableTours.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour tour1) {
            }
        });
    }
    /**
     * fill table
     */
    private void fillTableTour(){
        try {
            Direction direction = new Direction();
            direction.setName(textTourDirection.getText());
            Tour tour = new Tour();
            tour.setKind("\'%" + textTourKind.getText() + "%\'");
            tour.setDateStart(LocalDate.of(0001,01,01));

            fillTableTour(SQLRequests.selectTourForTourOperator((conn), workItem.getId(), direction, tour));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableTour(ResultSet localResultSet) throws SQLException {
        tableTours.getItems().clear();
        while (localResultSet.next()) {
            Tour tour = new Tour();
            tour.setId(localResultSet.getInt(Tour.FIELD_ID));
            tour.setName(localResultSet.getString(Tour.FIELD_NAME));
            tour.setIdTourOperator(localResultSet.getInt(Tour.FIELD_ID_TOUR_OPERATOR));
            tour.setOffersCount(localResultSet.getInt(Tour.FIELD_OFFERS_COUNT));
            tour.setVouchersCount(localResultSet.getInt(Tour.FIELD_VOUCHERS_COUNT));
            tour.setIdHotel(localResultSet.getInt(Tour.FIELD_ID_HOTEL));
            tour.setPrice(localResultSet.getDouble(Tour.FIELD_PRICE));
            tour.setDateStart(localResultSet.getDate(Tour.FIELD_DATE_START).toLocalDate());
            tour.setDaysCount(localResultSet.getInt(Tour.FIELD_DAYS_COUNT));
            tour.setIdKind(localResultSet.getInt(Tour.FIELD_ID_KIND));
            tour.setIdCategory(localResultSet.getInt(Tour.FIELD_ID_CATEGORY));

            ResultSet resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_TOUR_OPERATOR, tour.getIdTourOperator());
            resultSetFromOtherTable.next();
            tour.setTourOperator(resultSetFromOtherTable.getString(TourOperator.FIELD_NAME));
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_HOTELS, tour.getIdHotel());
            resultSetFromOtherTable.next();
            tour.setHotel(resultSetFromOtherTable.getString(Hotel.FIELD_NAME));
            tour.setIdDirection(resultSetFromOtherTable.getInt(Hotel.FIELD_ID_DIRETION));
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_DIRECTION, tour.getIdDirection());
            resultSetFromOtherTable.next();
            tour.setDirection(resultSetFromOtherTable.getString(Direction.FIELD_NAME));
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_KIND, tour.getIdKind());
            resultSetFromOtherTable.next();
            tour.setKind(resultSetFromOtherTable.getString(Kind.FIELD_NAME));
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_KIND, tour.getIdCategory());
            resultSetFromOtherTable.next();
            tour.setCategory(resultSetFromOtherTable.getString(Category.FIELD_NAME));
            tours.add(tour);
        }
    }

    /**
     * search in table
     */
    public void onTourSearch(ActionEvent actionEvent) {
        try {
            Direction direction = new Direction();
            String directionText = textTourDirection.getText();
            direction.setName(directionText);
            Tour tour = new Tour();
            String kind = textTourKind.getText();
            tour.setKind(kind);
            LocalDate date = dateTourDate.getValue();
            if (date == null || date.toString() == null || date.toString().equals(""))
                date = LocalDate.of(0001, 01, 01);
            tour.setDateStart(date);
            ResultSet set = SQLRequests.selectTourForTourOperator((conn), workItem.getId(), direction, tour);
            if (set.isBeforeFirst()) {
                fillTableTour(set);
            } else {
                MessageWindow.showInformation("Поиск туров", "Результат поиска - 0 записей");
                tours.clear();
            }
            dateTourDate.setValue(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * work with selected item in table
     */
    public void onTourClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1)
        {
            try {
                Tour selectedItem = tableTours.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Tour("новая запись");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleTour.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                addStage.setTitle("Работа с записью тура - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerTour controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(950);
                addStage.setMinHeight(500);
                addStage.showAndWait();
                fillTableTour();
            } catch (IOException ex) {
                MessageWindow.showError("Открытие окна", ex.getMessage());
            }
        }
    }
    /**
     * checking input data
     */
    private boolean validation(String process){
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Имя, либо больше 45 символов");
            return false;
        }
        if (Validation.checkContainNotInt(textPhone.getText()) || textPhone.getText().length() > 10) {
            MessageWindow.showError(process, "Неверно введен Телефон, либо больше 10 символов");
            return false;
        }
        if (textEmail.getText().equals("") || textEmail.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введен Адрес электронной почты, либо больше 45 символов");
            return false;
        }
        if (Validation.checkContainNotInt(textUniqueNumber.getText()) || textUniqueNumber.getText().length() > 13) {
            MessageWindow.showError(process, "Неверно введен Уникальный номер организации, либо больше 13 символов");
            return false;
        }
        return true;
    }
    /**
     * set into work item new data
     */
    private void setWorkItem() {
        workItem.setName(textName.getText());
        workItem.setPhone(textPhone.getText());
        workItem.setUniqueNumber(textUniqueNumber.getText());
        workItem.setEmail(textEmail.getText());
    }
    /**
     * add new record in data base
     */
    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new TourOperator();
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
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_TOUR_OPERATOR, workItem.getId()) > 0) {
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
