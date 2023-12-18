package com.example.rentallmotorbike.modelo;

public class Extras {
    private int id_extra;
    private String descricao;
    private float preco;

    public Extras(int id_extra, String descricao, float preco) {
        this.id_extra = id_extra;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getId() {
        return id_extra;
    }

    public void setId(int id) {
        this.id_extra = id_extra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
