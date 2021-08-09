package com.example.myappv1.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "detail")
public class Detail {
    @PrimaryKey(autoGenerate = true)
   private int id;
   private int itemId;
   private String title;
   private double money;
   private boolean isPayed;

   private Long lastUpdate;

   private Long firstNote;

    public Detail( int itemId, String title, double money, boolean isPayed, Long lastUpdate, Long firstNote) {

        this.itemId = itemId;
        this.title = title;
        this.money = money;
        this.isPayed = isPayed;
        this.lastUpdate = lastUpdate;
        this.firstNote = firstNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getFirstNote() {
        return firstNote;
    }

    public void setFirstNote(Long firstNote) {
        this.firstNote = firstNote;
    }
}
