package networking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.turja.spotit2.ShowRankList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ApiCall;
import model.RequestData;

/**
 * Created by CSE_BUET on 1/11/2017.
 */

public class SendRankRequest extends AsyncTask<String, Void, String[]> {
    private Context context;

    public SendRankRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        String []result=new String[2];
//        if(strings[0].equals("location")){
            RequestData requestData=new RequestData("ranking",strings[0]);
            JSONObject jsonObject=requestData.constructJson();
            ApiCall api=new ApiCall();
            api.setGetRelativeUrl("getRank");
             result[0]=api.httpPost(jsonObject.toString(),"application/json");
        result[1]=strings[0];
//        }
        return result;
    }

    @Override
    protected void onPostExecute(String[] s) {
        ArrayList<String> locations=new ArrayList<String>(); ArrayList<Integer>count=new ArrayList<Integer>();
        try {
            JSONArray ja=new JSONArray(s[0]);
            for(int i=0;i<ja.length();i++){
                JSONObject jo=ja.getJSONObject(i);
                locations.add(jo.getString("location"));
                count.add(jo.getInt("count"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(locations);
//        System.out.println(count);
        Intent intent=new Intent(context,ShowRankList.class);
        intent.putExtra("key",locations);
        intent.putExtra("count",count);
        intent.putExtra("criteria",s[1]);
        context.startActivity(intent);
    }
}
