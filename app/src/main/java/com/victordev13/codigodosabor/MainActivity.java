package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


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
        verificaCadastrado();
        if(!verificaCadastrado()){
            Intent intentLogin = new Intent(this, Cadastro.class);
            startActivity(intentLogin);
            finish();
        };

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
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE, null);

            db.execSQL("CREATE TABLE IF NOT EXISTS itens (\n" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    nome VARCHAR(45) NOT NULL,\n" +
                    "    valor DECIMAL(2) NOT NULL)"
            );
            Log.i("Tabela criada:", "itens");

            db.execSQL("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    fk_item INT," +
                    "    quantidade INT," +
                    "    valor DOUBLE,  " +
                    "    registro DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    FOREIGN KEY (fk_item) REFERENCES itens (id_item))"
            );
            Log.i("Tabela criada:", "pedidos");

        } catch (Exception e){
            Log.i("Erro", "tabelas não criadas");
            e.printStackTrace();
        }
    }

    public boolean verificaCadastrado(){
        try {
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);

            Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);
            if(cursor.getCount() > 0){
                return true;
            }else{
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}