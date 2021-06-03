package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mains.Main;
import mains.MessageWindow;
import mains.Validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerVoucher {

    public DatePicker dateDate;
    public Label labelCost;
    public Label labelTourName;
    public Label labelCustomer;
    public Label labelEmployee;
    public TextField textTouristSurname;
    public TextField textTouristPassport;
    public TableView<Client> tableTourist;
    public TableColumn<Client, String> columnTouristSurname;
    public TableColumn<Client, String> columnTouristName;
    public TableColumn<Client, String> columnTouristPatronymic;
    public TableColumn<Client, String> columnTouristPassport;
    public TableColumn<Client, String> columnTouristSex;
    public TableColumn<Client, LocalDate> columnTouristBirthDate;
    public TableColumn<Client, String> columnTouristPhone;
    public TableColumn<Client, String> columnTouristEmail;
    public TextField textClientSurname;
    public TextField textClientPassport;
    public TableView<Client> tableClients;
    public TableColumn<Client, String> columnClientSurname;
    public TableColumn<Client, String> columnClientName;
    public TableColumn<Client, String> columnClientPatronymic;
    public TableColumn<Client, String> columnClientPassport;
    public TableColumn<Client, String> columnClientSex;
    public TableColumn<Client, LocalDate> columnClientBirthDate;
    public TableColumn<Client, String> columnClientPhone;
    public TableColumn<Client, String> columnClientEmail;


    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Voucher workItem;
    private Client customer;
    private Employee employee;
    private Tour tour;

    private ObservableList<Client> tourists = FXCollections.observableArrayList();
    private ObservableList<Client> customers = FXCollections.observableArrayList();

    public void setDialogStage(Stage dialogStage, int idTour) {
        setDialogStage(dialogStage, new Voucher(idTour), idTour);
    }
    public void setDialogStage(Stage dialogStage, Voucher workItem, int idTour){
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        setTour(idTour);
        setCustomer(workItem.getIdClient());
        setEmployee(Main.getUser().getId());
        dateDate.setValue(workItem.getDate());
        if (workItem.getId() > 0)
            labelCost.setText(String.valueOf(workItem.getCost()));
        else
            labelCost.setText(String.valueOf(calculateFullCost()));
        if (dateDate.getValue().getYear() < LocalDate.now().getYear())
            dateDate.setValue(LocalDate.of(LocalDate.now().getYear(), 01, 01));

        setTableClients();
        setTableTourists();
    }

    public void setCustomer(int idCustomer){
        try {
            ResultSet set = SQLRequests.selectOneRow((conn), DbHandler.TABLE_NAME_CLIENT, idCustomer);
            if (set.isBeforeFirst()){
                set.next();
                Client client = new Client();
                client.setId(set.getInt(Client.FIELD_ID));
                client.setName(set.getString(Client.FIELD_NAME));
                client.setSurname(set.getString(Client.FIELD_SURNAME));
                client.setPatronymic(set.getString(Client.FIELD_PATRONYMIC));
                client.setPhone(set.getString(Client.FIELD_PHONE));
                client.setPassport(set.getString(Client.FIELD_PASSPORT));
                client.setSex(set.getString(Client.FIELD_SEX));
                client.setBirthDate(set.getDate(Client.FIELD_BIRTH_DATE).toLocalDate());
                client.setEmail(set.getString(Client.FIELD_EMAIL));
                this.customer = client;
                labelCustomer.setText(customer.getSurname() + " " + customer.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setCustomer(Client customer){
        this.customer = customer;
        labelCustomer.setText(customer.getSurname() + " " + customer.getName());
    }

    public void setEmployee(int idEmployee){
        try {
            ResultSet set = SQLRequests.selectOneRow((conn), DbHandler.TABLE_NAME_EMPLOYEE, idEmployee);
            if (set.isBeforeFirst()){
                set.next();
                Employee employee = new Employee();
                employee.setId(set.getInt(Employee.FIELD_ID));
                employee.setName(set.getString(Employee.FIELD_NAME));
                employee.setSurname(set.getString(Employee.FIELD_SURNAME));
                employee.setPatronymic(set.getString(Employee.FIELD_PATRONYMIC));
                employee.setEmail(set.getString(Employee.FIELD_EMAIL));
                this.employee = employee;
                labelEmployee.setText(this.employee.getSurname() + " " + this.employee.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setEmployee(Employee employee){
        this.employee = employee;
        labelEmployee.setText(this.employee.getSurname() + " " + this.employee.getName());
    }

    public void setTour(int idTour){
        try {
            ResultSet set = SQLRequests.selectOneRow((conn), DbHandler.TABLE_NAME_TOUR, idTour);
            if (set.isBeforeFirst()){
                set.next();
                Tour tour = new Tour();
                tour.setId(set.getInt(Tour.FIELD_ID));
                tour.setName(set.getString(Tour.FIELD_NAME));
                tour.setIdTourOperator(set.getInt(Tour.FIELD_ID_TOUR_OPERATOR));
                tour.setOffersCount(set.getInt(Tour.FIELD_OFFERS_COUNT));
                tour.setVouchersCount(set.getInt(Tour.FIELD_VOUCHERS_COUNT));
                tour.setIdHotel(set.getInt(Tour.FIELD_ID_HOTEL));
                tour.setPrice(set.getDouble(Tour.FIELD_PRICE));
                tour.setDateStart(set.getDate(Tour.FIELD_DATE_START).toLocalDate());
                tour.setDaysCount(set.getInt(Tour.FIELD_DAYS_COUNT));
                tour.setIdKind(set.getInt(Tour.FIELD_ID_KIND));
                tour.setIdCategory(set.getInt(Tour.FIELD_ID_CATEGORY));

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
                resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_CATEGORY, tour.getIdCategory());
                resultSetFromOtherTable.next();
                tour.setCategory(resultSetFromOtherTable.getString(Category.FIELD_NAME));
                this.tour = tour;
                labelTourName.setText(this.tour.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setTour(Tour tour){
        this.tour = tour;
        labelTourName.setText(this.tour.getName());
    }


    public double calculateFullCost(){
        try{
            ResultSet setTour = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_TOUR , tour.getId());
            if (setTour.isBeforeFirst()) {
                setTour.next();
                ResultSet setCategory = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_CATEGORY , setTour.getInt("CATEGORY"));
                setCategory.next();
                int daysCount = setTour.getInt("DAYS_COUNT");
                double price = setTour.getInt("PRICE");
                double discount = setCategory.getDouble("DISCOUNT");
                double addedValue = setCategory.getDouble("ADDED_VALUE");
                double cost = (price * daysCount) * discount + addedValue;
                return cost;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void setTableClients() {
        columnClientName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnClientSurname.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().surnameNumberProperty());
        columnClientPatronymic.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().patronymicProperty());
        columnClientSex.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().sexProperty());
        columnClientBirthDate.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().birthDateProperty());
        columnClientPassport.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().passportProperty());
        columnClientPhone.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().phoneProperty());
        columnClientEmail.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().emailProperty());
        conn = dbHandler.getConnection();
        fillTableClients();
        tableClients.setItems(customers);
        tableClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client client, Client client1) {
            }
        });
    }
    private void fillTableClients() {
        try {
            fillTableClients(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_CLIENT));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void fillTableClients(ResultSet localResultSet) throws SQLException {
        tableClients.getItems().clear();
        while (localResultSet.next()) {
            Client client = new Client();
            client.setId(localResultSet.getInt(Client.FIELD_ID));
            client.setName(localResultSet.getString(Client.FIELD_NAME));
            client.setSurname(localResultSet.getString(Client.FIELD_SURNAME));
            client.setPatronymic(localResultSet.getString(Client.FIELD_PATRONYMIC));
            client.setPhone(localResultSet.getString(Client.FIELD_PHONE));
            client.setPassport(localResultSet.getString(Client.FIELD_PASSPORT));
            client.setSex(localResultSet.getString(Client.FIELD_SEX));
            client.setBirthDate(localResultSet.getDate(Client.FIELD_BIRTH_DATE).toLocalDate());
            client.setEmail(localResultSet.getString(Client.FIELD_EMAIL));
            customers.add(client);
        }
    }

    public void setTableTourists() {
        columnTouristName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnTouristSurname.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().surnameNumberProperty());
        columnTouristPatronymic.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().patronymicProperty());
        columnTouristSex.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().sexProperty());
        columnTouristBirthDate.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().birthDateProperty());
        columnTouristPassport.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().passportProperty());
        columnTouristPhone.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().phoneProperty());
        columnTouristEmail.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().emailProperty());
        conn = dbHandler.getConnection();
        if (workItem.getId() > 0) fillTableTourists();
        tableTourist.setItems(tourists);
        tableTourist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client client, Client client1) {
            }
        });
    }
    private void fillTableTourists() {
        try {
            Client client = new Client();
            client.setSurname("\'%\'");
            client.setPassport("\'%\'");
            fillTableTourists(SQLRequests.selectTouristsForVoucher((conn), workItem.getId(), client));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void fillTableTourists(ResultSet localResultSet) throws SQLException {
        tableTourist.getItems().clear();
        while (localResultSet.next()) {
            Client client = new Client();
            client.setId(localResultSet.getInt(Client.FIELD_ID));
            client.setName(localResultSet.getString(Client.FIELD_NAME));
            client.setSurname(localResultSet.getString(Client.FIELD_SURNAME));
            client.setPatronymic(localResultSet.getString(Client.FIELD_PATRONYMIC));
            client.setPhone(localResultSet.getString(Client.FIELD_PHONE));
            client.setPassport(localResultSet.getString(Client.FIELD_PASSPORT));
            client.setSex(localResultSet.getString(Client.FIELD_SEX));
            client.setBirthDate(localResultSet.getDate(Client.FIELD_BIRTH_DATE).toLocalDate());
            client.setEmail(localResultSet.getString(Client.FIELD_EMAIL));
            tourists.add(client);
        }
    }


    public void onTouristSearch(ActionEvent actionEvent) {
        try {
            Client item = new Client();
            String surname = textTouristSurname.getText();
            String passport = textTouristPassport.getText();
            item.setSurname("\'%" + surname + "%\'");
            if (Validation.checkContainNotInt(passport))
                passport = "";
            item.setPassport("\'%" + passport + "%\'");
            ResultSet result = SQLRequests.selectTouristsForVoucher((conn), workItem.getId(), item);
            if (result.isBeforeFirst())
                fillTableTourists(result);
            else MessageWindow.showInformation("Поиск туристов", "туристов с фамилией содержащей (" +
                    surname + ") и паспортом содержащим (" + passport + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void onClientSearch(ActionEvent actionEvent) {
        try {
            Client item = new Client();
            String surname = textClientSurname.getText();
            String passport = textClientPassport.getText();
            item.setSurname("\'%" + surname + "%\'");
            if (Validation.checkContainNotInt(passport))
                passport = "";
            item.setPassport("\'%" + passport + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableClients(result);
            else MessageWindow.showInformation("Поиск клиентов", "клиентов с фамилией содержащей (" +
                    surname + ") и паспортом содержащим (" + passport + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onClientMenuAddAsCustomer(ActionEvent actionEvent) {
        setCustomer(tableClients.getSelectionModel().getSelectedItem());
    }
    public void onClientMenuAddAsTourist(ActionEvent actionEvent) {
        if (workItem.getId() > 0)
        {
            try {
                if (tour.getOffersCount() > tour.getVouchersCount()) {
                    int result = SQLRequests.addOneRow(conn, tableClients.getSelectionModel().getSelectedItem().getId(), workItem.getId());
                    if (result > 0) {
                        MessageWindow.showInformation("Добавление туриста", "Турист добавлен в путевку");
                        tour.setVouchersCount(tour.getVouchersCount() + 1);
                    } else
                        MessageWindow.showError("Добавление туриста", "Невозможно добавить туриста");
                }
                else {
                    MessageWindow.showError("Добавление туриста", "Невозможно добавить туриста, т.к. в туре закончились предложения");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            MessageWindow.showError("Добавление туриста", "Невозможно добавить туриста, т.к. путевка еще не оформлена");
        }
    }
    public void onTouristMenuDelete(ActionEvent actionEvent) {
        if (workItem.getId() > 0)
        {
            try {
                int result = SQLRequests.deleteOneTourist(conn, tableTourist.getSelectionModel().getSelectedItem().getId(), workItem.getId());
                if (result > 0) {
                    MessageWindow.showInformation("Удаление туриста", "Турист удален из путевки");
                    tour.setVouchersCount(tour.getVouchersCount() - 1);
                }
                else
                    MessageWindow.showError("Удаление туриста", "Невозможно удалить туриста");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            MessageWindow.showError("Удаление туриста", "Невозможно удалить туриста, т.к. путевка еще не оформлена");
        }
    }

    private boolean validation(String process) {
        if (dateDate.getValue().toString().equals("") || dateDate.getValue() == null) {
            MessageWindow.showError(process, "Не введено значение даты оформления путевки");
            return false;
        }
        if (labelCustomer.getText().equals("-") || labelCustomer.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран заказчик путевки");
            return false;
        }
        if (labelEmployee.getText().equals("-") || labelEmployee.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран оформитель путевки");
            return false;
        }
        return true;
    }

    private void setWorkItem() {
        workItem.setDate(dateDate.getValue());
        workItem.setIdClient(customer.getId());
        workItem.setIdEmployee(employee.getId());
        workItem.setIdTour(tour.getId());
        workItem.setIdEmployee(employee.getId());
        workItem.setIdClient(customer.getId());
    }

    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
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
                if (SQLRequests.deleteOneVoucher(conn, workItem) > 0) {

                    MessageWindow.showInformation("Удаление", "Путевка удалена");
                    dialogStage.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            MessageWindow.showError("Удаление", "Отсутсвует запись для удалениея");
        }
    }
}
