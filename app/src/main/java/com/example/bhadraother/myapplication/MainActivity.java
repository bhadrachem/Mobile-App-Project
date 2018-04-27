package com.example.bhadraother.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

import xyz.klinker.android.article.ArticleIntent;

public class MainActivity extends AppCompatActivity {
    ArrayList<Article> articles;
    ArrayList<Article> savedArticles;
    CardView articleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleView=(CardView)findViewById(R.id.ArticleView);
    }
    public void testGPS(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }
    public void shareArticle(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        intent.putExtra(Intent.EXTRA_TEXT, "http://wuvanews.com/2018/04/16/news/former-nfl-player-and-lgbtq-activist-michael-sam-speaks-at-mcleod-hall/");
        startActivity(Intent.createChooser(intent, "Share URL"));
    }
    public void openArticle(View view){
        ArticleIntent intent = new ArticleIntent.Builder(this, "7dd79f0f8c798222747d06c5dd39e308")
                .setToolbarColor(0)
                .build();

        intent.launchUrl(this, Uri.parse("http://wuvanews.com/2018/04/16/news/former-nfl-player-and-lgbtq-activist-michael-sam-speaks-at-mcleod-hall/"));
    }
    public void refreshArticles(View view){

    }
}
