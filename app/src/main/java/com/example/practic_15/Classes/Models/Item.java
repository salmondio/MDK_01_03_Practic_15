package com.example.practic_15.Classes.Models;

public class Item {
    public int Id;
    public String Name;
    public String Model;
    public Integer Price;
    public Integer IdCategory;
    public Item(String name, String model, Integer price, Integer idCategory){
        Name = name;
        Model = model;
        Price = price;
        IdCategory = idCategory;
    }
}
