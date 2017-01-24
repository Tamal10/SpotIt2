/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import com.sun.xml.wss.impl.misc.Base64;
import db.DBConnector;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import model.LocationRank;
import model.RequestData;
import model.TrafficViolation;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author CSE_BUET
 */
@Path("service")
public class WebService {

    @Context
    private UriInfo context;
    private String imageFolder="E:\\Masters\\HDDM\\SpotItImages";
    /**
     * Creates a new instance of WebService
     */
    public WebService() {
    }

    /**
     * Retrieves representation of an instance of api.WebService
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("image/{id}")
    @Produces("application/json")
    public String getJson( @PathParam("id") String id) {
        InputStream stream=null;
        JSONObject json=new JSONObject();
        try {
            stream = new FileInputStream(imageFolder+"\\"+id+".jpg");
            byte[] data=new byte[(int)new File(imageFolder+"\\"+id+".jpg").length()];
            stream.read(data);
            String encoded= Base64.encode(data);
            json.put("photo", encoded);
            return json.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GET
    @Produces("application/json")
    public String get() {
        return "{'a':'b'}";
    }
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String getTypes(String content) {
        //TODO return proper representation object
        System.out.println("....");
        DBConnector db=new DBConnector();
        ArrayList<String> types=db.searchAllTypes();
        JSONArray js = new JSONArray();
        js.put(types);
        
        return js.getJSONArray(0).toString();
    }
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("daySearch")
    public String searchByDay(String content) {
        //TODO return proper representation object
        JSONObject jo=new JSONObject(content);
        DBConnector db=new DBConnector();
        ArrayList<TrafficViolation> types=db.searchDay(jo.getString("day"));
        JSONArray js = new JSONArray();
        js.put(types);  
        return js.getJSONArray(0).toString();
    }
    /**
     * PUT method for updating or creating an instance of WebService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("getRank")
    public String getRank(String content) {
//        JSONObject jsonObject = new JSONObject(content);
//        RequestData rqd= new RequestData();
//        rqd.constructDataFromJson(jsonObject);
        JSONArray js = new JSONArray();
        System.out.println("....");
        if(content.contains("Location")){
            DBConnector db= new DBConnector();
            ArrayList<LocationRank> lr=db.searchRankByLocation();
            js.put(lr);
        }
        else if(content.contains("Day")){
            DBConnector db= new DBConnector();
            ArrayList<LocationRank> lr=db.searchRankByDay();
            js.put(lr);
        }
        else if(content.contains("Violation Type")){
            DBConnector db= new DBConnector();
            ArrayList<LocationRank> lr=db.searchRankByType();
            js.put(lr);
        }
        else if(content.contains("Time Range")){
            DBConnector db= new DBConnector();
            ArrayList<LocationRank> lr=db.searchRankByTime();
            js.put(lr);
        }
//        String t=js.toString();
//        JSONArray j=new JSONArray(t);
//        j=j.getJSONArray(0);
//        for(int i=0;i<j.length();i++){
//            JSONObject jo=j.getJSONObject(i);
//            System.out.println(jo);
//        }
        return js.getJSONArray(0).toString();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("search")
    public String search(String content) {
        JSONArray js = new JSONArray();
        if(content!=null){
            JSONObject jsonTv = new JSONObject(content);
            String location=jsonTv.getString("location");
            String start=jsonTv.getString("start");
            String end=jsonTv.getString("end");
            String type=jsonTv.getString("type");
            int dayTime=jsonTv.getInt("dayTime");
            DBConnector db=new DBConnector();
            ArrayList<TrafficViolation> ar=db.search(location, start, end, type, dayTime);
            js.put(ar);
            System.out.println(js.getJSONArray(0).toString());
            return js.getJSONArray(0).toString();
        }
        return null;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("getphoto")
    public String getPhoto(String content) {
        InputStream stream=null;
        try {
            JSONObject json=new JSONObject(content);
            int id=json.getInt("id");
            stream = new FileInputStream(imageFolder+"\\"+id+".jpg");
            byte[] data=new byte[(int)new File(imageFolder+"\\"+id+".jpg").length()];
            stream.read(data);
            String encoded= Base64.encode(data);
            json.put("photo", encoded);
            return json.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("submit")
    public String submitReport(@Context HttpServletRequest headers, String content){
        try{
            JSONObject jsonTv = new JSONObject(content);
            TrafficViolation tv= new TrafficViolation();
            System.out.println(content);
            
            
            
            if(tv.constructDataFromJson(jsonTv)){
                DBConnector db= new DBConnector();
                int nRow=db.insertTrafficViolation(tv);
                if(nRow!=-1){
                    String photo=jsonTv.getString("photo");
                    byte[] data = Base64.decode(photo);
                    OutputStream stream = new FileOutputStream(imageFolder+"\\"+nRow+".jpg");
                    stream.write(data);
                    stream.flush();stream.close();
                    String Response=new Response("100", "Success").createJSONObject().toString();
                    return Response;
                }
                return new Response("505", "Failed1").createJSONObject().toString();
            }
            else{
                return new Response("505", "Failed2").createJSONObject().toString();
            }
            
        }
        catch(Exception e){
            return new Response("506", e.getLocalizedMessage()).createJSONObject().toString();
        }
    }
}
