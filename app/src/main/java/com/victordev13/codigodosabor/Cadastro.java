package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText endereco;
    EditText bairro;
    Button btnCadastro;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        createTable();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        endereco = findViewById(R.id.endereco);
        bairro = findViewById(R.id.bairro);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String nameString = name.getText().toString();
                String passwordString = password.getText().toString();
                String enderecoString = endereco.getText().toString();
                String bairroString = bairro.getText().toString();

                if(nameString.equals("") || emailString.equals("") || passwordString.equals("") || enderecoString.equals("") || bairroString.equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha os campos", Toast.LENGTH_SHORT).show();
                }else{
                    if(insertDB(nameString, emailString, passwordString, enderecoString, bairroString)){
                        Toast.makeText(getApplicationContext(), "Seja, bem vindo!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Erro ao inserir cadastro!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public boolean insertDB(String nome, String email, String senha, String endereco, String bairro){

        try {
           db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE, null);
           //inserir dados no banco
           db.execSQL("INSERT INTO clientes " +
                   "(nome, email, senha, endereco, bairro) " +
                   "VALUES ('"+ nome +"', '"+ email +"', '"+ senha +"', '"+ endereco +"', '"+ bairro +"');");

            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
}

    public void createTable(){
        try {
            //criando o banco de dados
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE, null);

            //criar tabela de clientes
            db.execSQL("CREATE TABLE IF NOT EXISTS clientes (\n" +
                    "    id_clientes INTEGER PRIMARY KEY NOT NULL,\n" +
                    "    nome VARCHAR(50) NOT NULL,\n" +
                    "    email VARCHAR(50) UNIQUE NOT NULL,\n" +
                    "    senha VARCHAR(50) NOT NULL,\n" +
                    "    endereco VARCHAR(45),\n" +
                    "    bairro VARCHAR(45)\n);"
            );

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}