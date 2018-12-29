package com.example.acer.newsonthego;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    TextView txtTitle;
    TextView txtAuthor;
    TextView txtDescription;
    TextView txtDate;
    ArrayList<News_Stuff> news ;
    //String[] news = new String[10];

    public List_Adapter(Context context,ArrayList<News_Stuff> arrayList){
        this.context = context;
        news = arrayList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.list_view,null);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtDate = view.findViewById(R.id.txtDate);
        txtTitle.setText(news.get(position).getTitle());
        txtAuthor.setText(news.get(position).getAuthor());
        txtDescription.setText(news.get(position).getHeadline());
        txtDate.setText(news.get(position).getDate());
        return view;
    }



    /*private class JSONNewsStuffTask extends AsyncTask<String,Void,News_Stuff> {
        Context context;
        ProgressDialog progressDialog;
        int position;
        public JSONNewsStuffTask(Context context,int position){
            this.context = context;
            this.position = position;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait while the news are fetching...");
        }

        @Override
        protected News_Stuff doInBackground(String... strings) {
            News_Stuff news_stuff = new News_Stuff();
            String data = ((new HTTPNewsClient()).getNewsStuff());
            try{
                news_stuff = JSON_newsParser.get_News_Stuff(data,position);
            }catch (Throwable t){
                t.printStackTrace();
            }
            return news_stuff;
        }

        @Override
        protected void onPostExecute(News_Stuff news_stuff) {
            super.onPostExecute(news_stuff);
            txtTitle.setText(news_stuff.getTitle());
            txtAuthor.setText(news_stuff.getAuthor());
            txtDescription.setText(news_stuff.getHeadline());
            txtDate.setText(news_stuff.getDate());

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }*/
}
