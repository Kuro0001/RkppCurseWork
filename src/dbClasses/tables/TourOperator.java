package dbClasses.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourOperator {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty uniqueNumber;
    private StringProperty phone;
    private StringProperty email;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_UNIQUE_NUMBER = "UNIQUE_NUMBER";
    public static final String FIELD_PHONE = "PHONE";
    public static final String FIELD_EMAIL = "EMAIL";

    public TourOperator(int id, String name, String uniqueNumber, String phone, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.uniqueNumber = new SimpleStringProperty(uniqueNumber);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    public TourOperator() { this(0,"", "", "",""); }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getUniqueNumber() { return uniqueNumber.get(); }
    public StringProperty uniqueNumberProperty() {
        return uniqueNumber;
    }
    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber.set(uniqueNumber);
    }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) {
        this.email.set(email);
    }
}
