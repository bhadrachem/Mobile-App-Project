package com.example.bhadraother.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;
import java.lang.String;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import xyz.klinker.android.article.ArticleIntent;

public class MainActivity extends AppCompatActivity {
    ArrayList<Article> articles;
    RecyclerView rvArticles;
    ArticleViewAdapter adapter;
    ArrayList<Article> savedArticles = new ArrayList<Article>();
    List<Object> list;
    String wuvaurl = "http://wuvanews.com/wp-json/wp/v2/posts?filter[posts_per_page]=10&_embed=true";
    Gson gson;
    ProgressDialog progressDialog;
    Map<String,Object> mapPost;
    Map<String,Object> mapEmbed;
    Map<String,Object> mapTitle;
    Map<String,Object> mapMedia;
    Map<String,Object> mapAuthor;
    ArrayList<Map<String,Object>> Author;
    Map<String,Object> mapAuthorName;
    ArrayList<Map<String,Object>> Media;
    Map<String,Object> mapLinktoSource;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvArticles = (RecyclerView) findViewById(R.id.recyclerview);
        articles = new ArrayList<Article>();
        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        refreshArticles();
        setAdapter(articles);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                MyAlertDialogFragment alert = new MyAlertDialogFragment();
                alert.show(getSupportFragmentManager(), "Alert");
            }
        });
    }
    public void testGPS(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void refreshArticles(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, wuvaurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);

                for(int i=0;i<list.size();++i) {
                    mapPost = (Map<String, Object>) list.get(i);
                    mapEmbed = (Map<String,Object>) mapPost.get("_embedded");
                    mapTitle = (Map<String, Object>) mapPost.get("title");
                    Author = (ArrayList<Map<String, Object>>) mapEmbed.get("author");
                    mapAuthorName = (Map<String,Object>) Author.get(0);
                    String author = (String) mapAuthorName.get("name");
                    Media = (ArrayList<Map<String,Object>>) mapEmbed.get("wp:featuredmedia");
                    String htitle = (java.lang.String) mapTitle.get("rendered");
                    String title = htitle;
                    String date = (String) mapPost.get("date");
                    String url = (String) mapPost.get("link");
                    mapMedia = (Map<String,Object>) Media.get(0);//(String) mapLinks.get("source_url");
                    String previewURL = (String) mapMedia.get("link");
                    double id = (double) mapPost.get("id");
                    Article article = new Article(id, title, author, date, true, url, previewURL);
                    if (!savedArticles.contains(article))
                        article.setSaved(false);
                    articles.add(article);
                }
                setAdapter(articles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);
        setAdapter(articles);
    }
    public void setAdapter(ArrayList<Article> articles){
        adapter = new ArticleViewAdapter(this,articles);
        rvArticles.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
