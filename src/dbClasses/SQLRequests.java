package dbClasses;

import dbClasses.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
/**
 * class for sending queries to the database and getting results
 * @author Kuro
 * @version 1.0
 */
public class SQLRequests {
    /**
     * method for obtaining results of work by (Employee)s for a specified period
     * @param date1 start date of the period
     * @param date2 end date of the period
     * @return result set for model(Result)
     * @throws SQLException
     */
    public static ResultSet selectCalculateResult(Connection conn, String date1, String date2) throws SQLException {
        String query = "SELECT employee.id, employee.name, employee.surname, employee.patronymic, " +
                "COUNT(tourist.id_tourist) AS count, SUM(voucher.cost) As sum " +
                "FROM voucher, tourist, employee " +
                "WHERE  voucher.employee = employee.id and tourist.id_voucher = voucher.id and " +
                "voucher.date > " + date1 + " and voucher.date < " + date2 + " GROUP BY employee.id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * method for counting the number of (Tourist)s in all packages of a particular (Tour)
     * @param idTour id of current (Tour) for search
     * @return result set as ("Count")
     * @throws SQLException
     */
    public static ResultSet selectCalculateVouchersCount(Connection conn, int idTour) throws SQLException {
        String query = "SELECT COUNT(tourist.ID_tourist) as COUNT FROM tourist WHERE tourist.ID_voucher IN (SELECT id FROM voucher WHERE tour = " + idTour + ")";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * method for finding one record in a table for a given ID
     * @param tableName table name
     * @param id given ID
     * @return result set for needed model
     * @throws SQLException
     */
    public static ResultSet selectOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * method for finding one record in a table (Employee) by "surname" and "email"
     * @param employee model(Employee) with "surname" and "email"
     * @return result set as model(Employee)
     * @throws SQLException
     */
    public static ResultSet selectOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".employee" + " WHERE surname = \'" + employee.getSurname() + "\'" +
                " AND email = \'" + employee.getEmail() + "\'";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Tour)s by "kind", "date" and "price"
     * @param tour model(Tour)
     * @return result set as model(Tour)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Tour tour) throws SQLException {
        String query = "SELECT tour.* FROM tour, kind, hotel " +
                "WHERE hotel.id in (SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + ")) " +
                "AND kind.id in (SELECT id FROM kind WHERE name LIKE " + tour.getKind() + ") " +
                "AND tour.price < " + tour.getPrice() + " AND kind.id = tour.kind  AND hotel.id = tour.hotel " +
                "AND tour.date_start > DATE(" + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + ")";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Client)s by "surname" and "pasport"
     * @param client model(Client)
     * @return result set as model(Client)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Client client) throws SQLException {
        String query = "SELECT * FROM CLIENT " +
                " WHERE surname LIKE " + client.getSurname() + " AND pasport LIKE " + client.getPassport();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (TourOperator)s by "name" and "unique_number"
     * @param tourOperator model(TourOperator)
     * @return result set as model(TourOperator)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "SELECT * FROM TOUR_OPERATOR " +
                " WHERE name LIKE " + tourOperator.getName() + " AND unique_number LIKE " + tourOperator.getUniqueNumber();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Kind)s by "name"
     * @param kind model(Kind)
     * @return result set as model(Kind)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Kind kind) throws SQLException {
        String query = "SELECT * FROM KIND " +
                " WHERE name LIKE " + kind.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Category)s by "name"
     * @param category model(Category)
     * @return result set as model(Category)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Category category) throws SQLException {
        String query = "SELECT * FROM CATEGORY " +
                " WHERE name LIKE " + category.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Direction)s by "name"
     * @param direction model(Direction)
     * @return result set as model(Direction)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Direction direction) throws SQLException {
        String query = "SELECT * FROM DIRECTION " +
                " WHERE name LIKE " + direction.getName();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Hotel)s by "name" and "direction"
     * @param hotel model(Hotel)
     * @return result set as model(Hotel)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Hotel hotel) throws SQLException {
        String query = "SELECT hotel.id, hotel.name, hotel.address, hotel.direction FROM HOTEL, DIRECTION " +
                " WHERE hotel.name LIKE " + hotel.getName() +
                " AND direction.id IN (SELECT id FROM DIRECTION WHERE name LIKE " + hotel.getDirection() + ") " +
                "AND direction.id = hotel.direction";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Tour)s by "name", "direction" and "date"
     * @param tour model(Tour)
     * @return result set as model(Tour)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Voucher voucher , Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM tour, voucher " +
                "WHERE tour.id in (SELECT id FROM tour WHERE hotel in " +
                "(SELECT id FROM hotel WHERE direction in (SELECT id FROM direction WHERE name LIKE " + tour.getDirection() + "))) " +
                "AND tour.name LIKE " + tour.getName() + " " +
                "AND tour.id = voucher.tour AND voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Employee)s by "surname"
     * @param employee model(Employee)
     * @return result set as model(Employee)
     * @throws SQLException
     */
    public static ResultSet selectSearch(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM EMPLOYEE " +
                " WHERE surname LIKE " + employee.getSurname();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * method for finding all records in a table (Voucher) for current (Client). Find all voucher where current (Client) as Customer.n
     * @param idClient number, id, of (Client)
     * @param voucher model(Voucher)
     * @param tour model(Tour)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
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
    /**
     * method for finding all records in a table (Voucher) for current (Client). Find all voucher where current (Client) as Tourist.
     * @param idTourist number, id, of (Client)
     * @param voucher model(Voucher)
     * @param tour model(Tour)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
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

    /**
     * for finding all records in a table (Voucher) for current (Employee)
     * @param idEmployee number, id, of (Employee)
     * @param voucher model(Voucher)
     * @param tour model(Tour)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
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
    /**
     * for finding all records in a table (Voucher) for current (Tour) by parameter "date"
     * @param voucher model(Voucher)
     * @param tour model(Tour)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
    public static ResultSet selectVouchersForTour(Connection conn, Voucher voucher, Tour tour) throws SQLException {
        String query = "SELECT voucher.* FROM voucher WHERE " +
                " voucher.date > " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") +
                " AND voucher.tour = " + tour.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * for finding all records in a table (Tour) for current (TourOperator) by parameters of "name", "date" and "kind"
     * @param idTourOperator number, id, of (TourOperator)
     * @param direction  model(Direction)
     * @param tour model(Tour)
     * @return result set as model(Tour)
     * @throws SQLException
     */
    public static ResultSet selectTourForTourOperator(Connection conn, int idTourOperator,  Direction direction, Tour tour) throws SQLException {
        String query = "SELECT tour.* FROM tour WHERE tour.tour_operator = " + idTourOperator +
                " AND tour.hotel in ( SELECT id FROM hotel WHERE direction in " +
                "(SELECT id FROM direction WHERE name LIKE \'%" +  direction.getName()+ "%\')) AND " +
                "tour.kind LIKE "+ tour.getKind() + " AND " +
                "tour.date_start > " + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "");
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * for finding all records in a table (Client) for current (Voucher) by parameters of "surname" and "pasport"
     * @param idVoucher number, id, of (Voucher)
     * @param client model(client)
     * @return result set as model(client)
     * @throws SQLException
     */
    public static ResultSet selectTouristsForVoucher(Connection conn, int idVoucher,  Client client) throws SQLException {
        String query = "SELECT client.* FROM client " +
                " WHERE surname LIKE " + client.getSurname() + " AND pasport LIKE " + client.getPassport() +
                " AND client.id in (SELECT id_tourist FROM tourist WHERE id_voucher = " + idVoucher + ")";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    /**
     * delete one row in selected Table by number, ID, of record
     * @param tableName selected Table
     * @param id selected record
     * @return count(int) of selected rows
     * @throws SQLException
     */
    public static int deleteOneRow(Connection conn, String tableName, int id) throws SQLException {
        String query = "DELETE FROM " + DbHandler.DB_NAME + "." + tableName + " WHERE ID=" + id;
        int rowsCount = conn.createStatement().executeUpdate(query);
        return rowsCount;
    }

    /**
     * dellte one record from table Tourist and update the corresponding record in table (Tour), field "vouchers_count"
     * @param idTourist selected tourist(Client) by ID
     * @param idVoucher selected voucher(Voucher) by ID
     * @return count(int) of deleted rows
     * @throws SQLException
     */
    public static int deleteOneTourist(Connection conn, int idTourist, int idVoucher) throws SQLException {
        String query = "DELETE FROM tourist WHERE ID_TOURIST = " + idTourist + " AND ID_VOUCHER = " + idVoucher;
        int count = conn.createStatement().executeUpdate(query);
        if (count > 0)
        {
            query = "UPDATE tour SET  tour.vouchers_count = tour.vouchers_count-1 " +
                    "WHERE tour.id = (SELECT tour FROM voucher WHERE id = " + idVoucher + ")";
            count = conn.createStatement().executeUpdate(query);
        }
        return count;
    }

    /**
     * delete one (Voucher) from table Voucher and all Tourists with selectef (Voucher)
     * @param voucher model(Voucher)
     * @return count(int) of deleted rows
     * @throws SQLException
     */
    public static int deleteOneVoucher(Connection conn, Voucher voucher) throws SQLException {
        String query = "DELETE FROM tourist WHERE ID_VOUCHER = " + voucher.getId();
        int count = conn.createStatement().executeUpdate(query);
        if (count > 0)
        {
            query = "UPDATE tour SET  tour.vouchers_count = tour.vouchers_count-" + count +
                    "WHERE tour.id = (SELECT tour FROM voucher WHERE id = " + voucher.getId() + ")";
            conn.createStatement().executeUpdate(query);
        }
        String query2 = "DELETE FROM voucher WHERE ID = " + voucher.getId();
        count += conn.createStatement().executeUpdate(query2);
        return count;
    }
    /**
     * select first 100 rows in selected Table
     * @return result set for selected Table
     * @throws SQLException
     */
    public static ResultSet selectAllInTable(Connection conn, String tableName) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + "." + tableName + " LIMIT 0, 100";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Tour per selected exemplar of model(Kind)
     * @param kind model(Kind)
     * @return result set as model (Tour)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Kind kind) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE KIND=" + kind.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Tour per selected exemplar of model(Category)
     * @param category model(Category)
     * @return result set as model (Tour)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Category category) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE CATEGORY=" + category.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Hotel per selected exemplar of model(Direction)
     * @param direction model(Direction)
     * @return result set as model (Hotel)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Direction direction) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".HOTEL" + " WHERE DIRECTION=" + direction.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Tour per selected exemplar of model(Hotel)
     * @param hotel model(Hotel)
     * @return result set as model (Tour)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Hotel hotel) throws SQLException {
        String query = "SELECT * FROM " + DbHandler.DB_NAME + ".TOUR" + " WHERE HOTEL=" + hotel.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Voucher and Tourists per selected exemplar of model(Client)
     * @param client model(Client)
     * @return result set as model (Tourists)
     * @throws SQLException
     */
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
    /**
     * find rows in table Voucher per selected exemplar of model(Employee)
     * @param employee model(Employee)
     * @return result set as model (Voucher)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Employee employee) throws SQLException {
        String query = "SELECT * FROM voucher" + " WHERE employee =" + employee.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Tour per selected exemplar of model(TourOperator)
     * @param tourOperator model(TourOperator)
     * @return result set as model (Tour)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "SELECT * FROM TOUR" + " WHERE tour_operator =" + tourOperator.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * find rows in table Voucher per selected exemplar of model(Tour)
     * @param tour model(Tour)
     * @return result set as model (Voucher)
     * @throws SQLException
     */
    public static ResultSet selectReferences(Connection conn, Tour tour) throws SQLException {
        String query = "SELECT * FROM VOUCHER" + " WHERE tour =" + tour.getId();
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
    /**
     * add record in selected Table
     * @param kind model(Kind)
     * @return result set as model(Kind)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Kind kind) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".kind(name) VALUES (\'"+ kind.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param category model(Category)
     * @return result set as model(Category)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Category category) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".category(name, added_value, discount) " +
                "VALUES (\'"+ category.getName() +"\', \'" + category.getAddedValue() +"\', \'" + category.getDiscount() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param direction model(Direction)
     * @return result set as model(Direction)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Direction direction) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".DIRECTION(name) " +
                "VALUES (\'"+ direction.getName() +"\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param hotel model(Hotel)
     * @return result set as model(Hotel)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Hotel hotel) throws SQLException {
        String query = "INSERT  " + DbHandler.DB_NAME + ".HOTEL(name, address, direction) " +
                "VALUES (\'"+ hotel.getName() +"\', \'" + hotel.getAddress() + "\', " + hotel.getIdDirection() + ")";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param client model(Client)
     * @return result set as model(Client)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Client client) throws SQLException {
        String query = "INSERT CLIENT(name, surname, patronymic, sex, birth_date, pasport, phone, email) " +
                "VALUES (\'"+ client.getName() +"\', \'" + client.getSurname() + "\', \'" + client.getPatronymic() + "\', " +
                "\'" + client.getSex() + "\', \'" + client.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + "\', " +
                " \'" + client.getPassport() + "\', \'" + client.getPhone() + "\', \'" + client.getEmail() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param employee model(Employee)
     * @return result set as model(Employee)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "INSERT EMPLOYEE(name, surname, patronymic, email) " +
                "VALUES (\'"+ employee.getName() +"\', \'" + employee.getSurname() + "\', " +
                "\'" + employee.getPatronymic() + "\', \'" + employee.getEmail() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param tourOperator model(TourOperator)
     * @return result set as model(TourOperator)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "INSERT TOUR_OPERATOR(name, email, phone, unique_number) " +
                "VALUES (\'"+ tourOperator.getName() +"\', \'" + tourOperator.getEmail() + "\', " +
                "\'" + tourOperator.getPhone() + "\', \'" + tourOperator.getUniqueNumber() + "\')";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param tour model(Tour)
     * @return result set as model(Tour)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Tour tour) throws SQLException {
        String query = "INSERT INTO tour(name,tour_operator,offers_count,vouchers_count,hotel,price,date_start,days_count,kind,category) VALUES " +
                "(\'"+ tour.getName() +"\', " + tour.getIdTourOperator() + ", " + tour.getOffersCount() +  ", " +
                "" + tour.getVouchersCount() + ", " + tour.getIdHotel() + ", " +
                "" + tour.getPrice() + ", " + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + ", " +
                " " + tour.getDaysCount() + ", " + tour.getIdKind() + ", " + tour.getIdCategory() + ")";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }
    /**
     * add record in selected Table
     * @param voucher model(Voucher)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, Voucher voucher) throws SQLException {
        String query = "INSERT INTO voucher(tour, client, employee, date, cost) VALUES" +
                "(" + voucher.getIdTour() + ", " + voucher.getIdClient() + ", " + voucher.getIdEmployee() + ", " +
                " " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "")  + ", " +
                "" + voucher.getCost() + ")";
        int count = conn.createStatement().executeUpdate(query);
        return count;
    }

    /**
     * add record in Table Tourist
     * @param idTourist selected (Client)
     * @param idVoucher selected (Voucher)
     * @return count(int) of seelcted rows
     * @throws SQLException
     */
    public static int addOneRow(Connection conn, int idTourist, int idVoucher) throws SQLException {
        String query = "INSERT INTO tourist(id_tourist, id_voucher) VALUES " +
                "(" + idTourist + ", " + idVoucher + ")";
        int count = conn.createStatement().executeUpdate(query);
        if (count > 0)
        {
            query = "UPDATE tour SET  tour.vouchers_count = tour.vouchers_count+1 " +
                    "WHERE tour.id = (SELECT tour FROM voucher WHERE id = " + idVoucher + ")";
            count = conn.createStatement().executeUpdate(query);
        }
        return count;
    }
    /**
     * edit one row
     * @param kind model(Kind)
     * @return result set as model(Kind)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Kind kind) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".kind SET NAME = \'" + kind.getName() + "\' WHERE ID=" + kind.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param category model(Category)
     * @return result set as model(Category)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Category category) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".category SET " +
                "NAME = \'" + category.getName() + "\', ADDED_VALUE=\'" +category.getAddedValue()+"\', DISCOUNT=\'" + category.getDiscount() +"\'" +
                "WHERE ID=" + category.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param direction model(Direction)
     * @return result set as model(Direction)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Direction direction) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".DIRECTION SET " +
                "NAME = \'" + direction.getName() + "\' WHERE ID=" + direction.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param hotel model(Hotel)
     * @return result set as model(Hotel)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Hotel hotel) throws SQLException {
        String query = "UPDATE " + DbHandler.DB_NAME + ".HOTEL SET " +
                "NAME = \'" + hotel.getName() + "\', ADDRESS = \'" + hotel.getAddress() + "\', " +
                "DIRECTION = \'" + hotel.getIdDirection() + "\' WHERE ID=" + hotel.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param client model(Client)
     * @return result set as model(Client)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Client client) throws SQLException {
        String query = "UPDATE CLIENT SET " +
                "name = \'"+ client.getName() +"\', surname = \'" + client.getSurname() + "\', patronymic = \'" + client.getPatronymic() + "\', " +
                " sex = \'" + client.getSex() + "\', birth_date = \'" + client.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + "\', " +
                " pasport = " + client.getPassport() + ", phone = " + client.getPhone() + ", email = \'" + client.getEmail() + "\' WHERE ID=" + client.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param employee model(Employee)
     * @return result set as model(Employee)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Employee employee) throws SQLException {
        String query = "UPDATE EMPLOYEE SET " +
                "name = \'"+ employee.getName() +"\', surname = \'" + employee.getSurname() + "\', patronymic = \'" + employee.getPatronymic() + "\', " +
                " email = \'" + employee.getEmail() + "\' WHERE ID=" + employee.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param tourOperator model(TourOperator)
     * @return result set as model(TourOperator)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, TourOperator tourOperator) throws SQLException {
        String query = "UPDATE TOUR_OPERATOR SET " +
                "name = \'"+ tourOperator.getName() + "\', phone = \'" + tourOperator.getPhone() + "\', " +
                "unique_number = \'" + tourOperator.getUniqueNumber() + "\', email = \'" + tourOperator.getEmail() + "\' WHERE ID=" + tourOperator.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param tour model(Tour)
     * @return result set as model(Tour)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Tour tour) throws SQLException {
        String query = "UPDATE TOUR SET " +
                "name = \'"+ tour.getName() +"\', tour_operator = " + tour.getIdTourOperator() + ", offers_count = " + tour.getOffersCount() +  ", " +
                " vouchers_count = " + tour.getVouchersCount() + ", hotel = " + tour.getIdHotel() + ", " +
                " price = " + tour.getPrice() + ", date_start = " + tour.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") + ", " +
                " days_count = " + tour.getDaysCount() + ", kind = " + tour.getIdKind() + ", category = " + tour.getIdCategory() + " WHERE id = " + tour.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
    /**
     * edit one row
     * @param voucher model(Voucher)
     * @return result set as model(Voucher)
     * @throws SQLException
     */
    public static int editOneRow(Connection conn, Voucher voucher) throws SQLException {
        String query = "UPDATE voucher SET " +
                " tour = " + voucher.getIdTour()+ ", client =  " + voucher.getIdClient() + ", employee = " + voucher.getIdEmployee() +
                ", date = " + voucher.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "") +
                ", cost = " + voucher.getCost() +
                " WHERE id = " + voucher.getId();
        int set = conn.createStatement().executeUpdate(query);
        return set;
    }
}
