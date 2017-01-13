/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author CSE_BUET
 */
public class RequestData {
    String request;
    String type;

    public RequestData(String request, String type) {
        this.request = request;
        this.type = type;
    }

    public RequestData() {
        request="";
        type="";
    }

    public boolean constructDataFromJson(JSONObject js){
        try{
            request=js.getString("request");
            type=js.getString("type");
            return true;
        }catch(JSONException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public JSONObject constructJson(){
        JSONObject jsonData= new JSONObject();
        try {
            jsonData.put("request",this.request);
            jsonData.put("type",this.type);
            return jsonData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


