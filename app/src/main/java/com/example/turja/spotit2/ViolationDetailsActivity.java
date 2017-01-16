package com.example.turja.spotit2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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
        ListView lv1= (ListView) findViewById(R.id.key);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.search_item, key);
        lv1.setAdapter(adapter);
        ListView lv2= (ListView) findViewById(R.id.value);

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.search_item, value);
        lv2.setAdapter(adapter1);

        ImageView iv= (ImageView) findViewById(R.id.imageView);

        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
