package com.example.examenpractico.datos_pais;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.examenpractico.datos.consultas;

public class conexion extends SQLiteOpenHelper {

    public conexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(com.example.examenpractico.datos.consultas.CrearTablaContacto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL(consultas.DropTableContacto);
        onCreate(db);
    }
}
