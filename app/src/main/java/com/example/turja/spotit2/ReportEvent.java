package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import model.ApiCall;
import model.TrafficViolation;

public class ReportEvent extends Activity {
//    private DatabaseReference mDatabase;
    EditText datePicker;
    ImageButton galaryBtn,camBtn;
    TimePickerFragment newTimeFragment = new TimePickerFragment();
    DatePickerFragment newDateFragment = new DatePickerFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_event);
        galaryBtn=(ImageButton)findViewById(R.id.upload);
        datePicker=(EditText)findViewById(R.id.datepicker);
    }
    public void showTimePickerDialog(View v) {
        newTimeFragment.r=this;
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        newDateFragment.r=this;
        newDateFragment.show(getFragmentManager(), "datePicker");
    }
    public void btnListener(View v)
    {
        if(v.getId()==R.id.upload) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        }
        else if(v.getId()==R.id.takepic)
        {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        }
        else if(v.getId()==R.id.submit){
            EditText et= (EditText) findViewById(R.id.location);
            String location=et.getText().toString();
            Log.d("location","location is "+ location);
            et= (EditText) findViewById(R.id.datepicker);
            String date=et.getText().toString();
            et= (EditText) findViewById(R.id.timepicker);
            String time=et.getText().toString();
            et=  (EditText) findViewById(R.id.feedBack);
            String description=et.getText().toString();
//            mDatabase = FirebaseDatabase.getInstance().getReference();
            TrafficViolation tv= new TrafficViolation();
            tv.setLocation(location);
            tv.setDate_time(date,time);
            tv.setDescription(description);
            tv.setType("Signal Break");
//            mDatabase.child("tv").child("1").setValue(tv);
//            ApiCall api=new ApiCall();
//            api.setGetRelativeUrl("submit");
//            String response=api.httpPost(tv.construcJson().toString(),"application/json");
            new SendTvData(this).execute(tv.constructJson().toString(),"submit");
//            System.out.println(response);
        }
    }


}
