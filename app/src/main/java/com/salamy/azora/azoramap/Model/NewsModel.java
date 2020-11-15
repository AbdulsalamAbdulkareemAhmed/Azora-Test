package com.salamy.azora.azoramap.Model;

import android.graphics.Bitmap;

import com.android.volley.toolbox.StringRequest;

public class NewsModel {
    int ID;
    String Name;
    String Image;
    String Date;
    String Title;
    String Content;

    public NewsModel(int id,String name, String image, String date, String title, String content) {
        this.ID=id;
        Name = name;
        Image = image;
        Date = date;
        Title = title;
        Content = content;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getDate() {
        return Date;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}
