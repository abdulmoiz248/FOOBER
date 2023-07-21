package com.example.foober;

public class Driver {
    private String name;
    private double basefare;
    private String Vehicle;
    private double fare;

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    @Override
    public String toString() {
        return name+" Rs."+fare;
    }
   public void calculatefare(int i)
   {
      fare=  this.basefare+(i+1)*10;
   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasefare() {
        return basefare;
    }

    public void setBasefare(double basefare) {
        this.basefare = basefare;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
//may be fare calculator fun
}
