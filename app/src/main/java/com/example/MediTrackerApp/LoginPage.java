package com.example.MediTrackerApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Laura Campos and Ben You
 */

public class LoginPage extends AppCompatActivity {

    //Creating variables to hold loginID,loginPassword
    private EditText Name;
    private EditText Password;
    private TextView Information;
    private Button LoginButton;
    private Button createAccount;
    private int loginCounter = 3;
    //private Button CreateAccount;


    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //create account page
        createAccount = (Button)findViewById(R.id.CreateAccount);


        //Gets the details from the UI and puts them into the variables.
        Name = (EditText)findViewById(R.id.loginName);
        Password = (EditText)findViewById(R.id.loginPassword);
        LoginButton = (Button) findViewById((R.id.loginButton));
        Information = (TextView)findViewById(R.id.loginInformation) ;

        //Login ButtonListener.
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                checkIDPWInfo(Name.getText().toString(), Password.getText().toString());

            }
        });

        //Create Account listener
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCreateAccount();
            }
        });

    }


    private void checkIDPWInfo(String userID, String userPassword){

        System.out.println("UserID: " +userID);
        System.out.println("password: " +userPassword);


        if (userID.equals("") && userPassword.equals("") ){
            Intent intent = new Intent(this, SearchParameterPage.class);
            startActivity(intent);
        }
        else if (userID.equals("hussian") && userPassword.equals("zaidi")){
            Intent intent = new Intent(this, SearchParameterPage.class);
            startActivity(intent);
        }
        else {

            Information.setText("Incorrect login, you have "+String.valueOf(loginCounter)+" tries left.");
            loginCounter--;

            if (loginCounter == 0){

                LoginButton.setEnabled(false);

            }

        }


    }
    public void openCreateAccount(){
        Intent intent = new Intent (this, CreateAccount.class );
        startActivity(intent);


    }

}
