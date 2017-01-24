package com.example.turja.spotit2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import networking.SendPhotoRequest;
import networking.SendSearchRequest;

public class TvListActivity extends Activity {
    ArrayAdapter location,type,dayTime,description;
    ArrayList<Integer> ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_list);
        Bundle bundle = getIntent().getExtras();
        ids=bundle.getIntegerArrayList("id");



        if(ids.isEmpty()){
            Toast.makeText(this, "Nothing found", Toast.LENGTH_LONG).show();
        }else {
//            String[] item=new String[id.size()];
//            for(int i=0;i<id.size();i++){
//                item[i]=bundle.getStringArrayList("type").get(i)+" at "+bundle.getStringArrayList("location").get(i)+
//                " time "+ bundle.getStringArrayList("dateTime").get(i);
//            }

            location = new ArrayAdapter<String>(this,
                    R.layout.search_item, bundle.getStringArrayList("location"));
            type = new ArrayAdapter<String>(this,
                    R.layout.search_item, bundle.getStringArrayList("type"));
            dayTime = new ArrayAdapter<String>(this,
                    R.layout.search_item, bundle.getStringArrayList("dateTime"));
            description = new ArrayAdapter<String>(this,
                    R.layout.search_item, bundle.getStringArrayList("description"));
//            ListView lv1 = (ListView) findViewById(R.id.loc);
//            lv1.setAdapter(location);
//            ListView lv2 = (ListView) findViewById(R.id.typ);
//            lv2.setAdapter(type);
//            final ListView lv3 = (ListView) findViewById(R.id.tim);
//            lv3.setAdapter(dayTime);
//
//            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
////                    String s= (String) lv3.getItemAtPosition(position);
//                    ArrayList<String> toPass=new ArrayList<String>();
//
//
//
//                    toPass.add(Integer.toString(ids.get(position)));
//                    toPass.add((String) type.getItem(position));
//                    toPass.add((String) location.getItem(position));
//                    toPass.add((String) dayTime.getItem(position));
//                    toPass.add((String) description.getItem(position));
//
//                    new SendPhotoRequest(view.getContext()).execute(toPass);
//
////                    Intent intent=new Intent(view.getContext(),ViolationDetailsActivity.class);
////                    intent.putExtra("values",toPass);
////                    startActivity(intent);
//                }
//            });

            TableLayout t1 = null;

            TableLayout tl = (TableLayout) findViewById(R.id.tv_list);
            TableRow thead=new TableRow(this);
            thead.setPadding(5,0,0,5);

            TextView col1 = new TextView(this);
            col1.setId(View.generateViewId());
            col1.setText("Date Time");
            col1.setTextSize(18);
            col1.setPadding(10,10,10,10);

            col1.setTextColor(Color.BLUE);
            thead.addView(col1);

            TextView col2 = new TextView(this);
            col2.setId(View.generateViewId());
            col2.setText("Location");
            col2.setTextSize(18);
            col2.setPadding(15,10,10,10);
            col2.setTextColor(Color.BLUE);
            thead.addView(col2);

            TextView col3 = new TextView(this);
            col3.setId(View.generateViewId());
            col3.setText("Type");
            col3.setTextSize(18);
            col3.setPadding(15,10,10,10);
            col3.setTextColor(Color.BLUE);
            thead.addView(col3);

            tl.addView(thead, new TableLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.FILL_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));


            for(int i=0;i<ids.size();i++) {
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
                date.setText(dayTime.getItem(i).toString());
                date.setTextSize(18);
                date.setPadding(10,10,10,10);
                date.setTextColor(Color.BLACK);
                tr_head.addView(date);

                TextView loc = new TextView(this);
                loc.setId(View.generateViewId());
                loc.setText(location.getItem(i).toString());
                loc.setTextSize(18);
                loc.setPadding(15,10,10,10);
                loc.setTextColor(Color.BLACK);
                tr_head.addView(loc);

                TextView typ = new TextView(this);
                typ.setId(View.generateViewId());
                typ.setText(type.getItem(i).toString());
                typ.setTextSize(18);
                typ.setPadding(15,10,10,10);
                typ.setTextColor(Color.BLACK);
                tr_head.addView(typ);

                tl.addView(tr_head, new TableLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.FILL_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

                tr_head.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                            int position= (int) view.getTag();
//                           Toast toast = Toast.makeText(getApplicationContext(), "Tabele row clicked "+ tag , Toast.LENGTH_LONG);
//                           toast.show();
                            ArrayList<String> toPass=new ArrayList<String>();

                            toPass.add(Integer.toString(ids.get(position)));
                            toPass.add((String) type.getItem(position));
                            toPass.add((String) location.getItem(position));
                            toPass.add((String) dayTime.getItem(position));
                            toPass.add((String) description.getItem(position));

//                            new SendPhotoRequest(view.getContext()).execute(toPass);
                           Intent intent=new Intent(getApplicationContext(),ViolationDetailsActivity.class);
                           intent.putExtra("values",toPass);
                           startActivity(intent);
                       }
                   }

                );
            }
        }

    }
}
