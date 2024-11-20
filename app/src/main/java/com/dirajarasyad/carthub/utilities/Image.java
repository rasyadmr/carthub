package com.dirajarasyad.carthub.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import java.io.ByteArrayOutputStream;

public class Image {
    private Drawable image;

    public Image(Drawable image) {
        this.image = image;
    }

    public Image(byte[] bytes, Context context) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        this.image = new BitmapDrawable(context.getResources(), bitmap);
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public byte[] getByteArray() {
        Bitmap bitmap = this.getBitmap(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG format

        return byteArrayOutputStream.toByteArray();
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            drawable.draw(new Canvas(bitmap));
            return bitmap;
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

}
