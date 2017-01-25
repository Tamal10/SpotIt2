package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import networking.SendSearchRequest;

public class SearchEvent extends Activity {
    static final int PLACE_PICKER_REQUEST = 3;
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

    public void showMap(View v){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Context context = getApplicationContext();
        EditText et= (EditText) findViewById(R.id.location);
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
//            et.setText("GooglePlayServicesRepairableException");
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
//            et.setText("GooglePlayServicesNotAvailableException");
        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode)
            {case PLACE_PICKER_REQUEST:
                Place place = PlacePicker.getPlace(this, data);
                EditText et= (EditText) findViewById(R.id.location);
                et.setText(place.getName());
                break;
            }
        }
    }
}
