package com.example.foober;

import java.util.ArrayList;

public class Order {
    ArrayList<Items> items=new ArrayList<>();
public void additem(String name,double price)
{
    Items items1=new Items();
    items1.setName(name);
    items1.setPrice(price);
    items.add(items1);
}

public double calbill()
{
    double total=0;
    for(Items i:items)
    {
        total+=i.getPrice();
    }
    return total;
}

}
