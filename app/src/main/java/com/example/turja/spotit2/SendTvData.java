package com.example.turja.spotit2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import model.ApiCall;
import model.TrafficViolation;

/**
 * Created by CSE_BUET on 1/10/2017.
 */

public class SendTvData extends AsyncTask<String,Void,String> {


    AlertDialog.Builder alertDialog;
    private Context context;

    public SendTvData(Context c){
        context=c;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context);
    }

    @Override
    protected String doInBackground(String... trafficViolations) {
        ApiCall api=new ApiCall();
        api.setGetRelativeUrl(trafficViolations[1]);

        String response=api.httpPost(trafficViolations[0],"application/json");
//        System.out.println(response);
        System.out.println(response);
        return response;
    }

//    @Override
    protected void onPostExecute(String result) {
//        System.out.println(result);
//        alertDialog.setTitle("The Process");
////        alertDialog.setIcon(R.drawable.success);
//        alertDialog.setCancelable(false);
//
        if(result.contains("100")){
//            alertDialog.setTitle("Successfully Submitted");
            Toast.makeText(context, "Report Submitted", Toast.LENGTH_LONG).show();
        }
        else{
//            alertDialog.setTitle("Failed to Submit");
            Toast.makeText(context, "Failed to submit", Toast.LENGTH_LONG).show();
        }
//        alertDialog.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent= new Intent(context,HomeActivity.class);
//                        context.startActivity(intent);
//                    }
//                }
//        );
//        alertDialog.show();

    }


}
