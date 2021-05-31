package dbClasses.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Kind {
    private IntegerProperty id;
    private StringProperty name;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";

    public Kind(String name) {
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
    }

    public Kind(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public Kind() {
        this(0,"");
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
}
