package com.example.practiceone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    private EditText userName;
    private EditText userPassword;
    private Button Submit;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        Submit = (Button) findViewById(R.id.Submit);

        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = userName.getText().toString();
                password = userPassword.getText().toString();
                open2();
            }
        });
    }

    public void open2(){
        Intent intent = new Intent(this, SearchParameterPage.class);
        startActivity(intent);
        finish();

    }
}
