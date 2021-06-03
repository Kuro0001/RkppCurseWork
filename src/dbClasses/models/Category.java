package dbClasses.models;

import javafx.beans.property.*;

public class Category {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty addedValue;
    private DoubleProperty discount;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_ADDED_VALUE = "ADDED_VALUE";
    public static final String FIELD_DISCOUNT = "DISCOUNT";

    public Category(int id, String name, double addedValue, double discount) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.addedValue = new SimpleDoubleProperty(addedValue);
        this.discount = new SimpleDoubleProperty(discount);
    }

    public Category() {
        this(0,"",0,0);
    }
    public Category(String name) {
        this(0,name,0,0);
    }

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

    public double getAddedValue() { return addedValue.get(); }
    public DoubleProperty addedValueProperty() {
        return addedValue;
    }
    public void setAddedValue(double addedValue) {
        this.addedValue.set(addedValue);
    }

    public double getDiscount() { return discount.get(); }
    public DoubleProperty discountProperty() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount.set(discount);
    }
}
