package com.example.turja.spotit2;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.TrafficViolation;
import networking.SendTvData;
import networking.SendTypesRequest;
import networking.mapApi;

import com.drew.metadata.exif.ExifReader;

public class Report extends Activity {
    final int REQUEST_SELECT_IMAGE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST = 3;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 4;
    static final int MY_PERMISSIONS_REQUEST_FINE_ACCESS = 5;
    EditText datePicker;
    ImageButton galaryBtn, camBtn;
    private TimePickerFragment newTimeFragment = new TimePickerFragment();
    private DatePickerFragment newDateFragment = new DatePickerFragment();
    private Uri selectedImage;
    private Bitmap photo;
    String picturePath;
    private boolean fileUri;
    Calendar c = Calendar.getInstance();
    private String[] violations = {"Signal Break", "Motorbike in Footpath", "Street Crossing", "Speed Break"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        galaryBtn = (ImageButton) findViewById(R.id.upload);
        datePicker = (EditText) findViewById(R.id.datepicker);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> vs = bundle.getStringArrayList("values");
        AutoCompleteTextView av = (AutoCompleteTextView) findViewById(R.id.violation_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, vs);
        av.setAdapter(adapter);

        RelativeLayout rl= (RelativeLayout) findViewById(R.id.content);
        rl.setVisibility(View.INVISIBLE);
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200,90,90,90)));
//        Calendar cl=Calendar.getInstance();
//        EditText edt= (EditText) findViewById(R.id.timepicker);
//        setTimeEditText(cl);
//        getGPSData();
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
    public void showMap(View v) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Context context = getApplicationContext();
        EditText et = (EditText) findViewById(R.id.location);
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
        newTimeFragment.r = this;
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        newDateFragment.r = this;
        newDateFragment.show(getFragmentManager(), "datePicker");
    }

    public void btnListener(View v) {
        if (v.getId() == R.id.upload) {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                System.out.println(1);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
            } else {
//                System.out.println(2);
                dispatchGalleryIntent();
            }

        } else if (v.getId() == R.id.takepic) {
            // Open default camera
            dispatchTakePictureIntent();

        } else if (v.getId() == R.id.submit) {
            EditText et = (EditText) findViewById(R.id.location);
            String location = et.getText().toString();
            et = (EditText) findViewById(R.id.datepicker);
            String date = et.getText().toString();
            et = (EditText) findViewById(R.id.timepicker);
            String time = et.getText().toString();
            et = (EditText) findViewById(R.id.feedBack);
            String description = et.getText().toString();
//            et=  (EditText) findViewById(R.id.violation_type);
            AutoCompleteTextView atv = (AutoCompleteTextView) findViewById(R.id.violation_type);
            String type = atv.getText().toString();
//            mDatabase = FirebaseDatabase.getInstance().getReference();
            if (location.equals("") && date.equals("") && time.equals("") && type.equals("")) {
                Toast.makeText(this, "Please enter location,date and time", Toast.LENGTH_LONG).show();
                return;
            } else if (photo == null) {
                Toast.makeText(this, "Please upload a photo", Toast.LENGTH_LONG).show();
                return;
            }
            TrafficViolation tv = new TrafficViolation();
            tv.setLocation(location);
            tv.setDate_time(date, time);
            tv.setDescription(description);
            tv.setType(type);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            tv.setPhoto(Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP));
//            mDatabase.child("tv").child("1").setValue(tv);
//            ApiCall api=new ApiCall();
//            api.setGetRelativeUrl("submit");
//            String response=api.httpPost(tv.construcJson().toString(),"application/json");
            new SendTvData(this).execute(tv.constructJson().toString(), "submit");
//            System.out.println(response);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void dispatchGalleryIntent() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SELECT_IMAGE);
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_SELECT_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        RelativeLayout rl;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    selectedImage = data.getData();
                    getMetaData(selectedImage);
                    photo = (Bitmap) data.getExtras().get("data");
                    ImageView iv = (ImageView) findViewById(R.id.reportImage);
                    iv.setImageBitmap(photo);
                    Calendar cl=Calendar.getInstance();
                    EditText edt= (EditText) findViewById(R.id.timepicker);
                    if(edt.getText().equals(""))
                        setTimeEditText(cl);
                    rl= (RelativeLayout) findViewById(R.id.content);
                    rl.setVisibility(View.VISIBLE);
                    break;
                case REQUEST_SELECT_IMAGE:
                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        getMetaData(uri);
                        try {
                            photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            iv = (ImageView) findViewById(R.id.reportImage);
                            iv.setImageBitmap(photo);
                            rl= (RelativeLayout) findViewById(R.id.content);
                            rl.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                    EditText et = (EditText) findViewById(R.id.location);
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
            case MY_PERMISSIONS_REQUEST_FINE_ACCESS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getGPSData();
                }
                else{

                }
            return;
        }
    }


    void getMetaData(Uri uri) {
        String path = getRealPathFromURI(Uri.parse(uri.getPath()));

        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);


            Metadata metadata = null;
            if (inputStream != null) {
                metadata = ImageMetadataReader.readMetadata(new BufferedInputStream(inputStream));
            }
            if (metadata != null) {
                if (metadata.containsDirectoryOfType(GpsDirectory.class)) {
                    GpsDirectory gpsDirectory = (GpsDirectory) metadata.getFirstDirectoryOfType(GpsDirectory.class);
                    GeoLocation location = gpsDirectory.getGeoLocation();
                    System.out.println("lat: " + location.getLatitude());

                    new mapApi((EditText) findViewById(R.id.location), this).execute(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

//                                    Geocoder geocoder;
//                                    List<Address> addresses;
//                                    geocoder = new Geocoder(this, Locale.getDefault());
//                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                                    String cityName = addresses.get(0).getAddressLine(0);
//                                    String stateName = addresses.get(0).getAddressLine(1);
//                                    String countryName = addresses.get(0).getAddressLine(2);
//                                    String localityName = addresses.get(0).getLocality();
//                                    String sublocalityName = addresses.get(0).getSubLocality();
//                                    System.out.println("country: " + countryName);
//                                    System.out.println("city: " + cityName);
//                                    System.out.println("state: " + stateName);
//                                    System.out.println("locality: " + localityName);
//                                    System.out.println("sublocality: " + sublocalityName);
                }

                ExifSubIFDDirectory directory
                        = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (directory != null) {
                    Date date
                            = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
//                                    c=Calendar.getInstance();
                    if (date != null) {
                        c.setTime(date);
                        setTimeEditText(c);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getGPSData() {
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println(111);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_ACCESS);
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        Location loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        System.out.println("GPS lat "+loc.getLatitude());
        new mapApi((EditText) findViewById(R.id.location),this).execute(String.valueOf(loc.getLatitude()),String.valueOf(loc.getLongitude()));
    }

    void setTimeEditText(Calendar cl){
//        Calendar cl=Calendar.getInstance();
        String time = "";
        if (cl.get(Calendar.HOUR_OF_DAY) > 12) {
            if (cl.get(Calendar.HOUR) < 10)
                time = "0" + String.valueOf(cl.get(Calendar.HOUR));
            else
                time = String.valueOf(cl.get(Calendar.HOUR));

            if (cl.get(Calendar.MINUTE) < 10)
                time += ":0" + cl.get(Calendar.MINUTE);
            else
                time += ":" + cl.get(Calendar.MINUTE);
            time += " PM";
        } else {
            if (cl.get(Calendar.HOUR) < 10)
                time = "0" + String.valueOf(cl.get(Calendar.HOUR));
            else
                time = String.valueOf(cl.get(Calendar.HOUR));

            if (cl.get(Calendar.MINUTE) < 10)
                time += ":0" + cl.get(Calendar.MINUTE);
            else
                time += ":" + cl.get(Calendar.MINUTE);
            time += " AM";
        }
        EditText et = (EditText) findViewById(R.id.timepicker);
        et.setText(time);

        String d = "";
        int day = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("day "+day);
        if (day < 10) {
            d = "0";
        }
        d += day + "/";
        int month = c.get(Calendar.MONTH)+1;
        System.out.println("month "+month);
        if (month < 10) {
            d += "0";
        }
        d += month + "/";
        int year = cl.get(Calendar.YEAR);
        System.out.println("year "+year);
        d += year;
        EditText e = (EditText) findViewById(R.id.datepicker);
        e.setText(d);
    }





    // from one source
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

    /** from http://stackoverflow.com/questions/33208911/get-realpath-return-null-on-android-marshmallow **/
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    /**********/
}

class MyLocationListener implements LocationListener {



    @Override
    public void onLocationChanged(Location loc) {

//
//        String longitude = "Longitude: " + loc.getLongitude();
//        Log.v("long", longitude);
//        String latitude = "Latitude: " + loc.getLatitude();
//        Log.v("lat", latitude);
//
//        /*------- To get city name from coordinates -------- */
//        String cityName = null;
//        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
//        List<Address> addresses;
//        try {
//            addresses = gcd.getFromLocation(loc.getLatitude(),
//                    loc.getLongitude(), 1);
//            if (addresses.size() > 0) {
//                System.out.println(addresses.get(0).getLocality());
//                cityName = addresses.get(0).getLocality();
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
//                + cityName;
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
