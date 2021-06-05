package dbClasses.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * model for table Employee
 * @author Kuro
 * @version 1.0
 */
public class Employee {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty patronymic;
    private StringProperty email;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_SURNAME = "SURNAME";
    public static final String FIELD_PATRONYMIC = "PATRONYMIC";
    public static final String FIELD_EMAIL = "EMAIL";

    public Employee(int id, String name, String surname, String patronymic, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.email = new SimpleStringProperty(email);
    }

    public Employee() { this(0,"", "", "",""); }
    public Employee(String surname) { this(0,"", surname, "",""); }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getSurname() { return surname.get(); }
    public StringProperty surnameNumberProperty() { return surname; }
    public void setSurname(String surname) {  this.surname.set(surname); }

    public String getPatronymic() { return patronymic.get(); }
    public StringProperty patronymicProperty() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic.set(patronymic);}

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }
}
