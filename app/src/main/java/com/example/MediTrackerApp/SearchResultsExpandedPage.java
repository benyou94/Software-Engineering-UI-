package com.example.MediTrackerApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SearchResultsExpandedPage extends AppCompatActivity {

    private TextView productNameTextView;
    private TextView productSKUTextView;
    private TextView suppplierNameTextView;

    //Creating variables here to hold our pass data from page 3 (searchresultspage)
    String resultExpandedProductNameString;
    String resultExpandedProductSKUString;
    String resultExpandedSupplierString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creates the activity and displays the page (blank)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_expanded_page);

        //Changes the actionbar Title, and add a backbutton for this page.
        getSupportActionBar().setTitle("Search Results Expanded:");
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //This grabs the product name, sku, supplier from searchResultPage and assigns them into a global variable we created.
        resultExpandedProductNameString = getIntent().getStringExtra("passProductName");
        resultExpandedProductSKUString = getIntent().getStringExtra("passProductSKUName");
        resultExpandedSupplierString = getIntent().getStringExtra("passSupplierName");

        //This finds the TextView from the XML Page and assignes it into our textview created here
        productNameTextView = (TextView)findViewById(R.id.passProductNamePrevAct);
        productSKUTextView = (TextView)findViewById(R.id.passProductSKUPrevAct);
        suppplierNameTextView = (TextView)findViewById(R.id.passSupplierPrevAct);


        //This changes the text in our activity to the data we got from page 3 (Supply Results Page)
        productNameTextView.setText("Product Name: " +resultExpandedProductNameString);
        productSKUTextView.setText("Product ID: " +resultExpandedProductSKUString);
        suppplierNameTextView.setText("Supplier: " +resultExpandedSupplierString);


        //Ben -->
        //TODO: (1) Need to create another listview similar to the custom one i made for the compoment page.
        //TODO: (2) Maybe need to pass the arraylist of the Component page to here and display it.
        //TODO: (3) Will need to replace the productname/SKU/Supplier if they continue to click on it. (
        //              Use stack on the arraylist like if arraylist is empty then gtfo.
        //TODO: (4) Create a top-right menu bar that guides the user back to the search area. (will need to dispose data?)
        //TODO: (5) OPTIONAL: add a logout button or some shit.


    }

    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, SearchResultPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/


}
