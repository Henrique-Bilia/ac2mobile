package com.example.minhasfinancas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GastoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "financas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "gastos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESC = "descricao";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_DATA = "data";

    public GastoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_VALOR + " REAL, " +
                COLUMN_CATEGORIA + " TEXT, " +
                COLUMN_DATA + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long inserirGasto(String descricao, double valor, String categoria, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, descricao);
        values.put(COLUMN_VALOR, valor);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_DATA, data);
        return db.insert(TABLE_NAME, null, values);
    }


    public Cursor listarGastos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}