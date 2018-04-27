package com.example.bhadraother.myapplication;

import java.util.Date;

/**
 * Created by kvell on 4/16/2018.
 */

public class Article {
    String title;
    String author;
    String date;
    String url;
    boolean saved;
    Article(String title,String author,String date,boolean saved,String url){
        this.title = title;
        this.author = author;
        this.date = date;
        this.saved = saved;
        this.url = url;
    }
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
    boolean isSaved() {return saved;}
}
