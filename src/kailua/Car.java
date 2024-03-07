package kailua;

import java.sql.Date;

public class Car {
    
    private String model;
    private String brand;
    private FuelType fuelType;
    private String licensePlate;
    private java.sql.Date registration;
    private int odometer;
    private CarType typeOfCar;

    public Car(String model, String brand, FuelType fuelType, String licensePlate, Date registration,
               int odometer, CarType typeOfCar) {
        this.model = model;
        this.brand = brand;
        this.fuelType = fuelType;
        this.licensePlate = licensePlate;
        this.registration = registration;
        this.odometer = odometer;
        this.typeOfCar = typeOfCar;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public java.sql.Date getRegistration() {
        return registration;
    }

    public void setRegistration(java.sql.Date registration) {
        this.registration = registration;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public CarType getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(CarType typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    @Override
    public String toString() {
        String typeOfCarLowerCase = typeOfCar.toString().charAt(0) + typeOfCar.toString().substring(1).toLowerCase();
        String fuelTypeLowerCase = fuelType.toString().charAt(0) + fuelType.toString().substring(1).toLowerCase();

        return ConsoleColors.ORANGE+"Car: " + brand +" " + model + ". Fuel: " + fuelTypeLowerCase + ". Licence plate: "
                + licensePlate + ". Registration: " + registration +  ". Odometer: " + odometer + ". Car type: " +
                typeOfCarLowerCase + "." + ConsoleColors.RESET;
    }
}
