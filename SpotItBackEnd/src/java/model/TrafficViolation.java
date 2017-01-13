/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author CSE_BUET
 */
public class TrafficViolation {
    private int id;
    private String type;
    private String description;
    private int photoId;
    private String location;
    private String date_time;
    private String day;
    private String[] dayMap={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public TrafficViolation(int id, String type, String description, int photoId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.photoId = photoId;
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
        if(time.charAt(6)=='a') hour+=12; // :/ :/ :/
        
        int min=Integer.parseInt(time.substring(3,5));
        int month=Integer.parseInt(date.substring(3, 5))-1;
        
        c.set(Integer.parseInt(date.substring(6)),month , Integer.parseInt(date.substring(0, 2))-1);
        c.set(Calendar.HOUR,hour);
        c.set(Calendar.MINUTE, min);
        int weekDay=c.get(Calendar.DAY_OF_WEEK);
        day=dayMap[weekDay-1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        date_time = sdf.format(c.getTime());
    }
    
    public boolean constructDataFromJson(JSONObject js){
        try{
        location=js.getString("location");
        type=js.getString("type");
        this.date_time=js.getString("date_time");
        this.description=js.getString("description");
        this.photoId=js.getInt("photo_id");
        this.day=js.getString("day");
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
            return jsonData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Calendar c=Calendar.getInstance();
        System.out.println(Calendar.SATURDAY);;
    }
}
