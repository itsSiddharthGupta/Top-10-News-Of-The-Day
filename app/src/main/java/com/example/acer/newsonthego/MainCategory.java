package com.example.acer.newsonthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainCategory extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    TextView txtCategory;
    TextView txtCategoryCountry;
    RadioGroup radioCategory;
    RadioGroup radioCategoryCountry;
    int selectedCategoryId;
    int selectedCountryId;
    String countryCode;
    String categoryCode;
    Button btnFetch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_category);
        txtCategory = findViewById(R.id.txtCategories);
        txtCategoryCountry = findViewById(R.id.txtCategoryCountry);
        radioCategory = findViewById(R.id.radioCategory);
        radioCategoryCountry = findViewById(R.id.radioCountries);
        btnFetch = findViewById(R.id.btnFetch);
        countryCode = "in";
        categoryCode = "general";

        radioCategory.setOnCheckedChangeListener(MainCategory.this);
        radioCategoryCountry.setOnCheckedChangeListener(MainCategory.this);

        btnFetch.setOnClickListener(MainCategory.this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()){
            case R.id.radioCategory:
                selectedCategoryId = radioCategory.getCheckedRadioButtonId();
                break;

            case R.id.radioCountries:
                selectedCountryId = radioCategoryCountry.getCheckedRadioButtonId();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //GET COUNTRY CODE
        switch (selectedCountryId){
            case R.id.radioIndia:
                countryCode = "in";
                break;

            case R.id.radioUS:
                countryCode = "us";
                break;

            case R.id.radioAus:
                countryCode = "au";
                break;

            case R.id.radioUAE:
                countryCode = "ae";
                break;

            case R.id.radioChina:
                countryCode = "cn";
                break;
        }

        switch (selectedCategoryId){
            case R.id.radioBussiness:
                categoryCode = "business";
                break;

            case R.id.radioTechnology:
                categoryCode = "technology";
                break;

            case R.id.radioSports:
                categoryCode = "sports";
                break;

            case R.id.radioHealth:
                categoryCode = "health";
                break;

            case R.id.radioEntertainment:
                categoryCode = "entertainment";
                break;
        }
        //List_Activity list_activity = new List_Activity();
        Intent intent = new Intent(MainCategory.this,MainActivity.class);
        intent.putExtra("COUNTRY_CODE",countryCode);
        intent.putExtra("CATEGORY_CODE",categoryCode);
        startActivity(intent);
    }
}
