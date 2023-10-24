package com.example.musicplayer;

import android.graphics.Bitmap;
import android.net.Uri;

public class Music {

    public Uri URI;

    public String NAME;
    public int DURATION ;
    public int SIZE;
    public Bitmap THUMBNAIL;


    public Music (Uri uri, String name, int duration, int size, Bitmap thumbnail) {

        URI = uri;
        NAME = name;
        DURATION = duration;
        SIZE = size;
        THUMBNAIL = thumbnail;
    }

    public Music() {

    }



    public String getName() {
        return NAME ;
    }
    public Uri getUri() {
        return URI ;
    }
    public int  getDuration() {
        return DURATION;
    }
    public int getSize() {
        return SIZE;
    }
    public Bitmap getThumbnail() {
        return THUMBNAIL ;
    }
}

