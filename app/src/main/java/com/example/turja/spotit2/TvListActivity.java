package com.example.turja.spotit2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;
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
import networking.SendTypesRequest;

import static android.R.drawable.list_selector_background;
import static com.example.turja.spotit2.R.drawable.selector1;

public class TvListActivity extends Activity {
    ArrayAdapter location,type,dayTime,description;
    ArrayList<Integer> ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_list);
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200,90,90,90)));
        actionBar.setTitle("SpotIt");
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

            TableRow.LayoutParams tlp = new TableRow.LayoutParams();
            tlp.width=0;

            TableLayout tl = (TableLayout) findViewById(R.id.tv_list);
            TableRow thead=new TableRow(this);
            thead.setPadding(5,0,0,5);

            TextView col1 = new TextView(this);
            col1.setId(View.generateViewId());
            col1.setText("Date Time");
            col1.setTextSize(18);
            col1.setPadding(10,10,10,10);
            col1.setLayoutParams(tlp);

            col1.setTextColor(Color.BLUE);
            thead.addView(col1);

            TextView col2 = new TextView(this);
            col2.setId(View.generateViewId());
            col2.setText("Location");
            col2.setTextSize(18);
            col2.setPadding(15,10,10,10);
            col2.setTextColor(Color.BLUE);
            col2.setLayoutParams(tlp);
            thead.addView(col2);

            TextView col3 = new TextView(this);
            col3.setId(View.generateViewId());
            col3.setText("Type");
            col3.setTextSize(18);
            col3.setPadding(15,10,10,10);
            col3.setTextColor(Color.BLUE);
            col3.setLayoutParams(tlp);
            thead.addView(col3);

            tl.addView(thead, new TableLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.FILL_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

            final TableRow []tr= new TableRow[10];
            for(int i=0;i<ids.size();i++) {
                tr[i] = new TableRow(this);
                tr[i].setPadding(5, 0, 0, 5);
                tr[i].setTag(i);
                //tr[i].setBackground(selector1);

//                tr_head.setBackground();
//                tr_head.setFocusable(true);
//                tr_head.setFocusableInTouchMode(true);
                tr[i].setClickable(true);
                if(i%2==0){
                    tr[i].setBackgroundColor(Color.GRAY);
                }
                TextView date = new TextView(this);
                date.setId(View.generateViewId());
                date.setText(dayTime.getItem(i).toString());
                date.setTextSize(18);
                date.setPadding(10,10,10,10);
                date.setTextColor(Color.BLACK);
                date.setLayoutParams(tlp);
                tr[i].addView(date);

                TextView loc = new TextView(this);
                loc.setId(View.generateViewId());
                loc.setText(location.getItem(i).toString());
                loc.setTextSize(18);
                loc.setPadding(15,10,10,10);
                loc.setTextColor(Color.BLACK);
                loc.setLayoutParams(tlp);
                tr[i].addView(loc);

                TextView typ = new TextView(this);
                typ.setId(View.generateViewId());
                typ.setText(type.getItem(i).toString());
                typ.setTextSize(18);
                typ.setPadding(15,10,10,10);
                typ.setTextColor(Color.BLACK);
                typ.setLayoutParams(tlp);
                tr[i].addView(typ);

                tl.addView(tr[i], new TableLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.FILL_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

                tr[i].setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                            int position= (int) view.getTag();
//                           Toast toast = Toast.makeText(getApplicationContext(), "Tabele row clicked "+ tag , Toast.LENGTH_LONG);
//                           toast.show();
                            //tr[position].setBackgroundResource(list_selector_background);
                            ArrayList<String> toPass=new ArrayList<String>();

                            toPass.add(Integer.toString(ids.get(position)));
                            toPass.add((String) type.getItem(position));
                            toPass.add((String) location.getItem(position));
                            toPass.add((String) dayTime.getItem(position));
                            toPass.add((String) description.getItem(position));
//                           if(position%2!=0){
//                               tr_head.setBackgroundColor(Color.GRAY);
//                           }
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
