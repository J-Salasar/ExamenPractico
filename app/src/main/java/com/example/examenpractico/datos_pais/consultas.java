package com.example.examenpractico.datos_pais;

public class consultas {
    public static final String ubicacion="ubicacion ";
    public static final String id="id";
    public static final String pais="pais";
    public static final String codigo="codigo";
    public static final String DataBase="lista";
    public static final String CrearTablaPais="CREATE TABLE "+ubicacion+" "+
            "("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            pais+" TEXT,"+
            codigo+" TEXT,"+
            ")";
    public static final String DropTableContacto="DROP TABLE IF EXISTS "+ubicacion;
}
