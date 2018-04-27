package com.example.bhadraother.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
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
    Map<String,Object> mapTitle;
    Map<String,Object> mapAuthor;
    Map<String,Object> mapAuthorName;
    Map<String,Object> mapLinks;
    Map<String,Object> mapLinktoSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvArticles = (RecyclerView) findViewById(R.id.recyclerview);
        articles = new ArrayList<Article>();
        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        refreshArticles();
        setAdapter(articles);
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
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);

                for(int i=0;i<list.size();++i) {
                    mapPost = (Map<String, Object>) list.get(i);
                    mapTitle = (Map<String, Object>) mapPost.get("title");
                    //mapAuthor = (Map<String, Object>) mapPost.get("author");
                    //mapAuthorName = (Map<String, Object>) mapAuthor.get(0);
                    //mapLinks = (Map<String, Object>) mapPost.get("wp:featuredmedia");
                    String htitle = (java.lang.String) mapTitle.get("rendered");
                    String title = htitle;
                    String date = (String) mapPost.get("date");
                    String url = (String) mapPost.get("link");
                    //mapLinktoSource = (Map<String, Object>) mapLinks.get(0);
                    String previewURL = "a string";//(String) mapLinks.get("source_url");
                    String author = "Genus of the Species";//(String) mapAuthorName.get("name");
                    double id = (double) mapPost.get("id");
                    Article article = new Article(id, title, author, date, true, url, previewURL);
                    if (!savedArticles.contains(article))
                        article.setSaved(false);
                    articles.add(article);
                }
                progressDialog.dismiss();
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
}
