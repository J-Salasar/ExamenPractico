package com.example.examenpractico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examenpractico.datos.conexion;
import com.example.examenpractico.datos.consultas;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
private ImageView imagen;
private EditText e_nombre,e_numero,e_nota;
private Spinner p_spinner;
private String currentPhotoPath;
private Button guardar;
private static final int REQUESTCODECAMARA=100;
private static final int REQUESTTAKEFOTO=101;
private int turno=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen=(ImageView) findViewById(R.id.foto);
        e_nombre=(EditText) findViewById(R.id.txtnombre);
        e_numero=(EditText) findViewById(R.id.txtnumero);
        e_nota=(EditText) findViewById(R.id.txtnota);
        p_spinner=(Spinner) findViewById(R.id.spinner_pais);
        guardar=(Button) findViewById(R.id.salvar);
        String[] opciones = {""};
        ArrayAdapter<String> adactador = new ArrayAdapter<String>(this, R.layout.item_spinner, opciones);
        p_spinner.setAdapter(adactador);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
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
    public void validar(View view){
        if(verifica(e_nombre.getText().toString().trim(),turno)){
            turno=2;
            if(verifica(e_numero.getText().toString().trim(),turno)){
                turno=3;
                if(verifica(e_nota.getText().toString().trim(),turno)){
                    turno = 4;
                    if(verifica(p_spinner.getSelectedItem().toString().trim(),turno)) {
                        turno = 1;
                        agregarcontacto();
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
    public void galleryAddPic(){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f=new File(currentPhotoPath);
        Uri contenUri=Uri.fromFile(f);
        mediaScanIntent.setData(contenUri);
        this.sendBroadcast(mediaScanIntent);
    }
    public void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try {
                photoFile=createImageFile();
            }
            catch (IOException ex){
            }
            if(photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(this,"com.example.examenpractico.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUESTTAKEFOTO);
            }
        }
    }
    public File createImageFile() throws IOException {
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
    //Agrega la foto al cuadro
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTTAKEFOTO && resultCode==RESULT_OK){
            File foto=new File(currentPhotoPath);
            imagen.setImageURI(Uri.fromFile(foto));
            guardar.setEnabled(true);
            galleryAddPic();
        }
    }
    public void permisos(){
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
    public void agregarcontacto(){
        /*conexion a_conexion=new conexion(this, consultas.DataBase,null,1);
        SQLiteDatabase db=a_conexion.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put(consultas.nombres,e_nombre.getText().toString());
        valores.put(consultas.numero,e_numero.getText().toString());
        valores.put(consultas.nota,e_nota.getText().toString());
        valores.put(consultas.url,currentPhotoPath);
        valores.put(consultas.pais,String.valueOf(p_spinner.getSelectedItem()));
        Long resultado=db.insert(consultas.contacto,consultas.id,valores);
        Toast.makeText(getApplicationContext(),"Registro guardado",Toast.LENGTH_LONG).show();
        db.close();*/
        limpiar();
    }
    public void limpiar(){
        e_nombre.setText("");
        e_numero.setText("");
        currentPhotoPath="";
        File foto=new File(currentPhotoPath);
        imagen.setImageURI(Uri.fromFile(foto));
        e_nota.setText("");
        guardar.setEnabled(false);
    }
}