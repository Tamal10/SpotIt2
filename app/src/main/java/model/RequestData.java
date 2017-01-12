package model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CSE_BUET on 1/11/2017.
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
