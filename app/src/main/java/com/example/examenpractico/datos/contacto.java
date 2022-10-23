package com.example.examenpractico.datos;

public class contacto {
    private Integer id;
    private String nombres,numero,nota,url,pais;
    public contacto(){

    }
    public contacto(Integer id, String nombres, String numero, String nota, String url, String pais) {
        this.id = id;
        this.nombres = nombres;
        this.numero = numero;
        this.nota = nota;
        this.url=url;
        this.pais=pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNota() {
        return nota;
    }

    public void setApellidos(String nota) {
        this.nota = nota;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}