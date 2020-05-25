package com.example.showpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    EditText username;
    EditText Password;
    Button Login;
    TextView Register;
    login_database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("auth", 0);
        editor.commit();



         db = new login_database(this);
         username = findViewById(R.id.edittext_username);
         Password = findViewById(R.id.edittext_password);
         Login = findViewById(R.id.button_login);
         Register = findViewById(R.id.textview_register);

         Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(),register.class);
                startActivity(registerIntent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pwd =Password.getText().toString().trim();
                 if (user.length()!=0 && pwd.length()!=0){
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user", user);
                    editor.commit();
                    Intent HomePage = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(HomePage);

                }
                else if (res==false){
                    Toast.makeText(getApplicationContext(),"wrong usenname or password", Toast.LENGTH_SHORT).show();

                }
                 }
                else if (user.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"please enter username", Toast.LENGTH_SHORT).show();
                }
                else if(pwd.length()==0)
                 {
                     Toast.makeText(getApplicationContext(),"password is missing", Toast.LENGTH_SHORT).show();

                 }

            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();

    }
}
