package com.example.turja.spotit2;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import networking.SendTypesRequest;

public class HomeActivity extends Activity {
    Button newReport,searchBtn,rankBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        newReport=(Button) findViewById(R.id.reportBtn);
        searchBtn=(Button)findViewById(R.id.searchBtn);
        rankBtn=(Button)findViewById(R.id.ranklistBtn);

        //setting font of the buttons to OldBook
        Typeface typeface = Typeface.createFromAsset(getAssets(), "BOOKOS.TTF");
        newReport.setTypeface(typeface);
        searchBtn.setTypeface(typeface);
        rankBtn.setTypeface(typeface);
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200,90,90,90)));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        System.out.println(123);
        return true;
    }
    //onClickListener
    public void buttonListner(View view)
    {
        if(view.getId()==R.id.reportBtn) {
            new SendTypesRequest(this).execute();
//            Intent intent = new Intent(this, ReportEvent.class);
//            startActivity(intent);
        }
        else if(view.getId()==R.id.ranklistBtn){
            Intent intent = new Intent(this,RankTV.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this,SearchEvent.class);
            startActivity(intent);
        }
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
