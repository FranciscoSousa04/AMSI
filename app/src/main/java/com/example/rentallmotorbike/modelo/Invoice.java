package com.example.rentallmotorbike.modelo;

import java.util.List;

public class Invoice {
    private List<CartItem> items;
    private double total;


    public Invoice(List<CartItem> items){
        this.items = items;
        calculateTotal();
    }

    private void calculateTotal() {
        total=0;
        for (CartItem item : items) {
            total += item.getItemPrice() * item.getQuantity();
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}
