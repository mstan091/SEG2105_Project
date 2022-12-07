package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.adapters.ProcessOrderViewAdapter;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.misc.MealOrder;

public class ProcessOrderActivity extends AppCompatActivity {

    ArrayList<MealOrder> orders;
    ArrayList<HashMap<String, String>> viewDataOrder = new ArrayList<HashMap<String, String>>();
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_order);

        String cookEmail = (String) getIntent().getExtras().get("UserID");
        orders = db.getCurrentOrder(cookEmail);
        Log.d("order size ", String.valueOf(orders.size()));

        orders.forEach(order -> {
            HashMap<String, String> hmOrder = new HashMap<>();
            hmOrder.put(MealOrder.COOK_EMAIL, order.getCookEmail());
            hmOrder.put(MealOrder.CLIENT_EMAIL, order.getClientEmail());

            hmOrder.put(MealOrder.MEAL_NAME, order.getMealName());
            hmOrder.put(MealOrder.MEAL_TYPE, order.getMealType());
            hmOrder.put(MealOrder.CUISINE_TYPE, order.getCuisineType());

            //hmOrder.put(MealOrder.ORDER_STATUS, order.getStatus().toString());
            hmOrder.put(MealOrder.PICKUP_TIME, order.getPickupTime());
            hmOrder.put(MealOrder.ORDER_ID, String.valueOf(order.getRowID()));
            viewDataOrder.add(hmOrder);
        });

        String[] from = {MealOrder.MEAL_NAME, MealOrder.MEAL_TYPE, MealOrder.CUISINE_TYPE, MealOrder.PICKUP_TIME};
        int[] to = {R.id.tvMyMealName, R.id.tvMyMealType, R.id.tvMyCuisineType, R.id.tvMyPickupTime};

        ProcessOrderViewAdapter adptOrderView = new ProcessOrderViewAdapter(this, viewDataOrder, R.layout.process_order_item, from, to);
        adptOrderView.setDBHelper(db);
        ListView lvOrder = findViewById(R.id.lvProcessOrder);
        lvOrder.setAdapter(adptOrderView);


        Button btnBack = findViewById(R.id.btnBack2ProcessOrder);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });






    }
}