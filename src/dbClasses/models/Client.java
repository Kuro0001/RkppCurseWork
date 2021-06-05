package dbClasses.models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * model for table Category
 * @author Kuro
 * @version 1.0
 */
public class Client {
    /** field id */
    private IntegerProperty id;
    /** field name */
    private StringProperty name;
    /** field surname */
    private StringProperty surname;
    /** field patronymic */
    private StringProperty patronymic;
    /** field passport */
    private StringProperty passport;
    /** field birthDate */
    private ObjectProperty<LocalDate> birthDate;
    /** field sex */
    private StringProperty sex;
    /** field phone */
    private StringProperty phone;
    /** field email */
    private StringProperty email;
    /** constant for column name ID */
    public static final String FIELD_ID = "ID";
    /** constant for column name NAME */
    public static final String FIELD_NAME = "NAME";
    /** constant for column name SURNAME */
    public static final String FIELD_SURNAME = "SURNAME";
    /** constant for column name PATRONYMIC */
    public static final String FIELD_PATRONYMIC = "PATRONYMIC";
    /** constant for column name PASPORT */
    public static final String FIELD_PASSPORT = "PASPORT";
    /** constant for column name PHONE */
    public static final String FIELD_PHONE = "PHONE";
    /** constant for column name SEX */
    public static final String FIELD_SEX = "SEX";
    /** constant for column name birth_date */
    public static final String FIELD_BIRTH_DATE = "birth_date";
    /** constant for column name EMAIL */
    public static final String FIELD_EMAIL = "EMAIL";
    /**
     * constructor - creating a new object
     * @param id field - id
     * @param name field - name
     * @param surname field - surname
     * @param patronymic field - patronymic
     * @param passport field - passport
     * @param birthDate field - birthDate
     * @param sex field - sex
     * @param phone field - phone
     * @param email field - email
     */
    public Client(int id, String name, String surname, String patronymic, String passport, LocalDate birthDate, String sex, String phone, String email) {
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
    /**
     * constructor - creating a new object
     */
    public Client() { this(0,"", "", "","",LocalDate.MIN,"","",""); }
    /**
     * constructor - creating a new object
     * @param surname field - surname
     */
    public Client(String surname) { this(0,"", surname, "","",LocalDate.MIN,"","",""); }
    /**
     * to get field id
     */
    public int getId() { return id.get(); }
    /**
     * to get field id
     */
    public IntegerProperty idProperty() { return id; }
    /**
     * to set field id
     */
    public void setId(int id) { this.id.set(id); }
    /**
     * to get field name
     */
    public String getName() { return name.get(); }
    /**
     * to get field name
     */
    public StringProperty nameProperty() { return name; }
    /**
     * to set field name
     */
    public void setName(String name) { this.name.set(name); }
    /**
     * to get field surname
     */
    public String getSurname() { return surname.get(); }
    /**
     * to get field surname
     */
    public StringProperty surnameNumberProperty() { return surname; }
    /**
     * to set field surname
     */
    public void setSurname(String surname) {  this.surname.set(surname); }
    /**
     * to get field patronymic
     */
    public String getPatronymic() { return patronymic.get(); }
    /**
     * to get field patronymic
     */
    public StringProperty patronymicProperty() { return patronymic; }
    /**
     * to set field patronymic
     */
    public void setPatronymic(String patronymic) { this.patronymic.set(patronymic);}
    /**
     * to get field passport
     */
    public String getPassport() { return passport.get(); }
    /**
     * to get field passport
     */
    public StringProperty passportProperty() { return passport; }
    /**
     * to set field passport
     */
    public void setPassport(String passport) {  this.passport.set(passport); }
    /**
     * to get field birthDate
     */
    public LocalDate getBirthDate() { return birthDate.get(); }
    /**
     * to get field birthDate
     */
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }
    /**
     * to set field birthDate
     */
    public void setBirthDate(LocalDate birthDate) {  this.birthDate.set(birthDate); }
    /**
     * to get field sex
     */
    public String getSex() { return sex.get(); }
    /**
     * to get field sex
     */
    public StringProperty sexProperty() { return sex; }
    /**
     * to set field sex
     */
    public void setSex(String sex) {  this.sex.set(sex); }
    /**
     * to get field phone
     */
    public String getPhone() { return phone.get(); }
    /**
     * to get field phone
     */
    public StringProperty phoneProperty() { return phone; }
    /**
     * to set field phone
     */
    public void setPhone(String phone) {  this.phone.set(phone); }
    /**
     * to get field email
     */
    public String getEmail() { return email.get(); }
    /**
     * to get field email
     */
    public StringProperty emailProperty() { return email; }
    /**
     * to set field email
     */
    public void setEmail(String email) { this.email.set(email); }
}
