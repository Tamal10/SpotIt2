/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import com.sun.jmx.snmp.SnmpDataTypeEnums;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LocationRank;
import model.TrafficViolation;

/**
 *
 * @author CSE_BUET
 */
public class DBConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/spot_it?zeroDateTimeBehavior=convertToNull";
    static final String USER = "root";
    static final String DBHOST1 = "localhost";
    static final String PASS = "";
    private Connection conn;
    private Statement stmt;
    
    public DBConnector() {

    }
    
    private void getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception se) {
            se.printStackTrace();
//            log.log(Priority.FATAL, "DBServer: " + DB_URL1 + " is Down!");
        }
    }
    
    private void closeConnection() {
        try {
            conn.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }
    
    public static void main(String[] args) {
        DBConnector db= new DBConnector();
//        db.getConnection();
//        System.out.println("1");
//        db.closeConnection();
//        String date="05/01/2017";
//        String time="02:30 pm";
//        Calendar c= Calendar.getInstance();
//        
//        int hour=Integer.parseInt(time.substring(0,2))%12;
//        if(time.charAt(6)=='a') hour+=12; // :/ :/ :/
//        
//        int min=Integer.parseInt(time.substring(3,5));
//        int month=Integer.parseInt(date.substring(3, 5))-1;
//        
//        c.set(Integer.parseInt(date.substring(6)),month , Integer.parseInt(date.substring(0, 2))-1);
//        c.set(Calendar.HOUR,hour);
//        c.set(Calendar.MINUTE, min);
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
//        String  formated_time = sdf.format(c.getTime());
//        System.out.println("formatted time is: "+formated_time);
        
        TrafficViolation tv= new TrafficViolation();
        tv.setLocation("mirpur");
        tv.setDate_time("05/01/2017", "02:30 pm");
        tv.setType("motorcycle in footpath");
        tv.setDescription("foul motorbikewala");
        tv.setPhotoId(2);
        db.insertTrafficViolation(tv);
    }
    
    public int insertTrafficViolation(TrafficViolation tv){
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql=String.format("insert into traffic_violation (location,date_time,photo_id,type,description,day) values ('%s','%s',%d,'%s','%s','%s')",tv.getLocation(),
            tv.getDate_time(),tv.getPhotoId(),tv.getType(),tv.getDescription(),tv.getDay());
            System.out.println(sql);
            int key=stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                key=rs.getInt(1);
            }
            rs.close();
            
            
            sql=String.format("select id from violation_type where name='%s'",tv.getType());
            rs=stmt.executeQuery(sql);
            if(!rs.next()){
                sql=String.format("insert into violation_type (name) values ('%s')",tv.getType());
                stmt.executeUpdate(sql);
            }   
            rs.close();
            
            closeConnection();
            return key;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public ArrayList searchRankByLocation(){
        ArrayList<LocationRank> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select location,count(*) as total from traffic_violation group by location order by total desc limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LocationRank lrank=new LocationRank(rs.getString("location"),rs.getInt("total"));
                lr.add(lrank);
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    public ArrayList searchRankByDay(){
        ArrayList<LocationRank> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select day,count(*) as total from traffic_violation where day is not NULL and day != '' group by day order by total desc limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LocationRank lrank=new LocationRank(rs.getString("day"),rs.getInt("total"));
                lr.add(lrank);
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    public ArrayList searchRankByType(){
        ArrayList<LocationRank> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select type,count(*) as total from traffic_violation where type is not NULL and type != '' group by type order by total desc limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LocationRank lrank=new LocationRank(rs.getString("type"),rs.getInt("total"));
                lr.add(lrank);
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    public ArrayList searchRankByTime(){
        ArrayList<LocationRank> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select hour(date_time), count(*) as total from traffic_violation group by hour(date_time) order by total desc limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                LocationRank lrank=new LocationRank(rs.getInt("hour(date_time)"),rs.getInt("total"));
                lr.add(lrank);
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    
    public ArrayList searchAllTypes(){
        ArrayList<String> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select name from violation_type";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                lr.add(rs.getString("name"));
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    public ArrayList searchDay(String day){
        ArrayList<TrafficViolation> lr= new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql=String.format("select id,location,type,date_time,description from traffic_violation where day='%s'",day);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                TrafficViolation tv=new TrafficViolation(rs.getInt("id"), rs.getString("type"), rs.getString("description"), 0);
                tv.setDate_time(rs.getString("date_time"));
                tv.setLocation(rs.getString("location"));
//                tv.setDescription(rs.getString("description"));
                lr.add(tv);
            }
            closeConnection();
//            return lr;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }
    
    public ArrayList search(String location, String start, String end, String type, int dayTime){
        ArrayList<TrafficViolation> ar=new ArrayList<>();
        try {
            getConnection();
            stmt=conn.createStatement();
            String sql="select id,location,type,date_time,description from traffic_violation where ";
            System.out.println("daytime "+dayTime);
            if(location!=null && !location.equals("")){
                sql+=String.format("location = '%s' ", location);
            }
            if(type!=null && !type.equals("")){
                if(sql.endsWith("\' ")) sql+="and ";
                sql+=String.format("type = '%s' ", type);
            }
            // 0--> all  1--> none 2-->only time 3-->only date
            if(dayTime==3 || dayTime==0){
                if(sql.endsWith("\' ")) sql+="and ";
                sql+=String.format("date(date_time) = date('%s') ", start);
            }
            
            if(dayTime==2 || dayTime==0){
                if(sql.endsWith("\' ") || sql.endsWith(") ")) sql+="and ";
                sql+=String.format("time(date_time) between time('%s') and time('%s')", start,end);
            }
            
            if(sql.endsWith("where ")) sql+="1";
            sql+=" order by date_time desc limit 10";
            System.out.println(sql);
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
                TrafficViolation tv=new TrafficViolation(rs.getInt("id"), rs.getString("type"), rs.getString("description"), 0);
                tv.setDate_time(rs.getString("date_time"));
                tv.setLocation(rs.getString("location"));
//                tv.setDescription(rs.getString("description"));
                ar.add(tv);
            }
            
            closeConnection();
            return ar;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
