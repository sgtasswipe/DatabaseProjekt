package kailua;


import java.io.Console;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MySqlConnection {
    private String database = "jdbc:mysql://localhost:3306/kailua";
    private String username = "otto";
    private String password = "root";
    private Connection connection = null;

    public MySqlConnection() {
        createConnection();
    }

    private void createConnection() {
        if (connection != null)
            return; // If connection already created, just return that to ensure singleton

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);
        } catch (Exception e) {
            System.out.println(ConsoleColors.BLOOD_RED+"Exception: " + e.getMessage()+ConsoleColors.RESET);
        }
    }

    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customers (first_name, last_name, address, zipcode, city, phone_number, email," +
                " s, drivers_license_issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getZipCode());
            preparedStatement.setString(5, customer.getCity());
            preparedStatement.setLong(6, customer.getPhoneNr());
            preparedStatement.setString(7, customer.geteMail());
            preparedStatement.setLong(8, customer.getDriversLicenseNumber());
            preparedStatement.setDate(9, customer.getDriversLicenseIssueDate());

            preparedStatement.executeUpdate();

            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED+"EXCEPTION: " + e.getStackTrace()+ConsoleColors.RESET);
        }
    }

    public void deleteCustomerById(int id) {
        
        String query = "DELETE FROM customers WHERE drivers_license_number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED + "EXCEPTION: " + e.getMessage() + ConsoleColors.RESET);
        }
    }


    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                int zipCode = rs.getInt("zipcode");
                String city = rs.getString("city");
                long phoneNr = rs.getLong("phone_number");
                String email = rs.getString("email");
                int driversLicenseNumber = rs.getInt("drivers_license_number");
                Date driversLicenseIssueDate = rs.getDate("drivers_license_issue_date");
                Customer customer = new Customer(firstName, lastName, address, zipCode, city, phoneNr, email, driversLicenseNumber, driversLicenseIssueDate);
                customers.add(customer);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return customers;
    }
       /*  public void sortAllCars () {
        ArrayList<Car> allCars = new ArrayList<>();
        ArrayList<Car> allSportsCars = new ArrayList<>();
        ArrayList<Car> allFamilyCars = new ArrayList<>();
        ArrayList<Car> allLuxuryCars = new ArrayList<>();

            String query = "SELECT * FROM cars_info";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            for (int i = 0; i < query.; i++) {


            }

            for (Car car: allCars                 ) {
                if (car==CarType.SPORT){
                    allSportCars.add(car);
                }
                if (car=CarType.FAMILY){
                    allFamilyCars.add(car);
            }
                if(car=CarType.LUXURY){
                    allFamilyCars.add(car);
                }


        }
    } */

    public ArrayList<Customer> searchCustomerById( int customerId) {
      ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM customers WHERE drivers_license_number = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
               customerId = rs.getInt("drivers_license_number");
               String address = rs.getString("address");
               int zipCode = rs.getInt("zipcode");
               String city = rs.getString("city");
               long phoneNr = rs.getLong("phone_number");
               String eMail = rs.getString("email");
               Date issuedate = rs.getDate("drivers_license_issue_date");
               Customer customer = new Customer(firstName, lastName, address, zipCode, city, phoneNr,
                       eMail, customerId, issuedate);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED+"EXCEPTION: " + e.getStackTrace()+ConsoleColors.RESET);
        }
        connection = null;
    }
}
