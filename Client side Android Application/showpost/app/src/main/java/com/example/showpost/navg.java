package com.example.showpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class navg extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int auth;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        auth = prefs.getInt("auth",0);

        if (auth==1){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }
        else if (auth==0){
            Intent intent=new Intent(getApplicationContext(),login.class);
            startActivity(intent);
        }









    }
}
