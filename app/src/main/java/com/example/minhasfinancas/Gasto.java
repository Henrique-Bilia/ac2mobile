package com.example.minhasfinancas;

public class Gasto {
    private String descricao;
    private double valor;
    private String categoria;
    private String data;

    public Gasto(String descricao, double valor, String categoria, String data) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
    }

    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public String getCategoria() { return categoria; }
    public String getData() { return data; }
}
