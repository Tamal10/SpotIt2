package networking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.turja.spotit2.ViolationDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ApiCall;

/**
 * Created by CSE_BUET on 1/14/2017.
 */

public class SendPhotoRequest extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {
    private Context context;

    public SendPhotoRequest(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... strings) {
        // 0-->id 1-->type 2-->loc 3-->date 4-->description
        JSONObject req=new JSONObject();
        try {
            req.put("id",Integer.parseInt(strings[0].get(0)));
            ApiCall api=new ApiCall();
            api.setGetRelativeUrl("getphoto");
            String photo=api.httpPost(req.toString(),"application/json");
            JSONObject jo=new JSONObject(photo);
            photo=jo.getString("photo");
            strings[0].add(photo);
//            for(int i=0;i<strings[0].size();i++)
//                System.out.println(strings[0].get(i)+" ..");

            return strings[0];
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(ArrayList<String> a) {
        Intent intent=new Intent(context,ViolationDetailsActivity.class);
        intent.putExtra("values",a);
        context.startActivity(intent);
    }
}
