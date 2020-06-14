package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    ImageView btCardapio;
    ImageView btPedidos;
    ImageView btDados;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTables();

        //se o usuário não estiver logado a activity Login é iniciada
        if(!verificaCadastrado()){
            Intent intentLogin = new Intent(this, Cadastro.class);
            startActivity(intentLogin);
            finish();
        };

        try {
            //db = openOrCreateDatabase("codigoDoSabor", MODE_PRIVATE, null);
            //criacao da tabela principal
            //db.execSQL("");
        } catch(Exception e){
            e.printStackTrace();
        }

        btCardapio = findViewById(R.id.btCardapio);
        btPedidos = findViewById(R.id.btPedidos);
        btDados = findViewById(R.id.btDados);

        btCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cardapio.class);
                startActivity(intent);
            }
        });
        btPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pedidos.class);
                startActivity(intent);
            }
        });
        btDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MeusDados.class);
                startActivity(intent);
            }
        });

    }

    public void createTables(){
        try {
            //criando o banco de dados
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE, null);

            //criar tabela de cardapio
            //criar tabela de pedidos
            db.execSQL("");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean verificaCadastrado(){
        //verificar se existe registro no banco
        return false;
    }

    //fim funções usuário

}