package com.example.turja.spotit2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import networking.SendSearchRequest;

public class TvListActivity extends Activity {
    ArrayAdapter location,type,dayTime,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_list);
        Bundle bundle = getIntent().getExtras();
        ArrayList<Integer>id=bundle.getIntegerArrayList("id");
        if(id.isEmpty()){
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
            ListView lv1 = (ListView) findViewById(R.id.loc);
            lv1.setAdapter(location);
            ListView lv2 = (ListView) findViewById(R.id.typ);
            lv2.setAdapter(type);
            final ListView lv3 = (ListView) findViewById(R.id.tim);
            lv3.setAdapter(dayTime);

            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
//                    String s= (String) lv3.getItemAtPosition(position);
                    ArrayList<String> toPass=new ArrayList<String>();
                    toPass.add((String) type.getItem(position));
                    toPass.add((String) location.getItem(position));
                    toPass.add((String) dayTime.getItem(position));
                    toPass.add((String) description.getItem(position));
                    Intent intent=new Intent(view.getContext(),ViolationDetailsActivity.class);
                    intent.putExtra("values",toPass);
                    startActivity(intent);
                }
            });
        }

    }
}
