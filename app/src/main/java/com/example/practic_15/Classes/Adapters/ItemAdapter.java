package com.example.practic_15.Classes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practic_15.Classes.IOnClickInterface;
import com.example.practic_15.Classes.Models.Item;
import com.example.practic_15.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    public IOnClickInterface AddBasket;
    private LayoutInflater Inflater; // класс позволяющий из содержимого, создать View элемент
    private List<Item> Items; // список объектов

    public ItemAdapter(Context context, List<Item> items, IOnClickInterface addBasket) {
        this.Inflater = LayoutInflater.from(context); // инициализируем
        this.Items = items; // запоминаем объекты в переменную
        this.AddBasket = addBasket;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_card, parent, false); // создаём View из свёрстанного элемента
        return new ItemAdapter.ViewHolder(view); // возвращаем
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item Item = Items.get(position); // получаем данные из списка, для каждой View, свои данные
        holder.TvName.setText(Item.Name); // устанавливаем имя
        holder.TvModell.setText(Item.Model); // устанавливаем модель
        holder.TvPrice.setText("₽" + String.valueOf(Item.Price)); // устанавливаем стоимость
        holder.btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AddBasket.setClick(view, Item.Id);
            }
        });
    }

    @Override
    public int getItemCount() { return Items.size(); } // метод для получения количества элементов

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView TvName, TvModell, TvPrice; // текстовые поля для взаимодействия
        public LinearLayout btnAdd;

        ViewHolder(View view) {
            super(view);
            TvName = view.findViewById(R.id.tv_name); // находим имя
            TvModell = view.findViewById(R.id.tv_modell); // находим модель
            TvPrice = view.findViewById(R.id.tv_price); // находим стоимость

            btnAdd = view.findViewById(R.id.btnAdd);
        }
    }
}
