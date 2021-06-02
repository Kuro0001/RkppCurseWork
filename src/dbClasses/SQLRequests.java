package dbClasses;

import dbClasses.tables.*;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SQLRequests {

    public static ResultSet selectCalculateResult(Connection conn, String date1, String date2) throws SQLException {
        String query = "SELECT employee.id, employee.name, employee.surname, employee.patronymic, " +
                "COUNT(tourist.id_tourist) AS count, SUM(voucher.cost) As sum " +
                "FROM voucher, tourist, employee " +
                "WHERE  voucher.employee = employee.id and tourist.id_voucher = voucher.id and " +
                "voucher.date > " + date1 + " and voucher.date < " + date2 + " GROUP BY employee.id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".employee" + " WHERE surname = \'" + employee.getSurname() + "\'" +
                " AND email = \'" + employee.getEmail() + "\'";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectSearch(Connection conn, Tour tour) throws SQLException {
        String query = "SELECT tour.* FROM tour, kind, hotel " +
                "WHERE hotel.id in (SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + ")) " +
                "AND kind.id in (SELECT id FROM kind WHERE name LIKE " + tour.getKind() + ") " +
                "AND tour.price < " + tour.getPrice() + " AND kind.id = tour.kind  AND hotel.id = tour.hotel " +
                "AND tour.date_start > DATE(" + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + ")";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Client client) throws SQLException {
        String query = "SELECT * FROM CLIENT " +
                " WHERE surname LIKE " + client.getSurname() + " AND pasport LIKE " + client.getPassport();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "SELECT * FROM TOUR_OPERATOR " +
                " WHERE name LIKE " + tourOperator.getName() + " AND unique_number LIKE " + tourOperator.getUniqueNumber();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Kind kind) throws SQLException {
        String query = "SELECT * FROM KIND " +
                " WHERE name LIKE " + kind.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Category category) throws SQLException {
        String query = "SELECT * FROM CATEGORY " +
                " WHERE name LIKE " + category.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Direction direction) throws SQLException {
        String query = "SELECT * FROM DIRECTION " +
                " WHERE name LIKE " + direction.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Hotel hotel) throws SQLException {
        String query = "SELECT hotel.id, hotel.name, hotel.address, hotel.direction FROM HOTEL, DIRECTION " +
                " WHERE hotel.name LIKE " + hotel.getName() +
                " AND direction.id IN (SELECT id FROM DIRECTION WHERE name LIKE " + hotel.getDirection() + ") " +
                "AND direction.id = hotel.direction";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Voucher voucher , Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM tour, voucher " +
                "WHERE tour.id in (SELECT id FROM tour WHERE hotel in " +
                "(SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + "))) " +
                "AND tour.name LIKE " + tour.getName() + " " +
                "AND tour.id = voucher.tour AND voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectSearch(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM EMPLOYEE " +
                " WHERE surname LIKE " + employee.getSurname();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectVouchersForClient(Connection conn, int idClient, Voucher voucher, Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM tour, voucher " +
                "WHERE tour.id in (SELECT id FROM tour WHERE hotel in " +
                "(SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + "))) " +
                "AND tour.name LIKE " + tour.getName() + " " +
                "AND tour.id = voucher.tour AND voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") +
                " AND voucher.client = " + idClient;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectVouchersForTourists(Connection conn, int idTourist, Voucher voucher, Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM tour, voucher, tourist " +
                "WHERE tour.id in (SELECT id FROM tour WHERE hotel in " +
                "(SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + "))) " +
                "AND tour.name LIKE " + tour.getName() + " " +
                "AND tour.id = voucher.tour AND voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") +
                " AND tourist.ID_voucher = voucher.id AND tourist.ID_tourist = " + idTourist;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectVouchersForEmployee(Connection conn, int idEmployee, Voucher voucher, Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM tour, voucher, tourist " +
                "WHERE tour.id in (SELECT id FROM tour WHERE hotel in " +
                "(SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + "))) " +
                "AND tour.name LIKE " + tour.getName() + " " +
                "AND tour.id = voucher.tour AND voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") +
                " AND tourist.ID_voucher = voucher.id AND voucher.employee = " + idEmployee;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectTourForTourOperator(Connection conn, int idTourOperator,  Direction direction, Tour tour) throws SQLException {
        String query = "SELECT tour.* FROM tour WHERE tour.tour_operator = " + idTourOperator +
                " AND tour.hotel in ( SELECT id FROM hotel WHERE direction in " +
                "(SELECT id FROM direction WHERE name LIKE \'%" +  direction.getName()+ "%\')) AND " +
                "tour.kind LIKE \'%"+ tour.getKind() + "%\' AND " +
                "tour.date_start > " + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }


    public static int deleteOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "DELETE FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        int rowsCount = conn.createStatement().executeUpdate(query);
        return rowsCount;
    }
    public static ResultSet selectAllInTable(Connection conn, String tableName) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName + " LIMIT 0, 100";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectReferences(Connection conn, Kind kind) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE KIND=" + kind.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectReferences(Connection conn, Category category) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE CATEGORY=" + category.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectReferences(Connection conn, Direction direction) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".HOTEL" + " WHERE DIRECTION=" + direction.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectReferences(Connection conn, Hotel hotel) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE HOTEL=" + hotel.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectReferences(Connection conn, Client client) throws SQLException {
        String query = "SELECT * FROM voucher" + " WHERE client =" + client.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        if (!set.isBeforeFirst())
        {
            query = "SELECT * FROM tourist" + " WHERE id_tourist =" + client.getId();
            set = conn.createStatement().executeQuery(query);
        }
        return set;
    }
    public static ResultSet selectReferences(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM voucher" + " WHERE employee =" + employee.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    public static ResultSet selectReferences(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "SELECT * FROM TOUR" + " WHERE tour_operator =" + tourOperator.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }




    public static int addOneRow(Connection conn, Kind kind) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".kind(name) VALUES (\'"+ kind.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, Category category) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".category(name, added_value, discount) " +
                "VALUES (\'"+ category.getName() +"\', \'" + category.getAddedValue() +"\', \'" + category.getDiscount() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, Direction direction) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".DIRECTION(name) " +
                "VALUES (\'"+ direction.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, Hotel hotel) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".HOTEL(name, address, direction) " +
                "VALUES (\'"+ hotel.getName() +"\', \'" + hotel.getAddress() + "\', " + hotel.getIdDirection() + ")";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, Client client) throws SQLException {
        String query = "INSERT CLIENT(name, surname, patronymic, sex, birth_date, pasport, phone, email) " +
                "VALUES (\'"+ client.getName() +"\', \'" + client.getSurname() + "\', \'" + client.getPatronymic() + "\', " +
                "\'" + client.getSex() + "\', \'" + client.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + "\', " +
                " \'" + client.getPassport() + "\', \'" + client.getPhone() + "\', \'" + client.getEmail() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "INSERT EMPLOYEE(name, surname, patronymic, email) " +
                "VALUES (\'"+ employee.getName() +"\', \'" + employee.getSurname() + "\', " +
                "\'" + employee.getPatronymic() + "\', \'" + employee.getEmail() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    public static int addOneRow(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "INSERT TOUR_OPERATOR(name, email, phone, unique_number) " +
                "VALUES (\'"+ tourOperator.getName() +"\', \'" + tourOperator.getEmail() + "\', " +
                "\'" + tourOperator.getPhone() + "\', \'" + tourOperator.getUniqueNumber() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }


    public static int editOneRow(Connection conn, Kind kind) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".kind SET NAME = \'" + kind.getName() + "\' WHERE ID=" + kind.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, Category category) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".category SET " +
                "NAME = \'" + category.getName() + "\', ADDED_VALUE=\'" +category.getAddedValue()+"\', DISCOUNT=\'" + category.getDiscount() +"\'" +
                "WHERE ID=" + category.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, Direction direction) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".DIRECTION SET " +
                "NAME = \'" + direction.getName() + "\' WHERE ID=" + direction.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, Hotel hotel) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".HOTEL SET " +
                "NAME = \'" + hotel.getName() + "\', ADDRESS = \'" + hotel.getAddress() + "\', " +
                "DIRECTION = \'" + hotel.getIdDirection() + "\' WHERE ID=" + hotel.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, Client client) throws SQLException {
        String query = "UPDATE CLIENT SET " +
                "name = \'"+ client.getName() +"\', surname = \'" + client.getSurname() + "\', patronymic = \'" + client.getPatronymic() + "\', " +
                " sex = \'" + client.getSex() + "\', birth_date = \'" + client.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + "\', " +
                " pasport = " + client.getPassport() + ", phone = " + client.getPhone() + ", email = \'" + client.getEmail() + "\' WHERE ID=" + client.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "UPDATE EMPLOYEE SET " +
                "name = \'"+ employee.getName() +"\', surname = \'" + employee.getSurname() + "\', patronymic = \'" + employee.getPatronymic() + "\', " +
                " email = \'" + employee.getEmail() + "\' WHERE ID=" + employee.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    public static int editOneRow(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "UPDATE TOUR_OPERATOR SET " +
                "name = \'"+ tourOperator.getName() + "\', phone = \'" + tourOperator.getPhone() + "\', " +
                "unique_number = \'" + tourOperator.getUniqueNumber() + "\', email = \'" + tourOperator.getEmail() + "\' WHERE ID=" + tourOperator.getId();
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
