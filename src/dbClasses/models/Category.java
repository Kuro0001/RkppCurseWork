package dbClasses.models;

import javafx.beans.property.*;

/**
 * model for table Category
 * @author Kuro
 * @version 1.0
 */
public class Category {
    /** field id */
    private IntegerProperty id;
    /** field name */
    private StringProperty name;
    /** field addedValue */
    private DoubleProperty addedValue;
    /** field discount */
    private DoubleProperty discount;
    /** constant for column name ID */
    public static final String FIELD_ID = "ID";
    /** constant for column name NAME */
    public static final String FIELD_NAME = "NAME";
    /** constant for column name ADDED_VALUE */
    public static final String FIELD_ADDED_VALUE = "ADDED_VALUE";
    /** constant for column name DISCOUNT */
    public static final String FIELD_DISCOUNT = "DISCOUNT";

    /**
     * constructor - creating a new object
     * @param id field - id
     * @param name field - name
     * @param addedValue  field - addedValue
     * @param discount field - discount
     */
    public Category(int id, String name, double addedValue, double discount) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.addedValue = new SimpleDoubleProperty(addedValue);
        this.discount = new SimpleDoubleProperty(discount);
    }
    /**
     * constructor - creating a new object
     */
    public Category() {
        this(0,"",0,0);
    }
    /**
     * constructor - creating a new object
     * @param name field - name
     */
    public Category(String name) {
        this(0,name,0,0);
    }
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
     * to get field id
     */
    public String getName() { return name.get(); }
    /**
     * to get field id
     */
    public StringProperty nameProperty() { return name; }
    /**
     * to set field id
     */
    public void setName(String name) { this.name.set(name); }
    /**
     * to get field addedValue
     */
    public double getAddedValue() { return addedValue.get(); }
    /**
     * to get field addedValue
     */
    public DoubleProperty addedValueProperty() { return addedValue; }
    /**
     * to set field addedValue
     */
    public void setAddedValue(double addedValue) { this.addedValue.set(addedValue); }
    /**
     * to get field discount
     */
    public double getDiscount() { return discount.get(); }
    /**
     * to get field discount
     */
    public DoubleProperty discountProperty() { return discount; }
    /**
     * to set field discount
     */
    public void setDiscount(double discount) { this.discount.set(discount); }
}
