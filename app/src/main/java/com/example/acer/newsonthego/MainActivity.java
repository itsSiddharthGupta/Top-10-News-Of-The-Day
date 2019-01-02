package com.example.acer.newsonthego;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News_Stuff>> {
    ListView newsList;
    int count;
    String countryCode;
    String categoryCode;
    ProgressDialog progressDialog;
    List_Adapter list_adapter;
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
        Bundle extras = getIntent().getExtras();
        countryCode = extras.getString("COUNTRY_CODE");
        categoryCode = extras.getString("CATEGORY_CODE");
        progressDialog = new ProgressDialog(MainActivity.this);

        if(isNetworkAvailable()) {
            getLoaderManager().initLoader(0,null,this);
        }else{
            setContentView(R.layout.error_net);
        }
    }

    @Override
    public Loader<ArrayList<News_Stuff>> onCreateLoader(int id, Bundle args) {
        News_Stuff_Loader news_stuff_loader = new News_Stuff_Loader(MainActivity.this,countryCode,categoryCode);
        progressDialog.setTitle("Please wait while the news are fetching...");
        progressDialog.show();
        return news_stuff_loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News_Stuff>> loader,final ArrayList<News_Stuff> news) {

        list_adapter = new List_Adapter(MainActivity.this,news);


        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        //if the network is disrupted then instead of crashing we need to display the msg of network error
        if(news != null) {
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

    @Override
    public void onLoaderReset(Loader<ArrayList<News_Stuff>> loader) {
            Log.v("TAG","Loader Reset");
            list_adapter.clear();
    }
}
