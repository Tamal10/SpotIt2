package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.ApiCall;
import model.TrafficViolation;

public class ReportEvent extends Activity {
    //    private DatabaseReference mDatabase;
    final int REQUEST_SELECT_IMAGE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText datePicker;
    ImageButton galaryBtn,camBtn;
    TimePickerFragment newTimeFragment = new TimePickerFragment();
    DatePickerFragment newDateFragment = new DatePickerFragment();
    private Uri selectedImage;
    private Bitmap photo;
    String  picturePath;
    private boolean fileUri;

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
            dispatchGalleryIntent();
        }
        else if(v.getId()==R.id.takepic)
        {
            // Open default camera
            dispatchTakePictureIntent();

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

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            tv.setPhoto(Base64.encodeToString(stream.toByteArray(),Base64.NO_WRAP));
            System.out.println("wtf");
//            mDatabase.child("tv").child("1").setValue(tv);
//            ApiCall api=new ApiCall();
//            api.setGetRelativeUrl("submit");
//            String response=api.httpPost(tv.construcJson().toString(),"application/json");
            new SendTvData(this).execute(tv.constructJson().toString(),"submit");
//            System.out.println(response);
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    private void dispatchGalleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SELECT_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode)
            {
                case REQUEST_IMAGE_CAPTURE:
                    selectedImage = data.getData();
                    photo = (Bitmap) data.getExtras().get("data");
                    break;
                case REQUEST_SELECT_IMAGE:
                    if(data!=null && data.getData()!=null)
                    {
                        Uri uri = data.getData();

                        try {
                            photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
        }
    }


}
