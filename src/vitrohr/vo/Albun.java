/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vitrohr.vo;

/**
 *CREATE TABLE IF NOT EXISTS T_ALBUN(
		cod_albun INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
		nombre VARCHAR(100) NOT NULL,
		url_img text,
		cod_artista INT NOT NULL,
    	cod_estilo INT NOT NULL,
    	CONSTRAINT FK_ALBUN_ESTILO FOREIGN KEY (cod_estilo) REFERENCES T_ESTILO(cod_estilo),
		CONSTRAINT FK_ALBUN_ARTISTA FOREIGN KEY (cod_artista) REFERENCES T_ARTISTA(cod_artista)
);
 */
public class Albun {
    private int codigo;
    private String nombre, url;
    private Artista artista;

    public Albun(int codigo, String nombre, String url, Artista artista) {
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

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
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
    
}
