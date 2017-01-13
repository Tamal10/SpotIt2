/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import org.json.JSONObject;

/**
 *
 * @author CSE_BUET
 */
public class Response {
    String status;
    String description;

    public Response(String status, String description) {
        this.status = status;
        this.description = description;
    }
    JSONObject createJSONObject(){
        JSONObject jsonData= new JSONObject();
        jsonData.put("Status",this.status);
        jsonData.put("Description",this.description);
        return jsonData;
    }
}
