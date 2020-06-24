package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cardapio extends AppCompatActivity {

    EditText textoTarefa;
    Button btnAdicionar;
    ListView listaItens;

    ArrayAdapter<String> itensAdaptador;
    ArrayList<String> itens;
    ArrayList<Integer> ids;

    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        getCardapio();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cardápio");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void getCardapio(){
        try {
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT id_item FROM itens ORDER BY id_item DESC;", null);
            listaItens = findViewById(R.id.listaId);
            int indiceItem = cursor.getColumnIndex("nome");

            itens = new ArrayList<String>();

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    Log.i("Resultado - ","NumeroPedido: " + cursor.getString(indiceItem));
                    itens.add(cursor.getString(indiceItem));
                }

            }else{
                createItensCardapio();
                Log.i("Resultado", "Nenhum dado encontrado");
            }
            itens.add("Prato 1");
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, itens);
            listaItens.setAdapter(itensAdaptador);

        } catch (Exception e){
            e.getStackTrace();
            Log.i("Erro Adapter", e.getMessage());
        }
    }


    private void createItensCardapio(){
        try {
            db.execSQL("INSERT INTO itens(nome, valor, quantidade) VALUES('Prato Feito', '20.00', '10')");
            db.execSQL("INSERT INTO itens(nome, valor, quantidade) VALUES('Porção de Camarão', '35.00', '10')");
            db.execSQL("INSERT INTO itens(nome, valor, quantidade) VALUES('Churrasco', '40.00', '10')");
            db.execSQL("INSERT INTO itens(nome, valor, quantidade) VALUES('Lagosta', '70.00', '10')");

            Log.i("itens adicionados", "tabela itens");

        } catch (Exception e){
            Log.i("Erro ao adicionar", "true");
            e.printStackTrace();
        }
    }
}