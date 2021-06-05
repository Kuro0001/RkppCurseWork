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
import javafx.scene.control.*;
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
 * model for table Tour
 * @author Kuro
 * @version 1.0
 */
public class ControllerTour {

    public TextField textName;
    public TextField textOffersCount;
    public DatePicker dateDateStart;
    public TextField textDaysCount;
    public TextField textPrice;
    public Label labelVouchersCount;
    public Label lableHotel;
    public Label labelTourOperator;
    public Label labelDirection;
    public Label labelKind;
    public Label labelCategory;
    public TextField textTourOperatorName;
    public TextField textTourOperatorUniqueNumber;
    public TextField textHotelName;
    public TextField textHotelDirection;
    public TextField textKindName;
    public TextField textCategoryName;
    public DatePicker dateVoucherDate;

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


    private Stage dialogStage;
    Connection conn;
    DbHandler dbHandler;
    private Tour workItem;
    private Hotel hotel;
    private TourOperator tourOperator;
    private Kind kind;
    private Category category;

    private ObservableList<Kind> kinds = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private ObservableList<TourOperator> tourOperators = FXCollections.observableArrayList();
    private ObservableList<Voucher> vouchers = FXCollections.observableArrayList();

    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage) {
        setDialogStage(dialogStage, new Tour("новая запись"));
    }
    /**
     * set work item at start
     */
    public void setDialogStage(Stage dialogStage, Tour workItem) {
        this.dialogStage = dialogStage;
        dbHandler = new DbHandler();
        conn = dbHandler.getConnection();
        this.workItem = workItem;

        textName.setText(workItem.getName());
        textOffersCount.setText(String.valueOf(workItem.getOffersCount()));
        dateDateStart.setValue(workItem.getDateStart());
        if (dateDateStart.getValue().getYear() < LocalDate.now().getYear())
            dateDateStart.setValue(LocalDate.of(LocalDate.now().getYear(), 01, 01));
        textDaysCount.setText(String.valueOf(workItem.getDaysCount()));
        labelTourOperator.setText(workItem.getTourOperator());
        labelVouchersCount.setText(String.valueOf(workItem.getVouchersCount()));
        lableHotel.setText(workItem.getHotel());
        labelDirection.setText(workItem.getDirection());
        textPrice.setText(String.valueOf(workItem.getPrice()));
        labelKind.setText(workItem.getKind());
        labelCategory.setText(workItem.getCategory());

        setTableKind();
        setTableCategories();
        setTableHotels();
        setTableTourOperators();
        setTableVoucher();
    }

    /**
     * method to refresh Tour after edit/add/delete vouchers
     */
    public void refreshTour(){
        try {
            ResultSet set = SQLRequests.selectOneRow((conn), DbHandler.TABLE_NAME_TOUR, workItem.getId());
            if(set.isBeforeFirst()) {
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

                workItem = tour;

                textName.setText(workItem.getName());
                textOffersCount.setText(String.valueOf(workItem.getOffersCount()));
                dateDateStart.setValue(workItem.getDateStart());
                if (dateDateStart.getValue().getYear() < LocalDate.now().getYear())
                    dateDateStart.setValue(LocalDate.of(LocalDate.now().getYear(), 01, 01));
                textDaysCount.setText(String.valueOf(workItem.getDaysCount()));
                labelTourOperator.setText(workItem.getTourOperator());
                labelVouchersCount.setText(String.valueOf(workItem.getVouchersCount()));
                lableHotel.setText(workItem.getHotel());
                labelDirection.setText(workItem.getDirection());
                textPrice.setText(String.valueOf(workItem.getPrice()));
                labelKind.setText(workItem.getKind());
                labelCategory.setText(workItem.getCategory());
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * set work item at start
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
    }
    /**
     * set data into table
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
        if (workItem.getId() > 0) fillTableVoucher();
        tableVouchers.setItems(vouchers);
        tableVouchers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voucher>() {
            @Override
            public void changed(ObservableValue<? extends Voucher> observableValue, Voucher voucher, Voucher voucher1) {
            }
        });
    }
    /**
     * fill table
     */
    private void fillTableVoucher() {
        try {
            Voucher voucher = new Voucher();
            voucher.setDate(LocalDate.of(0001, 01, 01));

            fillTableVoucher(SQLRequests.selectVouchersForTour((conn), voucher, workItem));
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
        }
    }

    /**
     * search in Table
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
            else
                MessageWindow.showInformation("Поиск туровоператоров", "туровоператоров с наименованием компании содержащим (" +
                        name + ") и уникальным номером содержащим (" + uniqueNumber + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * search in Table
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
            else MessageWindow.showInformation("Поиск отелей", "отелей с наименованием содержащим (" +
                    name + ") и направлением содержащим (" + direction + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * search in Table
     */
    public void onKindSearch(ActionEvent actionEvent) {
        try {
            Kind item = new Kind();
            String name = textKindName.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableKinds(result);
            else MessageWindow.showInformation("Поиск видов туров", "видров туров с наименованием содержащим (" +
                    name + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * search in Table
     */
    public void onCategorySearch(ActionEvent actionEvent) {
        try {
            Category item = new Category();
            String name = textCategoryName.getText();
            item.setName("\'%" + name + "%\'");
            ResultSet result = SQLRequests.selectSearch((conn), item);
            if (result.isBeforeFirst())
                fillTableCategories(result);
            else MessageWindow.showInformation("Поиск категорий туров", "категорий туров с наименованием содержащим (" +
                    name + ") не найдено");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * search in Table
     */
    public void onVoucherSearch(ActionEvent actionEvent) {
        try {
            Voucher voucher = new Voucher();
            voucher.setDate(dateVoucherDate.getValue());

            fillTableVoucher(SQLRequests.selectVouchersForTour((conn), voucher, workItem));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * select item in table
     */
    public void onHotelClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            hotel = tableHotels.getSelectionModel().getSelectedItem();
            lableHotel.setText(hotel.getName());
            labelDirection.setText(hotel.getDirection());
        }
    }
    /**
     * select item in table
     */
    public void onKindClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            kind = tableKinds.getSelectionModel().getSelectedItem();
            labelKind.setText(kind.getName());
        }
    }
    /**
     * select item in table
     */
    public void onCategoryClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            category = tableCategories.getSelectionModel().getSelectedItem();
            labelCategory.setText(category.getName());
        }
    }
    /**
     * select item in table
     */
    public void onTourOperatorClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() > 1) {
            tourOperator = tableTourOperators.getSelectionModel().getSelectedItem();
            labelTourOperator.setText(tourOperator.getName());
        }
    }

    /**
     * make new Voucher for current Tour
     */
    public void onAddVoucher(ActionEvent actionEvent) {
        if (workItem.getId() > 0 && workItem.getOffersCount() > workItem.getVouchersCount()) {
            try {
                Voucher selectedItem = new Voucher();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/views/sampleVoucher.fxml"));
                Parent page = loader.load();
                Stage addStage = new Stage();
                addStage.setTitle("Работа с записью путевки по туру - " + selectedItem.getTourName());
                addStage.initModality(Modality.APPLICATION_MODAL);
                addStage.initOwner(Main.getPrimaryStage());
                Scene scene = new Scene(page);
                addStage.setScene(scene);
                ControllerVoucher controller = loader.getController();
                controller.setDialogStage(addStage, selectedItem, workItem.getId());
                addStage.setMinWidth(900);
                addStage.setMinHeight(500);
                addStage.showAndWait();
                fillTableVoucher();
                refreshTour();
            } catch (IOException ex) {
                MessageWindow.showError("Открытие окна", ex.getMessage());
            }
        } else {
            MessageWindow.showError("Оформление новой путевки", "Невозможно оформление новой путевки по этому туру.");
        }
    }
    /**
     * work with selected Voucher
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
                        addStage.setTitle("Работа с записью путевки по туру - " + selectedItem.getTourName());
                        addStage.initModality(Modality.APPLICATION_MODAL);
                        addStage.initOwner(Main.getPrimaryStage());
                        Scene scene = new Scene(page);
                        addStage.setScene(scene);
                        ControllerVoucher controller = loader.getController();
                        controller.setDialogStage(addStage, selectedItem, workItem.getId());
                        addStage.setMinWidth(900);
                        addStage.setMinHeight(500);
                        addStage.showAndWait();
                        fillTableVoucher();
                        refreshTour();
                    }
                } catch (IOException ex) {
                    MessageWindow.showError("Открытие окна", ex.getMessage());
                }
        }
    }

    /**
     * checking input data
     */
    private boolean validation(String process) {
        if (textName.getText().equals("") || textName.getText().length() > 45) {
            MessageWindow.showError(process, "Неверно введено Наименование, либо больше 45 символов");
            return false;
        }
        if (textOffersCount.getText().equals("") || Validation.checkContainNotInt(textOffersCount.getText()) || Integer.parseInt(textOffersCount.getText()) < 1) {
            MessageWindow.showError(process, "Неверно введено значение Количества предложений, либо меньше 1");
            return false;
        }
        if (Integer.parseInt(textOffersCount.getText()) < calculateVouchersCount()) {
            MessageWindow.showError(process, "Неверно введено значение Количества предложений - меньше уже оформленных по этому туру путевок");
            return false;
        }
        if (dateDateStart.getValue().toString().equals("") || dateDateStart.getValue() == null) {
            MessageWindow.showError(process, "Не введено значение даты начала тура, либо ввдееная дата раньше нынешней");
            return false;
        }
        if (textDaysCount.getText().equals("") || Validation.checkContainNotInt(textDaysCount.getText()) || Integer.parseInt(textDaysCount.getText()) < 1) {
            MessageWindow.showError(process, "Неверно введено значение Количества дней, либо меньше 1");
            return false;
        }
        if (textPrice.getText().equals("") || Validation.checkContainNotDouble(textPrice.getText()) || Double.parseDouble(textPrice.getText()) < 0.1) {
            MessageWindow.showError(process, "Неверно введено значение цены, либо меньше 0.1");
            return false;
        }
        if (labelTourOperator.getText().equals("-") || labelTourOperator.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран туроператор");
            return false;
        }
        if (lableHotel.getText().equals("-") || lableHotel.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран отель");
            return false;
        }
        if (labelDirection.getText().equals("-") || labelDirection.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран отель");
            return false;
        }
        if (labelKind.getText().equals("-") || labelKind.getText().equals("")) {
            MessageWindow.showError(process, "Не выбран вид тура");
            return false;
        }
        if (labelCategory.getText().equals("-") || labelCategory.getText().equals("")) {
            MessageWindow.showError(process, "Не выбрана категория тура");
            return false;
        }
        return true;
    }
    /**
     * set into work item new data
     */
    private void setWorkItem() {
        workItem.setName(textName.getText());
        workItem.setOffersCount(Integer.parseInt(textOffersCount.getText()));
        workItem.setVouchersCount(Integer.parseInt(textOffersCount.getText()));
        workItem.setPrice(Double.parseDouble(textPrice.getText()));
        workItem.setDateStart(dateDateStart.getValue());
        workItem.setDaysCount(Integer.parseInt(textDaysCount.getText()));
        if (tourOperator != null) {
            workItem.setIdTourOperator(tourOperator.getId());
            workItem.setTourOperator(tourOperator.getName());
        }
        if (hotel != null) {
            workItem.setIdHotel(hotel.getId());
            workItem.setHotel(hotel.getName());
            workItem.setIdDirection(hotel.getIdDirection());
            workItem.setDirection(hotel.getDirection());
        }
        if (kind != null) {
            workItem.setIdKind(kind.getId());
            workItem.setKind(kind.getName());
        }
        if (category != null) {
            workItem.setIdCategory(category.getId());
            workItem.setCategory(category.getName());
        }
        if (workItem.getId() > 0)
            workItem.setVouchersCount(calculateVouchersCount());
    }

    /**
     * calculate count of Tourists per Voucher in current Tour
     */
    public int calculateVouchersCount() {
        try {
            ResultSet set = SQLRequests.selectCalculateVouchersCount((conn), workItem.getId());
            if (set.isBeforeFirst()) {
                set.next();
                return set.getInt("COUNT");
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    /**
     * add new record in data base
     */
    public void onAdd(ActionEvent actionEvent) {
        if (validation("Добавление")) {
            try {
                workItem = new Tour();
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
                    if (SQLRequests.deleteOneRow(conn, DbHandler.TABLE_NAME_TOUR, workItem.getId()) > 0) {
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
