package com.example.showpost;

import android.graphics.Bitmap;

public class ModelClass {
    private int post_id3;
    private String imageName;
    private Bitmap image;


    public ModelClass(int post_id,String imageName, Bitmap image) {

            this.imageName= imageName;
            this.image = image;
            this.post_id3 = post_id;
    }
    public int getpostid() {
        return post_id3;
    }


    public String getImageName() {

        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }
}
