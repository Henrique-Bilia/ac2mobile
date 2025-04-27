package com.example.minhasfinancas;

import java.util.ArrayList;
import java.util.List;

public class GastoRepository {
    public static List<Gasto> listaGastos = new ArrayList<>();

    public static void adicionarGasto(Gasto gasto) {
        listaGastos.add(gasto);
    }

    public static List<Gasto> getTodos() {
        return listaGastos;
    }
}
