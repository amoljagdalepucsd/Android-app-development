package com.example.showpost;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class ImageEffect extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagezoom);

        com.example.showpost.DatabaseHelper myDbHelper = new com.example.showpost.DatabaseHelper(ImageEffect.this);
        try {

            myDbHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        SQLiteDatabase mydb;
        mydb = myDbHelper.openDataBase();

        int id = getIntent().getIntExtra("img_row",0);
        Cursor cursor=mydb.rawQuery("SELECT * FROM posts WHERE post_id=?", new String[] {String.valueOf(id)});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

        }

        byte[] imageBytes = cursor.getBlob(3);
        Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        cursor.close();
        ImageView image = findViewById(R.id.im1);
        image.setImageBitmap(objectBitmap);

        Toolbar mtoolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar1);
        mtoolbar1.setTitle("Image");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu3,menu);
        return super.onCreateOptionsMenu(menu);

    }


}