package com.example.jmonsalve.consummingjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    volatile TextView mTxtDisplay;
    private static String url =//"https://jsonplaceholder.typicode.com/comments";
            //"http://demotfp.easysol.net/TxQRValidationController";//***************
                "http://172.16.17.22:9091/TxQRValidationController";//***************

    //https://jsonplaceholder.typicode.com/users";
    //https://jsonplaceholder.typicode.com/posts/1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtDisplay = findViewById(R.id.txtDisplay);
        final String[] serverResponse = new String[1];

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND + android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE);
                //consumeWS(url);
                //consumeWSsecondOption(url);
                //ConsumeWSthirdOption(url);
                ConsumeWSfourthOption(url);
                //consumeWSparametersOption(url);
                //serverResponse[0] = consumeWS(url);
                return true;
            }

            @Override
            protected void onPostExecute(final Boolean isSecureByRiskRules) {
                super.onPostExecute(isSecureByRiskRules);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //callingAsyncConsume(url);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (serverResponse[0] != null)
                 mTxtDisplay.setText(serverResponse[0].toString());
        //      mTxtDisplay.setTextColor(Color.BLACK);

    }

//{"sharedKey":"Juan_usertest","hashCode":9223372036854775807,"status":"PENDIENTE"}
    public String consumeWS(String url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);

            if (response.getStatusLine().getStatusCode() == 200) {
                String server_response = EntityUtils.toString(response.getEntity());
                mTxtDisplay.setText(server_response);
                Log.i("Server response", server_response);
                return server_response;
            } else {
                Log.i("Server response", "Failed to get server response");
                mTxtDisplay.setText("Error Obteniendo respuesta");
                return "no consumo";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String consumeWSsecondOption(String urlString) {
        try {
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);
            mTxtDisplay.setText(jsonString);

            return jsonString;//new JSONObject(jsonString);
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
        return "";
    }

    public String ConsumeWSthirdOption(String urlString){

        String result = "";
        String postId = "1";

        HttpClient httpclient = new DefaultHttpClient();
        urlString += "?postId=1";

        HttpGet request = new HttpGet(urlString);
        request.addHeader("postId",postId);

        //taking handler to get execution status
        ResponseHandler<String> handler = new BasicResponseHandler();
        try{
            result = httpclient.execute(request, handler);
        }catch (ClientProtocolException cpe){
            cpe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch(IllegalArgumentException iae){
            iae.printStackTrace();
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }

        httpclient.getConnectionManager().shutdown();
        Log.i("Result:   ",result);
        mTxtDisplay.setText(result);
        return result;
    }

    public String ConsumeWSfourthOption(String urlString){

        String result = "";
        String postId = "1";

        HttpClient httpclient = new DefaultHttpClient();
        String qrScanned ="";
        try {
             qrScanned = URLEncoder.encode("{\"sharedKey\":\"Juan_usertest\",\"hashCode\":9223372036854775807,\"status\":\"PENDIENTE\"}","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //urlString += "?sharedKey=Juan_usertest&textScan=9223372036854775807&status=PENDIENTE"+qrScanned;
        urlString += "?textScan="+qrScanned;
        HttpGet request = new HttpGet(urlString);
        request.addHeader("postId",postId);

        //taking handler to get execution status
        ResponseHandler<String> handler = new BasicResponseHandler();
        try{
            result = httpclient.execute(request, handler);
        }catch (ClientProtocolException cpe){
            cpe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch(IllegalArgumentException iae){
            iae.printStackTrace();
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }

        httpclient.getConnectionManager().shutdown();
        Log.i("Result:   ",result);
        //mTxtDisplay.setText(result);
        return result;
    }

//{"id":501}
    public String consumeWSparametersOption(String urlString) {
        try{
            String result = "";
            String postId = "1";

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);



            //taking handler to get execution status
            ResponseHandler<String> handler = new BasicResponseHandler();

            // Build the JSON object to pass parameters
            /*JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("id", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            // Create the POST object and add the parameters
            HttpPost httpPost = new HttpPost(url);
 //          StringEntity entity = null;
//                entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
//            entity.setContentType("application/json");
//            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient();
            String responsee = client.execute(httpPost, handler);



            //


            httpclient.getConnectionManager().shutdown();
            Log.i("Result:   ",responsee);
            mTxtDisplay.setText(responsee);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }catch(IllegalArgumentException iae){
            iae.printStackTrace();
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }

    return "";
    }
}