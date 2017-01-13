package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by CSE_BUET on 1/9/2017.
 */
public class TrafficViolation {
    private int id;
    private String type;
    private String description;
    private int photoId;
    private String location;
    private String date_time;
    private String day;
    private String date_time_end;
    private String photo;
    private String[] dayMap={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};


    public TrafficViolation(int id, String type, String description, int photoId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.photoId = photoId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public TrafficViolation() {
        type="";description="";location="";date_time="";day="";
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getLocation() {
        return location;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public String getDate_time_end() {
        return date_time_end;
    }

    public void setDate_time_end(String date_time_end) {
        this.date_time_end = date_time_end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public void setDate_time(Calendar c){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        date_time = sdf.format(c.getTime());
    }
    public void setDate_time(String date, String time){
        Calendar c= Calendar.getInstance();
        int hour=Integer.parseInt(time.substring(0,2))%12;
        if(time.charAt(6)=='p') hour+=12; // :/ :/ :/

        int min=Integer.parseInt(time.substring(3,5));
        int month=Integer.parseInt(date.substring(3, 5))-1;

        c.set(Integer.parseInt(date.substring(6)),month , Integer.parseInt(date.substring(0, 2)));
        c.set(Calendar.HOUR_OF_DAY,hour);
        System.out.println(hour+" AND "+Calendar.HOUR);
        c.set(Calendar.MINUTE, min);
        int weekDay=c.get(Calendar.DAY_OF_WEEK);
//        if(weekDay==0) weekDay=7;
        System.out.println(c.getTime());
        switch(weekDay){
            case Calendar.SATURDAY:
                day="Saturday";break;
            case Calendar.SUNDAY:
                day="Sunday";break;
            case Calendar.WEDNESDAY:
                day="Wednesday";break;
            case Calendar.FRIDAY:
                day="Friday";break;
            case Calendar.MONDAY:
                day="Monday";break;
            case Calendar.TUESDAY:
                day="Tuesday";break;
            case Calendar.THURSDAY:
                day="Thursday";break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        date_time = sdf.format(c.getTime());
    }

    public void setDate_time(String date, String time, int which){
//        photoId=0;// using  photoid to handle no date or no time case :/
        if(date.equals("") && time.equals("")){
            date_time="";date_time_end="";return;
        }
        Calendar c= Calendar.getInstance();
        if(!date.equals("")){
            int month=Integer.parseInt(date.substring(3, 5))-1;
            c.set(Integer.parseInt(date.substring(6)),month , Integer.parseInt(date.substring(0, 2)));
        }

        if(!time.equals("")){
            int hour=Integer.parseInt(time.substring(0,2))%12;
            if(time.charAt(6)=='p') hour+=12; // :/ :/ :/
            int min=Integer.parseInt(time.substring(3,5));
            c.set(Calendar.HOUR_OF_DAY,hour);
//            System.out.println(hour+" AND "+Calendar.HOUR);
            c.set(Calendar.MINUTE, min);
        }

        int weekDay=c.get(Calendar.DAY_OF_WEEK);

//        System.out.println(c.getTime());
        switch(weekDay){
            case Calendar.SATURDAY:
                day="Saturday";break;
            case Calendar.SUNDAY:
                day="Sunday";break;
            case Calendar.WEDNESDAY:
                day="Wednesday";break;
            case Calendar.FRIDAY:
                day="Friday";break;
            case Calendar.MONDAY:
                day="Monday";break;
            case Calendar.TUESDAY:
                day="Tuesday";break;
            case Calendar.THURSDAY:
                day="Thursday";break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        if(which==1)
            date_time = sdf.format(c.getTime());
        else
            date_time_end = sdf.format(c.getTime());
    }

    public boolean constructDataFromJson(JSONObject js){
        try{

            location=js.getString("location");
            type=js.getString("type");
            this.date_time=js.getString("date_time");
            this.description=js.getString("description");
            this.photoId=js.getInt("photo_id");
            this.day=js.getString("day");
            // image try

            return true;
        }catch(JSONException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public JSONObject constructJson(){
        JSONObject jsonData= new JSONObject();
        try {
            jsonData.put("location",this.location);
            jsonData.put("type",this.type);
            jsonData.put("date_time",this.date_time);
            jsonData.put("description",this.description);
            jsonData.put("photo_id",this.photoId);
            jsonData.put("day",this.day);
            jsonData.put("photo",this.photo);
            /*** image
            File imgPath = new File("");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imgPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(fis==null) System.out.println("Why?");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(bm==null){
                System.out.println("baal naki");

            }
           bm.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            String s= Base64.encodeToString(stream.toByteArray(),Base64.NO_WRAP);
            // image try end
            jsonData.put("photo",s);
             *****/
            return jsonData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject constructJsonForSearch(){
        JSONObject jsonData= new JSONObject();
        try {
            jsonData.put("location", this.location);
            jsonData.put("type", this.type);
            jsonData.put("start", this.date_time);
            jsonData.put("end",this.date_time_end);
            jsonData.put("dayTime",this.photoId);
            return jsonData;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }


}