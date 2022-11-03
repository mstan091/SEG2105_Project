package uott.seg.mealerproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumComplaintStatus;
import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.misc.UserComplaint;

public class ComplaintViewAdapter extends SimpleAdapter implements View.OnClickListener {

    Context context;
    ArrayList<HashMap<String, String>> ahmCompl;
    protected DatabaseHandler db;

    public ComplaintViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;

        ahmCompl = (ArrayList<HashMap<String, String>>) data;
        Log.d("List size = ", String.valueOf(ahmCompl.size()));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        ListView lvComplaint = view.findViewById(R.id.lvComplaint);

        Button btnRevoke = view.findViewById(R.id.btnRevoke);
        Button btnSuspend = view.findViewById(R.id.btnSuspend);
        Button btnDismiss = view.findViewById(R.id.btnDismiss);

        btnRevoke.setOnClickListener(this);
        btnSuspend.setOnClickListener(this);
        btnDismiss.setOnClickListener(this);

        btnRevoke.setTag(position);
        btnSuspend.setTag(position);
        btnDismiss.setTag(position);

        TextView tvCoolEmail = view.findViewById(R.id.tvCookEmail);

        return view;

    }

    public void onClick(View v) {

        int n = (int)v.getTag();

        Log.d("Item index", String.valueOf(n));
        HashMap<String, String> complItem = ahmCompl.get(n);

        long rowId = Long.parseLong(complItem.get(UserComplaint.ROWID));
        Log.d("RowID " ,  String.valueOf(rowId));

        String cookEmail = complItem.get(UserComplaint.COOK_EMAIL);
        EnumCookStatus curStatus = db.getCookStatus(cookEmail);

        switch (v.getId()) {
            case R.id.btnRevoke:

                db.setComplaintStatus(EnumComplaintStatus.REVOKED_COOK, rowId);
                db.setCookStatus(cookEmail, EnumCookStatus.REVOKED);
                break;

            case R.id.btnSuspend:
                db.setComplaintStatus(EnumComplaintStatus.SUSPENDED_COOK, rowId);

                if (curStatus.getStatusCode() > EnumCookStatus.SUSPENDED.getStatusCode()) {
                    db.setCookStatus(cookEmail, EnumCookStatus.SUSPENDED);
                }
                break;

            case R.id.btnDismiss:

                db.setComplaintStatus(EnumComplaintStatus.DISMISS, rowId);
                break;

        }

        ahmCompl.remove(n);
        notifyDataSetChanged();

    }

    public void setDBHelper(DatabaseHandler db) {
        this.db = db;
    }
}
