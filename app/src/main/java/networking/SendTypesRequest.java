package networking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.turja.spotit2.Report;
import com.example.turja.spotit2.ReportEvent;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import model.ApiCall;

/**
 * Created by CSE_BUET on 1/17/2017.
 */

public class SendTypesRequest extends AsyncTask<Void,Void,String> {

    private Context context;

    public SendTypesRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        ApiCall api= new ApiCall();
        api.setGetRelativeUrl("");
        String response=api.httpPost("","application/json");
        System.out.println(response);
        return response;
    }

    @Override
    protected void onPostExecute(String s){
        Intent intent = new Intent(context, Report.class);
        ArrayList<String> a=new ArrayList<>();
        try {
            JSONArray jo=new JSONArray(s);
            for(int i=0;i<jo.length();i++){
                a.add(jo.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra("values",a);
        context.startActivity(intent);
    }
}
