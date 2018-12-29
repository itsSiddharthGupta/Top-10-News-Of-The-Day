package com.example.acer.newsonthego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String[] news = {"news 1","news 2","news 3","news 4","news 5","news 6","news 7","news 8","news 9","news 10"};

    public List_Adapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return news.length;
    }

    @Override
    public Object getItem(int position) {
        return news[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.list_view,null);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtAuthor = view.findViewById(R.id.txtAuthor);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        TextView txtDate = view.findViewById(R.id.txtDate);
        return view;
    }
}
