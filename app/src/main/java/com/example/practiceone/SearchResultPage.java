package com.example.practiceone;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.example.APIClientAccess.MedDeviceAPIClientUsage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchResultPage extends AppCompatActivity {
    private String queryID = "";
    ArrayList<String> results = new ArrayList<>();

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
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results);
        listview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        //TODO fix back button issue with hanging loading screen (look into AsyncTask)
        super.onResume();

        addBuffer();
        new SearchTask().execute("1");
    }


    private void addBuffer() {
        results.add(""); //TODO fix layout so you don't need this
    }

    private void resetQueryID() { queryID = ""; }

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

    class SearchTask extends AsyncTask<String, Void, String> {
        ProgressDialog loadingBar = new SubProgressDialog(SearchResultPage.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingBar.setCancelable(true);
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.setMessage("\tLoading...");
            loadingBar.show();
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
            //do your request in here so that you don't interrupt the UI thread
            try {
                MedDeviceAPIClientUsage clientUser = new MedDeviceAPIClientUsage();
                return clientUser.getQueryID((params[0]));
            } catch (JSONException | InterruptedException e) {
                return "Unable to retrieve data. Parameters may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //Here you are done with the task
//            Toast.makeText(SearchResultPage.this, "QueryID: " + result, Toast.LENGTH_LONG).show();
            resetQueryID();
            results.add("Query ID: " + result);
            Log.d("testhere", queryID);
            loadingBar.dismiss();
        }


        private class SubProgressDialog extends ProgressDialog {
            public SubProgressDialog(Context context) {
                super(context);
            }
            @Override
            public void onBackPressed() {
                loadingBar.dismiss();
                SearchTask.this.cancel(true);
                resetQueryID();
            }
        }
    }




}
