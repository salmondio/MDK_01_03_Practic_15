package com.example.practic_15.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.practic_15.R;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practic_15.Classes.Models.Basket;
import com.example.practic_15.Classes.Adapters.BasketAdapter;
import com.example.practic_15.Classes.IOnClickInterface;

public class BasketActivity extends AppCompatActivity {

    public RecyclerView BasketRV; // список элементов в корзине
    public TextView tvSum, tvAllSum; // поля для отображения сумм
    public BasketAdapter BasketAdapter; // адаптер для RecyclerView
    public Context Context; // контекст активности

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basket);

        Context = this; // сохраняем контекст
        BasketRV = findViewById(R.id.basket_list); // инициализируем RecyclerView
        tvSum = findViewById(R.id.tv_sum); // поле для суммы товаров
        tvAllSum = findViewById(R.id.tv_all_sum); // поле для итоговой суммы

        // Инициализируем ItemTouchHelper для свайпов
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(SwipeAdapter);
        itemTouchHelper.attachToRecyclerView(BasketRV);

        // Создаём адаптер и привязываем его к RecyclerView
        BasketAdapter = new BasketAdapter(Context, MainActivity.init.BasketList, Delete, EventCost);
        BasketRV.setAdapter(BasketAdapter);

        CostCalculation(); // пересчитываем стоимость при запуске
    }

    public IOnClickInterface Delete = new IOnClickInterface() { // событие удаления товара в корзине
        @Override
        public void setClick(View view, int position) {
            MainActivity.init.BasketList.remove(position); // удаляем элемент из корзины
            BasketRV.setAdapter(BasketAdapter); // обновляем адаптер
        }
    };

    public IOnClickInterface EventCost = new IOnClickInterface() { // событие перерасчёта стоимости
        @Override
        public void setClick(View view, int position) {
            CostCalculation(); // вызываем метод перерасчёта стоимости
        }
    };


    // Обработчик свайпов (удаления элементов)
    ItemTouchHelper.SimpleCallback SwipeAdapter = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false; // не разрешаем перетаскивание
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            BasketRV.setAdapter(BasketAdapter); // обновляем адаптер после свайпа
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, r.getDisplayMetrics());

            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count);

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX < -px) {
                    bthDelete.setVisibility(View.VISIBLE);
                    bthCount.setVisibility(View.GONE);
                } else if (dX > px) {
                    bthDelete.setVisibility(View.GONE);
                    bthCount.setVisibility(View.VISIBLE);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    // Метод для пересчёта стоимости товаров в корзине
    public void CostCalculation() {
        float ItemPrice = 0;
        for (Basket Item : MainActivity.init.BasketList) {
            ItemPrice += Item.Item.Price * Item.Count; // считаем стоимость товаров
        }
        tvSum.setText("₽" + ItemPrice); // выводим сумму товаров

        ItemPrice += 60.20; // добавляем стоимость доставки
        tvAllSum.setText("₽" + ItemPrice); // выводим итоговую сумму
    }

    // Метод для закрытия активности
    public void ClosePopularActivity(View view) {
        finish(); // завершаем активность
    }
}