package com.example.gaurav.card;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Gaurav on 18-10-2016.
 */
public class Post {
    public String name;
    public BitmapDrawable thumbnail;
    public String imageurl;



    public Post(String name, BitmapDrawable thumbnail,String imageurl) {
        this.name = name;
        this.imageurl=imageurl;
        this.thumbnail = thumbnail;
        if(thumbnail==null)
        {
            Log.e("empty","emptyyyyyyyyyyyyy");
        }
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }



    public BitmapDrawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(BitmapDrawable thumbnail) {
        this.thumbnail = thumbnail;
    }
}
