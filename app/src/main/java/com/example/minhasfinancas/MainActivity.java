package com.example.minhasfinancas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listaView;
    private Button btnNovoGasto, btnResumo;
    private GastoDbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaGastos;

    @Override
    protected void onResume() {
        super.onResume();
        carregarGastos();
    }

    private void carregarGastos() {
        Cursor cursor = dbHelper.listarGastos();
        listaGastos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

                String linha = descricao + " | R$ " + valor + " | " + categoria + " | " + data;
                listaGastos.add(linha);
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaGastos);
        listaView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaView = findViewById(R.id.listaGastos);
        btnNovoGasto = findViewById(R.id.btnNovoGasto);
        btnResumo = findViewById(R.id.btnResumo);

        dbHelper = new GastoDbHelper(this);

        btnNovoGasto.setOnClickListener(v ->
                startActivity(new Intent(this, AddGastoActivity.class)));

        btnResumo.setOnClickListener(v ->
                startActivity(new Intent(this, ResumoActivity.class)));
    }
}