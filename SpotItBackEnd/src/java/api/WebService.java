/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import com.sun.xml.wss.impl.misc.Base64;
import db.DBConnector;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        return new Response("505", "Failed").createJSONObject().toString();
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
    @Path("submit")
    public String submitReport(@Context HttpServletRequest headers, String content){
        try{
            JSONObject jsonTv = new JSONObject(content);
            TrafficViolation tv= new TrafficViolation();
            System.out.println(content);
            /** image save
            String photo=jsonTv.getString("photo");
            System.out.println("photo:?:? "+photo);
            byte[] data = Base64.decode(photo);
            OutputStream stream = new FileOutputStream("E:\\Masters\\ashce.jpg");
            stream.write(data);
            stream.flush();stream.close();
            * **/
            if(tv.constructDataFromJson(jsonTv)){
                DBConnector db= new DBConnector();
                if(db.insertTrafficViolation(tv)){
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
