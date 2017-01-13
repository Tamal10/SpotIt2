package networking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.example.turja.spotit2.TvListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ApiCall;
import model.TrafficViolation;

/**
 * Created by CSE_BUET on 1/13/2017.
 */

public class SendSearchRequest extends AsyncTask<String,Void,String> {
    private Context context;

    public SendSearchRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        TrafficViolation tv = new TrafficViolation();
        // 0--> all  1--> none 2-->only time 3-->only date
        System.out.println("Loc: "+ strings[0]+" date: "+strings[1]+" start "+strings[2]+" end "+strings[3]+" type "+strings[4]);
        tv.setPhotoId(0);
        if ((strings[2].equals("") || strings[2].length()==0)&& (strings[1].equals("") || strings[1].length()==0)) {
            tv.setPhotoId(1);
        }
        else if (strings[1].equals("") || strings[1].length()==0){
            tv.setPhotoId(2);
        }
        else if (strings[2].equals("") || strings[2].length()==0){
            tv.setPhotoId(3);;
        }
        System.out.println(tv.getPhotoId()+"lenght: "+strings[2].length());
        tv.setLocation(strings[0]);
        tv.setDate_time(strings[1], strings[3], 0);
        tv.setDate_time(strings[1], strings[2], 1);
        tv.setType(strings[4]);
        String searchJson = tv.constructJsonForSearch().toString();

        ApiCall api = new ApiCall();
        api.setGetRelativeUrl("search");
        String result = api.httpPost(searchJson, "application/json");
        return result;
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
        System.out.println(locations);
        context.startActivity(intent);

    }
}
