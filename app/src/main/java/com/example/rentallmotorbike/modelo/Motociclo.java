package com.example.rentallmotorbike.modelo;

public class Motociclo {

    private int id, preco,franquia;
    private String marca, combustivel, modelo,matricula, descricao;
    private static int autoIncrementId=1;

    public Motociclo(int id, int preco, String descricao, String marca, String modelo, String combustivel,String matricula,int franquia) {
        this.id = id;
        this.preco = preco;
        this.descricao = descricao;
        this.marca = marca;
        this.combustivel = combustivel;
        this.modelo = modelo;
        this.matricula=matricula;
        this.franquia=franquia;
    }

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getFranquia() {
        return franquia;
    }

    public void setFranquia(int franquia) {
        this.franquia = franquia;
    }
}
