package uott.seg.mealerproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumOrderStatus;
import uott.seg.mealerproject.misc.MealOrder;

public class ProcessOrderViewAdapter extends SimpleAdapter implements View.OnClickListener {

    Context context;
    ArrayList<HashMap<String, String>> ahmOrder;
    protected DatabaseHandler db;

    public ProcessOrderViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        ahmOrder = (ArrayList<HashMap<String, String>>) data;
        Log.d("ADAPTER List size = ", String.valueOf(ahmOrder.size()));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        ListView lvOrder = view.findViewById(R.id.lvProcessOrder);

        Button btnAccept = view.findViewById(R.id.btnAccept);
        Button btnReject = view.findViewById(R.id.btnReject);

        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);

        btnAccept.setTag(position);
        btnReject.setTag(position);

        return view;
    }


    @Override
    public void onClick(View v) {

        int n = (int)v.getTag();

        Log.d("Item index", String.valueOf(n));
        HashMap<String, String> orderItem = ahmOrder.get(n);

        long rowId = Long.parseLong(orderItem.get(MealOrder.ORDER_ID));
        Log.d("RowID " ,  String.valueOf(rowId));

        switch (v.getId()) {
            case R.id.btnAccept:

                db.setOrderStatus(EnumOrderStatus.APPROVED, rowId);
                break;

            case R.id.btnReject:
                db.setOrderStatus(EnumOrderStatus.REJECTED, rowId);
                break;
        }

        ahmOrder.remove(n);
        notifyDataSetChanged();

    }

    public void setDBHelper(DatabaseHandler db) {
            this.db = db;
        }
}
