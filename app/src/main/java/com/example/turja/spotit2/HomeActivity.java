package com.example.turja.spotit2;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


    }
    //onClickListener
    public void buttonListner(View view)
    {
        if(view.getId()==R.id.reportBtn) {
            Intent intent = new Intent(this, ReportEvent.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.ranklistBtn){
            Intent intent = new Intent(this,RankTV.class);
            startActivity(intent);
        }
        else{

        }
    }


}
