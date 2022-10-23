package com.example.examenpractico.datos;

public class consultas {
    public static final String contacto="contactos";
    public static final String id="id";
    public static final String nombres="nombres";
    public static final String numero="numero";
    public static final String nota="nota";
    public static final String url= "url";
    public static final String DataBase="lista";
    public static final String CrearTablaContacto="CREATE TABLE "+contacto+" "+
            "("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            nombres+" TEXT,"+
            numero+" TEXT,"+
            nota+" TEXT,"+
            url+" TEXT"+
            ")";
    public static final String DropTableContacto="DROP TABLE IF EXISTS "+contacto;
}
