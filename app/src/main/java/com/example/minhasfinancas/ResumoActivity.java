package com.example.minhasfinancas;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class ResumoActivity extends AppCompatActivity {

    private TextView txtResumo;
    private GastoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        txtResumo = findViewById(R.id.txtResumo);
        dbHelper = new GastoDbHelper(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String resumo = calcularResumo();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtResumo.setText(resumo);
                    }
                });
            }
        }).start();
    }

    private String calcularResumo() {
        Cursor cursor = dbHelper.listarGastos();
        double totalGasto = 0;
        Map<String, Double> totalPorCategoria = new HashMap<>();
        String categoriaMaiorGasto = "";
        double maiorGasto = 0;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));

                totalGasto += valor;
                double totalCategoria = totalPorCategoria.getOrDefault(categoria, 0.0);
                totalCategoria += valor;
                totalPorCategoria.put(categoria, totalCategoria);

                if (totalCategoria > maiorGasto) {
                    maiorGasto = totalCategoria;
                    categoriaMaiorGasto = categoria;
                }
            } while (cursor.moveToNext());
        }

        StringBuilder resumo = new StringBuilder();
        resumo.append("Total de gastos: R$ ").append(totalGasto).append("\n\n");
        resumo.append("Gastos por categoria:\n");

        for (Map.Entry<String, Double> entry : totalPorCategoria.entrySet()) {
            resumo.append(entry.getKey()).append(": R$ ").append(entry.getValue()).append("\n");
        }

        resumo.append("\nCategoria com maior gasto: ").append(categoriaMaiorGasto)
                .append(" (R$ ").append(maiorGasto).append(")");

        return resumo.toString();
    }
}