package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.*;

public class Cardapio extends AppCompatActivity {

    ListView listaItens;

    ArrayAdapter<String> itensAdaptador;
    ArrayList<String> itens;
    ArrayList<String> valores;

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
            Cursor cursor = db.rawQuery("SELECT * FROM itens ORDER BY id_item DESC;", null);
            listaItens = findViewById(R.id.listaId);
            int indiceItem = cursor.getColumnIndex("nome");
            int valorItem = cursor.getColumnIndex("valor");

            itens = new ArrayList<String>();
            valores = new ArrayList<String>();
            cursor.moveToFirst();
            if(cursor.getCount() != 0){
                for(int i = 0; i < cursor.getCount(); i++){
                    Log.i("Resultado - ","Prato: " + cursor.getString(indiceItem));
                    itens.add(cursor.getString(indiceItem));
                    valores.add("R$"+cursor.getString(valorItem));
                    cursor.moveToNext();
                }
            }else{
                createItensCardapio();
                Log.i("Resultado", "Nenhum dado encontrado");
            }

            //itensAdaptador = new ArrayAdapter<String>(getApplicationContext(), layout.simple_list_item_2, android.R.id.text1, itens);
            itensAdaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, itens) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textoDeCima = (TextView) view.findViewById(android.R.id.text1);
                    TextView textoDeBaixo = (TextView) view.findViewById(android.R.id.text2);

                    textoDeCima.setText(itens.get(position).toString());
                    textoDeBaixo.setText(valores.get(position).toString());
                    return view;
                }
            };

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