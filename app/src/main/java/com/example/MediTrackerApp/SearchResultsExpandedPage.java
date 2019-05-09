package com.example.MediTrackerApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SearchResultsExpandedPage extends AppCompatActivity {

    //TextView for displaying data.
    private TextView productNameTextView;
    private TextView productSKUTextView;
    private TextView suppplierNameTextView;

    //Creating variables here to hold our pass data from page 3 (searchresultspage)
    String resultExpandedProductNameString;
    String resultExpandedProductSKUString;
    String resultExpandedSupplierString;
    String resultExpandedComponentProductNameString;
    String resultExpandedComponentProductSKUString;
    String resultExpandedComponentSupplierString;


    //ListView for our subcomponent data (customized)
    ListView subcomponentListView;

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

        resultExpandedComponentProductNameString = getIntent().getStringExtra("passComponentProductName");
        resultExpandedComponentProductSKUString = getIntent().getStringExtra("passComponentProductSKU");
        resultExpandedComponentSupplierString = getIntent().getStringExtra("passComponentSupplierName");



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





        //Laura -->
        //TODO: (4) Create a top-right menu bar that guides the user back to the search area. (will need to dispose data?)
        //TODO: (5) OPTIONAL: add a logout button or some shit.




        //ListView Details:

        //Gets the listview from searchresultpage
        subcomponentListView = (ListView)findViewById(R.id.resultExplandListView);

        //Creates a customAdapter (custom listview) for the listview
        CustomAdapter customAdapter = new CustomAdapter();

        //sets the listview to the custom adapter
        subcomponentListView.setAdapter(customAdapter);

    }

    //CustomAdapter for the custom ListView Display (these methods are generated automatically to handle the custom ListView)
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //dunno what this does. forgot about it :P -Ben
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        //The "View" method essentially displays all the data out, customized listview.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Creating a view and creating variables to grab data and store them in
            convertView = getLayoutInflater().inflate(R.layout.listview2_detail,null);

            //This assigns the listview_detail's TextViews into a variable we can display
            productNameTextView = (TextView) convertView.findViewById((R.id.componentNameTextView));
            productSKUTextView =  (TextView) convertView.findViewById(R.id.componentSKUTextView);
            suppplierNameTextView = (TextView) convertView.findViewById((R.id.componentSupplierTextView));


            //Dummy Data: Change the array names here to the WebAPI's Array.
            //This also prints in the UI (SearchResultsExpandedPage) to accurately display data.
            productNameTextView.setText("Component Name: " +resultExpandedComponentProductNameString);
            productSKUTextView.setText("Component SKU: " +resultExpandedComponentProductSKUString);
            suppplierNameTextView.setText("Component Supplier: " +resultExpandedComponentSupplierString);


            //System.out.println("String Array contents: " +productNameStringArray[position] + " | position#: " +position);
            //System.out.println("--ArayList stuff: "  + productNameArrayList.get(position)+" | arraylistContents: " +productNameArrayList.size());

            //textview_name.setText(productNameString[0]=productSKUSting);


            return convertView;
        }
    }


    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, SearchResultPage.class);
        startActivityForResult(myIntent, 0);
        return true;
    }*/




}
