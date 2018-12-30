package com.example.acer.newsonthego;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView newsList;
    int count;
    ArrayList<News_Stuff> news;
    String countryCode;
    String categoryCode;
    /*
    * Now i want to create a layout for making user to click the category for which he wants to view the news
    */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList = findViewById(R.id.newsList);
        count = 0;
        news = new ArrayList<News_Stuff>();
        Bundle extras = getIntent().getExtras();
        countryCode = extras.getString("COUNTRY_CODE");
        categoryCode = extras.getString("CATEGORY_CODE");

        if(isNetworkAvailable()) {
            JSONNewsStuffTask jsonNewsStuffTask = new JSONNewsStuffTask(MainActivity.this);
            jsonNewsStuffTask.execute();
        }else{
            setContentView(R.layout.error_net);
        }


    }
    private class JSONNewsStuffTask extends AsyncTask<String,Void,News_Stuff> {
        Context context;
        ProgressDialog progressDialog;

        public JSONNewsStuffTask(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait while the news are fetching...");
            progressDialog.show();
        }

        @Override
        protected News_Stuff doInBackground(String... strings) {
            News_Stuff news_stuff = new News_Stuff();
            String data = ((new HTTPNewsClient(countryCode,categoryCode)).getNewsStuff());
            try{
                for(count = 0;count<10;count++) {
                    news_stuff = new News_Stuff();
                    news_stuff = JSON_newsParser.get_News_Stuff(data,count);
                    news.add(news_stuff);
                }
            }catch (Throwable t){
                t.printStackTrace();
            }
            return news_stuff;
        }

        @Override
        protected void onPostExecute(News_Stuff news_stuff) {
            super.onPostExecute(news_stuff);
            List_Adapter list_adapter = new List_Adapter(MainActivity.this,news);
            newsList.setAdapter(list_adapter);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
