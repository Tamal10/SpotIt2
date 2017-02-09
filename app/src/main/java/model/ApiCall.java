package model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by CSE_BUET on 1/10/2017.
 */

public class ApiCall {
    String url;
    String relativeUrl;

    public ApiCall(String getRelativeUrl, String url) {
        this.relativeUrl = getRelativeUrl;
        this.url = url;
    }

    public ApiCall() {
//        this.url="http://10.0.2.2:8080/SpotItBackEnd/webresources/service/";
        this.url="http://172.20.30.112:8080/SpotItBackEnd/webresources/service/";
//        this.url="http://192.168.137.1:8080/SpotItBackEnd/webresources/service/";
    }

    public void setGetRelativeUrl(String getRelativeUrl) {
        this.relativeUrl = getRelativeUrl;
    }

    public String httpPost(String contentData, String contentType){
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url+relativeUrl);
            StringEntity entity = null;
            entity = new StringEntity(contentData, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", contentType);
            httpPost.setHeader("Accept", contentType);
            String response;
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = (httpResponse == null) ? null : httpResponse.getEntity();
            response= EntityUtils.toString(httpEntity,"UTF-8");
            return response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String httpGet(){
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url+relativeUrl);
        String response=null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = (httpResponse == null) ? null : httpResponse.getEntity();
            response= EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
