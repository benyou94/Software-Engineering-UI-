package com.example.MediTrackerApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.APIClientAccess.BlockChainQueryAPIClientUsage;
import com.example.Data.Component;
import com.example.Data.MedProduct;
import com.example.ResultParsers.QueryResultParser;
import com.example.UnitTesting.ComponentTestGenerator;
import com.example.UnitTesting.TestDataGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lisa Chen and Ben You
 */
public class SearchResultPage extends AppCompatActivity {

    private SearchTask queryTask;
    private final String LOADING_DIALOG_TEXT = "Loading Results. Please wait.";
    private ArrayAdapter arrayAdapter;
    private ArrayList<Component> productAPIResults;
    ListView listview;
    Context context;
    boolean asyncTaskDone = false;
    ArrayList<Component> currentArray;


    //Dummy data test 1. can remove once we get the official data.
    String[] productSKUStringArray = {"0000000000000000000000","11111111111111111111112222222222222222222222222222222222222222","2","3","4","5"};
    String[] productNameStringArray = {"Computer","Pokemon","Tomogachi","Jojo's Bizarre Adventure: HOLY SHIT I GOTA SHIT TON OF TEXT I GATTA REWWWEKK", "Maple Story","Weed"};
    String[] supplierStringArray = {"Sensei","Ash","Ben","GIOGIO","Nexon","Snoop Dog"};


    //These variables are to hold the information from the search parameter variables that passed over
    String productSKUString,productNameString,supplierNameString,blockChainIDString;

    //Creating Arraylists to hold the data from the API.
    ArrayList<String> productNameArrayList = new ArrayList<String>();
    ArrayList<String> productIDArrayList = new ArrayList<String>();
    ArrayList<String> supplierNameArrayList = new ArrayList<String>();
    ArrayList<String> orderIDArrayList = new ArrayList<String>();
    ArrayList<String> orderDateArrayList = new ArrayList<String>();

    //Component Data: Creating Arraylist to hold/pass data to the next page.
    ArrayList<String> subCompProductNameArrayList = new ArrayList<String>();
    ArrayList<String> subCompProductIDArrayList = new ArrayList<String>();
    ArrayList<String> subCompSupplierArrayList = new ArrayList<String>();



    //Local Data JSON, created for temp use.
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


            //need to pass context (you can only retrieve during onCreate)
            context = getApplicationContext();

            // (!) Component Data
            ArrayList<Component> queryData = TestDataGenerator.getQueryData(context);
            ArrayList<Component> componentData = TestDataGenerator.getComponentData(context);

            ComponentTestGenerator test = new ComponentTestGenerator();
            ArrayList<Component> testSamples = test.getTestProducts();

            //SearchResultPage itemlist populate
            if (queryData != null) {
//                populateScreenArray(queryData);
            }

            //Component Data: store component stuff here and pass it to page 4. (searchresultexpanded)
            if (componentData != null) {
                for (Component item : componentData) {
                    /*Log.d("LisaUnitTest", "Name: " + item.getName() + ", ProductID: " +
                            item.getSKU() + ",Supplier: " + item.getSupplier());
                            */
                    //TODO this is for component data - a different screen

                    subCompProductNameArrayList.add(item.getName());
                    subCompProductIDArrayList.add(item.getSKU());
                    subCompSupplierArrayList.add(item.getSupplier());

                    System.out.println("===== Component Name: " +item.getName()+" | ProductID: " +item.getSKU()+ " | Supplier: " +item.getSupplier()
                            + " | subcomponent: " +item.getSubComponents()+1
                            + " | arraysize: " +subCompProductNameArrayList.size());

                }
                System.out.println("(!!!!!!) STOP ADDING SHIT ====================================================");
            }


            if (testSamples != null){
                for (Component item : testSamples){


                    MedProduct parent = (MedProduct) item;
                    System.out.println("-----> parent name: " +parent.getName());


                    System.out.println("-----> name: " +item.getSubComponents());
                    System.out.println(" ---> Size: " +item.getSubComponents().size());

                    //subCompProductNameArrayList.add();


                }

                currentArray = testSamples;
                populateScreenArray(testSamples);
            }


        //Gets the listview from searchresultpage
        listview = (ListView)findViewById(R.id.listView);

        //Creates a customAdapter (custom listview) for the listview
        CustomAdapter customAdapter = new CustomAdapter();

        //sets the listview to the custom adapter
        listview.setAdapter(customAdapter);


        //(!) ItemClicker for ListView: When clicked, it will go to the next activity.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Creates intent
                Intent intent = new Intent(view.getContext(), SearchResultsExpandedPage.class);

                //(!) This will pass the data to the next activity.
                updateScreenArray(intent, position);


                //Starts the SearchResultsExpanded Activity.
                startActivityForResult(intent,position);
            }
        });


        //ComponentTestGenerator test = new ComponentTestGenerator();
        //ArrayList<Component> testSamples = test.getTestProducts();
    }

    private void resetCompArrays() {
        subCompProductNameArrayList = new ArrayList<>();
        subCompProductIDArrayList = new ArrayList<>();
        subCompSupplierArrayList = new ArrayList<>();
    }

    private void updateScreenArray(Intent intent, int position) {
        intent.putExtra("passProductName", productNameArrayList.get(position));
        intent.putExtra("passProductSKUName", productIDArrayList.get(position));
        intent.putExtra("passSupplierName",supplierNameArrayList.get(position));

        ArrayList<Component> subcomponents = currentArray.get(position).getSubComponents();
        System.out.println("LisaSize: " + subcomponents.size());

        resetCompArrays();
        populateComponentArray(subcomponents);


//        for (int i = 0; i < subcomponents.size(); i++) {
            intent.putExtra("passComponentProductName", subCompProductNameArrayList);
            intent.putExtra("passComponentProductSKU", subCompProductIDArrayList);
            intent.putExtra("passComponentSupplierName", subCompSupplierArrayList);
//        }

        reassignProductArrayList();
    }

    private void reassignProductArrayList() {
        productNameArrayList = subCompProductNameArrayList;
        productIDArrayList = subCompProductIDArrayList;
        supplierNameArrayList = subCompSupplierArrayList;
    }

    /**
     * Populates the array that is displayed onto the UI with the MedProduct data.
     * @param data The data to display
     */
    //Product/SKU/Suppl;ier/OrderDate Data
    private void populateScreenArray(ArrayList<Component> data) {
        for (Component item : data) {
            MedProduct prod = (MedProduct) item;

            //Grabs the data and puts it into the arrayList.
            productNameArrayList.add(prod.getName());
            productIDArrayList.add(prod.getSKU());
            supplierNameArrayList.add(prod.getSupplier());
            orderIDArrayList.add(prod.getOrderID());
            orderDateArrayList.add(prod.getOrderDate());

            System.out.println("==Product Name: " +prod.getName()+" | productID: " +prod.getSKU() +" | ArraySize: " +productNameArrayList.size()+ " |subcomponent: " +prod.getSubComponents() );

        }

        System.out.println("======================================================");
    }

    private void populateComponentArray(ArrayList<Component> data) {
        for (Component item : data) {

            //Grabs the data and puts it into the arrayList.
            subCompProductNameArrayList.add(item.getName());
            subCompProductIDArrayList.add(item.getSKU());
            subCompSupplierArrayList.add(item.getSupplier());
        }

    }



    @Override
    protected void onResume() {

        super.onResume();
        queryTask = new SearchTask();

        //Parameter ("BlockChain Choose", ProductID, productName, supplier)
        //Need to implement a Spinner to get data from activity 2 (searchparameter)
        //0=Etherium, 1=Hyper Ledger, 2=Open Chain
        //return 0 if user enters etherium

        //TODO you still need to pass the blockchain reference from the dropdown
        queryTask.execute("1", "ABC123", null, null);

//        don't populate data until it is complete
//        while (!asyncTaskDone);

        while (productAPIResults == null);
        if (productAPIResults != null)
            populateScreenArray(productAPIResults);
    }

    //CustomAdapter for the custom ListView Display (these methods are generated automatically to handle the custom ListView)
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //dunno what this does. forgot about it :P -Ben
            return productNameArrayList.size();
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
            convertView = getLayoutInflater().inflate(R.layout.listview_detail,null);

            //This assigns the listview_detail's TextViews into a variable we can display
            TextView textview_name = (TextView) convertView.findViewById((R.id.productNameTextView));
            TextView textview_sku =  (TextView) convertView.findViewById(R.id.productSKUTextView);
            TextView textview_supplier = (TextView) convertView.findViewById((R.id.supplierTextView));
            TextView textview_orderID = (TextView)convertView.findViewById(R.id.orderIDTextView);
            TextView textview_orderDate = (TextView)convertView.findViewById(R.id.orderDateTextView);

            System.out.println("Position here" + position);
            //Dummy Data: Change the array names here to the WebAPI's Array.
            //This also prints in the UI (SearchResultsPage) to accurately display data.
            textview_name.setText("Product Name: " +productNameArrayList.get(position));
            textview_sku.setText("Product SKU/ID: " +productIDArrayList.get(position));
            textview_supplier.setText("Supplier Name: " +supplierNameArrayList.get(position));
            textview_orderID.setText("Order ID: " +orderIDArrayList.get(position));
            textview_orderDate.setText("Order Date: " +orderDateArrayList.get(position));

            //System.out.println("String Array contents: " +productNameStringArray[position] + " | position#: " +position);
            //System.out.println("--ArayList stuff: "  + productNameArrayList.get(position)+" | arraylistContents: " +productNameArrayList.size());

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
            asyncTaskDone = false;
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
                JSONObject results = clientUser.getJSONResults(params[0], params[1],params[2],
                        params[3]);
                Log.d("LisaAPIConnectAtSearch", results.toString());
                QueryResultParser queryParser = new QueryResultParser(results);
                Log.d("LisaDoIn", "got to here");
                productAPIResults = queryParser.getParsedResults();
                Log.d("LisaAPIResults", productAPIResults.get(0).getName());
//                asyncTaskDone = true;
//                for (Component c : productAPIResults) {
//                    MedProduct product = (MedProduct) c;
//                    Log.d("LisaAPIConnectionTest", "Name: " + c.getName() + ", SKU: " +
//                            c.getSKU() + ", Supplier: " + c.getSupplier() + ", OrderID: " +
//                            product.getOrderID() + ", OrderDate: " + product.getOrderDate());
//
//                    /*
//                    String getNameString;
//                    getNameString = c.getName();
//                    productNameArrayList.add(getNameString);
//
//                    System.out.println("(===) Arraylist: " +productNameArrayList.get(i));
//                    i++;
//                    */
//                }
                if (isCancelled())
                    return null;
                String queryID = null;
                while (queryID == null) {
                    queryID = clientUser.getQueryID();
                }

                return queryID; //TODO do not pass queryID
            } catch (JSONException | InterruptedException | IOException e) {
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


    public void openLogin(){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }
    //function to return to previous page
    public void openSearchResults(){
        Intent intent = new Intent(this, SearchParameterPage.class);
        startActivity(intent);
        finish();

    }
}
