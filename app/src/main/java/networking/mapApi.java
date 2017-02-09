package networking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by CSE_BUET on 2/8/2017.
 */

public class mapApi extends AsyncTask<String,Void,String> {
    EditText et;
    private ProgressDialog dialog;
    public mapApi(EditText et, Activity activity) {
        this.et = et;
        dialog=new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpGet httpGet = new HttpGet("https://maps.googleapis.com/maps/api/geocode/json?latlng="+strings[0]+","+strings[1]+"&key=AIzaSyDSHj3OtDWTyYBh8ybajayrXj37ukdWusg");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();
        String res=null;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            res= EntityUtils.toString(entity,"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject = new JSONObject();
        String location=null;
        try {
            jsonObject = new JSONObject(s);
            location = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONArray("address_components").getJSONObject(1)
                    .getString("long_name");
            et.setText(location);
            dialog.cancel();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
