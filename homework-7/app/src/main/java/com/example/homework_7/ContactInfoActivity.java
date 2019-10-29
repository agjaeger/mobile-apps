package com.example.homework_7;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class ContactInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Intent intent = getIntent();

        // handle name text view
        TextView tv_name = findViewById(R.id.id_contact_name);
        tv_name.setText(
            intent.getStringExtra("firstName") + " " + intent.getStringExtra("lastName")
        );

        // handle phone number
        TextView tv_phoneNumber = findViewById(R.id.id_contact_phone);
        final String phoneNumber = intent.getStringExtra("phoneNumber").toString();
        tv_phoneNumber.setText(phoneNumber);

        // handle email
        TextView tv_email = findViewById(R.id.id_contact_email);
        final String emailAddr = intent.getStringExtra("email").toString();
        tv_email.setText(emailAddr);

        // handle address
        TextView tv_address = findViewById(R.id.id_contact_address);
        final String address = intent.getStringExtra("address").toString();
        tv_address.setText(address);

        // handle website
        TextView tv_website = findViewById(R.id.id_contact_website);
        final String website = intent.getStringExtra("website").toString();
        tv_website.setText(website);


        // -----------------------------------------------------------------------------------------

        // handle call button
        Button callBtn = (Button) findViewById(R.id.id_call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri parsedURI = Uri.parse("tel:" + phoneNumber);

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(parsedURI);

                startActivity(intent);
            }
        });

        // handle text button
        Button textBtn = (Button) findViewById(R.id.id_text_btn);
        textBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri parsedURI = Uri.parse("smsto:" + phoneNumber);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(parsedURI);

                startActivity(intent);
            }
        });

        // handle email button
        Button emailBtn = (Button) findViewById(R.id.id_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailSubject = "Department Meeting";
                String emailText = "We will discuss new curriculum on Tue. at 9:00am @ room BU340";
                String emailReceiverList[] = {"vmchau@ualr.edu"};

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, emailReceiverList);
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                intent.putExtra(Intent.EXTRA_TEXT, emailText);

                startActivity(intent);
            }
        });

        // handle map button
        Button mapBtn = (Button) findViewById(R.id.id_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri parsedURI = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode(address));

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(parsedURI);

                intent.setPackage("com.google.android.apps.maps");

                startActivity(intent);
            }
        });

        // handle web button
        Button webBtn = (Button) findViewById(R.id.id_web_btn);
        webBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri parsedURI = Uri.parse(website);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(parsedURI);

                startActivity(intent);
            }
        });
    }
}