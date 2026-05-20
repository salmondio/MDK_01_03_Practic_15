package com.example.practic_15.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.practic_15.Classes.Adapters.CategoryAdapter;
import com.example.practic_15.Classes.Adapters.ItemAdapter;
import com.example.practic_15.Classes.Contexts.CategoryContext;
import com.example.practic_15.Classes.Contexts.ItemContext;
import com.example.practic_15.Classes.IOnClickInterface;
import com.example.practic_15.Classes.Models.Basket;
import com.example.practic_15.Classes.Models.Category;
import com.example.practic_15.Classes.Models.Item;
import com.example.practic_15.R;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Basket> BasketList = new ArrayList<Basket>();
    public ArrayList<Item> Items;
    public static MainActivity init;
    public Context Context; // ссылка на контекст

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Context = this; // запоминаем контекст
        init = this;

        ArrayList<Category> Categorys = CategoryContext.All(); // получаем категории
        Items = ItemContext.All(); // получаем товары

        RecyclerView CategoryList = findViewById(R.id.category_list); // получаем recyclerView для категорий
        RecyclerView CardList = findViewById(R.id.card_list); // получаем recyclerView для товаров

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categorys, Click); // Создаём адаптер для категорий
        CategoryList.setAdapter(CategoryAdapter); // назначаем адаптер для категорий

        ItemAdapter CardAdapter = new ItemAdapter(this, Items, AddBasket); // Создаём адаптер для предметов
        CardList.setAdapter(CardAdapter); // назначаем адаптер для предметов

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); // создаём транзакцию для фрагмента
        MenuNavigation fragment = new MenuNavigation(); // инициализируем фрагмент меню
        ft.add(R.id.menu_navigation, fragment); // добавляем фрагмент на экран
        ft.commit(); // запоминаем
    }

    public void OpenPopularView(View view) { // открытие популярных товаров
        Intent newIntent = new Intent(this, PopularActivity.class); // создаём интент
        newIntent.putExtra("Category", -1); // запоминаем категорию
        startActivity(newIntent); // открываем активность
    }

    IOnClickInterface Click = new IOnClickInterface() { // Создаём обработчик события при выборе категории
        @Override
        public void setClick(View view, int position) { // при нажатии на категорию
            Intent newIntent = new Intent(Context, PopularActivity.class); // создаём интент
            newIntent.putExtra("Category", position); // запоминаем категорию
            startActivity(newIntent); // открываем активность
        }
    };

    public IOnClickInterface AddBasket = new IOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Basket Item = BasketList.stream()
                    .filter(item -> item.Item.Id == position)
                    .findAny()
                    .orElse(null);
            Item FindItem = Items.stream().filter(item -> item.Id == position)
                    .findAny()
                    .orElse(null);
            if(Item == null){
                Item = new Basket(FindItem, 1);
                BasketList.add(Item);
            }
            else
                Item.Count++;

            Toast.makeText(Context, "Товар был добален в корзину", Toast.LENGTH_SHORT).show();

        }
    };

    public void OpenBasketView(View view){
        Intent newIntent = new Intent(this, BasketActivity.class);
        startActivity(newIntent);
    }
}