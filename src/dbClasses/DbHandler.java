package dbClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to connect to DataBase
 * @author Kuro
 * @version 1.0
 */
public class DbHandler {
    /** exemplar of (Connection) */
    protected Connection connection;
    /** constant for data base (scheme) name */
    public static final String DB_NAME = "curesworktouristagency";
    /** constant for data base address */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    /** constant for data base login */
    private static final String LOGIN = "root";
    /** constant for data base password */
    private static final String PASSWORD = "password";
    /** constant for data base table for model(Kind) */
    public static final String TABLE_NAME_KIND = "kind";
    /** constant for data base table for model(Category) */
    public static final String TABLE_NAME_CATEGORY = "category";
    /** constant for data base table for model(Direction) */
    public static final String TABLE_NAME_DIRECTION = "direction";
    /** constant for data base table for model(Hotel) */
    public static final String TABLE_NAME_HOTELS = "hotel";
    /** constant for data base table for model(TourOperator) */
    public static final String TABLE_NAME_TOUR_OPERATOR = "tour_operator";
    /** constant for data base table for model(Tour) */
    public static final String TABLE_NAME_TOUR = "tour";
    /** constant for data base table for model(Client) */
    public static final String TABLE_NAME_CLIENT = "client";
    /** constant for data base table for model(Employee) */
    public static final String TABLE_NAME_EMPLOYEE = "employee";
    /** constant for data base table for model(Voucher) */
    public static final String TABLE_NAME_VOUCHER = "voucher";
    /**
     * constant for data base table Tourist.
     * intermediate table implementing many-to-many relationship
     */
    public static final String TABLE_NAME_TOURIST = "tourist";

    /**
     * method to connect to data base and load Driver
     * @return Connection to database
     */
    public Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        try
        {
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
        } catch (SQLException throwables)
        {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return connection;
    }
}
