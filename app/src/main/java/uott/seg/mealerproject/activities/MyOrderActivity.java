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
import uott.seg.mealerproject.adapters.MyOrderViewAdapter;
import uott.seg.mealerproject.adapters.ProcessOrderViewAdapter;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.misc.MealOrder;

public class MyOrderActivity extends AppCompatActivity {


    ArrayList<MealOrder> orders;
    ArrayList<HashMap<String, Object>> viewDataOrder = new ArrayList<HashMap<String, Object>>();
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        String clientEmail = (String) getIntent().getExtras().get("UserID");
        orders = db.getMyCurrentOrder(clientEmail);
        Log.d("order size ", String.valueOf(orders.size()));

        orders.forEach(order -> {
            HashMap<String, Object> hmOrder = new HashMap<>();
            hmOrder.put(MealOrder.COOK_EMAIL, order.getCookEmail());
            hmOrder.put(MealOrder.CLIENT_EMAIL, order.getClientEmail());

            hmOrder.put(MealOrder.MEAL_NAME, order.getMealName());
            hmOrder.put(MealOrder.MEAL_TYPE, order.getMealType());
            hmOrder.put(MealOrder.CUISINE_TYPE, order.getCuisineType());

            hmOrder.put(MealOrder.ORDER_STATUS, order.getStatus().toString());
            hmOrder.put(MealOrder.PICKUP_TIME, order.getPickupTime());
            hmOrder.put(MealOrder.ORDER_ID, order.getRowID());


            viewDataOrder.add(hmOrder);
        });

        String[] from = {MealOrder.MEAL_NAME, MealOrder.MEAL_TYPE, MealOrder.CUISINE_TYPE, MealOrder.PICKUP_TIME, MealOrder.COOK_EMAIL, MealOrder.ORDER_STATUS};
        int[] to = {R.id.tvMyMealName, R.id.tvMyMealType, R.id.tvMyCuisineType, R.id.tvMyPickupTime, R.id.tvMyCookEmail, R.id.tvOrderStatus};

        MyOrderViewAdapter adptMyOrderView = new MyOrderViewAdapter(this, viewDataOrder, R.layout.my_order_item, from, to);
        adptMyOrderView.setDBHelper(db);
        ListView lvOrder = findViewById(R.id.lvMyOrder);
        lvOrder.setAdapter(adptMyOrderView);


        Button btnBack = findViewById(R.id.btnBack2ClientWelcome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}