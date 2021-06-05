package dbClasses.models;

import javafx.beans.property.*;
/**
 * model for table Result
 * @author Kuro
 * @version 1.0
 */
public class Result {
    private IntegerProperty id;
    private StringProperty nameSurnamePatronymic;
    private IntegerProperty vouchersCount;
    private DoubleProperty cashSum;

    public static final String FIELD_ID = "ID";
    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_SURNAME = "SURNAME";
    public static final String FIELD_PATRONYMIC = "PATRONYMIC";
    public static final String FIELD_VOUCHERS_COUNT = "COUNT";
    public static final String FIELD_CASH_SUM = "SUM";

    public Result(int id, String nameSurnamePatronymic, int vouchersCount, double cashSum) {
        this.id = new SimpleIntegerProperty(id);
        this.nameSurnamePatronymic = new SimpleStringProperty(nameSurnamePatronymic);
        this.vouchersCount = new SimpleIntegerProperty(vouchersCount);
        this.cashSum = new SimpleDoubleProperty(cashSum);
    }

    public Result() { this(0,"",0,0); }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getNameSurnamePatronymic() { return nameSurnamePatronymic.get(); }
    public StringProperty nameSurnamePatronymicProperty() { return nameSurnamePatronymic; }
    public void setNameSurnamePatronymic(String nameSurnamePatronymic) { this.nameSurnamePatronymic.set(nameSurnamePatronymic); }

    public int getVouchersCount() { return vouchersCount.get(); }
    public IntegerProperty vouchersCountProperty() { return vouchersCount; }
    public void setVouchersCount(int vouchersCount) { this.vouchersCount.set(vouchersCount); }

    public double getCashSum() { return cashSum.get(); }
    public DoubleProperty cashSumProperty() { return cashSum; }
    public void setCashSum(double cashSum) { this.cashSum.set(cashSum); }
}
