package com.example.bhadraother.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
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
    private DrawerLayout mDrawerLayout;
    private SharedPreferences sp;

    DatabaseHelper dbHelper;
    long newRowId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0:
                setTheme(R.style.Material);
            case 1:
                setTheme(R.style.MaterialDark);
                break;
            case 2:
                setTheme(R.style.Holo);
            default:
                setTheme(R.style.Material);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvArticles = (RecyclerView) findViewById(R.id.recyclerview);
        articles = new ArrayList<Article>();
        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        if (checkDataBase()) {
            getAllData(articles);
        } else {
            refreshArticles();
        }
        setAdapter(articles);
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
                });

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
        progressDialog.setMessage("Loading Articles...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, wuvaurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);

                for (int i = 0; i < list.size(); ++i) {
                    mapPost = (Map<String, Object>) list.get(i);
                    mapEmbed = (Map<String, Object>) mapPost.get("_embedded");
                    mapTitle = (Map<String, Object>) mapPost.get("title");
                    Author = (ArrayList<Map<String, Object>>) mapEmbed.get("author");
                    mapAuthorName = (Map<String, Object>) Author.get(0);
                    String author = (String) mapAuthorName.get("name");
                    Media = (ArrayList<Map<String, Object>>) mapEmbed.get("wp:featuredmedia");
                    String htitle = (java.lang.String) mapTitle.get("rendered");
                    String title = htitle;
                    String date = (String) mapPost.get("date");
                    String url = (String) mapPost.get("link");
                    mapMedia = (Map<String, Object>) Media.get(0);//(String) mapLinks.get("source_url");
                    String previewURL = (String) mapMedia.get("link");
                    double id = (double) mapPost.get("id");
                    saveToDB(title, author, url, date, id, previewURL);
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
    public void saveToDB(String title, String author, String url, String date, double id, String previewURL)  {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Author", author);
        values.put("URL", url);
        values.put("previewURL", previewURL);
        values.put("Date", date);
        values.put("Saved", 1);
        values.put("id", id);

        // Insert the new row, returning the primary key value of the new row
        newRowId = db.insert("ArticleDB", null, values);
    }

    public void readFromDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                "Title",
                "Author",
                "URL",
                "previewURL",
                "Date",
                "Saved",
                "id"
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                "id" + " DESC";

        Cursor cursor = db.query(
                "ArticleDB",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
    }
    public ArrayList<Article> getAllData (ArrayList<Article> articles) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + "ArticleDB", null);

        while (res.moveToNext()) {
            String title = res.getString(0);   //0 is the number of id column in your database table, it could be different in your database table
            String author = res.getString(1);
            String url = res.getString(2);
            String previewURL = res.getString(3);
            String date = res.getString(4);
            int saved = res.getInt(5);
            double id = res.getDouble(6);

            boolean save = false;
            if (saved == 1) {
                save = true;
            }

            Article newArticle = new Article(id, title, author, date, save, url, previewURL);
            articles.add(newArticle);
        }

        Toast.makeText(this, "This is a test 2!", Toast.LENGTH_SHORT).show();
        return articles;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("ArticleDB", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

}
