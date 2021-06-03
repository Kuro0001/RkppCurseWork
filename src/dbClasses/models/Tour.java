package dbClasses.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Tour {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty offersCount;
    private IntegerProperty vouchersCount;
    private DoubleProperty price;
    private ObjectProperty<LocalDate> dateStart;
    private IntegerProperty daysCount;
    private IntegerProperty idTourOperator;
    private StringProperty tourOperator;
    private IntegerProperty idHotel;
    private StringProperty hotel;
    private IntegerProperty idDirection;
    private StringProperty direction;
    private IntegerProperty idKind;
    private StringProperty kind;
    private IntegerProperty idCategory;
    private StringProperty category;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_ID_TOUR_OPERATOR = "TOUR_OPERATOR";
    public static final String FIELD_OFFERS_COUNT = "OFFERS_COUNT";
    public static final String FIELD_VOUCHERS_COUNT = "VOUCHERS_COUNT";
    public static final String FIELD_ID_HOTEL = "HOTEL";
    public static final String FIELD_PRICE = "PRICE";
    public static final String FIELD_DATE_START = "DATE_START";
    public static final String FIELD_DAYS_COUNT = "DAYS_COUNT";
    public static final String FIELD_ID_KIND = "KIND";
    public static final String FIELD_ID_CATEGORY = "CATEGORY";

    public Tour(int id, String name,int idTourOperator, String tourOperator, int offersCount, int vouchersCount,
                int idHotel, String hotel,int idDirection, String direction, double price, LocalDate dateStart,
                int daysCount, int idKind, String kind, int idCategory, String category) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.idTourOperator = new SimpleIntegerProperty(idTourOperator);
        this.tourOperator = new SimpleStringProperty(tourOperator);
        this.offersCount = new SimpleIntegerProperty(offersCount);
        this.vouchersCount = new SimpleIntegerProperty(vouchersCount);
        this.idHotel = new SimpleIntegerProperty(idHotel);
        this.hotel = new SimpleStringProperty(hotel);
        this.idDirection = new SimpleIntegerProperty(idDirection);
        this.direction = new SimpleStringProperty(direction);
        this.price = new SimpleDoubleProperty(price);
        this.dateStart = new SimpleObjectProperty<LocalDate>(dateStart);
        this.daysCount = new SimpleIntegerProperty(daysCount);
        this.idKind = new SimpleIntegerProperty(idKind);
        this.kind = new SimpleStringProperty(kind);
        this.idCategory = new SimpleIntegerProperty(idCategory);
        this.category = new SimpleStringProperty(category);
    }

    public Tour() { this(0,"",0,"",0,0,0,"",0,"",0,LocalDate.MIN, 0,0,"",0,""); }
    public Tour(String name) { this(0,"",0,"",0,0,0,"",0,"",0,LocalDate.MIN, 0,0,"",0,""); }


    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public int getIdTourOperator() { return idTourOperator.get(); }
    public void setIdTourOperator(int idTourOperator) { this.idTourOperator.set(idTourOperator); }

    public String getTourOperator() { return tourOperator.get(); }
    public StringProperty tourOperatorProperty() { return tourOperator; }
    public void setTourOperator(String tourOperator) { this.tourOperator.set(tourOperator); }

    public int getOffersCount() { return offersCount.get(); }
    public IntegerProperty offersCountProperty() { return offersCount; }
    public void setOffersCount(int offersCount) { this.offersCount.set(offersCount); }

    public int getVouchersCount() { return vouchersCount.get(); }
    public IntegerProperty vouchersCountProperty() { return vouchersCount; }
    public void setVouchersCount(int vouchersCount) { this.vouchersCount.set(vouchersCount); }

    public int getIdHotel() { return idHotel.get(); }
    public void setIdHotel(int idHotel) { this.idHotel.set(idHotel); }

    public String getHotel() { return hotel.get(); }
    public StringProperty hotelProperty() { return hotel; }
    public void setHotel(String hotel) { this.hotel.set(hotel); }

    public int getIdDirection() { return idDirection.get(); }
    public void setIdDirection(int idDirection) { this.idDirection.set(idDirection); }

    public String getDirection() { return direction.get(); }
    public StringProperty directionProperty() { return direction; }
    public void setDirection(String direction) { this.direction.set(direction); }

    public Double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }
    public void setPrice(Double price) { this.price.set(price); }

    public LocalDate getDateStart() { return dateStart.get(); }
    public ObjectProperty<LocalDate> dateStartProperty() { return dateStart; }
    public void setDateStart(LocalDate dateStart) { this.dateStart.set(dateStart); }

    public int getDaysCount() { return daysCount.get(); }
    public IntegerProperty daysCountProperty() { return daysCount; }
    public void setDaysCount(int daysCount) { this.daysCount.set(daysCount); }

    public int getIdKind() { return idKind.get(); }
    public void setIdKind(int idKind) { this.idKind.set(idKind); }

    public String getKind() { return kind.get(); }
    public StringProperty kindProperty() { return kind; }
    public void setKind(String kind) { this.kind.set(kind); }

    public int getIdCategory() { return idCategory.get(); }
    public void setIdCategory(int idCategory) { this.idCategory.set(idCategory); }

    public String getCategory() { return category.get(); }
    public StringProperty categoryProperty() { return category; }
    public void setCategory(String category) { this.category.set(category); }
}
