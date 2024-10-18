package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // constructor
    public Transaction(LocalDate _date, LocalTime _time, String _description, String _vendor, double _amount) {
        this.date = _date;
        this.time = _time;
        this.description = _description;
        this.vendor = _vendor;
        this.amount = _amount;
    }
    // getters and setters

    public String getVendor() {

        return vendor;
    }


    public LocalDate getDate() {

        return date;
    }

    public double getAmount() {
        return amount;
    }


    // Updated toString method to match the desired output format
    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%s|%s|%s|%s|%.2f", date, time.format(timeFormatter), description, vendor, amount);
    }
}
