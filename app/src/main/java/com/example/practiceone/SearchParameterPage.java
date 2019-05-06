package com.example.practiceone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchParameterPage extends AppCompatActivity {

    //Creating Nessiary variables
    private EditText productSKUString; //EditText for Textfield
    private EditText productNameString;
    private EditText supplierString;
    private Button searchingButtion; //Button for button.
    private TextView informationTextView;

    public static String productSKUExtra = "com.example.practiceone.example.productSKUExtra";
    public static String productNameExtra = "com.example.practiceone.example.productNameExtra";
    public static String supplierExtra = "com.example.practiceone.example.supplierExtra";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parameter_page);


        //Getting all the created buttons/textfield/textview from secondActivity and assigning them variables to do stuff.
        productSKUString = (EditText)findViewById(R.id.productSKUTextField);
        productNameString = (EditText)findViewById(R.id.productNameTextField);
        supplierString = (EditText)findViewById(R.id.supplierTextField);
        searchingButtion = (Button)findViewById(R.id.searchButton);
        informationTextView = (TextView)findViewById(R.id.infoTextView);


        //Login ButtonListener.
        searchingButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                searchInfo(productSKUString.getText().toString(), productNameString.getText().toString(),supplierString.getText().toString());


            }
        });

    }


    public void searchInfo(String productSKU, String productName, String supplierName){

        if (productSKU.equals("") || productName.equals("") || supplierName.equals("")){


            informationTextView.setText("Please enter the correct information for all three search fields");

        }

        else{

            //If user enters anything
            informationTextView.setText("");

            //0=Etherium, 1=Hyper Ledger, 2=Open Chain
            Intent intent = new Intent(this, SearchResultPage.class);
                intent.putExtra(productSKUExtra, productSKU);
                intent.putExtra(productNameExtra, productName);
                intent.putExtra(supplierExtra, supplierName);
            startActivity(intent);

        }

    }


}
