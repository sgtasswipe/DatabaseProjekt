package kailua;


import java.io.Console;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class MySqlConnection {
    private String database = "jdbc:mysql://localhost:3306/kailua";
    private String username = "otto";
    private String password = "EnterPassHere";
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
            System.out.println(ConsoleColors.BLOOD_RED + "Exception: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customers (first_name, last_name, address, zipcode, city, phone_number, email, " +
                "drivers_license_number, drivers_license_issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            System.out.println(ConsoleColors.RUBY_RED + "EXCEPTION: " + e.getStackTrace() + ConsoleColors.RESET);
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
            System.out.println(ConsoleColors.RUBY_RED + "ERROR: " + "Customer is part of a leasing contract and " +
                    "cannot be deleted!" + ConsoleColors.RESET);
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
                Customer customer = new Customer(firstName, lastName, address, zipCode, city, phoneNr, email,
                        driversLicenseNumber, driversLicenseIssueDate);
                customers.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public ArrayList<Car> getAllCars() {

        ArrayList<Car> allCars = new ArrayList<>();
        String query = "SELECT * FROM cars_info";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String model = rs.getString("model");
                String brand = rs.getString("brand");
                FuelType fuelType = FuelType.valueOf(rs.getString("fuel_type").toUpperCase());
                String licensePlate = rs.getString("license_plate");
                Date registration = rs.getDate("registration");
                int odometer = rs.getInt("odometer");
                CarType carType = CarType.valueOf(rs.getString("type_of_car").toUpperCase());

                Car car = new Car(model, brand, fuelType, licensePlate, registration, odometer, carType);
                allCars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        return allCars;
    }


    public ArrayList<Customer> searchCustomerById(int customerId) {
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

    public void updateCustomerInfo(int drivers_license_number, String field, String newValue) {
        try {
            String query = "UPDATE Customers SET " + field + " = ? WHERE drivers_license_number = ?";
             PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, drivers_license_number);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer updated.");
            } else {
                System.out.println("No Customer found with the provided ID");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /* CARS METHODS GOES HERE */

    public void addCar(Car car) {
        String query = "INSERT INTO cars_info (model, brand, fuel_type, license_plate, registration, odometer, type_of_car) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getFuelType().toString().toLowerCase());
            preparedStatement.setString(4, car.getLicensePlate());
            preparedStatement.setDate(5, car.getRegistration());
            preparedStatement.setInt(6, car.getOdometer());
            preparedStatement.setString(7, car.getTypeOfCar().toString().toLowerCase());

            preparedStatement.executeUpdate();

            System.out.println("Car added successfully.");
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

    }

    public void deleteCar(String licensePlate) {
        String query = "DELETE FROM cars_info WHERE license_plate = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, licensePlate);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Car deleted successfully.");
            } else {
                System.out.println("Car with License plate " + licensePlate + " not found.");

            }

        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED + "ERROR: " + "License plate is part of a leasing contract" +
                    " and cannot be deleted!" + ConsoleColors.RESET);
        }
    }


    public void updateCarOdometer(String licensePlate, int newOdometer) {
        String query = "UPDATE cars_info  SET odometer = ? WHERE license_plate =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newOdometer);
            preparedStatement.setString(2, licensePlate);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Car updated.");
            } else {
                System.out.println("No car found with the provided license plate");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addContract(LeaseContract leaseContract) {
        String query = "INTERT INTO lease_contract (customer_id, odometer_start, license_plate, start_date, end_date, max_km) VALUES (?, ?, ?, ?, ?, ?) ";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, leaseContract.getCustomer_ID());
            preparedStatement.setInt(2, leaseContract.getOdometer_start());
            preparedStatement.setString(3, leaseContract.getLicense_plate());
            preparedStatement.setDate(4, leaseContract.getStart_time());
            preparedStatement.setDate(5, leaseContract.getEnd_time());
            preparedStatement.setInt(6, leaseContract.getMax_km());

            preparedStatement.executeUpdate();

            System.out.println("Contract added successfully.");
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED + "EXCEPTION: " + e.getStackTrace() + ConsoleColors.RESET);
        }
    }


    public ArrayList<LeaseContract> getAllContracts() {
        String query = "select lc.customer_id, lc.odometer_start, lc.license_plate, lc.start_date, lc.end_date, lc.max_km, " +
                "c.first_name, c.last_name, c.phone_number, c.email, c.drivers_license_number, c.drivers_license_issue_date, c.address, c.zipCode, c.city " +
                "from lease_contract lc " +
                "join customers c on lc.customer_id = c.drivers_license_number";
        ArrayList<LeaseContract> contracts = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                int odometerStart = rs.getInt("odometer_start");
                String licensePlate = rs.getString("license_plate");
                Date startDate = Date.valueOf(rs.getString("start_date"));
                Date endDate = Date.valueOf(rs.getString("end_date"));
                int maxKm = rs.getInt("max_km");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                long phoneNr = rs.getLong("phone_number");
                String email = rs.getString("email");
                int driversLicenseNumber = rs.getInt("drivers_license_number");
                Date driversLicenseIssueDate = rs.getDate("drivers_license_issue_date");
                String address = rs.getString("address");
                int zipCode = rs.getInt("zipCode");
                String city = rs.getString("city");

                Customer customer = new Customer(firstName, lastName, address, zipCode, city, phoneNr, email,
                        driversLicenseNumber, driversLicenseIssueDate);
                LeaseContract contract = new LeaseContract(customerId, odometerStart, licensePlate, startDate, endDate, maxKm);
                contracts.add(contract);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RUBY_RED + "EXCEPTION: " + e.getStackTrace() + ConsoleColors.RESET);
        }
        connection = null;
    }
}



