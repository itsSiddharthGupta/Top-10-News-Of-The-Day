package com.example.acer.newsonthego;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPNewsClient {
    private String countryCode;
    private String categoryCode;
    private static String BASE_URL;

    public HTTPNewsClient(String countryCode,String categoryCode){
        this.countryCode = countryCode;
        this.categoryCode = categoryCode;
        BASE_URL = "https://newsapi.org/v2/top-headlines?country=" + countryCode + "&category=" +
                categoryCode + "&apiKey=d0f03357767249dba9395c94bfa3e1a9";
    }

    public static String getNewsStuff(){
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection)(new URL(BASE_URL)).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                stringBuffer.append(line + "\n");
            }

            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuffer.toString();
        }catch (Throwable t){
            t.printStackTrace();
            Log.i("SEE","What Happenend");
        }finally {
            try {
                //If the connection is abandoned in between fetching then the input stream will be null bcz it recieved nothing
                //And if we try to close inputStream i.e. null it will give us
                // java.lang.NullPointerException: Attempt to invoke virtual method 'void java.io.InputStream.close()' on a null object reference
                if(inputStream != null)
                    inputStream.close();
                httpURLConnection.disconnect();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
