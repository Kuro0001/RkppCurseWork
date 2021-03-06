package controllersAll;

import dbClasses.DbHandler;
import dbClasses.SQLRequests;
import dbClasses.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mains.MessageWindow;
import mains.Main;
import mains.Validation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * model for Main window
 * @author Kuro
 * @version 1.0
 */
public class ControllerMainWindow {
    public Label labelLog;
    public Label labelAuthorization;
    public TableView<Client> tableClients;
    public TableColumn<Client, String> columnClientSurname;
    public TableColumn<Client, String> columnClientName;
    public TableColumn<Client, String> columnClientPatronymic;
    public TableColumn<Client, String> columnClientPassport;
    public TableColumn<Client, String> columnClientSex;
    public TableColumn<Client, LocalDate> columnClientBirthDate;
    public TableColumn<Client, String> columnClientPhone;
    public TableColumn<Client, String> columnClientEmail;
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
    public TableView<TourOperator> tableTourOperators;
    public TableColumn<TourOperator, String> columnTourOperatorName;
    public TableColumn<TourOperator, String> columnTourOperatorPhone;
    public TableColumn<TourOperator, String> columnTourOperatorNumber;
    public TableColumn<TourOperator, String> columnTourOperatorEmail;
    public TableView<Kind> tableKinds;
    public TableColumn<Kind, String> columnKindName;
    public TableView<Category> tableCategories;
    public TableColumn<Category, String> columnCategoryName;
    public TableColumn<Category, Double> columnCategoryAddedValue;
    public TableColumn<Category, Double> columnCategoryDiscount;
    public TableView<Direction> tableDirections;
    public TableColumn<Direction, String> columnDirectionsName;
    public TableView<Hotel> tableHotels;
    public TableColumn<Hotel, String> columnHotelName;
    public TableColumn<Hotel, String> columnHotelAddress;
    public TableColumn<Hotel, String> columnHotelDirection;
    public TableView<Voucher> tableVouchers;
    public TableColumn<Voucher, String> columnVoucherTour;
    public TableColumn<Voucher, String> columnVoucherTourDirection;
    public TableColumn<Voucher, String> columnVoucherClient;
    public TableColumn<Voucher, String> columnVoucherEmployee;
    public TableColumn<Voucher, Double> columnVoucherCost;
    public TableColumn<Voucher, LocalDate> columnVoucherDate;
    public TableView<Employee> tableEmployee;
    public TableColumn<Employee, String> columnEmployeeSurname;
    public TableColumn<Employee, String> columnEmployeeName;
    public TableColumn<Employee, String> columnEmployeePatronymic;
    public TableColumn<Employee, String> columnEmployeeEmail;
    public TableView<Result> tableResults;
    public TableColumn<Result, String> columnResultNameSurnamePatronymic;
    public TableColumn<Result, Integer> columnResultVouchers;
    public TableColumn<Result, Double> columnResultSum;
    public DatePicker datePickerDateStartForResult;
    public DatePicker datePickerDateEndForResult;
    public TextField textClientSurname;
    public TextField textClientPassport;
    public TextField textTourKind;
    public TextField textTourPrice;
    public TextField textTourDirection;
    public DatePicker dateTourDate;
    public TextField textTourOperatorName;
    public TextField textTourOperatorUniqueNumber;
    public TextField textKindName;
    public TextField textCategoryName;
    public TextField textDirectionName;
    public TextField textHotelName;
    public TextField textHotelDirection;
    public TextField textVoucherName;
    public TextField textVoucherDirection;
    public DatePicker dateVoucherDate;
    public TextField textEmployeeSurname;


    private ObservableList<Kind> kinds = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Direction> directions = FXCollections.observableArrayList();
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private ObservableList<TourOperator> tourOperators = FXCollections.observableArrayList();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
    private ObservableList<Voucher> vouchers = FXCollections.observableArrayList();
    private ObservableList<Result> results = FXCollections.observableArrayList();
    Connection conn;
    DbHandler dbHandler;

    /**
     * initialize methode to set data in table after authorisation
     */
    @FXML
    void initialize() {
        authorisation();
        setTableKind();
        setTableCategories();
        setTableDirections();
        setTableHotels();
        setTableTourOperators();
        setTableEmployees();
        setTableClients();
        setTableTour();
        setTableVoucher();
        setTableResults();
    }
    /**
     * set data into table
     */
    public void setTableKind() {
        columnKindName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        fillTableKinds();
        tableKinds.setItems(kinds);
        tableKinds.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Kind>() {
            @Override
            public void changed(ObservableValue<? extends Kind> observableValue, Kind kind, Kind kind1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableKinds() {
        try {
            fillTableKinds(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_KIND));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableKinds(ResultSet localResultSet) throws SQLException {
        tableKinds.getItems().clear();
        while (localResultSet.next()) {
            Kind kind = new Kind();
            kind.setId(localResultSet.getInt(Kind.FIELD_ID));
            kind.setName(localResultSet.getString(Kind.FIELD_NAME));
            kinds.add(kind);
            labelLog.setText("???????????????????? ?????????????? Kinds (???????? ??????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableCategories() {
        columnCategoryName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnCategoryAddedValue.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().addedValueProperty().asObject());
        columnCategoryDiscount.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().discountProperty().asObject());
        conn = dbHandler.getConnection();
        fillTableCategories();
        tableCategories.setItems(categories);
        tableCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observableValue, Category category, Category category1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableCategories() {
        try {
            fillTableCategories(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_CATEGORY));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableCategories(ResultSet localResultSet) throws SQLException {
        tableCategories.getItems().clear();
        while (localResultSet.next()) {
            Category category = new Category();
            category.setId(localResultSet.getInt(Category.FIELD_ID));
            category.setName(localResultSet.getString(Category.FIELD_NAME));
            category.setAddedValue(localResultSet.getDouble(Category.FIELD_ADDED_VALUE));
            category.setDiscount(localResultSet.getDouble(Category.FIELD_DISCOUNT));
            categories.add(category);
            labelLog.setText("???????????????????? ?????????????? Categories (?????????????????? ??????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableDirections() {
        columnDirectionsName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        conn = dbHandler.getConnection();
        fillTableDirections();
        tableDirections.setItems(directions);
        tableDirections.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Direction>() {
            @Override
            public void changed(ObservableValue<? extends Direction> observableValue, Direction direction, Direction direction1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableDirections() {
        try {
            fillTableDirections(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_DIRECTION));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableDirections(ResultSet localResultSet) throws SQLException {
        tableDirections.getItems().clear();
        while (localResultSet.next()) {
            Direction direction = new Direction();
            direction.setId(localResultSet.getInt(Direction.FIELD_ID));
            direction.setName(localResultSet.getString(Direction.FIELD_NAME));
            directions.add(direction);
            labelLog.setText("???????????????????? ?????????????? Directions (?????????????????????? ??????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableHotels() {
        columnHotelName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnHotelAddress.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().addressProperty());
        columnHotelDirection.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().directionProperty());
        conn = dbHandler.getConnection();
        fillTableHotels();
        tableHotels.setItems(hotels);
        tableHotels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hotel>() {
            @Override
            public void changed(ObservableValue<? extends Hotel> observableValue, Hotel hotel, Hotel hotel1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableHotels() {
        try {
            fillTableHotels(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_HOTELS));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableHotels(ResultSet localResultSet) throws SQLException {
        tableHotels.getItems().clear();
        while (localResultSet.next()) {
            Hotel hotel = new Hotel();
            hotel.setId(localResultSet.getInt(Hotel.FIELD_ID));
            hotel.setName(localResultSet.getString(Hotel.FIELD_NAME));
            hotel.setAddress(localResultSet.getString(Hotel.FIELD_ADDRESS));
            hotel.setIdDirection(localResultSet.getInt(Hotel.FIELD_ID_DIRETION));
            ResultSet resultSetFromTableDirection = SQLRequests.selectOneRow
                    (conn, DbHandler.TABLE_NAME_DIRECTION, localResultSet.getInt(Hotel.FIELD_ID_DIRETION));
            resultSetFromTableDirection.next();
            hotel.setDirection(resultSetFromTableDirection.getString(Direction.FIELD_NAME));
            hotels.add(hotel);
            labelLog.setText("???????????????????? ?????????????? Hotels (??????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableTourOperators() {
        columnTourOperatorName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnTourOperatorNumber.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().uniqueNumberProperty());
        columnTourOperatorPhone.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().phoneProperty());
        columnTourOperatorEmail.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().emailProperty());
        conn = dbHandler.getConnection();
        fillTableTourOperators();
        tableTourOperators.setItems(tourOperators);
        tableTourOperators.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TourOperator>() {
            @Override
            public void changed(ObservableValue<? extends TourOperator> observableValue, TourOperator tourOperator, TourOperator tourOperator1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableTourOperators() {
        try {
            fillTableTourOperators(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_TOUR_OPERATOR));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableTourOperators(ResultSet localResultSet) throws SQLException {
        tableTourOperators.getItems().clear();
        while (localResultSet.next()) {
            TourOperator tourOperator = new TourOperator();
            tourOperator.setId(localResultSet.getInt(TourOperator.FIELD_ID));
            tourOperator.setName(localResultSet.getString(TourOperator.FIELD_NAME));
            tourOperator.setUniqueNumber(localResultSet.getString(TourOperator.FIELD_UNIQUE_NUMBER));
            tourOperator.setPhone(localResultSet.getString(TourOperator.FIELD_PHONE));
            tourOperator.setEmail(localResultSet.getString(TourOperator.FIELD_EMAIL));
            tourOperators.add(tourOperator);
            labelLog.setText("???????????????????? ?????????????? Tour operators (??????????????????????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableEmployees() {
        columnEmployeeName.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameProperty());
        columnEmployeeSurname.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().surnameNumberProperty());
        columnEmployeePatronymic.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().patronymicProperty());
        columnEmployeeEmail.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().emailProperty());
        conn = dbHandler.getConnection();
        fillTableEmployees();
        tableEmployee.setItems(employees);
        tableEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee employee, Employee employee1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableEmployees() {
        try {
            fillTableEmployees(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_EMPLOYEE));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableEmployees(ResultSet localResultSet) throws SQLException {
        tableEmployee.getItems().clear();
        while (localResultSet.next()) {
            Employee employee = new Employee();
            employee.setId(localResultSet.getInt(Employee.FIELD_ID));
            employee.setName(localResultSet.getString(Employee.FIELD_NAME));
            employee.setSurname(localResultSet.getString(Employee.FIELD_SURNAME));
            employee.setPatronymic(localResultSet.getString(Employee.FIELD_PATRONYMIC));
            employee.setEmail(localResultSet.getString(Employee.FIELD_EMAIL));
            employees.add(employee);
            labelLog.setText("???????????????????? ?????????????? Employee (???????????????????? ????????????????)");
        }
    }
    /**
     * set data into table
     */
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
        tableClients.setItems(clients);
        tableClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client client, Client client1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableClients() {
        try {
            fillTableClients(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_CLIENT));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
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
            clients.add(client);
            labelLog.setText("???????????????????? ?????????????? Clients (????????????????)");
        }
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
        fillTableTour();
        tableTours.setItems(tours);
        tableTours.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour tour1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableTour() {
        try {
            fillTableTour(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_TOUR));
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
            resultSetFromOtherTable = SQLRequests.selectOneRow(conn, DbHandler.TABLE_NAME_CATEGORY, tour.getIdCategory());
            resultSetFromOtherTable.next();
            tour.setCategory(resultSetFromOtherTable.getString(Category.FIELD_NAME));
            tours.add(tour);
            labelLog.setText("???????????????????? ?????????????? Tours (??????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableVoucher() {
        columnVoucherTour.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().tourNameProperty());
        columnVoucherTourDirection.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().tourDirectionProperty());
        columnVoucherClient.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().clientNameSurnameProperty());
        columnVoucherEmployee.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().employeeNameSurnameProperty());
        columnVoucherCost.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().costProperty().asObject());
        columnVoucherDate.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().dateProperty());
        conn = dbHandler.getConnection();
        fillTableVoucher();
        tableVouchers.setItems(vouchers);
        tableVouchers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voucher>() {
            @Override
            public void changed(ObservableValue<? extends Voucher> observableValue, Voucher voucher, Voucher voucher1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableVoucher() {
        try {
            fillTableVoucher(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_VOUCHER));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
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
            labelLog.setText("???????????????????? ?????????????? Vouchers (??????????????)");
        }
    }
    /**
     * set data into table
     */
    public void setTableResults() {
        columnResultNameSurnamePatronymic.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().nameSurnamePatronymicProperty());
        columnResultVouchers.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().vouchersCountProperty().asObject());
        columnResultSum.setCellValueFactory(StringCellDataFeatures -> StringCellDataFeatures.getValue().cashSumProperty().asObject());
        tableResults.setItems(results);
        tableResults.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Result>() {
            @Override
            public void changed(ObservableValue<? extends Result> observableValue, Result result, Result result1) {
            }
        });
        labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
    }
    /**
     * fill table
     */
    private void fillTableResults() {
        try {
            fillTableResults(SQLRequests.selectCalculateResult((conn), "20000101", "20000102"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * fill table
     */
    private void fillTableResults(ResultSet localResultSet) throws SQLException {
        tableResults.getItems().clear();
        while (localResultSet.next()) {
            Result result = new Result();
            result.setId(localResultSet.getInt(Result.FIELD_ID));
            String fullName = localResultSet.getString(Result.FIELD_SURNAME) + " " +
                    localResultSet.getString(Result.FIELD_NAME) + " " +
                    localResultSet.getString(Result.FIELD_PATRONYMIC);
            result.setNameSurnamePatronymic(fullName);
            result.setVouchersCount(localResultSet.getInt(Result.FIELD_VOUCHERS_COUNT));
            result.setCashSum(localResultSet.getInt(Result.FIELD_CASH_SUM));
            results.add(result);
            labelLog.setText("???????????????????? ?????????????? Results (?????????????????????? ????????????)");
        }
    }

    /**
     * authorisation method in separated window
     */
    private void authorisation() {
        try {
            labelLog.setText("???????????????????????? ??????????????????????");
            Main.setUser(new Employee());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/sampleAuthorisation.fxml"));
            Parent page = loader.load();
            Stage addStage = new Stage();
            addStage.setTitle("?????????????????????? ????????????????????????");
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            addStage.setScene(scene);
            ControllerAuthorisation controller = loader.getController();
            controller.setDialogStage(addStage, Main.getUser());
            addStage.setMinWidth(200);
            addStage.setMinHeight(220);
            addStage.showAndWait();
            if (Main.getUser().getName() != "") {
                labelAuthorization.setText("????????????????????????: " + Main.getUser().getSurname() + " " + Main.getUser().getName());
            }
            else {
                System. exit(0);
            }
        } catch (IOException ex) {
            MessageWindow.showError("??????????????????????", ex.getMessage());
        }
    }

    /**
     * in menu button to refresh tables
     */
    public void onMenuRefreshTables(ActionEvent actionEvent) {
        try {
            fillTableKinds(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_KIND));
            fillTableCategories(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_CATEGORY));
            fillTableDirections(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_DIRECTION));
            fillTableHotels(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_HOTELS));
            fillTableTourOperators(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_TOUR_OPERATOR));
            fillTableEmployees(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_EMPLOYEE));
            fillTableClients(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_CLIENT));
            fillTableTour(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_TOUR));
            fillTableVoucher(SQLRequests.selectAllInTable((conn), DbHandler.TABLE_NAME_VOUCHER));
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * in menu button to see personal information
     */
    public void onMenuAccountEnter(ActionEvent actionEvent) {
        employeeAccountEnter(Main.getUser());
    }
    /**
     * in menu button to exit from account
     */
    public void onMenuExit(ActionEvent actionEvent) {
        Main.setUser(null);
        authorisation();
    }

    /**
     * button event to calculate result of work per (Employee)s
     */
    public void onResultCalculate(ActionEvent actionEvent) {
        try {
            String date1 = datePickerDateStartForResult.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
            String date2 = datePickerDateEndForResult.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
            fillTableResults(SQLRequests.selectCalculateResult((conn), date1, date2));
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Click on table to open special window
     */
    public void onKindClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Kind selectedItem = tableKinds.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Kind("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleKind.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ???????? - " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ???????? - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerKind controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(300);
                addStage.setMinHeight(150);
                addStage.showAndWait();
                fillTableKinds();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            }catch (IOException ex){
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onCategoryClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Category selectedItem = tableCategories.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Category("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleCategory.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ?????????????????? - " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ?????????????????? - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerCategory controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(300);
                addStage.setMinHeight(150);
                addStage.showAndWait();
                fillTableCategories();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onDirectionClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Direction selectedItem = tableDirections.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Direction("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleDirection.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ?????????????????????? - " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ?????????????????????? - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerDirection controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(300);
                addStage.setMinHeight(150);
                addStage.showAndWait();
                fillTableDirections();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onHotelClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Hotel selectedItem = tableHotels.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Hotel("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleHotel.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ?????????? - " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ?????????? - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerHotel controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(300);
                addStage.setMinHeight(150);
                addStage.showAndWait();
                fillTableHotels();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }


    /**
     * Click on table to open special window
     */
    public void onClientClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Client selectedItem = tableClients.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Client("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleClient.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ?????????????? - " + selectedItem.getSurname()  + " " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ?????????????? - " + selectedItem.getSurname()  + " " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerClient controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(950);
                addStage.setMinHeight(500);
                addStage.showAndWait();
                fillTableClients();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onTourOperatorClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                TourOperator selectedItem = tableTourOperators.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new TourOperator("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleTourOperator.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ???????????????????????? - " +  selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ???????????????????????? - " + selectedItem.getName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerTourOperator controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem);
                addStage.setMinWidth(950);
                addStage.setMinHeight(500);
                addStage.showAndWait();
                fillTableTourOperators();
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onEmployeeClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            employeeAccountEnter(tableEmployee.getSelectionModel().getSelectedItem());
        }
    }
    /**
     * method to open special window
     */
    private void employeeAccountEnter(Employee selectedItem){
        try {
            if (selectedItem == null)
                selectedItem = new Employee("?????????? ????????????");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/views/sampleEmployee.fxml"));
            Parent page = loader.load();
            Stage addStage = new Stage();
            labelLog.setText("???????????? ?? ?????????????? ???????????????????????? - " + selectedItem.getSurname()  + " " + selectedItem.getName());
            addStage.setTitle("???????????? ?? ?????????????? ???????????????????????? - " + selectedItem.getSurname()  + " " + selectedItem.getName());
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.initOwner(Main.getPrimaryStage());
            Scene scene = new Scene(page);
            addStage.setScene(scene);
            ControllerEmployee controller = loader.getController();
            controller.setDialogStage(addStage, selectedItem);
            addStage.setMinWidth(1000);
            addStage.setMinHeight(400);
            addStage.showAndWait();
            fillTableEmployees();
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (IOException ex) {
            MessageWindow.showError("???????????????? ????????", ex.getMessage());
        }
    }
    /**
     * Click on table to open special window
     */
    public void onTourClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                Tour selectedItem = tableTours.getSelectionModel().getSelectedItem();
                if (selectedItem == null)
                    selectedItem = new Tour("?????????? ????????????");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleTour.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                labelLog.setText("???????????? ?? ?????????????? ???????? - " + selectedItem.getName());
                addStage.setTitle("???????????? ?? ?????????????? ???????? - " + selectedItem.getName());
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
                labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }
    /**
     * Click on table to open special window
     */
    public void onVoucherClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            try {
                if (tableVouchers.getSelectionModel().getSelectedItem() != null) {
                    Voucher selectedItem = tableVouchers.getSelectionModel().getSelectedItem();
                    if (selectedItem == null)
                        selectedItem = new Voucher();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/views/sampleVoucher.fxml"));
                    Parent page = loader.load();
                    Stage addStage = new Stage();
                    labelLog.setText("???????????? ?? ?????????????? ?????????????? ???? ???????? - " + selectedItem.getTourName());
                    addStage.setTitle("???????????? ?? ?????????????? ?????????????? ???? ???????? - " + selectedItem.getTourName());
                    addStage.initModality(Modality.APPLICATION_MODAL);
                    addStage.initOwner(Main.getPrimaryStage());
                    Scene scene = new Scene(page);
                    addStage.setScene(scene);
                    ControllerVoucher controller = loader.getController();
                    controller.setDialogStage(addStage, selectedItem, selectedItem.getIdTour());
                    addStage.setMinWidth(900);
                    addStage.setMinHeight(500);
                    addStage.showAndWait();
                    fillTableVoucher();
                    labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
                }
            } catch (IOException ex) {
                MessageWindow.showError("???????????????? ????????", ex.getMessage());
            }
        }
    }

    /**
     * method to search in table
     */
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
            else MessageWindow.showInformation("?????????? ????????????????", "???????????????? ?? ???????????????? ???????????????????? (" +
                    surname + ") ?? ?????????????????? ???????????????????? (" + passport + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onTourSearch(ActionEvent actionEvent) {
        try {
            Tour item = new Tour();
            String direction = textTourDirection.getText();
            String kind = textTourKind.getText();
            String price = textTourPrice.getText();
            LocalDate date = dateTourDate.getValue();
            item.setDirection("\'%" + direction + "%\'");
            item.setKind("\'%" + kind + "%\'");
            if (Validation.checkContainNotDouble(price))
                price = String.valueOf(Integer.MAX_VALUE);
            item.setPrice(Double.parseDouble(price));
            if(date == null || date.toString() == null || date.toString().equals("")) date = LocalDate.of(0001,01,01);
            item.setDateStart(date);
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableTour(result);
            else MessageWindow.showInformation("?????????? ??????????", "?????????? ?? ???????????????????????? ???????????????????? (" +
                    direction + "), ?????????? ???????????????????? (" + kind + "), ?????????? ???? (" + price + ") ?? ?????????? ???? (" + date.toString() + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            dateTourDate.setValue(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onTourOperatorSearch(ActionEvent actionEvent) {
        try {
            TourOperator item = new TourOperator();
            String name = textTourOperatorName.getText();
            String uniqueNumber = textTourOperatorUniqueNumber.getText();
            item.setName("\'%" + name + "%\'");
            item.setUniqueNumber("\'%" + uniqueNumber + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableTourOperators(result);
            else MessageWindow.showInformation("?????????? ??????????????????????????????", "?????????????????????????????? ?? ?????????????????????????? ???????????????? ???????????????????? (" +
                    name + ") ?? ???????????????????? ?????????????? ???????????????????? (" + uniqueNumber + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onKindSearch(ActionEvent actionEvent) {
        try {
            Kind item = new Kind();
            String name = textKindName.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableKinds(result);
            else MessageWindow.showInformation("?????????? ?????????? ??????????", "???????????? ?????????? ?? ?????????????????????????? ???????????????????? (" +
                    name + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onCategorySearch(ActionEvent actionEvent) {
        try {
            Category item = new Category();
            String name = textCategoryName.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableCategories(result);
            else MessageWindow.showInformation("?????????? ?????????????????? ??????????", "?????????????????? ?????????? ?? ?????????????????????????? ???????????????????? (" +
                    name + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onDirectionSearch(ActionEvent actionEvent) {
        try {
            Direction item = new Direction();
            String name = textDirectionName.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableDirections(result);
            else MessageWindow.showInformation("?????????? ?????????????????????? ??????????", "?????????????????????? ?????????? ?? ?????????????????????????? ???????????????????? (" +
                    name + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onHotelSearch(ActionEvent actionEvent) {
        try {
            Hotel item = new Hotel();
            String name = textHotelName.getText();
            String direction = textHotelDirection.getText();
            item.setName("\'%" + name + "%\'");
            item.setDirection("\'%" + direction + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableHotels(result);
            else MessageWindow.showInformation("?????????? ????????????", "???????????? ?? ?????????????????????????? ???????????????????? (" +
                    name + ") ?? ???????????????????????? ???????????????????? (" + direction + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onVoucherSearch(ActionEvent actionEvent) {
        try {
            Voucher item = new Voucher();
            Tour subItem = new Tour();
            String direction = textVoucherDirection.getText();
            String name = textVoucherName.getText();
            LocalDate date = dateVoucherDate.getValue();
            subItem.setDirection("\'%" + direction + "%\'");
            subItem.setName("\'%" + name + "%\'");
            if(date == null || date.toString() == null || date.toString().equals("")) date = LocalDate.of(0001,01,01);
            item.setDate(date);
            ResultSet result = SQLRequests.selectSearch((conn), item, subItem);
            if (result.isBeforeFirst())
                fillTableVoucher(result);
            else MessageWindow.showInformation("?????????? ??????????????", "?????????????? ?? ???????????????????????? ???????????????????? (" +
                    direction + "), ???????????????????????? ???????????????????? (" + name + ") ?? ?????????? ?? (" + date.toString() + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
            dateVoucherDate.setValue(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * method to search in table
     */
    public void onEmployeeSearch(ActionEvent actionEvent) {
        try {
            Employee item = new Employee();
            String surname = textEmployeeSurname.getText();
            item.setSurname("\'%" + surname + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableEmployees(result);
            else MessageWindow.showInformation("?????????? ??????????????????????", "?????????????????????? ?? ???????????????? ???????????????????? (" +
                    surname + ") ???? ??????????????");
            labelLog.setText("???????????????? ???????????????? ????????????????????????. ???????????????? ????????????.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
