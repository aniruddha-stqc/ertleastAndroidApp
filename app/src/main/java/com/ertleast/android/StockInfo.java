package com.ertleast.android;

public class StockInfo {
    private String name;
    private String id;
    private String city;
    private String salary;

    public StockInfo(String name, String id, String city, String salary) {
        this.name = name;
        this.id = id;
        this.city = city;
        this.salary = salary;
    }

    // @Override
    public String toString() {
        return "Employee: name = " + name + "; Id = " + id + "; City = " + city
                + "; Salary = " + salary;
    }
}
