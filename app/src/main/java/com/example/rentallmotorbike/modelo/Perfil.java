package com.example.rentallmotorbike.modelo;

public class Perfil {
    private int id, telemovel, nif,nrcarta;
    private String nome, apelido, imgPerfil;

    public Perfil(int id, String nome, String apelido, /*String imgPerfil,*/ int telemovel, int nif,int nrcarta) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        //this.imgPerfil = imgPerfil;
        this.telemovel = telemovel;
        this.nif = nif;
        this.nrcarta=nrcarta;
    }

    public int getId() {
        return id;
    }

    public void setId(int idDados) {
        this.id = idDados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(Integer telemovel) {
        this.telemovel = telemovel;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public int getNrCarta() {
        return nrcarta;
    }

    public void setNrCarta(Integer nrcarta) {
        this.nrcarta = nrcarta;
    }
}
