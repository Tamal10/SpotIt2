package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

import networking.SearchDay;
import networking.SendPhotoRequest;
import networking.SendSearchRequest;
import networking.SendTypesRequest;

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
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200,90,90,90)));

        Bundle bundle = getIntent().getExtras();
        key = bundle.getStringArrayList("key");
        count = bundle.getIntegerArrayList("count");
        criteria = bundle.getString("criteria");

        TextView t= (TextView) findViewById(R.id.textView3);
        t.setText("Rank by "+criteria);

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
        col1.setTextSize(22);
        col1.setPadding(10,10,10,10);
        col1.setTypeface(null, Typeface.BOLD);
//        col1.setMinimumWidth(100);
//        col1.setMinWidth(50);
        col1.setWidth(100);
        col1.setTextColor(Color.BLUE);

        thead.addView(col1);

        TextView col2 = new TextView(this);
        col2.setId(View.generateViewId());
        col2.setText(countList[0]);
        col2.setTextSize(22);
        col2.setPadding(15,10,10,10);
        col2.setTextColor(Color.BLUE);
        col2.setTypeface(null, Typeface.BOLD);
        col2.setGravity(Gravity.CENTER);
        thead.addView(col2);

        tl.addView(thead, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,1f));

        for(int i=1;i<list.length;i++) {
            TableRow tr_head = new TableRow(this);
            tr_head.setPadding(5, 0, 0, 5);
            tr_head.setTag(i);
            tr_head.setFocusable(true);
            tr_head.setFocusableInTouchMode(true);
            tr_head.setClickable(true);
            if(i%2==1){
                tr_head.setBackgroundColor(Color.GRAY);
            }


            TextView date = new TextView(this);
            date.setId(View.generateViewId());
            date.setText(list[i]);
            date.setTextSize(22);
            date.setPadding(10,10,10,10);
            date.setTextColor(Color.BLACK);
//            date.setMinimumWidth(100);

            date.setWidth(100);
            tr_head.addView(date);


            TextView loc = new TextView(this);
            loc.setId(View.generateViewId());
            loc.setText(countList[i]);
            loc.setTextSize(22);
            loc.setPadding(15,10,10,10);
            loc.setTextColor(Color.BLACK);
            loc.setGravity(Gravity.CENTER);
            tr_head.addView(loc);

            tl.addView(tr_head, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,1f));

            tr_head.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               int position= (int) view.getTag();
//                           Toast toast = Toast.makeText(getApplicationContext(), "Tabele row clicked "+ tag , Toast.LENGTH_LONG);
//                           toast.show();
                                                if(list[0].equals("Location"))
                                                        new SendSearchRequest(getApplicationContext()).execute(list[position], "", "", "", "");

                                                else if(list[0].equals("Day"))
                                                        new SearchDay(getApplicationContext()).execute(list[position]);
                                                else if(list[0].equals("Violation Type"))
                                                        new SendSearchRequest(getApplicationContext()).execute("", "", "", "", list[position]);

                                                else if(list[0].equals("Time Range")) {
                                                    int startTime = Integer.parseInt(list[position].substring(0, list[position].indexOf(":")));
                                                    int endTime = 0;
                                                    String ampm;
                                                    System.out.println("start is " + startTime);
                                                    if (startTime >= 12) {
                                                        startTime = startTime % 12;
                                                        endTime = startTime + 1;
                                                        if (startTime == 0) startTime = 12;
                                                        ampm = "pm";
                                                    } else {
                                                        endTime = startTime + 1;
                                                        ampm = "am";
                                                    }
                                                    String start, end;
                                                    if (startTime < 10)
                                                        start = "0" + Integer.toString(startTime) + ":00 " + ampm;
                                                    else
                                                        start = Integer.toString(startTime) + ":00 " + ampm;
                                                    if (endTime < 10)
                                                        end = "0" + Integer.toString(endTime) + ":00 " + ampm;
                                                    else
                                                        end = Integer.toString(endTime) + ":00 " + ampm;

                                                    System.out.println(start + " " + end);
                                                    new SendSearchRequest(getApplicationContext()).execute("", "", start, end, "");
                                                }


//                                               new SendPhotoRequest(view.getContext()).execute(toPass);
                                           }
                                       }

            );
        }
//        t1.setColumnStretchable(0,true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent=new Intent(this,HomeActivity.class);;
        if (id == R.id.menu_report) {
//            return true;
            new SendTypesRequest(this).execute();
            return true;
        }
        else if (id == R.id.menu_search) {
//          return true;
            intent = new Intent(this,SearchEvent.class);
        }
        else if(id==R.id.view_RL){
            intent = new Intent(this,RankTV.class);
        }
//        return super.onOptionsItemSelected(item);
        startActivity(intent);
        return true;
    }
}
