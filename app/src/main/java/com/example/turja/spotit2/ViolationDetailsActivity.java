package com.example.turja.spotit2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViolationDetailsActivity extends Activity {
    String []key={"Violation Type","Location","Time","Details"};
    String []value= new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_details);
        Bundle bundle = getIntent().getExtras();

        value[0]=bundle.getStringArrayList("values").get(1);
        value[1]=bundle.getStringArrayList("values").get(2);
        value[2]=bundle.getStringArrayList("values").get(3);
        value[3]=bundle.getStringArrayList("values").get(4);
        String photo=bundle.getStringArrayList("values").get(5);
//        ListView lv1= (ListView) findViewById(R.id.key);
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.search_item, key);
//        lv1.setAdapter(adapter);
//        ListView lv2= (ListView) findViewById(R.id.value);
//
//        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
//                R.layout.search_item, value);
//        lv2.setAdapter(adapter1);
        TableLayout t1 = null;

        TableLayout tl = (TableLayout) findViewById(R.id.vio_details);
        LayoutInflater inflater = getLayoutInflater();
        for(int i=0;i<key.length;i++) {
            TableRow tr_head = new TableRow(this);
            tr_head.setPadding(5,0,0,5);

            if(i%2==0){
                tr_head.setBackgroundColor(Color.GRAY);
            }

            TextView tkey = new TextView(this);
            tkey.setId(View.generateViewId());
            tkey.setText(key[i]);
            tkey.setTextColor(Color.BLUE);
            tkey.setTextSize(24);



            tkey.setPadding(15, 15, 5, 5);

            tr_head.addView(tkey);

            TextView tval = new TextView(this);
            tval.setId(View.generateViewId());
            tval.setText(value[i]);
            tval.setTextSize(24);
            tval.setTextColor(Color.BLACK);
            tval.setPadding(18, 15, 5, 5);
            tval.setSingleLine(false);
            tr_head.addView(tval);


            tl.addView(tr_head, new TableLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.FILL_PARENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        }
//        t1.setPadding(15,15,15,15);
        ImageView iv= (ImageView) findViewById(R.id.imageView);

        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
