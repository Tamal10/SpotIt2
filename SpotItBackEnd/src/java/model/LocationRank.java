/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author CSE_BUET
 */
public class LocationRank {
    String location;
    int count;

    public LocationRank(String location, int count) {
        this.location = location;
        this.count = count;
    }

    public LocationRank(int time, int count) {
//        String timeRange=Integer.toString(time)+":00 - " + Integer.toString((time+1)%24)+":00";  
        this.location = Integer.toString(time)+":00 - " + Integer.toString((time+1)%24)+":00";
        this.count = count;
    }
    
    public LocationRank() {
    }
    public String getLocation() {
        return location;
    }

    public int getCount() {
        return count;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public static void main(String[] args) {
        LocationRank lr=new LocationRank(23,1);
        System.out.println(lr.location);
    }
}
