package com.example.examenpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private ImageView imagen;
private EditText e_nombre,e_numero,e_nota;
private Spinner p_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen=(ImageView) findViewById(R.id.foto);
        e_nombre=(EditText) findViewById(R.id.txtnombre);
        e_numero=(EditText) findViewById(R.id.txtnumero);
        e_nota=(EditText) findViewById(R.id.txtnota);
        p_spinner=(Spinner) findViewById(R.id.spinner_pais);
    }
}