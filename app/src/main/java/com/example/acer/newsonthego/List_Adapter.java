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

    public void clear() {
        news.clear();
    }
}
