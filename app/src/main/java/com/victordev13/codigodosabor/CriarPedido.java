package com.victordev13.codigodosabor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CriarPedido extends AppCompatActivity {

    ListView listaItens;
    Spinner spin;
    Spinner qtd;
    TextView total;
    Button btnPedido;
    Double valorTotal;
    Double valorItem;
    int qtdTotal = 1;
    int itemId;
    Integer nums[] = {1,2,3,4,5,6,7,8,9,10};

    ArrayAdapter<String> itensAdaptador;
    ArrayAdapter<Integer> adapterQtd;
    ArrayList<String> itens;
    ArrayList<Double> valores;
    ArrayList<Integer> ids;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_pedido);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Pedido");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPedido = findViewById(R.id.btnPedido);
        getCardapio();

        qtd = findViewById(R.id.qtd);
        ArrayAdapter adapterQtd = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nums);
        adapterQtd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtd.setAdapter(adapterQtd);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(position != 0){
                    setItemLista(position);
                    Log.i("Info", ":Posição:"+position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        qtd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setTotal(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registraPedido();
            }
        });

    }

    private void getCardapio(){
        try {
            db = openOrCreateDatabase("CodigoDoSabor", MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT * FROM itens ORDER BY id DESC;", null);

            listaItens = findViewById(R.id.listaId);
            spin = findViewById(R.id.spinner);

            int idItem = cursor.getColumnIndex("id");
            int nomeItem = cursor.getColumnIndex("nome");
            int valorItem = cursor.getColumnIndex("valor");

            itens = new ArrayList<String>();
            valores = new ArrayList<Double>();
            ids = new ArrayList<Integer>();

            cursor.moveToFirst();
            itens.add("");
            valores.add(0.0);
            ids.add(0);
            if(cursor.getCount() != 0){
                for(int i = 0; i < cursor.getCount(); i++){
                    Log.i("Resultado - ","Prato: " + cursor.getString(nomeItem)+"id:"+Integer.parseInt(cursor.getString(idItem)));
                    itens.add(cursor.getString(nomeItem) + " -   R$"+Double.parseDouble(cursor.getString(valorItem)));
                    valores.add(Double.parseDouble(cursor.getString(valorItem)));
                    ids.add(Integer.parseInt(cursor.getString(idItem)));
                    cursor.moveToNext();
                }
            }else{
                Log.i("Resultado", "Nenhum dado encontrado");
            }

            itensAdaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itens);
            itensAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(itensAdaptador);

            listaItens.setAdapter(itensAdaptador);

        } catch (Exception e){
            e.getStackTrace();
            Log.i("Erro:", e.getMessage());
        }
    }

    public void setTotal(int qtd){

        if(valorItem != null) {
            total.setText("");
            qtdTotal = qtd;
            valorTotal = qtdTotal * valorItem;
            total.setText("R$" + valorTotal);
            Log.i("Info", "Valor Item: "+valorItem);
            Log.i("Info", "Quantidade: "+qtdTotal);
            Log.i("Info", "Valor: "+valorTotal);
            Log.i("Info", "Quantidade selecionada: " + qtd);
        }

    }
    public void setItemLista(int id){

        total = findViewById(R.id.total);
        total.setText("");
        valorItem = valores.get(id);
        itemId = id;
        setTotal(qtdTotal);

        Log.i("Info", "Id item selecionado: "+id);
        Log.i("Info", "item selecionado: "+itens.get(id));
        Log.i("Info", "Valor selecionado: "+valores.get(id));

    }


    private void registraPedido(){
        try {
            if(this.valorTotal != null){
                db.execSQL("INSERT INTO pedidos(fk_item, quantidade, valor) VALUES('"+ itemId +"','"+qtdTotal+"', '"+valorTotal+"')");
                Log.i("Info Cadastro", ":id="+itemId+":quantidade="+qtdTotal+":valor="+valorTotal);
                Toast.makeText(getApplicationContext(), "Pedido Criado com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();

            }else{
                Toast.makeText(getApplicationContext(), "Erro ao criar pedido!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}