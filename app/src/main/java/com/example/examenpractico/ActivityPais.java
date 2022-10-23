package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityPais extends AppCompatActivity {
    private EditText e_pais,e_codigo;
    private Button crear_b,editar_b;
    private TextView signo;
    private int turno=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);
        e_pais=(EditText) findViewById(R.id.p_nombre);
        e_codigo=(EditText) findViewById(R.id.c_telefono);
        crear_b=(Button) findViewById(R.id.b_crear);
        editar_b=(Button) findViewById(R.id.b_editar);
        signo=(TextView) findViewById(R.id.simbolo);
    }
    public boolean verifica(String dato,int numero){
        String opcion1="[A-Z,a-z,Á,É,Í,Ó,Ú,Ñ,á,é,í,ó,ú,ñ,' ',0-9]{1,200}";
        String opcion2="[0-9]{1,5}";
        switch(numero){
            case 1:{
                return dato.matches(opcion1);
            }
            case 2:{
                return dato.matches(opcion2);
            }
            default:{
                return false;
            }
        }
    }
    public void validar(View view){
        if(verifica(e_pais.getText().toString().trim(),turno)){
            turno=2;
            if(verifica(e_codigo.getText().toString().trim(),turno)){
                turno=1;
                agregarpais();
            }
            else{
                turno=1;
                Toast.makeText(this,"Escribe el codigo telefonico",Toast.LENGTH_LONG).show();
            }
        }
        else{
            turno=1;
            Toast.makeText(this,"Escribe un país",Toast.LENGTH_LONG).show();
        }
    }
    public void agregarpais(){

    }
}