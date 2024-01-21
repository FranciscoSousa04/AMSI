package com.example.rentallmotorbike.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MotocicloBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bd_motociclos";
    private static final int DB_VERSION = 1;
    private static final String TABLE_MOTOCICLOS = "motociclos";
    private static final String ID = "id_motociclo", MARCA = "marca", MODELO = "modelo", COMBUSTIVEL = "combustivel", PRECO = "preco", DESCRICAO = "descricao", FRANQUIA = "franquia";
    private final SQLiteDatabase db;

    public MotocicloBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQLTableLivro = "CREATE TABLE " + TABLE_MOTOCICLOS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MARCA + " TEXT NOT NULL, " +
                MODELO + " TEXT NOT NULL, " +
                COMBUSTIVEL + " TEXT NOT NULL, " +
                PRECO + " INTEGER NOT NULL, " +
                DESCRICAO + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(createSQLTableLivro);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String deleteSQLTableLivro = "DROP TABLE IF EXISTS " + TABLE_MOTOCICLOS;
        sqLiteDatabase.execSQL(deleteSQLTableLivro);
        this.onCreate(sqLiteDatabase);
    }

    public Motociclo adicionarMotocicloBD(Motociclo motociclo) {
        removerAllMotociclosBD();
        ContentValues values = new ContentValues();
        values.put(ID, motociclo.getId());
        values.put(MARCA, motociclo.getMarca());
        values.put(MODELO, motociclo.getModelo());
        values.put(COMBUSTIVEL, motociclo.getCombustivel());
        values.put(PRECO, motociclo.getPreco());
        values.put(DESCRICAO, motociclo.getDescricao());
        db.insert(TABLE_MOTOCICLOS, null, values);

        long id = db.insert(TABLE_MOTOCICLOS, null, values);

        if (id > -1) {
            motociclo.setId((int) id);
            return motociclo;
        }
        return null;
    }

    public boolean editarMotocicloBD(Motociclo motociclo) {

        ContentValues values = new ContentValues();
        values.put(MARCA, motociclo.getMarca());
        values.put(MODELO, motociclo.getModelo());
        values.put(COMBUSTIVEL, motociclo.getCombustivel());
        values.put(PRECO, motociclo.getPreco());
        values.put(DESCRICAO, motociclo.getDescricao());

        return db.update(TABLE_MOTOCICLOS, values, ID + "=?", new String[]{motociclo.getId() + ""}) == 1;


    }

    public boolean removerMotocicloBD(int id) {
        return db.delete(TABLE_MOTOCICLOS, ID + "=?", new String[]{id + ""}) == 1;
    }


    public void removerAllMotociclosBD() {
        db.delete(TABLE_MOTOCICLOS, null, null);

    }

    public ArrayList<Motociclo> getAllMotocicloBD() {
        ArrayList<Motociclo> motociclos = new ArrayList<>();
        Cursor cursor = db.query(TABLE_MOTOCICLOS, new String[]{PRECO, DESCRICAO, MARCA, MODELO, COMBUSTIVEL, ID, FRANQUIA}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Motociclo MotocicloAux = new Motociclo(cursor.getInt(5), cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),  cursor.getInt(6));
                motociclos.add(MotocicloAux);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return motociclos;
    }
}