package com.example.foober;

import java.util.ArrayList;

public class Menu {
    ArrayList<Items> items=new ArrayList<>();
    public void additem(String name,double price)
    {
        Items items1=new Items();
        items1.setName(name);
        items1.setPrice(price);
        items.add(items1);
    }
    public void display()
    {
        for(Items i: items) {
            System.out.println(i.getName());
            System.out.println(i.getPrice());
        }
    }
}
