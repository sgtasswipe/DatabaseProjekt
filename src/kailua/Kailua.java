package kailua;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Kailua {
    MySqlConnection mySqlConnection;
    public Kailua() {
        mySqlConnection = new MySqlConnection();
    }
    public static void main(String[] args) {
        new Kailua().run();
    }


    private void run() {
        boolean running = true;

        while (running) {
            MenuChoice menuChoice = showMainMenu();
            switch (menuChoice) {
                case CREATE_CUSTOMER -> createCustomer();
                case SHOW_ALL_CUSTOMERS -> showAllCustomers();

                case DELETE_CUSTOMER_BY_ID -> deleteCustomerById();
                case SEARCH_BY_CUSTOMER_ID -> searchByCustomerId();
                case QUIT -> running = false;
            }
        }
        mySqlConnection.closeConnection();
    }

    private void searchByCustomerId() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the customers id you want to seacrch for:");
        int searchId = in.nextInt();
        ArrayList<Customer> customers = mySqlConnection.searchCustomerById(searchId);
        printCustomer(customers);
    }

    private void deleteCustomerById() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the customer id you want to delete:");
        int id = in.nextInt();
       mySqlConnection.deleteCustomerById(id);
    }


    private void showAllCustomers() {
        ArrayList<Customer> customers = mySqlConnection.getAllCustomers();
        printCustomer(customers);
    }

    private void printCustomer(ArrayList<Customer> customers) {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private void createCustomer() {
        Customer customer = userCreatesCustomer();
        mySqlConnection.addCustomer(customer);
    }

    private Customer userCreatesCustomer() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Scanner in = new Scanner(System.in);

        System.out.println("\nCREATE CUSTOMER");
        System.out.print("First name: ");
        String firstName = in.nextLine();
        System.out.print("Last name: ");
        String lastName = in.nextLine();
        System.out.print("Address: ");
        String address = in.nextLine();
        System.out.print("Zip Code: ");
        int zipCode = in.nextInt();
        System.out.print("City: ");
        String city = in.nextLine();
        System.out.print("Phone Number: ");
        int phoneNr = in.nextInt();
        in.nextLine(); // ScannerBug
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Driver license number: ");
        int driversLicenseNumber = in.nextInt();
        in.nextLine(); // ScannerBug

        System.out.println("Driver License \n dd-MM-yyyy\n Date of issue: ");
        String driversLicenseIssueDateStr = in.nextLine();
        LocalDate driversLicenseIssueDate = LocalDate.parse(driversLicenseIssueDateStr, formatter);
        Date sqlDriversLicenseIssueDate = Date.valueOf(driversLicenseIssueDate);


        return new Customer(firstName, lastName, address, zipCode, city, phoneNr, email, driversLicenseNumber,
                sqlDriversLicenseIssueDate);
    }


    private MenuChoice showMainMenu() {
        Scanner in = new Scanner(System.in);

        System.out.println("\nMAIN MENU\n" +
                "1. Create customer\n" +
                "2. Show all customers\n" + "4. Delete customer by id\n" + "5. Search by customer id\n" +
                "Q. Quit\n");

        char choice = in.nextLine().toLowerCase().charAt(0);
        MenuChoice menuChoice = null;
        switch (choice) {
            case '1' -> menuChoice = MenuChoice.CREATE_CUSTOMER;
            case '2' -> menuChoice = MenuChoice.SHOW_ALL_CUSTOMERS;

            case '4' -> menuChoice = MenuChoice.DELETE_CUSTOMER_BY_ID;
            case '5' -> menuChoice = MenuChoice.SEARCH_BY_CUSTOMER_ID;
            case 'q' -> menuChoice = MenuChoice.QUIT;
        }
        return menuChoice;
    }
}


