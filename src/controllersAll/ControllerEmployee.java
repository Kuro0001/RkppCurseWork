package controllersAll;

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
import mains.MessageWindow;
import mains.Validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerEmployee {
    public TextField textSurname;
    public TextField textName;
    public TextField textPatronymic;
    public TextField textEmail;


    public TableView<Voucher> tableVouchers;
    public TableColumn<Voucher, String> columnVoucherTour;
    public TableColumn<Voucher, String> columnVoucherTourDirection;
    public TableColumn<Voucher, String> columnVoucherClient;
    public TableColumn<Voucher, String> columnVoucherEmployee;
    public TableColumn<Voucher, Double> columnVoucherCost;
    public TableColumn<Voucher, LocalDate> columnVoucherDate;
    public TextField textVoucherName;
    public TextField textVoucherDirection;
    public DatePicker dateVoucherDate;

    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Employee workItem;
    private ObservableList<Voucher> vouchers = FXCollections.observableArrayList();

    public void setDialogStage(Stage dialogStage){
        setDialogStage(dialogStage, new Employee("новая запись"));
    }
    public void setDialogStage(Stage dialogStage, Employee workItem){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        textSurname.setText(workItem.getSurname());
        textName.setText(workItem.getName());
        textPatronymic.setText(workItem.getPatronymic());
        textEmail.setText(workItem.getEmail());

        setTableVoucher();
    }

    public void setTableVoucher() {
        columnVoucherTour.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().tourNameProperty());
        columnVoucherTourDirection.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().tourDirectionProperty());
        columnVoucherClient.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().clientNameSurnameProperty());
        columnVoucherEmployee.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().employeeNameSurnameProperty());
        columnVoucherCost.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().costProperty().asObject());
        columnVoucherDate.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().dateProperty());
        conn = dbHandler.getConnection();
        if (workItem.getId() > 0) fillTableVoucher();
        tableVouchers.setItems(vouchers);
        tableVouchers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voucher>() {
            @Override
            public void changed(ObservableValue<? extends Voucher> observableValue, Voucher voucher, Voucher voucher1) {
            }
        });
    }
    private void fillTableVoucher(){
        try {
            Voucher voucher = new Voucher();
            Tour tour = new Tour();
            tour.setDirection("\'%\'");
            tour.setName("\'%\'");
            voucher.setDate(LocalDate.of(0001,01,01));

            fillTableVoucher(SQLRequests.selectVouchersForEmployee((conn), workItem.getId(), voucher, tour));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void fillTableVoucher(ResultSet localResultSet) throws SQLException {
        tableVouchers.getItems().clear();
        while (localResultSet.next()) {
            Voucher voucher = new Voucher();
            voucher.setId(localResultSet.getInt(Voucher.FIELD_ID));
            voucher.setIdTour(localResultSet.getInt(Voucher.FIELD_ID_TOUR));
            voucher.setIdClient(localResultSet.getInt(Voucher.FIELD_ID_CLIENT));
            voucher.setIdEmployee(localResultSet.getInt(Voucher.FIELD_ID_EMPLOYEE));
            voucher.setDate(localResultSet.getDate(Voucher.FIELD_DATE).toLocalDate());
            voucher.setCost(localResultSet.getInt(Voucher.FIELD_COST));


            ResultSet resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_TOUR, voucher.getIdTour());
            resultSetFromOtherTable.next();
            voucher.setTourName(resultSetFromOtherTable.getString(Tour.FIELD_NAME));

            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_HOTELS, resultSetFromOtherTable.getInt(Tour.FIELD_ID_HOTEL));
            resultSetFromOtherTable.next();
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_DIRECTION, resultSetFromOtherTable.getInt(Hotel.FIELD_ID_DIRETION));
            resultSetFromOtherTable.next();
            voucher.setTourDirection(resultSetFromOtherTable.getString(Direction.FIELD_NAME));

            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_CLIENT, voucher.getIdClient());
            resultSetFromOtherTable.next();
            voucher.setClientNameSurname(resultSetFromOtherTable.getString(Client.FIELD_SURNAME) + " " + resultSetFromOtherTable.getString(Client.FIELD_NAME));

            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_EMPLOYEE, voucher.getIdEmployee());
            resultSetFromOtherTable.next();
            voucher.setEmployeeNameSurname(resultSetFromOtherTable.getString(Employee.FIELD_SURNAME) + " " + resultSetFromOtherTable.getString(Employee.FIELD_NAME));
            vouchers.add(voucher);
        }
    }

    public void onSearchVoucher(ActionEvent actionEvent) {
        try {
            Voucher voucher = new Voucher();
            Tour tour = new Tour();
            String direction = textVoucherDirection.getText();
            String name = textVoucherName.getText();
            LocalDate date = dateVoucherDate.getValue();
            tour.setDirection("\'%" + direction + "%\'");
            tour.setName("\'%" + name + "%\'");
            if (date == null || date.toString() == null || date.toString().equals(""))
                date = LocalDate.of(0001, 01, 01);
            voucher.setDate(date);

            ResultSet set = SQLRequests.selectVouchersForEmployee((conn), workItem.getId(), voucher, tour);
            if (set.isBeforeFirst()) {
                fillTableVoucher(set);
            } else {
                MessageWindow.showInformation("Поиск путевок", "Результат поиска - 0 записей");
                vouchers.clear();
            }
            dateVoucherDate.setValue(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onVoucherClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1)
        {

        }
    }

    private boolean validation(String process){
        if (textSurname.getText().equals("") || textSurname.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введена Фамилия, либо больше 45 символов");
            return false;
        }
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Имя, либо больше 45 символов");
            return false;
        }
        if (textPatronymic.getText().equals("") || textPatronymic.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Отчество, либо больше 45 символов");
            return false;
        }
        if (textEmail.getText().equals("") || textEmail.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введен Адрес электронной почты, либо больше 45 символов");
            return false;
        }
        return true;
    }

    private void setWorkItem() {
        workItem.setName(textName.getText());
        workItem.setSurname(textSurname.getText());
        workItem.setPatronymic(textPatronymic.getText());
        workItem.setEmail(textEmail.getText());
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Employee();
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
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_EMPLOYEE, workItem.getId()) > 0) {
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
