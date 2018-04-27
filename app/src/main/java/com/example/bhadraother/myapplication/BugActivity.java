package com.example.bhadraother.myapplication;

import android.content.Intent;
import android.net.Uri;
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
    String receiveReply;

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
            receiveReply = "Expects reply.";
        }
        else {
            receiveReply = "No reply required.";
        }
        String complete_data = subjectText + ", " + emailText + ", " + bodyText + ", " + receiveReply;
        // send this info somewhere

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report Test");
        intent.putExtra(Intent.EXTRA_TEXT, complete_data);
        intent.setData(Uri.parse("mailto:suppusan@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);

//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
    }

}
