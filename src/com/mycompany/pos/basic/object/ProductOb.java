/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos.basic.object;

/**
 *
 * @author tdev
 */
public class ProductOb {
    private int id;
    private String name;
    private double price;
    private TypeOb type;

    public ProductOb(int id, String name, double price, TypeOb type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }
    
    public ProductOb(String name, double price, TypeOb type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }
     public ProductOb(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TypeOb getType() {
        return type;
    }

    public void setType(TypeOb type) {
        this.type = type;
    }
    
    
    
    
    
}
