package com.example.myappv1.Adapter;

import com.example.myappv1.model.Item;

public interface ItemClickViewDetail {
    public void onClick(Item item);
    public void edit(Item item);

   public  void delete(Item item);
   public void pay(Item item);
}
