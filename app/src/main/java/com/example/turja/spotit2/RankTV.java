package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RankTV extends Activity {
    Button rankByLocation,rankByTime,rankByType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_tv);
        rankByLocation=(Button) findViewById(R.id.reportBtn);
        rankByTime=(Button)findViewById(R.id.searchBtn);
        rankByType=(Button)findViewById(R.id.ranklistBtn);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "BOOKOS.TTF");
        rankByLocation.setTypeface(typeface);
        rankByTime.setTypeface(typeface);
        rankByType.setTypeface(typeface);
    }
    public void buttonListner(View view)
    {
        if(view.getId()==R.id.reportBtn){
            SendRankRequest snd=new SendRankRequest(this);
            snd.execute("location");
            Intent intent=new Intent(this,ShowTvList.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.ranklistBtn){

        }
        else{

        }
    }
}
