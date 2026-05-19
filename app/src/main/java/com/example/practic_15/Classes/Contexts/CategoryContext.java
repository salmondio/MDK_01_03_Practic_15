package com.example.practic_15.Classes.Contexts;

import com.example.practic_15.Classes.Models.Category;

import java.util.ArrayList;

public class CategoryContext {
    public static ArrayList<Category> All(){
        ArrayList<Category> Categories = new ArrayList<>();

        Categories.add(new Category(0, "Все"));
        Categories.add(new Category(1, "Outdoor"));
        Categories.add(new Category(2, "Tennis"));
        Categories.add(new Category(3, "Running"));
        Categories.add(new Category(4, "BDSM"));

        return Categories;
    }
}
