package com.example.bhadraother.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Article> articles;
    CardView articleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleView=(CardView)findViewById(R.id.ArticleView);
    }
}
