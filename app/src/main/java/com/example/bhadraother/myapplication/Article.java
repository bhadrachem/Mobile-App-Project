package com.example.bhadraother.myapplication;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kvell on 4/16/2018.
 */

public class Article {
    private double id;
    private String title;
    private String author;
    private String date;
    private String url;
    private String previewURL;
    private boolean saved;
    Article(double id, String title,String author,String date,boolean saved,String url,String previewURL){
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = dateFormat(date);
        this.saved = saved;
        this.url = url;
        this.previewURL = previewURL;

    }
    private String dateFormat(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        String out="";
        try {
            Date date2 = dateFormat.parse(date);

            out = dateFormat2.format(date2);
            Log.e("Time", out);
        } catch (ParseException e) {
        }
        return out;
    }
    double getId(){return id;}
    String getTitle(){
        return title;
    }
    String getAuthor(){
        return author;
    }
    String getDate(){
        return date;
    }
    String getURL(){
        return url;
    }
    String getPreviewURL(){return previewURL;}
    boolean isSaved() {return saved;}
    void setSaved(boolean saved){this.saved=saved;}
}
