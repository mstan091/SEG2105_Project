package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumComplaintStatus;
import uott.seg.mealerproject.adapters.ComplaintViewAdapter;
import uott.seg.mealerproject.misc.UserComplaint;

public class ComplaintActivity extends AppCompatActivity {
    ArrayList<UserComplaint> userComplaints;
    ArrayList<HashMap<String, String>> viewDataCompl = new ArrayList<HashMap<String, String>>();
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        userComplaints = db.getUserComplaint(EnumComplaintStatus.NEW);
        Log.d("compl size ", String.valueOf(userComplaints.size()));

        userComplaints.forEach(compl -> {
            HashMap<String, String> complItem = new HashMap<>();
            complItem.put(UserComplaint.ROWID, String.valueOf(compl.getComplId()));
            complItem.put(UserComplaint.COOK_EMAIL  , compl.getCookEmail());
            complItem.put(UserComplaint.COMPLAINT, compl.getUserCompl());
            complItem.put(UserComplaint.CLIENT_EMAIL, compl.getClientEmail());

            viewDataCompl.add(complItem);
        });

        String[] from = {UserComplaint.COOK_EMAIL, UserComplaint.COMPLAINT, UserComplaint.CLIENT_EMAIL};
        int[] to = {R.id.tvCookEmail, R.id.tvComplaint, R.id.tvClientEmail};

        ComplaintViewAdapter adptCompl = new ComplaintViewAdapter(this, viewDataCompl, R.layout.complait_item, from, to);
        adptCompl.setDBHelper(db);
        ListView lvComplaint = findViewById(R.id.lvComplaint);
        lvComplaint.setAdapter(adptCompl);

        lvComplaint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Listview click ", String.valueOf( i));
            }
        });

    }



}