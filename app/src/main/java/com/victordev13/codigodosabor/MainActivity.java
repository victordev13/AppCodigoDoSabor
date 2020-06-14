package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.victordev13.codigodosabor.R;


public class MainActivity extends AppCompatActivity {

    ImageView btCardapio;
    ImageView btPedidos;
    ImageView btDados;
    Boolean usuarioLogado = false;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCardapio = findViewById(R.id.btCardapio);
        btPedidos = findViewById(R.id.btPedidos);
        btDados = findViewById(R.id.btDados);

        try {
            //db = openOrCreateDatabase("codigoDoSabor", MODE_PRIVATE, null);
            //criacao da tabela principal
            //db.execSQL("");
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(usuarioLogado == true){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        };
    }

    public boolean verificaLogin(){
        //verificar se o usuário está logado

        if(usuarioLogado == false){
            //caso nao esteja, executa a primeira activity

            return true;
        }else{
            return false;
        }

    }
}