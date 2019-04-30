package com.example.practiceone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //Toolbar toolbar = findViewById(R.id.toolbar);

        //Gets the search results from activity two
        Intent intent = getIntent();
        String productSKUSting = intent.getStringExtra(SecondActivity.productSKUExtra);


        ListView listview = (ListView)findViewById(R.id.listView);
    }




}
