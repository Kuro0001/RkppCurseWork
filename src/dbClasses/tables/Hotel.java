package dbClasses.tables;

import javafx.beans.property.*;

public class Hotel {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty address;
    private IntegerProperty idDirection;
    private StringProperty direction;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_ADDRESS = "ADDRESS";
    public static final String FIELD_ID_DIRETION = "DIRECTION";

    public Hotel(int id, String name, String address, int idDirection, String direction) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.idDirection = new SimpleIntegerProperty(idDirection);
        this.direction = new SimpleStringProperty(direction);
    }

    public Hotel() {
        this(0,"", "", 0,"");
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

    public String getAddress() { return address.get(); }
    public StringProperty addressProperty() {
        return address;
    }
    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getIdDirection() { return idDirection.get(); }
    public void setIdDirection(int idDirection) { this.idDirection.set(idDirection); }

    public String getDirection() { return direction.get(); }
    public StringProperty directionProperty() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction.set(direction);
    }
}
