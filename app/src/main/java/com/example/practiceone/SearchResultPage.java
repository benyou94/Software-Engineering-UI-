package com.example.practiceone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SearchResultPage extends AppCompatActivity {


    int[] productSKUInteger = {0,1,2,3,4,5};

    String[] productNameString = {"Computer","Pokemon","Tomogachi","Jojo's Bizarre Adventure", "Maple Story","Weed"};

    String[] supplierString = {"Sensei","Ash","Ben","GIOGIO","Nexon","Snoop Dog"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //Toolbar toolbar = findViewById(R.id.toolbar);

        //Gets the search results from activity two
        Intent intent = getIntent();
        String productSKUSting = intent.getStringExtra(SearchParameterPage.productSKUExtra);


        //Gets the listview from activity_third
        ListView listview = (ListView)findViewById(R.id.listView);




    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return productSKUInteger.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }



}
