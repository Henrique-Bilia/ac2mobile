package com.example.minhasfinancas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddGastoActivity extends AppCompatActivity {

    private EditText edtDescricao, edtValor, edtData;
    private Spinner spinnerCategoria;
    private Button btnSalvar;

    private GastoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasto);

        edtDescricao = findViewById(R.id.edtDescricao);
        edtValor = findViewById(R.id.edtValor);
        edtData = findViewById(R.id.edtData);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Carrega categorias no Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);


        dbHelper = new GastoDbHelper(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = edtDescricao.getText().toString();
                String valorStr = edtValor.getText().toString();
                String data = edtData.getText().toString();
                String categoria = spinnerCategoria.getSelectedItem().toString();

                if (descricao.isEmpty() || valorStr.isEmpty() || data.isEmpty()) {
                    Toast.makeText(AddGastoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valor = Double.parseDouble(valorStr);

                long resultado = dbHelper.inserirGasto(descricao, valor, categoria, data);
                if (resultado != -1) {
                    Toast.makeText(AddGastoActivity.this, "Gasto salvo!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddGastoActivity.this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}