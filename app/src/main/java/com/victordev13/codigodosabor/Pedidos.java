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

    @Override
    protected void onResume() {
        super.onResume();
        getPedidos();
    }

    private void getPedidos(){
        try {
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT * FROM  pedidos ORDER BY id DESC;", null);
            listaPedidos = findViewById(R.id.listaId);
            int indicePedido = cursor.getColumnIndex("id");
            int valorItem = cursor.getColumnIndex("valor");

            itens = new ArrayList<String>();
            cursor.moveToFirst();

            Log.i("info", ":Quantidade de itens"+cursor.getCount());
            if(cursor.getCount() != 0){
                for(int i = 0; i < cursor.getCount(); i++){
                    Log.i("Info - ","NumeroPedido: " + cursor.getString(indicePedido));
                    itens.add("Pedido n° "+cursor.getString(indicePedido)+" - R$"+cursor.getString(valorItem));
                    cursor.moveToNext();
                }

            }else{
                Log.i("Info", "Nenhum dado encontrado");
            }
            //itens.add("Pedido n° 1 - R$00,00");
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, itens);
            listaPedidos.setAdapter(itensAdaptador);

        } catch (Exception e){
            e.getStackTrace();
            Log.i("Erro:", e.getMessage());
        }
    }


}