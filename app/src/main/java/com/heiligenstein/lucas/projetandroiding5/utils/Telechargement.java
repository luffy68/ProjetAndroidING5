package com.heiligenstein.lucas.projetandroiding5.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lucas on 28/11/2018.
 */

public class Telechargement {

    static String envoiNotificationFirebase(Context context) {



        try {
            URL url = new URL("https://fcm.googleapis.com/v1/projects/Ing5Android/messages:send HTTP/1.1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("Authorization", "Bearer "+"AAAA1TXgmUM:APA91bHHC-fNi5ufx-BY6EXalwpyEg7eNB25Bue1vLA0d30RlpI9962cklz9Yhgx2fRXXvdpg0qgxJ7yHeydHCgw7c45Pxgm_QMJC8eY72M0AhGOOH9Rm5lIUQfQMx20sdVSjDIhqrnw");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            String v = "{\n" +
                    "  \"message\":{\n" +
                    "    \"token\" : \"dlAdz-blXd4:APA91bEEHNx9a9-phwQH4WfCVsj-6pBpoGcjrqK2vfJ8aSwvnleSHgzYQswMunxiO9XaMsmIIBXwy6tZKer6Fv-yPf-t72PPXATPoEk1584VPeN-EQ56eVw52jF7aIwJkxf8g6uTh1YF\",\n" +
                    "    \"notification\": {\n" +
                    "      \"title\": \"Breaking News\",\n" +
                    "      \"body\": \"New news story available.\"\n" +
                    "    },\n" +
                    "    \"data\": {\n" +
                    "      \"story_id\": \"story_12345\"\n" +
                    "    }\n" +
                    "}";
        /*
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("timestamp", 1488873360);
            jsonParam.put("uname", message.getUser());
            jsonParam.put("message", message.getMessage());
            jsonParam.put("latitude", 0D);
            jsonParam.put("longitude", 0D);


            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());


            os.flush();
            os.close();

          */

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(v.toString());


            os.flush();
            os.close();
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
