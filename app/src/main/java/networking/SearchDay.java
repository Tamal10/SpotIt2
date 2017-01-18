package networking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.turja.spotit2.TvListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ApiCall;

/**
 * Created by CSE_BUET on 1/18/2017.
 */

public class SearchDay extends AsyncTask<String,Void,String> {
        private Context context;

    public SearchDay(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        JSONObject jo=new JSONObject();
        String response=null;
        try {
            jo.put("day",strings[0]);
            ApiCall api=new ApiCall();
            api.setGetRelativeUrl("daySearch");
            response=api.httpPost(jo.toString(),"application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  response;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<Integer> id= new ArrayList<Integer>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();
        ArrayList<String> dateTime = new ArrayList<String>();
        ArrayList<String> description = new ArrayList<String>();
        JSONArray ja = null;
        try {
            ja = new JSONArray(s);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                locations.add(jo.getString("location"));
                id.add(jo.getInt("id"));
                types.add(jo.getString("type"));
                dateTime.add(jo.getString("date_time"));
                description.add(jo.getString("description"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent=new Intent(context,TvListActivity.class);
        intent.putExtra("location",locations);
        intent.putExtra("id",id);
        intent.putExtra("type",types);
        intent.putExtra("dateTime",dateTime);
        intent.putExtra("description",description);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
}
