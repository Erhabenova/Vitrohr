/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vitrohr.vo;

import javax.swing.ImageIcon;

/**
 *
 */
public class Artista {
    private ImageIcon imagen;
    private int codigo;
    private String nombre, url;
    private Estilo estilo;

    public Artista(int codigo, String nombre, String url, Estilo estilo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.url = url;
        this.estilo = estilo;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Artista " + this.nombre;
    }
}
