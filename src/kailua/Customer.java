package kailua;

import java.sql.Date;


public class Customer {
    private String firstName;
    private String lastName;
    private String address;
    private int zipCode;
    private String city;
    private long phoneNr;
    private String eMail;
    private long driversLicenseNumber;
    private Date driversLicenseIssueDate;


    public Customer(String firstName, String lastName, String address, int zipCode, String city, long phoneNr,
                    String eMail, long driversLicenseNumber, Date driversLicenseIssueDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNr = phoneNr;
        this.eMail = eMail;
        this.driversLicenseNumber = driversLicenseNumber;
        this.driversLicenseIssueDate = driversLicenseIssueDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(long phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public long getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public void setDriversLicenseNumber(long driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public java.sql.Date getDriversLicenseIssueDate() {
        return driversLicenseIssueDate;
    }

    public void setDriversLicenseIssueDate(java.sql.Date driversLicenseIssueDate) {
        this.driversLicenseIssueDate = driversLicenseIssueDate;
    }

    @Override
    public String toString() {
        String orange = ConsoleColors.ORANGE;
        String aqua = ConsoleColors.AQUA;

        return orange + "Name: " + aqua+firstName + " " + lastName +"."+orange+
                " Address: "+aqua+ address + ", " + zipCode +  ", " +  city + "."+ orange+
                " Phone and email: " +aqua +phoneNr +", " + eMail+"." + orange+
                " Drivers licence: Number: " +  aqua+driversLicenseNumber +"."+ orange +
                " Issue date: " + aqua +driversLicenseIssueDate + "."+ ConsoleColors.RESET;
    }
}
