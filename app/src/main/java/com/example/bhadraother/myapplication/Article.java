package com.example.bhadraother.myapplication;

import java.util.Date;

/**
 * Created by kvell on 4/16/2018.
 */

public class Article {
    String title;
    String author;
    Date date;
    String url;
    Article(String title,String author,Date date,String url){
        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
    }
}
