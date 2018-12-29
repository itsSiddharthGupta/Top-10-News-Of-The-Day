package com.example.acer.newsonthego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsList = findViewById(R.id.newsList);
        List_Adapter list_adapter = new List_Adapter(MainActivity.this);
        newsList.setAdapter(list_adapter);

    }
}
