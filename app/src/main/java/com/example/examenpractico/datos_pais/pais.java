package com.example.examenpractico.datos_pais;

public class pais {
    private Integer id;
    private String pais,codigo;
    public pais(){

    }
    public pais(Integer id, String pais, String codigo) {
        this.id = id;
        this.pais = pais;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String numero) {
        this.codigo = codigo;
    }
}
