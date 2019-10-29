package com.example.homework_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import android.view.View;

import com.example.homework_7.ContactInfoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.id_save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, ContactInfoActivity.class);

            // handle first name
            EditText et_firstName = findViewById(R.id.id_firstname_field);
            myIntent.putExtra("firstName", et_firstName.getText().toString());

            // handle last name
            EditText et_lastName = findViewById(R.id.id_lastname_field);
            myIntent.putExtra("lastName", et_lastName.getText().toString());

            // handle phone number
            EditText et_phoneNumber = findViewById(R.id.id_phone_field);
            myIntent.putExtra("phoneNumber", et_phoneNumber.getText().toString());

            // handle email
            EditText et_email = findViewById(R.id.id_email_field);
            myIntent.putExtra("email", et_email.getText().toString());

            // handle address
            EditText et_address = findViewById(R.id.id_address_field);
            myIntent.putExtra("address", et_address.getText().toString());

            // handle website
            EditText et_website = findViewById(R.id.id_website_field);
            myIntent.putExtra("website", et_website.getText().toString());

            startActivity(myIntent);
            }
        });
    }
}
