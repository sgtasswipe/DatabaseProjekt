package kailua;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Kailua {
    boolean running = true;

    MySqlConnection mySqlConnection;

    Scanner in = new Scanner(System.in);

    public Kailua() {
        mySqlConnection = new MySqlConnection();
    }

    public static void main(String[] args) {
        new Kailua().run();
    }


    private void run() {

        while (running) {
            MainMenuChoice mainMenuChoice = showMainMenu();
            switch (mainMenuChoice) {
                case CAR_MENU -> showCarMenu();
                case CUSTOMER_MENU -> showCustomerMenu();
                case CONTRACT_MENU -> showContractMenu();
                case QUIT -> running = false;
            }
        }
        mySqlConnection.closeConnection();
    }

    private MenuChoice showContractMenu() {
        System.out.println(ConsoleColors.LIGHT_GOLD + "\nCONTRACT MENU\n" +
                "1. Create Contract\n" +
                "2. Show all Contracts\n" + "3. Delete contract \n" +
                "9. Quit\n" + ConsoleColors.RESET);

        int choice = in.nextInt();
        MenuChoice menuChoice = null;
        switch (choice) {
            case 1 -> createContract();
            case 2 -> showAllContracts();
            case 3 -> deleteContract();
         //   case 9 -> running -> false;
        }
        return menuChoice;

    }
     public  void printContracts (ArrayList<LeaseContract> leaseContracts ) {
         for (LeaseContract l : leaseContracts) {
             System.out.println(l);

         }
     }
    private void showAllContracts() {
        printContracts(mySqlConnection.getAllContracts());
    }

    private void createContract() {
        LeaseContract leaseContract = userCreatesContract();
     mySqlConnection.addContract(leaseContract);
    }
    public void deleteContract() {
        System.out.println("This will deleted all contracts with associated ID");
        System.out.println("Enter the contract id you want to delete:");
        int id = in.nextInt();
        mySqlConnection.deleteContract(id);
    }

    private LeaseContract userCreatesContract() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Enter customer id");
        int id = in.nextInt();
        System.out.println("Enter odometer start value");
        int odometerStart = in.nextInt();
        in.nextLine(); // scanner bug
        System.out.println("Enter license plate");
        String plate = in.nextLine();
        System.out.println("Enter Start date in format DD-mm-YYYY");
        String startDateStr = in.nextLine();
        LocalDate startDateLocal = LocalDate.parse(startDateStr, formatter);
        Date sqlStartDate = Date.valueOf(startDateLocal);
        System.out.println("Enter End date in format DD-mm-YYYY");
        String endDateStr = in.nextLine();
        LocalDate endDateLocal = LocalDate.parse(endDateStr, formatter);
        Date sqlEndDate = Date.valueOf(endDateLocal);
        System.out.println("Enter max Km For given contract");
        int maxKm = in.nextInt();

        LeaseContract leaseContract = new LeaseContract(id,odometerStart,plate,sqlStartDate,sqlEndDate,maxKm);
        return  leaseContract;

    }

    private void showCarMenu() {
        System.out.println(ConsoleColors.LIGHT_GOLD + "\nCAR MENU\n" + "1. Add new car.\n" +
                "2. Delete car from system.\n" + "3. Update car.\n" + "4. Show list of all cars.\n" +
                "5. Show list of all family cars.\n" + "6. Show list of all luxury cars.\n" +
                "7. Show list of all sports cars.\n" + "9. Quit\n" + ConsoleColors.RESET);

        int choice = in.nextInt();
        MenuChoice menuChoice = null;
        switch (choice) {
            case 1 -> createCar();
            case 2 -> deleteCar();
            case 3 -> updateCarOdomoter();
            case 4 -> printAllCars();
            case 5 -> printAllFamilyCars();
            case 6 -> printAllLuxuryCars();
            case 7 -> printAllSportCars();
            case 9 -> running = false;
        }
    }

    private void showCustomerMenu() {
        System.out.println(ConsoleColors.LIGHT_GOLD + "\nMAIN MENU\n" +
                "1. Create customer\n" +
                "2. Show all customers\n" + "3. Delete customer by id\n" + "4. Search by customer id\n" + "5. Update Customer \n" +
                "9. Quit\n" + ConsoleColors.RESET);

        int choice = in.nextInt();

        switch (choice) {
            case 1 -> createCustomer();
            case 2 -> showAllCustomers();
            case 3 -> deleteCustomerById();
            case 4 -> searchByCustomerId();
            case 5 -> updateCustomerInfo();
            case 9 -> running = false;
        }
    }



    private MainMenuChoice showMainMenu() {
        System.out.println(ConsoleColors.LIGHT_GOLD + "\nMAIN MENU\n" +
                "1. CAR MENU\n" +
                "2. CUSTOMER MENU \n" +
                "3. CONTRACT MENU \n" +
                "9. Quit\n" + ConsoleColors.RESET);

        int choice = in.nextInt();
        MainMenuChoice mainMenuChoice = null;
        switch (choice) {
            case 1 -> mainMenuChoice = MainMenuChoice.CAR_MENU;
            case 2 -> mainMenuChoice = MainMenuChoice.CUSTOMER_MENU;
            case 3 -> mainMenuChoice = MainMenuChoice.CONTRACT_MENU;
            case 9 -> mainMenuChoice = MainMenuChoice.QUIT;
        }
        return mainMenuChoice;
    }

    /////CUSTOMER METHODS/////
    private void updateCustomerInfo() {
        System.out.println("Enter ID / license number for the customer you want to update");
        int Id = in.nextInt();
        in.nextLine(); // scanner bug
        System.out.println("Please enter which field you want to update (first_name, last_name, address.)");
        String field = in.nextLine();
        System.out.println("Please enter the new value");
        String newValue = in.nextLine();
        mySqlConnection.updateCustomerInfo(Id,field,newValue);

        }


    private void searchByCustomerId() {


        System.out.println("Enter the customers id you want to search for:");
        int searchId = in.nextInt();
        ArrayList<Customer> customers = mySqlConnection.searchCustomerById(searchId);
        printCustomer(customers);
    }

    private void deleteCustomerById() {

        System.out.println("Enter the customer id you want to delete:");
        int id = in.nextInt();
        mySqlConnection.deleteCustomerById(id);
    }


    private void showAllCustomers() {
        ArrayList<Customer> customers = mySqlConnection.getAllCustomers();
        printCustomer(customers);
    }
    private void printAllCars(){
        ArrayList<Car> allCars = mySqlConnection.getAllCars();
        printCar(allCars);
    }
    private void printCar(ArrayList<Car> allCars){

            for (Car c: allCars             ) {
            System.out.println(c);
        }
    }

    public void printAllFamilyCars() {
        ArrayList<Car> allCars = mySqlConnection.getAllCars();
        for (Car car : allCars ) {
            if (car.getTypeOfCar() == CarType.FAMILY) {
            System.out.println(car);
             }
        }
    }

    public void printAllSportCars() {
                ArrayList<Car> allCars = mySqlConnection.getAllCars();
                for (Car car : allCars ) {

                    if (car.getTypeOfCar() == CarType.SPORT) {
                        System.out.println(car);
                    }
                }
    }

    public void printAllLuxuryCars() {
            ArrayList<Car> allCars = mySqlConnection.getAllCars();
            for (Car car : allCars ) {

                if (car.getTypeOfCar() == CarType.LUXURY) {
                    System.out.println(car);
                }
            }
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
        in.nextLine(); // scanner bug
        System.out.println("\nCREATE CUSTOMER");
        System.out.print("First name: ");
        String firstName = in.nextLine();
        System.out.print("Last name: ");
        String lastName = in.nextLine();
        System.out.print("Address: ");
        String address = in.nextLine();
        System.out.print("Zip Code: ");
        int zipCode = in.nextInt();
        in.nextLine();
        System.out.print("City: ");
        String city = in.nextLine();
        System.out.print("Phone Number: ");
        int phoneNr = in.nextInt();
        in.nextLine(); // ScannerBug
        String email = typeEmailAddress();
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

    private String typeEmailAddress() {
        String email = "";
        while (email.isEmpty() || !isValid(email)) {
            System.out.println("Please enter the new members email: ");
            email = in.nextLine().trim();

            if (email.isEmpty()) {  // Sikre at emailen er i korrekt format, ellers skal den prompte igen
                System.out.println("Field can't be empty. Try again!");
            } else if (!isValid(email)) {
                System.out.println("Invalid email. Try again!");
            }
        }
        return email;
    }

    private boolean isValid(String email) {     // Logik til at tjekke om emailen er i korrekt format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    /////CAR METHODS/////


    private void createCar() {
        Car car = userCreatesCar();
        mySqlConnection.addCar(car);
    }

    private Car userCreatesCar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("\nCREATE CAR");
        System.out.println("Model: ");
        in.nextLine();
        String model = in.nextLine();
        System.out.println("Brand: ");
        String brand = in.nextLine();
        FuelType fuelType = chooseFuelType();
        in.nextLine(); // scanner bug
        System.out.println("License Plate: ");
        String licensePlate = in.nextLine();
        System.out.println("Registration date: (dd-MM-yyyy) ");
        String registrationDateStr = in.nextLine();
        LocalDate registrationDate = LocalDate.parse(registrationDateStr, formatter);
        Date registrationDateSql = Date.valueOf(registrationDate);
        System.out.println("Kilometers driven: ");
        int odometer = in.nextInt();
        CarType carType = chooseCarType();

        return new Car(model, brand, fuelType, licensePlate, registrationDateSql, odometer, carType);
    }


    private FuelType chooseFuelType() {
        FuelType fuelType = null;
        System.out.println("Choose Fueltype:");
        System.out.println("1. Gasoline");
        System.out.println("2. Diesel");
        System.out.println("3. Electric");

        int userInput = in.nextInt();

        switch (userInput) {
            case 1 -> fuelType = FuelType.GASOLINE;
            case 2 -> fuelType = FuelType.DIESEL;
            case 3 -> fuelType = FuelType.ELECTRIC;

        }
        return fuelType;
    }

    private CarType chooseCarType() {
        CarType carType = null;
        System.out.println("Choose type of car: ");
        System.out.println("1. Family");
        System.out.println("2. Luxury");
        System.out.println("3. Sport");

        int userInput = in.nextInt();

        switch (userInput) {
            case 1 -> carType = CarType.FAMILY;
            case 2 -> carType = CarType.LUXURY;
            case 3 -> carType = CarType.SPORT;
        }

        return carType;
    }

    private void deleteCar() {
        in.nextLine(); //scanner bug
        System.out.println("Enter the licence plate you want to delete:");
        String licensePlate = in.nextLine();
        mySqlConnection.deleteCar(licensePlate);
    }

    private void updateCarOdomoter() {
        in.nextLine(); //scanner bug
        System.out.println("Enter the licence plate: ");
        String licensePlate = in.nextLine();
        System.out.println("Kilometers driven: ");
        int odometer = in.nextInt();
        mySqlConnection.updateCarOdometer(licensePlate, odometer);
    }
}





