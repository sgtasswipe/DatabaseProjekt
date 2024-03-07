package kailua;

import java.sql.Date;

public class LeaseContract {

    private int customer_ID;
    private int odometer_start;
    private String license_plate;
    private Date start_time;
    private Date end_time;
    private int max_km;

    public LeaseContract(int customer_ID, int odometer_start, String license_plate, Date start_time, Date end_time, int max_km) {
        this.customer_ID = customer_ID;
        this.odometer_start = odometer_start;
        this.license_plate = license_plate;
        this.start_time = start_time;
        this.end_time = end_time;
        this.max_km = max_km;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getOdometer_start() {
        return odometer_start;
    }

    public void setOdometer_start(int odometer_start) {
        this.odometer_start = odometer_start;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getMax_km() {
        return max_km;
    }

    public void setMax_km(int max_km) {
        this.max_km = max_km;
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
                "customer_ID=" + customer_ID +
                ", odometer_start=" + odometer_start +
                ", license_plate='" + license_plate + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", max_km=" + max_km +
                '}';
    }
}
