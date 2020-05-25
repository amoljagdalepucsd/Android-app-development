package com.example.showpost;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class postListing extends AppCompatActivity {
    private RecyclerView objectRecyclerView;
    public String result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlisting);

        int row_id = getIntent().getIntExtra("text_row", 0);

        com.example.showpost.DatabaseHelper myDbHelper = new com.example.showpost.DatabaseHelper(postListing.this);
        /*try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }*/
        SQLiteDatabase mydb;
        mydb = myDbHelper.openDataBase();

        String result= DatabaseUtils.stringForQuery(mydb,"select topic_name from topics where topic_id"+"=?;", new String[]{String.valueOf(row_id)});

        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        mtoolbar.setTitle(result);

        try {
            objectRecyclerView = findViewById(R.id.imagesRV);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        ArrayList<ModelClass> objectModelClassList2 = myDbHelper.getAllImagesData(row_id);

        if(objectModelClassList2 == null)
        {
                try {
                    throw new MyException(" Posts Not Found");
                }catch (MyException exp){
                  Toast.makeText(this,"Exception: "+exp,Toast.LENGTH_LONG).show();
                }
        }
        else {
            RVAdapter objectRvAdapter = new RVAdapter(this, myDbHelper.getAllImagesData(row_id), row_id);
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectRvAdapter);

        }


        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        objectRecyclerView.addItemDecoration(divider);
    }

    public class MyException extends Exception {
        String str;
        MyException(String str1) {

            str = str1;
        }
        public String toString()
        {
            return(result + str);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu1, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.theme:
                Toast.makeText(this, "Green", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
