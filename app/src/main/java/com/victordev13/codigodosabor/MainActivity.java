package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

            //criar tabela de
            db.execSQL("CREATE TABLE IF NOT EXISTS itens (\n" +
                    "id_itens INT PRIMARY KEY NOT NULL ,\n" +
                    "    nome VARCHAR(45) NOT NULL,\n" +
                    "    descricao VARCHAR(45) NOT NULL,\n" +
                    "    valor DECIMAL(2) NOT NULL,\n" +
                    "    quantidade INT(3) NOT NULL)"
            );
            //criar tabela de pedidos
            db.execSQL("CREATE TABLE IF NOT EXISTS pedidos (\n" +
                    "    id_pedido INT PRIMARY KEY NOT NULL,\n" +
                    "    fk_cliente INT,\n" +
                    "    registro DATETIME NOT NULL,\n" +
                    "    FOREIGN KEY (fk_cliente) REFERENCES clientes (id_clientes))"
            );
                db.execSQL("CREATE TABLE IF NOT EXISTS itens_pedidos (\n" +
                    "\titens_id_itens INT NOT NULL,\n" +
                    "    pedidos_id_pedidos INT NOT NULL,\n" +
                    "    \n" +
                    "    FOREIGN KEY (itens_id_itens) REFERENCES itens (id_itens),\n" +
                    "    FOREIGN KEY (pedidos_id_pedidos) REFERENCES pedidos (id_pedido))"
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean verificaCadastrado(){
        //verificar se existe registro no banco
        try {
            //criando o banco de dados
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

    //fim funções usuário

}