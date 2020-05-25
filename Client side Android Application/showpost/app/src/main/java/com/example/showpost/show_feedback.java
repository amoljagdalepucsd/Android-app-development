package com.example.showpost;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class show_feedback extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        TextView textView;
        textView=findViewById(R.id.ft);
        int post_id=getIntent().getIntExtra("post_id",0);
        SQLiteDatabase myb;
        com.example.showpost.DatabaseHelper myDbHelper = new com.example.showpost.DatabaseHelper(this);

        myb=myDbHelper. getReadableDatabase();
        Cursor objectCursor = myb.rawQuery("select * from  feedback where post_id="+post_id,null);

        if (objectCursor!=null && objectCursor.getCount()!=0){
        objectCursor.moveToLast();
        String text=objectCursor.getString(2);
        textView.setText(text);
        }
        else {
            Toast.makeText(this, " no feedback for this post", Toast.LENGTH_SHORT).show();

        }


    }
    }
