package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;

import networking.SendSearchRequest;

public class ShowRankList extends Activity {
    String []rankedTV={"Traffic Signal Violation","Bike in FootPath","Drive in Wrong Side","Unfit car"};
    ArrayList<String> key=new ArrayList<String>();
    ArrayList<Integer> count=new ArrayList<Integer>();
    String criteria=null;
    Context context;
    ListView listView;
//    public ShowRankList(ArrayList<String> key, ArrayList<Integer> count) {
//        this.key = key;
//        this.count = count;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rank_list);
        Bundle bundle = getIntent().getExtras();
        key = bundle.getStringArrayList("key");
        count = bundle.getIntegerArrayList("count");
        criteria = bundle.getString("criteria");
        String[] list = new String[key.size() + 1];
        list[0] = criteria;
        for (int i = 0; i < key.size(); i++) list[i + 1] = key.get(i);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_item, list);
        final ListView listView = (ListView) findViewById(R.id.key);
        listView.setAdapter(adapter);
        String[] countList = new String[key.size() + 1];
        countList[0] = "Count";
        for (int i = 0; i < key.size(); i++) countList[i + 1] = count.get(i).toString();
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.activity_item, countList);
        ListView listView1 = (ListView) findViewById(R.id.count);
        listView1.setAdapter(adapter1);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String s= (String) listView.getItemAtPosition(position);
                if(position!=0) {
                    if (criteria.equals("Location")) {
                        new SendSearchRequest(getApplicationContext()).execute(s, "", "", "", "");
                    } else if (criteria.equals("Day")) {

                    } else if (criteria.equals("Violation Type")) {
                        new SendSearchRequest(getApplicationContext()).execute("", "", "", "", s);
                    } else if (criteria.equals("Time Range")) {

                    }
                }
            }
        });
    }
}
