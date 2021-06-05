package dbClasses.models;

import javafx.beans.property.*;

import java.time.LocalDate;
/**
 * model for table Voucher
 * @author Kuro
 * @version 1.0
 */
public class Voucher {
    private IntegerProperty id;
    private IntegerProperty idTour;
    private StringProperty tourName;
    private StringProperty tourDirection;
    private IntegerProperty idClient;
    private StringProperty clientNameSurname;
    private IntegerProperty idEmployee;
    private StringProperty employeeNameSurname;
    private ObjectProperty<LocalDate> date;
    private DoubleProperty cost;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_ID_TOUR = "TOUR";
    public static final String FIELD_ID_CLIENT = "CLIENT";
    public static final String FIELD_ID_EMPLOYEE = "EMPLOYEE";
    public static final String FIELD_DATE = "DATE";
    public static final String FIELD_COST = "COST";

    public Voucher(int id, int idTour, String tourName, String tourDirection, int idClient,
                  String clientNameSurname, int idEmployee, String employeeNameSurname , LocalDate date, double cost) {
        this.id = new SimpleIntegerProperty(id);
        this.idTour = new SimpleIntegerProperty(idTour);
        this.tourName = new SimpleStringProperty(tourName);
        this.tourDirection = new SimpleStringProperty(tourDirection);
        this.idClient = new SimpleIntegerProperty(idClient);
        this.clientNameSurname = new SimpleStringProperty(clientNameSurname);
        this.idEmployee = new SimpleIntegerProperty(idEmployee);
        this.employeeNameSurname = new SimpleStringProperty(employeeNameSurname);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public Voucher() { this(0,0,"","",0,"",0,"",LocalDate.MIN,0); }
    public Voucher(int idTour) { this(0,idTour,"","",0,"",0,"",LocalDate.MIN,0); }


    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public int getIdTour() { return idTour.get(); }
    public void setIdTour(int idTour) { this.idTour.set(idTour); }

    public String getTourName() { return tourName.get(); }
    public StringProperty tourNameProperty() { return tourName; }
    public void setTourName(String tourName) { this.tourName.set(tourName); }

    public String getTourDirection() { return tourDirection.get(); }
    public StringProperty tourDirectionProperty() { return tourDirection; }
    public void setTourDirection(String tourDirection) { this.tourDirection.set(tourDirection); }

    public int getIdClient() { return idClient.get(); }
    public void setIdClient(int idClient) { this.idClient.set(idClient); }

    public String getClientNameSurname() { return clientNameSurname.get(); }
    public StringProperty clientNameSurnameProperty() { return clientNameSurname; }
    public void setClientNameSurname(String clientNameSurname) { this.clientNameSurname.set(clientNameSurname); }

    public int getIdEmployee() { return idEmployee.get(); }
    public void setIdEmployee(int idEmployee) { this.idEmployee.set(idEmployee); }

    public String getEmployeeNameSurname() { return employeeNameSurname.get(); }
    public StringProperty employeeNameSurnameProperty() { return employeeNameSurname; }
    public void setEmployeeNameSurname(String employeeNameSurname) { this.employeeNameSurname.set(employeeNameSurname); }

    public LocalDate getDate() { return date.get(); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public void setDate(LocalDate date) { this.date.set(date); }

    public double getCost() { return cost.get(); }
    public DoubleProperty costProperty() { return cost; }
    public void setCost(double cost) { this.cost.set(cost); }
}
