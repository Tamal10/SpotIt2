package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import networking.SendPhotoRequest;
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

        TextView t= (TextView) findViewById(R.id.textView3);
        t.setText("Rank by"+criteria);

        final String[] list = new String[key.size() + 1];
        list[0] = criteria;
        for (int i = 0; i < key.size(); i++) list[i + 1] = key.get(i);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_item, list);

        String[] countList = new String[key.size() + 1];
        countList[0] = "Count";
        for (int i = 0; i < key.size(); i++) countList[i + 1] = count.get(i).toString();
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.activity_item, countList);


//        final ListView listView = (ListView) findViewById(R.id.key);
//        listView.setAdapter(adapter);
//        ListView listView1 = (ListView) findViewById(R.id.count);
//        listView1.setAdapter(adapter1);
//
//
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                String s= (String) listView.getItemAtPosition(position);
//                if(position!=0) {
//                    if (criteria.equals("Location")) {
//                        new SendSearchRequest(getApplicationContext()).execute(s, "", "", "", "");
//                    } else if (criteria.equals("Day")) {
//
//                    } else if (criteria.equals("Violation Type")) {
//                        new SendSearchRequest(getApplicationContext()).execute("", "", "", "", s);
//                    } else if (criteria.equals("Time Range")) {
//
//                    }
//                }
//            }
//        });


        TableLayout t1 = null;

        TableLayout tl = (TableLayout) findViewById(R.id.tv_list);
        TableRow thead=new TableRow(this);
        thead.setPadding(5,0,0,5);

        TextView col1 = new TextView(this);
        col1.setId(View.generateViewId());
        col1.setText(list[0]);
        col1.setTextSize(18);
        col1.setPadding(10,10,10,10);

        col1.setTextColor(Color.BLUE);
        thead.addView(col1);

        TextView col2 = new TextView(this);
        col2.setId(View.generateViewId());
        col2.setText(countList[0]);
        col2.setTextSize(18);
        col2.setPadding(15,10,10,10);
        col2.setTextColor(Color.BLUE);
        thead.addView(col2);

        tl.addView(thead, new TableLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.FILL_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

        for(int i=1;i<list.length;i++) {
            TableRow tr_head = new TableRow(this);
            tr_head.setPadding(5, 0, 0, 5);
            tr_head.setTag(i);
            tr_head.setFocusable(true);
            tr_head.setFocusableInTouchMode(true);
            tr_head.setClickable(true);
            if(i%2==0){
                tr_head.setBackgroundColor(Color.GRAY);
            }


            TextView date = new TextView(this);
            date.setId(View.generateViewId());
            date.setText(list[i]);
            date.setTextSize(18);
            date.setPadding(10,10,10,10);
            date.setTextColor(Color.BLACK);
            tr_head.addView(date);


            TextView loc = new TextView(this);
            loc.setId(View.generateViewId());
            loc.setText(countList[i]);
            loc.setTextSize(18);
            loc.setPadding(15,10,10,10);
            loc.setTextColor(Color.BLACK);
            tr_head.addView(loc);

            tl.addView(tr_head, new TableLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.FILL_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

            tr_head.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               int position= (int) view.getTag();
//                           Toast toast = Toast.makeText(getApplicationContext(), "Tabele row clicked "+ tag , Toast.LENGTH_LONG);
//                           toast.show();
                                                switch (position){
                                                    case 1:
                                                        new SendSearchRequest(getApplicationContext()).execute(list[position], "", "", "", "");
                                                        break;
                                                    case 2:
                                                        break;
                                                    case 3:
                                                        new SendSearchRequest(getApplicationContext()).execute("", "", "", "", list[position]);
                                                        break;
                                                    case 4:
                                                        break;
                                                }

//                                               new SendPhotoRequest(view.getContext()).execute(toPass);
                                           }
                                       }

            );
        }

    }
}
