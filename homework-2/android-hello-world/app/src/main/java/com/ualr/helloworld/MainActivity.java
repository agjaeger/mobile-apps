package com.ualr.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView userMsgTV;
    private EditText userInputET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userMsgTV = findViewById(R.id.userMsgTV);
        userInputET = findViewById(R.id.userInputET);
    }

    public void showTextMessage (View view) {
        if (!TextUtils.isEmpty(userInputET.getText())) {
            userMsgTV.setText(userInputET.getText().toString());
        }
    }

    public void clearTextMessage (View view) {
        userInputET.setText("");
    }
}
