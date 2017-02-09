package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
//import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import networking.SendRankRequest;
import networking.SendTypesRequest;

public class RankTV extends Activity {
    Button rankByLocation,rankByTime,rankByType,rankByDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_tv);
        rankByLocation=(Button) findViewById(R.id.reportBtn);
        rankByTime=(Button)findViewById(R.id.searchBtn);
        rankByType=(Button)findViewById(R.id.ranklistBtn);
        rankByDay=(Button)findViewById(R.id.rankByDay);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "BOOKOS.TTF");
        rankByLocation.setTypeface(typeface);
        rankByTime.setTypeface(typeface);
        rankByType.setTypeface(typeface);
        rankByDay.setTypeface(typeface);
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200,90,90,90)));
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

    public void buttonListner(View view)
    {
        String criteria="Location";
        if(view.getId()==R.id.reportBtn){
            criteria="Location";
        }
        else if(view.getId()==R.id.rankByDay){
            criteria="Day";
        }
        else if(view.getId()==R.id.ranklistBtn){
            criteria="Violation Type";
        }
        else if(view.getId()==R.id.searchBtn){
            criteria="Time Range";
        }
        else return;
        SendRankRequest snd=new SendRankRequest(this);
        snd.execute(criteria);
    }
}
