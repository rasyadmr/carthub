package com.dirajarasyad.carthub.model;

import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class Menu implements Serializable {
    private String title;
    private Drawable image;
    private Fragment fragment;

    public Menu(String title, Drawable image, Fragment fragment) {
        this.title = title;
        this.image = image;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
