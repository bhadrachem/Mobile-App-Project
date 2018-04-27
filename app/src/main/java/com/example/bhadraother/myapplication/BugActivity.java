package com.example.bhadraother.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class BugActivity extends AppCompatActivity {

    EditText subject;
    EditText email;
    EditText body;
    CheckBox checkBox;
    Button submit;

    String subjectText;
    String emailText;
    String bodyText;
    boolean receiveReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);

        subject = (EditText) findViewById(R.id.subjectText2);
        email = (EditText) findViewById(R.id.emailText);
        body = (EditText) findViewById(R.id.bodyText2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        submit = (Button) findViewById(R.id.submitButton2);
    }

    public void sendBug(View view) {
        subjectText = subject.getText().toString();
        emailText = email.getText().toString();
        bodyText = body.getText().toString();
        if (checkBox.isChecked()) {
            receiveReply = true;
        }
        else {
            receiveReply = false;
        }
        // send this info somewhere

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
