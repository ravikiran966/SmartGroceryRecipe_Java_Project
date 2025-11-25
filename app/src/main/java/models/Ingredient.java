package com.example.smartgrocery.models;


public class Ingredient {
    private String name;
    private double quantity;
    private String unit;


    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }


    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }


    public void setName(String name) { this.name = name; }
    public void setQuantity(double q) { this.quantity = q; }
    public void setUnit(String u) { this.unit = u; }
}