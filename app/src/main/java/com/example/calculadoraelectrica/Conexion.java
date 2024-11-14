package com.example.calculadoraelectrica;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class Conexion extends SQLiteOpenHelper {

    String CREATE_TABLE = "CREATE TABLE Calculos (" +
            "VIN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Voltage DECIMAL(10,2), " +
            "CaidaVoltage DECIMAL(5,2), " +
            "Longitud DECIMAL(20,2), " +
            "Intensidad DECIMAL(20,2), " +
            "FactorPotencia DECIMAL(3,2))";

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        //ESTO ES LA CONEXION A LA BASE DE DATOS
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        //ESTO SOLAMENTE ES LA CREACION DE LA TABLA DE LA BASE DE DATOS QUE NECESITAMOS
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int prevVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Calculos");
        db.execSQL(CREATE_TABLE);
    }
}
