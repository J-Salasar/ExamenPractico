package com.example.examenpractico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examenpractico.datos.conexion;
import com.example.examenpractico.datos.consultas;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityEditar extends AppCompatActivity {
    private EditText nombre_texto,numero_texto,nota_texto,buscador_texto;
    private Spinner pais_texto;
    private Button bt_eliminar,bt_cargar_,bt_buscar,bt_camara;
    private int turno=1,valor=0;
    private conexion conectar;
    private ImageView imagen;
    private String foto, currentPhotoPath;
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEFOTO=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        nombre_texto=(EditText) findViewById(R.id.nombre_txt);
        numero_texto=(EditText) findViewById(R.id.numero_txt);
        nota_texto=(EditText) findViewById(R.id.nota_txt);
        buscador_texto=(EditText) findViewById(R.id.txt_buscador);
        pais_texto=(Spinner) findViewById(R.id.spinner_e);
        bt_eliminar=(Button) findViewById(R.id.borrar_bt);
        bt_cargar_=(Button) findViewById(R.id.actualiza_bt);
        bt_buscar=(Button) findViewById(R.id.buscar_bt);
        imagen=(ImageView) findViewById(R.id.imagen_f);
        bt_camara=(Button) findViewById(R.id.camara_bt);
        String[] opciones={
                "",
                "Honduras",
                "El Salvador",
                "Guatemala",
                "Nicaragua",
                "Costa Rica",
        };
        ArrayAdapter<String> adactador = new ArrayAdapter<String>(this, R.layout.item_spinner, opciones);
        pais_texto.setAdapter(adactador);
    }
    public boolean verifica(String dato,int numero){
        String opcion1="[A-Z,a-z,Á,É,Í,Ó,Ú,Ñ,á,é,í,ó,ú,ñ,' ',0-9]{1,200}";
        String opcion2="[0-9]{1,100}";
        switch(numero){
            case 1:{
                return dato.matches(opcion1);
            }
            case 2:{
                return dato.matches(opcion2);
            }
            case 3:{
                return dato.matches(opcion1);
            }
            case 4:{
                return dato.matches(opcion1);
            }
            default:{
                return false;
            }
        }
    }
    public void validar3(View view){
        if(verifica(nombre_texto.getText().toString().trim(),turno)){
            turno=2;
            if(verifica(numero_texto.getText().toString().trim(),turno)){
                turno=3;
                if(verifica(nota_texto.getText().toString().trim(),turno)){
                    turno = 4;
                    if(verifica(pais_texto.getSelectedItem().toString().trim(),turno)) {
                        turno = 1;
                        actializa();
                    }
                    else{
                        turno = 1;
                        Toast.makeText(this, "Elija un país", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    turno=1;
                    Toast.makeText(this, "Escribe una nota", Toast.LENGTH_LONG).show();
                }
            }
            else{
                turno=1;
                Toast.makeText(this, "Escribe un numero de telefono", Toast.LENGTH_LONG).show();
            }
        }
        else{
            turno=1;
            Toast.makeText(this, "Escribe un nombre", Toast.LENGTH_LONG).show();
        }
    }
    public void volver(View view) {
        Intent lista=new Intent(this, ActivityLista.class);
        startActivity(lista);
    }
    public void eliminar(View view){
        SQLiteDatabase db= conectar.getWritableDatabase();
        if(db!=null){
            db.execSQL("DELETE FROM "+ consultas.contacto+
                    " WHERE "+consultas.nombres+"="+nombre_texto.getText().toString());
            db.close();
            Toast.makeText(getApplicationContext(),"Se elimino el registro.",Toast.LENGTH_LONG).show();
            nombre_texto.setEnabled(false);
            numero_texto.setEnabled(false);
            nota_texto.setEnabled(false);
            bt_camara.setEnabled(false);
            bt_eliminar.setEnabled(false);
            bt_cargar_.setEnabled(false);
            buscador_texto.setText("");
            nombre_texto.setText("");
            numero_texto.setText("");
            foto="";
            nota_texto.setText("");
            File foto1=new File(foto);
            imagen.setImageURI(Uri.fromFile(foto1));
        }
        else{
            Toast.makeText(getApplicationContext(),"Error al eliminar",Toast.LENGTH_LONG).show();
        }
    }
    public void actializa(){
        SQLiteDatabase db= conectar.getWritableDatabase();
        if(db!=null){
            db.execSQL("UPDATE "+consultas.contacto+" SET "+
                    consultas.nombres+"='"+nombre_texto.getText().toString()+"', "+
                    consultas.numero+"='"+numero_texto.getText().toString()+"', "+
                    consultas.nota+"='"+nota_texto.getText().toString()+"', "+
                    consultas.url+"='"+currentPhotoPath+"' "+
                    "WHERE "+consultas.nombres+"="+nombre_texto.getText().toString());
            db.close();
            Toast.makeText(getApplicationContext(),"Se actualizo los datos.",Toast.LENGTH_LONG).show();
            nombre_texto.setEnabled(false);
            numero_texto.setEnabled(false);
            bt_eliminar.setEnabled(false);
            bt_cargar_.setEnabled(false);
            bt_camara.setEnabled(false);
            nota_texto.setEnabled(false);
        }
        else{
            Toast.makeText(getApplicationContext(),"Error en actualizar",Toast.LENGTH_LONG).show();
        }
    }
    public void buscar(View view) {
        try{
            SQLiteDatabase db= conectar.getWritableDatabase();
            String[] parametro={nombre_texto.getText().toString()};
            String[] folders={  consultas.nombres,
                    consultas.numero,
                    consultas.nota,
                    consultas.url
            };
            String condicion=consultas.nombres+"=?";
            Cursor data=db.query(consultas.contacto,folders,condicion,parametro,null,null,null);
            data.moveToFirst();
            if(data.getCount()>0){
                valor=data.getInt(0);
                nombre_texto.setText(data.getString(1));
                numero_texto.setText(data.getString(2));
                nota_texto.setText(data.getString(3));
                foto=data.getString(4);
                currentPhotoPath=foto;
                File foto1=new File(foto);
                imagen.setImageURI(Uri.fromFile(foto1));
                Toast.makeText(getApplicationContext(),"Se encontro este resultado.",Toast.LENGTH_LONG).show();
                nombre_texto.setEnabled(true);
                numero_texto.setEnabled(true);
                bt_eliminar.setEnabled(true);
                bt_cargar_.setEnabled(true);
                nota_texto.setEnabled(true);
                bt_camara.setEnabled(true);
            }
            else{
                Toast.makeText(getApplicationContext(),"No hay nada en ese 'Nombre'.",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
    private void galleryAddPic(){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f=new File(currentPhotoPath);
        Uri contenUri=Uri.fromFile(f);
        mediaScanIntent.setData(contenUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try {
                photoFile=createImageFile();
            }
            catch (IOException ex){
            }
            if(photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(this,"xyz.buscaminas.ejemplo4.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUESTTAKEFOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    //Agregaa foto al cuadro
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTTAKEFOTO && resultCode==RESULT_OK){
            File foto=new File(currentPhotoPath);
            imagen.setImageURI(Uri.fromFile(foto));
            galleryAddPic();
        }
    }

    public void permisos(View view){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUESTCODECAMARA);
        }
        else{
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUESTCODECAMARA){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
            }
        }
    }
}