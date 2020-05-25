package com.example.showpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    login_database db;
    EditText rusername;
    EditText rPassword;
    EditText CnfPassword;
    Button Register;
    TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new login_database(this);
        rusername = findViewById(R.id.edittext_username);
        rPassword= findViewById(R.id.edittext_password);
        CnfPassword = findViewById(R.id.edittext_cnf_password);
        Register = findViewById(R.id.button_register);
        Login =findViewById(R.id.textview_login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(getApplicationContext(),login.class);
                startActivity(LoginIntent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = rusername.getText().toString().trim();
                String pwd = rPassword.getText().toString().trim();
                String cnf_pwd = CnfPassword.getText().toString().trim();

                if (user.length() != 0 && pwd.length() != 0 && cnf_pwd.length() != 0) {
                    if (pwd.equals(cnf_pwd)) {
                        long val = db.addUser(user, pwd);
                        if (val > 0) {
                            Toast.makeText(getApplicationContext(), "You have registered", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(getApplicationContext(), login.class);
                            startActivity(moveToLogin);
                        }
                        if (val == 0) {
                            Toast.makeText(getApplicationContext(), "user already exists", Toast.LENGTH_SHORT).show();

                        }
                    } else if (pwd.equals(cnf_pwd) == false) {
                        Toast.makeText(getApplicationContext(), "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (user.length()==0)
                         Toast.makeText(getApplicationContext(), "please enter username", Toast.LENGTH_SHORT).show();
                    else if (pwd.length()==0)
                        Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_SHORT).show();
                    else if (cnf_pwd.length()==0)
                        Toast.makeText(getApplicationContext(), "please confirm your  password", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }
}
