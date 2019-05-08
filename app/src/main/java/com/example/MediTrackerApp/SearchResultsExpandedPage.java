package com.example.MediTrackerApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchResultsExpandedPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_expanded_page);

        //Changes the actionbar Title, and add a backbutton for this page.
        getSupportActionBar().setTitle("Search Results Expanded:");
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, SearchResultPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/


}
