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
   private String timeNotify;


    public Item(String title, double total, String timeNotify) {

        this.title = title;
        this.total = total;
        this.timeNotify = timeNotify;
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

    public String getTimeNotify() {
        return timeNotify;
    }

    public void setTimeNotify(String notify) {
        this.timeNotify = notify;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", total=" + total +
                ", timeNotify='" + timeNotify + '\'' +
                '}';
    }
}
