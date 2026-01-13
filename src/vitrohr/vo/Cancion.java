package vitrohr.vo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mauricio Santos Ruiz
 */
public class Cancion {
    private int codigo;
   private String nombre, url;
   private Artista artista;

    public Cancion(int codigo, String nombre, String url, Artista artista) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.url = url;
        this.artista = artista;
    }

    public Artista getArtista() {
        return artista;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getUrl() {
        return url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
