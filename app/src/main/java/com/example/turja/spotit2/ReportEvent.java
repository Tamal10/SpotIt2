package com.example.turja.spotit2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import model.FileUtility;
import model.TrafficViolation;
import networking.SendTvData;

public class ReportEvent extends Activity {
    //    private DatabaseReference mDatabase;
    final int REQUEST_SELECT_IMAGE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 3;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL=4;
    EditText datePicker;
    ImageButton galaryBtn,camBtn;
    private TimePickerFragment newTimeFragment = new TimePickerFragment();
    private DatePickerFragment newDateFragment = new DatePickerFragment();
    private Uri selectedImage;
    private Bitmap photo;
    String  picturePath;
    private boolean fileUri;

    private String []violations={"Signal Break","Motorbike in Footpath","Street Crossing","Speed Break"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_event);
        galaryBtn=(ImageButton)findViewById(R.id.upload);
        datePicker=(EditText)findViewById(R.id.datepicker);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> vs=bundle.getStringArrayList("values");
        AutoCompleteTextView av= (AutoCompleteTextView) findViewById(R.id.violation_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, vs);
        av.setAdapter(adapter);
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



    public void showTimePickerDialog(View v) {
       // newTimeFragment.r=this;
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
       // newDateFragment.r=this;
        newDateFragment.show(getFragmentManager(), "datePicker");
    }

    public void btnListener(View v)
    {
        if(v.getId()==R.id.upload) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
                System.out.println(1);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
            }
            else{
                System.out.println(2);
                dispatchGalleryIntent();
            }

        }
        else if(v.getId()==R.id.takepic)
        {
            // Open default camera
            dispatchTakePictureIntent();

        }
        else if(v.getId()==R.id.submit){
            EditText et= (EditText) findViewById(R.id.location);
            String location=et.getText().toString();
            et= (EditText) findViewById(R.id.datepicker);
            String date=et.getText().toString();
            et= (EditText) findViewById(R.id.timepicker);
            String time=et.getText().toString();
            et=  (EditText) findViewById(R.id.feedBack);
            String description=et.getText().toString();
//            et=  (EditText) findViewById(R.id.violation_type);
            AutoCompleteTextView atv= (AutoCompleteTextView) findViewById(R.id.violation_type);
            String type=atv.getText().toString();
//            mDatabase = FirebaseDatabase.getInstance().getReference();
            if(location.equals("")  && date.equals("") && time.equals("") && type.equals("") ){
                Toast.makeText(this, "Please enter location,date and time", Toast.LENGTH_LONG).show();
                return;
            }
            else if(photo==null){
                Toast.makeText(this, "Please upload a photo", Toast.LENGTH_LONG).show();
                return;
            }
            TrafficViolation tv= new TrafficViolation();
            tv.setLocation(location);
            tv.setDate_time(date,time);
            tv.setDescription(description);
            tv.setType(type);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            tv.setPhoto(Base64.encodeToString(stream.toByteArray(),Base64.NO_WRAP));
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
                    ImageView iv= (ImageView) findViewById(R.id.reportImage);
                    iv.setImageBitmap(photo);
                    break;
                case REQUEST_SELECT_IMAGE:
                    if(data!=null && data.getData()!=null)
                    {
                        Uri uri = data.getData();

                        try {
                            String path = getRealPathFromURI( Uri.parse(uri.getPath()));
//                            String path = getRealPathFromURI( uri);
                            ExifInterface exif = new ExifInterface(path);

                            String date_time = exif.getAttribute(ExifInterface.TAG_DATETIME);
                            String latitude=exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                            String longitude=exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                            System.out.println(date_time);
                            System.out.println(latitude);
                            System.out.println(longitude);
                            photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            iv= (ImageView) findViewById(R.id.reportImage);
                            iv.setImageBitmap(photo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                    EditText et= (EditText) findViewById(R.id.location);
                    et.setText(place.getName());
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchGalleryIntent();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        System.out.println(result);
        return result;
    }
}
