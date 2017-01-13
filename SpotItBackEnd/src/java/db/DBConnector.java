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
    
    
}
