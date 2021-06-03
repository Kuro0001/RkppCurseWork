package dbClasses.models;

import javafx.beans.property.*;

public class Direction {
    private IntegerProperty id;
    private StringProperty name;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";

    public Direction(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public Direction() {
        this(0,"");
    }
    public Direction(String name) { this(0,name);}

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() {return name;}
    public void setName(String name) { this.name.set(name); }
}
