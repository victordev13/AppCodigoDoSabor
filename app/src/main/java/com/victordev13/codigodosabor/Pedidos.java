package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        inserePedido();
        getPedidos();

        btnAdicionarPedido = findViewById(R.id.btnAdicionarPedido);

        btnAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CriarPedido.class);
                startActivity(intent);
            }
        });

    }


    private void getPedidos(){
        try {
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT id_pedido FROM  pedidos ORDER BY id_pedido DESC;", null);
            listaPedidos = findViewById(R.id.listaId);
            int indiceTarefaId = cursor.getColumnIndex("id_pedido");

            itens = new ArrayList<String>();

            Toast.makeText(this, "Linhas: " + cursor.getCount(),Toast.LENGTH_LONG);
            if(cursor.getCount() != 0){
                while (cursor != null){
                    Log.i("Resultado - ","NumeroPedido: " + cursor.getString(indiceTarefaId));
                    itens.add("Pedido n° "+cursor.getString(indiceTarefaId)+" - R$"+"00,00");
                    cursor.moveToNext();
                }

            }else{
                Log.i("Resultado", "Nenhum dado encontrado");
            }
            itens.add("Pedido n° 1 - R$00,00");
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, itens);
            listaPedidos.setAdapter(itensAdaptador);

        } catch (Exception e){
            e.getStackTrace();
            Log.i("Erro Adapter", e.getMessage());
        }
    }

    private void inserePedido(){
        try{
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);
            String sql = "INSERT INTO pedidos (fk_cliente, fk_item, quantidade) VALUES(1,1,1);";

            db.execSQL(sql);

            Toast.makeText(getApplicationContext(), "Pedido Inserido", Toast.LENGTH_LONG).show();
            getPedidos();
        } catch (Exception e){
            Log.i("Erro", e.getMessage());
        }


    }

}