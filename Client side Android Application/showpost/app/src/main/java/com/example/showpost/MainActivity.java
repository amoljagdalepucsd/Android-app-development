package com.example.showpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public class MainActivity  extends AppCompatActivity implements Account.dialogListener {
    private com.example.showpost.DatabaseHelper objectDatabase;
    private RecyclerView objectRecyclerView;
    String user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("auth", 1);
        editor.commit();

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString("user",null);
        user1=user;
        objectRecyclerView = findViewById(R.id.topicview);
        objectDatabase = new com.example.showpost.DatabaseHelper(this);
        Toast.makeText(getApplicationContext(),user1,Toast.LENGTH_LONG).show();

       try {
           objectDatabase.createDataBase();
       }catch (IOException ioe) {
             throw new Error("Unable to create database");
        }
        objectDatabase.openDataBase();

       try{

            mainadapter objectRvAdapter = new mainadapter(this,objectDatabase.gettopicsData());
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectRvAdapter);

        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
       }

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        objectRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.frontpage:
                Toast.makeText(this,"Front Page",Toast.LENGTH_SHORT).show();
                break;
            case R.id.account :

                opendialog();
                //Toast.makeText(this,"No Account",Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public void opendialog() {
        Bundle bundle=new Bundle();
        bundle.putString("user",user1);
       Account dialog= new Account();
       dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void add1() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("auth", 0);
        editor.commit();
        Intent moveToLogin = new Intent(getApplicationContext(),login.class);
        startActivity(moveToLogin);

    }
    @Override
    public void onBackPressed() {
        finishAffinity();

    }

}