package FinalModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCRUD {
    
    final private String jdbcURL = "jdbc:mysql://localhost:3306/bootcamp";
    final private String jdbcUsername = "galatta";
    final private String jdbcPassword = "galatta";

    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO Employee" + 
            " (EmpLname, EmpFname, EmpAddress1, EmpAddress2, EmpCity, EmpState, EmpDOB, EmpBaseSalary) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_EMPLOYEE_BY_ID = "select EmpID, EmpLname, EmpFname, EmpAddress1, EmpAddress2, EmpCity, EmpState, EmpDOB, EmpBaseSalary from Employee where EmpID =?";
    private static final String SELECT_ALL_EMPLOYEE = "select * from Employee";
    private static final String DELETE_EMPLOYEE_SQL = "delete from Employee where EmpID = ?;";
    private static final String UPDATE_EMPLOYEE_SQL = "update users set EmpLname = ?, EmpFnamel= ?, EmpAddress1=? EmpAddress2= ?, EmpCity= ?, EmpState =? EmpDOB= ?, EmpBaseSalary =? where EmpID = ?;";

    public EmployeeCRUD(){}
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        //}
        return connection;
    }
//throws SQLException
    public void insertUser(Employee employee)  {
        System.out.println(INSERT_EMPLOYEE_SQL);
        // try-with-resource statement will auto close the connection.
        try 
        {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);
            preparedStatement.setString(1, employee.getLname());
            preparedStatement.setString(2, employee.getFname());
            preparedStatement.setString(3, employee.getAddress1());
            preparedStatement.setString(4, employee.getAddress2());
            preparedStatement.setString(5, employee.getCity());
            preparedStatement.setString(6, employee.getState());
            preparedStatement.setString(7, employee.getDOB());
            preparedStatement.setDouble(8, employee.getSalary());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Employee selectUser(int empid) {
        Employee employee = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);) {
            preparedStatement.setInt(1, empid);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String lname = rs.getString("EmpLname");
                String fname = rs.getString("EmpFname");
                String address1 = rs.getString("EmpAddress1");
                String address2= rs.getString("EmpAddress2");
                String city = rs.getString("EmpCity");
                String state = rs.getString("EmpState");
                String DOB = rs.getString("EmpAddress1");
                double salary = rs.getDouble("EmpAddress1");
                employee = new Employee(empid, lname, fname, address1, address2, city, state, DOB, salary);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employee;
    }

    public List < Employee > selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Employee > employee = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEE);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String lname = rs.getString("EmpLname");
                String fname = rs.getString("EmpFname");
                String address1 = rs.getString("EmpAddress1");
                String address2 = rs.getString("EmpAddress2");
                String city = rs.getString("EmpCity");                
                String state = rs.getString("EmpState"); 
                String DOB = rs.getString("EmpDOB");
                double salary = rs.getDouble("EmpBaseSalary");
                employee.add(new Employee(id, lname, fname, address1, address2, city, state, DOB, salary));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employee;
    }

    public boolean deleteUser(int empid) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);) {
            statement.setInt(1, empid);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(Employee employee) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL);) {
            statement.setString(1, employee.getLname());
            statement.setString(2, employee.getFname());
            statement.setString(3, employee.getAddress1());
            statement.setString(4, employee.getAddress2());
            statement.setString(5, employee.getCity());
            statement.setString(6, employee.getState());
            statement.setString(7, employee.getDOB());
            statement.setDouble(8, employee.getSalary());

            statement.setInt(9, employee.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
