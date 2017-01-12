package com.example.turja.spotit2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ApiCall;
import model.RequestData;

/**
 * Created by CSE_BUET on 1/11/2017.
 */

public class SendRankRequest extends AsyncTask<String, Void, String> {
    private Context context;

    public SendRankRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result=null;
        if(strings[0].equals("location")){
            RequestData requestData=new RequestData("ranking","location");
            JSONObject jsonObject=requestData.constructJson();
            ApiCall api=new ApiCall();
            api.setGetRelativeUrl("getRank");
             result=api.httpPost(jsonObject.toString(),"application/json");
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<String> locations=new ArrayList<String>(); ArrayList<Integer>count=new ArrayList<Integer>();
        Intent intent=new Intent(context,ShowTvList.class);
        try {
            JSONArray ja=new JSONArray(s);
            for(int i=0;i<ja.length();i++){
                JSONObject jo=ja.getJSONObject(i);
                locations.add(jo.getString("location"));
                count.add(jo.getInt("count"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("key",locations);
        intent.putExtra("count",count);
        context.startActivity(intent);

    }
}
