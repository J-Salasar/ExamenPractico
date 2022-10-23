package com.example.examenpractico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examenpractico.datos.conexion;
import com.example.examenpractico.datos.consultas;
import com.example.examenpractico.datos.contacto;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    private ListView lista;
    private conexion conectar;
    private ArrayList<contacto> listacontato;
    private ArrayList<String> arreglocontacto;
    private int turno=1;
    public boolean verifica(String dato,int numero){
        String opcion1="[A-Z,a-z,Á,É,Í,Ó,Ú,Ñ,á,é,í,ó,ú,ñ,' ',0-9]{1,200}";
        switch(numero){
            case 1:{
                return dato.matches(opcion1);
            }
            default:{
                return false;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lista=(ListView) findViewById(R.id.txt_lista);
        conectar=new conexion(this, consultas.DataBase,null,1);
        ObtenerLista();
        ArrayAdapter adp=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglocontacto);
        lista.setAdapter(adp);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alerta=new AlertDialog.Builder(ActivityLista.this);
                alerta.setMessage("Deseas llamar a: "+listacontato.get(i).getNombres())
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int y) {
                                Intent callintent=new Intent(Intent.ACTION_DIAL);
                                callintent.setData(Uri.parse("tel:"+listacontato.get(i).getNumero()));
                                startActivity(callintent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Llamada");
                titulo.show();
            }
        });
    }
    private void ObtenerLista() {
        SQLiteDatabase db=conectar.getReadableDatabase();
        contacto persona=null;
        listacontato=new ArrayList<contacto>();
        String envio="SELECT * FROM "+consultas.contacto+" WHERE 1";
        Cursor cursor=db.rawQuery(envio,null);
        while (cursor.moveToNext()){
            persona=new contacto();
            persona.setId(cursor.getInt(0));
            persona.setNombres(cursor.getString(1));
            persona.setNumero(cursor.getString(2));
            persona.setNota(cursor.getString(3));
            persona.setUrl(cursor.getString(3));
            listacontato.add(persona);
        }
        cursor.close();
        fllList();
    }

    private void fllList() {
        arreglocontacto=new ArrayList<String>();
        for(int i=0;i<listacontato.size();i++){
            arreglocontacto.add(
                    listacontato.get(i).getNombres()+"\n"+
                    listacontato.get(i).getNumero()
            );
        }
    }
    public void atras(View view){
        Intent inicio=new Intent(this,MainActivity.class);
        startActivity(inicio);
    }
    public void editar(View view){
        Intent mirar=new Intent(this,ActivityEditar.class);
        startActivity(mirar);
    }
}