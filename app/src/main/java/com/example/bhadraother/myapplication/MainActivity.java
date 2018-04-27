package com.example.bhadraother.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;


import xyz.klinker.android.article.ArticleIntent;

public class MainActivity extends AppCompatActivity {
    ArrayList<Article> articles;
    RecyclerView rvArticles;
    ArticleViewAdapter adapter;
    ArrayList<Article> savedArticles;
    CardView articleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvArticles = (RecyclerView) findViewById(R.id.recyclerview);
        articles = new ArrayList<Article>();
        articles.add(new Article("Dope Dope","Yo Mama", "12.2.2017",false,"http://wuvanews.com/2018/04/16/news/former-nfl-player-and-lgbtq-activist-michael-sam-speaks-at-mcleod-hall/"));
        articles.add(new Article("Lope Lope","Sir Konuri", "3.14.1593",false,"https://www.theverge.com/"));
        adapter = new ArticleViewAdapter(this,articles);
        rvArticles.setAdapter(adapter);
        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        rvArticles.invalidate();
    }
    public void testGPS(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void refreshArticles(View view){

    }
}
