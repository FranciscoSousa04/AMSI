package com.example.rentallmotorbike.modelo;

public class Reserva {

    private int id, motociclo_id, profile_id, seguro_id, preco;
    private String data_inicio, data_fim, marca, modelo, seguro, localizacao_levantamento, localizacao_devolucao;

    public Reserva(int id, String data_inicio, String data_fim, int motociclo_id, String marca, String modelo, int profile_id, int seguro_id, String localizacao_levantamento, String localizacao_devolucao, int preco) {
        this.id = id;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.motociclo_id = motociclo_id;
        this.marca = marca;
        this.modelo = modelo;
        this.profile_id = profile_id;
        this.seguro_id = seguro_id;
        this.localizacao_levantamento = localizacao_levantamento;
        this.localizacao_devolucao = localizacao_devolucao;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }

    public int getMotociclo_id() {
        return motociclo_id;
    }

    public void setMotociclo_id(int motociclo_id) {
        this.motociclo_id = motociclo_id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getSeguro_id() {
        return seguro_id;
    }

    public void setSeguro_id(int seguro_id) {
        this.seguro_id = seguro_id;
    }



    public String getLocalizacao_levantamento() {
        return localizacao_levantamento;
    }

    public void setLocalizacao_levantamento(String localizacao_levantamento) {
        this.localizacao_levantamento = localizacao_levantamento;
    }

    public String getLocalizacao_devolucao() {
        return localizacao_devolucao;
    }

    public void setLocalizacao_devolucao(String localizacao_devolucao) {
        this.localizacao_devolucao = localizacao_devolucao;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

}
