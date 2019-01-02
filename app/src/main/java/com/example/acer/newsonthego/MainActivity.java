package com.example.acer.newsonthego;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.credits){
            Intent intent = new Intent();
            intent.setClass(this,Credits.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

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

            //If the connectin is abandoned in between then the data will recieve null and hence we have to hanndle this error
            //in order to handle the crash. So if the data is null then we return a null to postExecute
            if(data != null) {
                try {
                    for (count = 0; count < 10; count++) {
                        news_stuff = new News_Stuff();
                        JSON_newsParser json_newsParser = new JSON_newsParser(categoryCode);
                        news_stuff = json_newsParser.get_News_Stuff(data, count);
                        news.add(news_stuff);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return news_stuff;
            }
            return null;
        }

        @Override
        protected void onPostExecute(News_Stuff news_stuff) {
            super.onPostExecute(news_stuff);
            Log.i("TAG","Onpostexecute" + news_stuff);
            List_Adapter list_adapter = new List_Adapter(MainActivity.this,news);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            //if we recieve a null object from doInBackground then it means that the network connection is disrupted
            //Hence instead of crashing we will display network_error message to the user

            if(news_stuff != null) {
                newsList.setAdapter(list_adapter);


                newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String url = news.get(position).getUrl();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }else{
                setContentView(R.layout.error_net);
            }
        }
    }
}
