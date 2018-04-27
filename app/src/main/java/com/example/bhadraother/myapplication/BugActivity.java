package com.example.bhadraother.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);

        subject = (EditText) findViewById(R.id.subjectText2);
        email = (EditText) findViewById(R.id.emailText);
        body = (EditText) findViewById(R.id.bodyText2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        submit = (Button) findViewById(R.id.submitButton2);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent intent = null;
                        String item = (String) menuItem.getTitle();
                        switch (item) {
                            case "All Stories":
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                break;
                            case "Settings":
                                intent = new Intent(getApplicationContext(), Settings.class);
                                break;
                            case "Send in a Tip":
                                intent = new Intent(getApplicationContext(), MapsActivity.class);
                            case "Shake or Tap to Send Feedback":
                                intent = new Intent(getApplicationContext(), BugActivity.class);
                                break;
                            default:
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                        }
                        startActivity(intent);
                        return true;
                    }
                });    }

    public void sendBug(View view) {
        subjectText = subject.getText().toString();
        emailText = email.getText().toString();
        bodyText = body.getText().toString();
        if (checkBox.isChecked()) {
            receiveReply = "Yes";
        }
        else {
            receiveReply = "No";
        }

        if (subjectText.equals("") || emailText.equals("") || bodyText.equals("")) {
            Snackbar.make(view, "Please fill in all the fields!", Snackbar.LENGTH_LONG).show();
        }
        else {
            String complete_data = "Subject: " + subjectText + '\n' + "Email: " + emailText
                    + '\n' + "Description: " + bodyText + '\n' + "Reply expected? " + receiveReply;
            // send this info somewhere

            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report Test");
            intent.putExtra(Intent.EXTRA_TEXT, complete_data);
            intent.setData(Uri.parse("mailto:suppusan@gmail.com")); // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);
        }
    }

}
