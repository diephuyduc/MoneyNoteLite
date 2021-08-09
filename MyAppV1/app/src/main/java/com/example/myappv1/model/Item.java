package com.example.myappv1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item")
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
   private int id;
   private String title;
   private double total;
   private boolean isLoan;

    public Item( String title, double total, boolean isLoan) {

        this.title = title;
        this.total = total;
        this.isLoan = isLoan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isLoan() {
        return isLoan;
    }

    public void setLoan(boolean loan) {
        isLoan = loan;
    }
}
