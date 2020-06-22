package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Pedidos extends AppCompatActivity {

    ListView listaPedidos;
    FloatingActionButton btnAdicionarPedido;

    ArrayAdapter<String> itensAdaptador;
    ArrayList<String> itens;
    ArrayList<Integer> ids;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        getPedidos();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meus Pedidos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        listaPedidos = findViewById(R.id.listaId);
        btnAdicionarPedido = findViewById(R.id.btnAdicionarPedido);

        btnAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CriarPedido.class);
                startActivity(intent);
            }
        });

        getPedidos();

    }


    private void getPedidos(){
        try {
            //Listar as informações contidas no banco de dados, utilizando a interface Cursor
            //rawQuery() executa o comando SELECT no banco de dados.
            Cursor cursor = db.rawQuery("SELECT * FROM  pedidos ORDER BY id DESC", null);

            //Recuperar indices das colunas para ser utilizado depois
            int indicePedido = cursor.getColumnIndex("id_pedido");

            //Criar a lista
            itens = new ArrayList<String>();

            //Criar o adaptador
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, itens);
            listaPedidos.setAdapter(itensAdaptador);

            //movendo o cursor para o 1° registro
            cursor.moveToFirst();

            while (cursor != null){

                //Adicionar elementos na lista
                itens.add(cursor.getString(indicePedido));

                //Mover cursor para o próximo item
                cursor.moveToNext();
            }
        } catch (Exception e){
            e.getStackTrace();
        }
    }

}