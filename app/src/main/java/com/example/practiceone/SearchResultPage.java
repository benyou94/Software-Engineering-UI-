package com.example.practiceone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.APIClientAccess.BlockChainQueryAPIClientUsage;
import com.example.Data.Component;
import com.example.Data.MedProduct;
import com.example.ResultParsers.QueryResultParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author Lisa Chen and Ben You
 */
public class SearchResultPage extends AppCompatActivity {

    private SearchTask queryTask;
    private final String LOADING_DIALOG_TEXT = "Loading Results. Please wait.";
    private ArrayAdapter arrayAdapter;
    ListView listview;


    //Dummy test data. Change it to the API later.
    String[] productSKUStringArray = {"0000000000000000000000","11111111111111111111112222222222222222222222222222222222222222","2","3","4","5"};
    String[] productNameStringArray = {"Computer","Pokemon","Tomogachi","Jojo's Bizarre Adventure: HOLY SHIT I GOTA SHIT TON OF TEXT I GATTA REWWWEKK", "Maple Story","Weed"};
    String[] supplierStringArray = {"Sensei","Ash","Ben","GIOGIO","Nexon","Snoop Dog"};


    //These variables are to hold the information from the search parameter variables that passed over
    String productSKUString,productNameString,supplierNameString,blockChainIDString;


    //Creating Arraylists to hold the data from the API.
    ArrayList<String> productNameArrayList = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Redirect's activityView to the called activity
        setContentView(R.layout.activity_search_result_page);

        //Toolbar toolbar = findViewById(R.id.toolbar);

        //Changes the actionbar Title
        getSupportActionBar().setTitle("Search Results:");

        //Gets the search results from activity two
        Intent intent = getIntent();

        //This line should get the information from the variable in the second activity.
        productSKUString = intent.getStringExtra(SearchParameterPage.productSKUExtra);
        productNameString = intent.getStringExtra(SearchParameterPage.productNameExtra);
        supplierNameString = intent.getStringExtra(SearchParameterPage.supplierExtra);


        //Gets the listview from searchresultpage
        listview = (ListView)findViewById(R.id.listView);


        //Creates a customAdapter (custom listview) for the listview
        CustomAdapter customAdapter = new CustomAdapter();

        //sets the listview to the custom adapter
        listview.setAdapter(customAdapter);
    }

    @Override
    protected void onResume() {

        super.onResume();
        queryTask = new SearchTask();



        //Parameter ("BlockChain Choose", ProductID, productName, supplier)
        //0=Etherium, 1=Hyper Ledger, 2=Open Chain
        //return 0 if user enters etherium
        queryTask.execute("1", productSKUString, productNameString, supplierNameString);
    }

    //CustomAdapter for the custom ListView Display
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return productSKUStringArray.length;
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

            //Creating a view and creating variables to grab data and store them in
            convertView = getLayoutInflater().inflate(R.layout.listview_detail,null);

            //This assigns the listview_detail's TextViews into a variable we can display
            TextView textview_name = (TextView) convertView.findViewById((R.id.productNameTextView));
            TextView textview_sku =  (TextView) convertView.findViewById(R.id.productSKUTextView);
            TextView textview_supplier = (TextView) convertView.findViewById((R.id.supplierTextView));
            TextView textview_orderID = (TextView)convertView.findViewById(R.id.orderIDTextView);
            TextView textview_orderDate = (TextView)convertView.findViewById(R.id.orderDateTextView);


            //Dummy Data: Change the array names here to the WebAPI's Array.
            textview_name.setText(productNameStringArray[position]);
            textview_sku.setText(productSKUStringArray[position]);
            textview_supplier.setText(supplierStringArray[position]);

            System.out.println("String Array contents: " +productNameStringArray[position] + " position#: " +position);
            //System.out.println("--ArayList stuff: " +productNameArrayList.get(position));

            //textview_name.setText(productNameString[0]=productSKUSting);


            return convertView;
        }
    }

    /**
     * Class representing the tasks for query searches. Query searches are done asynchronously.
     */
    class SearchTask extends AsyncTask<String, Void, String> {
        ProgressDialog loadingBar;

        /** Setup of task prior to execution. */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setupLoadingDialog();
        }
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {

            try {
                BlockChainQueryAPIClientUsage clientUser = new BlockChainQueryAPIClientUsage();
                JSONArray results = clientUser.getJSONResults(params[0], params[1],params[2],
                        params[3]);
                QueryResultParser queryParser = new QueryResultParser(results);
                ArrayList<Component> productResults = queryParser.getParsedResults();
                int i =0;
                for (Component c : productResults) {
                    MedProduct product = (MedProduct) c;
                    Log.d("LisaComponent", "Name: " + c.getName() + ", SKU: " + c.getSKU() +
                            ", Supplier: " + c.getSupplier() + ", OrderID: " + product.getOrderID() +
                            ", OrderDate: " + product.getOrderDate());
                    productNameArrayList.add(c.getName());

                    System.out.println("Arraylist: " +productNameArrayList.get(i));
                    i++;
                }
                if (isCancelled())
                    return null;
                String queryID = null;
                while (queryID == null) {
                    queryID = clientUser.getQueryID();
                }

                return queryID; //TODO do not pass queryID
            } catch (JSONException e) {
                return "Unable to retrieve data. Parameters may be invalid.";
            }
        }

        /**
         * Once task is complete, this method is called for completion.
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            populateSearchResults(result);
            loadingBar.dismiss();

        }

        /**
         * Populates the screen with the search results.
         * @param result The result from the query search
         */
        private void populateSearchResults(String result) {
//            TODO change for JSON results
//            ArrayList<String> results = new ArrayList<>();
//            results.add(""); //TODO fix layout so this line is not needed
//            results.add("Query ID: " + result);
//            arrayAdapter = new ArrayAdapter(SearchResultPage.this,
//                    android.R.layout.simple_list_item_1, results);
//            listview.setAdapter(arrayAdapter);
        }

        /** To set up the loading dialog seen by the user. */
        private void setupLoadingDialog() {
            loadingBar = new ProgressDialog(SearchResultPage.this);
            loadingBar.setCancelable(true);
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.setMessage("\t" + LOADING_DIALOG_TEXT);
            loadingBar.show();
        }
    }




}
