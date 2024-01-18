package com.example.rentallmotorbike.modelo;

public class Perfil {
    private int id, telemovel, nif,nrcarta;
    private String nome, apelido, imgPerfil, username, role, email;

    public Perfil(int id, String nome, String apelido,String username,int telemovel, int nif,int nrcarta, String role, String email) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.apelido = apelido;
        this.telemovel = telemovel;
        this.nif = nif;
        this.nrcarta=nrcarta;
        this.role = role;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int idDados) {
        this.id = idDados;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
