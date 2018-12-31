package com.example.acer.newsonthego;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    TextView txtCreator;
    TextView txtEmail;
    TextView txtPoweredBy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        txtCreator = findViewById(R.id.txtCreator);
        txtEmail = findViewById(R.id.txtEmail);
        txtPoweredBy = findViewById(R.id.txtPoweredBy);
    }
}
