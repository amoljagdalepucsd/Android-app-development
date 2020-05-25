package com.example.showpost;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class display  extends AppCompatActivity  implements dialog.dialogListener{
    int post_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w1);

        Toolbar mtoolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar1);
        SQLiteDatabase mydb;
        WebView web = findViewById(R.id.w1);
        com.example.showpost.DatabaseHelper myDbHelper = new com.example.showpost.DatabaseHelper(display.this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        mydb=myDbHelper.openDataBase();
        int post_id1=getIntent().getIntExtra("text_row",0);
        post_id=post_id1;

        String data= DatabaseUtils.stringForQuery(mydb,"select post_text from posts where post_id"+"=?;", new String[]{String.valueOf(post_id1)});



        Document document = Jsoup.parse(data);

        Elements imgtag = document.select("img");
        for (Element element :imgtag)
        {
            String key = element.attr("src");

            Cursor img_cur=mydb.rawQuery("SELECT * FROM image WHERE img_id=?", new String[] {key});
            if( img_cur!=null && img_cur.getCount()>0) {
                img_cur.moveToFirst();
                byte[] image= img_cur.getBlob(2);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(image, 0, image.length);

                String name = save(bitmap1,key);
                element.attr("src","file:///data/data/com.example.showpost/app_images/image"+key+".jpg");

            }



            img_cur.close();

        }

        String str = document.toString();
        web.loadDataWithBaseURL("",str,"text/html", "UTF-8","");
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setJavaScriptEnabled(true);


    }

    public String save(Bitmap bitmap,String str){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File file = new File(directory, "image"+str+".jpg");

        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
     return file.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
           case R.id.comment:
                openDialog();
                //Intent intent = new Intent(this,MainActivity.class);
                //startActivity(intent);
               // // Toast.makeText(this, "Front Page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.showcomment:
                Intent intent = new Intent(this, show_feedback.class);
                intent.putExtra("post_id",post_id);
                startActivity(intent);
                break;



            case R.id.remove:
                Toast.makeText(this, "Remove from Favourites", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    public void openDialog() {
        dialog dialog = new dialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void add(String comment) {
        SQLiteDatabase myb1;
        com.example.showpost.DatabaseHelper myDbHelper = new com.example.showpost.DatabaseHelper(display.this);
        myb1=myDbHelper. getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("post_id",post_id);
        values.put("com_text",comment);
        myb1.insert("feedback",null,values);
        Toast.makeText(this, "feedback submitted", Toast.LENGTH_SHORT).show();

    }

}

