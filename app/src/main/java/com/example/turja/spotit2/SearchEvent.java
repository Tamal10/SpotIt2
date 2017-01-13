package com.example.turja.spotit2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import networking.SendSearchRequest;

public class SearchEvent extends Activity {
    private TimePickerFragment newTimeFragment = new TimePickerFragment();
    private DatePickerFragment newDateFragment = new DatePickerFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);
    }
    public void showTimePickerDialog(View v) {
        newTimeFragment.s=this;
        if(v.getId()==R.id.endtimepicker) newTimeFragment.start=0;
        else newTimeFragment.start=1;
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        newDateFragment.s=this;
        newDateFragment.show(getFragmentManager(), "datePicker");
    }
    public void btnListener(View v) {
        EditText et= (EditText) findViewById(R.id.location);
        String location=et.getText().toString();
//        if(location.equals("")) System.out.println("null -_-");
//        else System.out.println("Not Null");
//        Log.d("Location",location);
        et= (EditText) findViewById(R.id.datepicker);
        String date=et.getText().toString();
//        Log.d("Date",date);
        et= (EditText) findViewById(R.id.timepicker);
        String startTime=et.getText().toString();

        et= (EditText) findViewById(R.id.endtimepicker);
        String endTime=et.getText().toString();
//        Log.d("Time",time);
        et=  (EditText) findViewById(R.id.feedBack);
        String type=et.getText().toString();

        if(location.equals("") && type.equals("") && date.equals("") && startTime.equals("") && endTime.equals("")){
            Toast.makeText(this, "Please enter values", Toast.LENGTH_LONG).show();
            return;
        }
        new SendSearchRequest(this).execute(location,date,startTime,endTime,type);
    }
}
