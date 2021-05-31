package dbClasses;

import dbClasses.tables.Category;
import dbClasses.tables.Direction;
import dbClasses.tables.Kind;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SQLRequests {

    @Nullable
    public static ResultSet selectCalculateResult(Connection conn, String date1, String date2) throws SQLException {
        String query = "SELECT employee.id, employee.name, employee.surname, employee.patronymic, " +
                "COUNT(tourist.id_tourist) AS count, SUM(voucher.cost) As sum " +
                "FROM voucher, tourist, employee " +
                "WHERE  voucher.employee = employee.id and tourist.id_voucher = voucher.id and " +
                "voucher.date > " + date1 + " and voucher.date < " + date2 + " GROUP BY employee.id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    @Nullable
    public static ResultSet selectOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static int deleteOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "DELETE FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        int rowsCount = conn.createStatement().executeUpdate(query);
        return rowsCount;
    }
    @Nullable
    public static ResultSet selectAllInTable(Connection conn, String tableName) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static int addKind(Connection conn, Kind kind) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".kind(name) VALUES (\'"+ kind.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int editKind(Connection conn, Kind kind) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".kind SET NAME = \'" + kind.getName() + "\' WHERE ID=" + kind.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }

    public static int addCategory(Connection conn, Category category) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".category(name, added_value, discount) " +
                "VALUES (\'"+ category.getName() +"\', \'" + category.getAddedValue() +"\', \'" + category.getDiscount() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int editCategory(Connection conn, Category category) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".category SET " +
                "NAME = \'" + category.getName() + "\', ADDED_VALUE=\'" +category.getAddedValue()+"\', DISCOUNT=\'" + category.getDiscount() +"\'" +
                "WHERE ID=" + category.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }

    public static int addDirection(Connection conn, Direction direction) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".DIRECTION(name) " +
                "VALUES (\'"+ direction.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int editDirection(Connection conn, Direction category) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".DIRECTION SET " +
                "NAME = \'" + category.getName() + "\' WHERE ID=" + category.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }

    /*
    public static void EditEntryInHumanWithId(Connection conn, Human hmn) throws SQLException {
        String query = "UPDATE institut.HUMANS SET NAME = \'" + hmn.getName() + "\', SURNAME = \'" + hmn.getSurname() + "\', PATRONYMIC = \'" + hmn.getPatronymic() + "\', GENDER = \'" + hmn.getGender() + "\', HEIGHT = " + hmn.getHeight() + ", WEIGHT = " + hmn.getWeight() + ", AGE = " + hmn.getAge() + ", TELEPHONE = \'" + hmn.getTelephone() + "\' WHERE ID = " + hmn.getId();
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteEntryInHumanWithId(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM institut.HUMANS WHERE ID = " + id;
        conn.createStatement().executeUpdate(query);
    }

    public static ResultSet SelectEldestFromHuman(Connection conn) throws SQLException {
        String query = "SELECT * FROM institut.humans WHERE AGE = (SELECT MAX(AGE) FROM institut.humans)";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
 */

}
