package com.example.practiceone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.APIClientAccess.MedDeviceAPIClientUsage;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author Lisa Chen and Ben You
 */
public class SearchResultPage extends AppCompatActivity {
    private SearchTask queryTask;
    private final String LOADING_DIALOG_TEXT = "Loading Results. Please wait.";
    private ArrayAdapter arrayAdapter;
    ListView listview;

    String[] productSKUInteger = {"0","1","2","3","4","5"};

    String[] productNameString = {"Computer","Pokemon","Tomogachi","Jojo's Bizarre Adventure", "Maple Story","Weed"};
    String[] supplierString = {"Sensei","Ash","Ben","GIOGIO","Nexon","Snoop Dog"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Redirect's activityView to the called activity
        setContentView(R.layout.activity_third);

        //Toolbar toolbar = findViewById(R.id.toolbar);

        //Changes the actionbar Title
        getSupportActionBar().setTitle("Search Results");

        //Gets the search results from activity two
        Intent intent = getIntent();

        String productSKUSting = intent.getStringExtra(SearchParameterPage.productSKUExtra);

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

        queryTask.execute("1"); //TODO change for actual parameters from UI
    }

    //CustomAdapter for the custom ListView Display
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

            //Creating a view and creating variables to grab data and store them in

            convertView = getLayoutInflater().inflate(R.layout.listview_detail,null);

            TextView textview_name = (TextView) convertView.findViewById((R.id.productNameTextView));
            TextView textview_sku =  (TextView) convertView.findViewById(R.id.productSKUTextView);
            TextView textview_supplier = (TextView) convertView.findViewById((R.id.supplierTextView));

            textview_name.setText(productNameString[position]);
            textview_sku.setText(productSKUInteger[position]);
            textview_supplier.setText(supplierString[position]);


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

            //TODO changed for JSON return and multiple parameters passed
            try {
                MedDeviceAPIClientUsage clientUser = new MedDeviceAPIClientUsage();
                clientUser.getJSONResults("1");
                if (isCancelled())
                    return null;
                return clientUser.getQueryID((params[0])); //TODO do not pass queryID
            } catch (JSONException | InterruptedException e) {
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
            //TODO change for JSON results
            //ArrayList<String> results = new ArrayList<>();
            //results.add(""); //TODO fix layout so this line is not needed
            //results.add("Query ID: " + result);
            //arrayAdapter = new ArrayAdapter(SearchResultPage.this,
                    //android.R.layout.simple_list_item_1, results);
            //listview.setAdapter(arrayAdapter);
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
