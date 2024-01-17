package com.example.rentallmotorbike.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ReservaBDHelper extends SQLiteOpenHelper {
    //create a class to save the data of the reservation locally
    private static final String DB_NAME = "bd_motociclos";
    private static final int DB_VERSION = 1;
    private static final String TABLE_RESERVAS = "reservas";
    private static final String ID = "id_reserva", DATA_INICIO = "data_inicio", DATA_FIM = "data_fim", MOTOCICLO_ID = "motociclo_id", MARCA = "marca", MODELO = "modelo", PROFILE_ID = "profile_id", SEGURO_ID = "seguro_id", SEGURO = "seguro", LOCALIZACAO_LEVANTAMENTO = "localizacao_levantamento", LOCALIZACAO_DEVOLUCAO = "localizacao_devolucao", PRECO = "preco";
    private final SQLiteDatabase db;

    public ReservaBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQLTableReserva = "CREATE TABLE " + TABLE_RESERVAS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATA_INICIO + " TEXT NOT NULL, " +
                DATA_FIM + " TEXT NOT NULL, " +
                MOTOCICLO_ID + " INTEGER NOT NULL, " +
                MARCA + " TEXT NOT NULL, " +
                MODELO + " TEXT NOT NULL, " +
                PROFILE_ID + " INTEGER NOT NULL, " +
                SEGURO_ID + " INTEGER NOT NULL, " +
                SEGURO + " TEXT NOT NULL, " +
                LOCALIZACAO_LEVANTAMENTO + " TEXT NOT NULL, " +
                LOCALIZACAO_DEVOLUCAO + " TEXT NOT NULL, " +
                PRECO + " INTEGER NOT NULL" +
                ");";
        db.execSQL(createSQLTableReserva);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteSQLTableReserva = "DROP TABLE IF EXISTS " + TABLE_RESERVAS;
        db.execSQL(deleteSQLTableReserva);
        this.onCreate(db);
    }

    public Reserva adicionarReservaBD(Reserva reserva) {
        ContentValues values = new ContentValues();
        values.put(ID, reserva.getId());
        values.put(DATA_INICIO, reserva.getData_inicio());
        values.put(DATA_FIM, reserva.getData_fim());
        values.put(MOTOCICLO_ID, reserva.getMotociclo_id());
        values.put(MARCA, reserva.getMarca());
        values.put(MODELO, reserva.getModelo());
        values.put(PROFILE_ID, reserva.getProfile_id());
        values.put(SEGURO_ID, reserva.getSeguro_id());
        values.put(SEGURO, reserva.getSeguro());
        values.put(LOCALIZACAO_LEVANTAMENTO, reserva.getLocalizacao_levantamento());
        values.put(LOCALIZACAO_DEVOLUCAO, reserva.getLocalizacao_devolucao());
        values.put(PRECO, reserva.getPreco());
        db.insert(TABLE_RESERVAS, null, values);

        long id = db.insert(TABLE_RESERVAS, null, values);
        reserva.setId((int) id);
        return reserva;
    }

    //edit reservation
    public boolean editarReservaBD(Reserva reserva) {

        ContentValues values = new ContentValues();
        values.put(ID, reserva.getId());
        values.put(DATA_INICIO, reserva.getData_inicio());
        values.put(DATA_FIM, reserva.getData_fim());
        values.put(MOTOCICLO_ID, reserva.getMotociclo_id());
        values.put(MARCA, reserva.getMarca());
        values.put(MODELO, reserva.getModelo());
        values.put(PROFILE_ID, reserva.getProfile_id());
        values.put(SEGURO_ID, reserva.getSeguro_id());
        values.put(SEGURO, reserva.getSeguro());
        values.put(LOCALIZACAO_LEVANTAMENTO, reserva.getLocalizacao_levantamento());
        values.put(LOCALIZACAO_DEVOLUCAO, reserva.getLocalizacao_devolucao());
        values.put(PRECO, reserva.getPreco());

        return db.update(TABLE_RESERVAS, values, ID + "=?", new String[]{reserva.getId() + ""}) > 0;
    }

    //delete reservation
    public boolean removerReservaBD(int id) {
        return db.delete(TABLE_RESERVAS, ID + "=?", new String[]{id + ""}) > 0;
    }

    //delete all reservations
    public void removerAllReservasBD() {
        db.delete(TABLE_RESERVAS, null, null);
    }

    //get all reservations from local database
    public ArrayList<Reserva> getAllReservaBD() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RESERVAS, new String[]{ID, DATA_INICIO, DATA_FIM, MOTOCICLO_ID, MARCA, MODELO, PROFILE_ID, SEGURO_ID, SEGURO, LOCALIZACAO_LEVANTAMENTO, LOCALIZACAO_DEVOLUCAO, PRECO}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String data_inicio = cursor.getString(1);
                String data_fim = cursor.getString(2);
                int motociclo_id = cursor.getInt(3);
                String marca = cursor.getString(4);
                String modelo = cursor.getString(5);
                int profile_id = cursor.getInt(6);
                int seguro_id = cursor.getInt(7);
                String seguro = cursor.getString(8);
                String localizacao_levantamento = cursor.getString(9);
                String localizacao_devolucao = cursor.getString(10);
                int preco = cursor.getInt(11);
                Reserva reserva = new Reserva(id, data_inicio, data_fim, motociclo_id, marca, modelo, profile_id, seguro_id, seguro, localizacao_levantamento, localizacao_devolucao, preco);
                reservas.add(reserva);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reservas;
    }

    


}
