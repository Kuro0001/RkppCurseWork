package dbClasses.tables;

import javafx.beans.property.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

public class Client {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    @Nullable
    private StringProperty patronymic;
    private StringProperty passport;
    private ObjectProperty<LocalDate> birthDate;
    private StringProperty sex;
    private StringProperty phone;
    private StringProperty email;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_SURNAME = "SURNAME";
    public static final String FIELD_PATRONYMIC = "PATRONYMIC";
    public static final String FIELD_PASSPORT = "PASPORT";
    public static final String FIELD_PHONE = "PHONE";
    public static final String FIELD_SEX = "SEX";
    public static final String FIELD_BIRTH_DATE = "birth_date";
    public static final String FIELD_EMAIL = "EMAIL";

    public Client(int id, String name, String surname,@Nullable String patronymic, String passport, LocalDate birthDate, String sex, String phone, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.passport = new SimpleStringProperty(passport);
        this.birthDate = new SimpleObjectProperty<LocalDate>(birthDate);
        this.sex = new SimpleStringProperty(sex);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    public Client() { this(0,"", "", "","",LocalDate.MIN,"","",""); }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getSurname() { return surname.get(); }
    public StringProperty surnameNumberProperty() { return surname; }
    public void setSurname(String surname) {  this.surname.set(surname); }

    @Nullable
    public String getPatronymic() { return patronymic.get(); }
    @Nullable
    public StringProperty patronymicProperty() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic.set(patronymic);}

    public String getPassport() { return passport.get(); }
    public StringProperty passportProperty() { return passport; }
    public void setPassport(String passport) {  this.passport.set(passport); }

    public LocalDate getBirthDate() { return birthDate.get(); }
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) {  this.birthDate.set(birthDate); }

    public String getSex() { return sex.get(); }
    public StringProperty sexProperty() { return sex; }
    public void setSex(String sex) {  this.sex.set(sex); }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) {  this.phone.set(phone); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }
}
