package com.example.taskapp.models;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class User implements Serializable {
    private String textNikname;
    private String imageUrl;

    public User() {

    }

    public User(String textNikname, String imageUrl) {
        this.textNikname = textNikname;
        this.imageUrl = imageUrl;
    }

    public String getTextNikname() {
        return textNikname;
    }

    public void setTextNikname(String textNikname) {
        this.textNikname = textNikname;
    }

    public String getImageAvatar() {
        return imageUrl;
    }

    public void setImageAvatar(String imageAvatar) {
        this.imageUrl = imageAvatar;
    }
}
