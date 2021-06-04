package dbClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class for data base
 * @author Kuro
 */
public class DbHandler {
    protected Connection connection;
    public static final String DB_NAME = "curesworktouristagency";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String LOGIN = "root";
    private static final String PASSWORD = "password";

    public static final String TABLE_NAME_KIND = "kind";
    public static final String TABLE_NAME_CATEGORY = "category";
    public static final String TABLE_NAME_DIRECTION = "direction";
    public static final String TABLE_NAME_HOTELS = "hotel";
    public static final String TABLE_NAME_TOUR_OPERATOR = "tour_operator";
    public static final String TABLE_NAME_TOUR = "tour";
    public static final String TABLE_NAME_CLIENT = "client";
    public static final String TABLE_NAME_EMPLOYEE = "employee";
    public static final String TABLE_NAME_VOUCHER = "voucher";
    public static final String TABLE_NAME_TOURIST = "tourist";

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
