package com.example.turja.spotit2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViolationDetailsActivity extends Activity {
    String []key={"Violation Type","Location","Time","Details"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_details);
        ListView lv1= (ListView) findViewById(R.id.key);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.search_item, key);
        lv1.setAdapter(adapter);
        ListView lv2= (ListView) findViewById(R.id.value);
        Bundle bundle = getIntent().getExtras();
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.search_item, bundle.getStringArrayList("values"));
        lv2.setAdapter(adapter1);


    }
}
