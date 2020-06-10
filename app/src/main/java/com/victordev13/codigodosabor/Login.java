package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(this, "Usuário não está logado", Toast.LENGTH_LONG).show();
    }

    public void login(){

    }
    public String[] getData(){
        String[] data = {"a", "b"};
        return data;
    }

}