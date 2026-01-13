package vitrohr.vo;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 */
public class Estilo {
    private ImageIcon imagen;
    private int codigo;
    private String nombre, url, descripcion;

    public Estilo(String nombre, String url, String descripcion, int codigo) {
        this.nombre = nombre;
        this.url = url;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public Estilo(String nombre) {
        this.nombre = nombre;
    }
    
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Estilo " + this.nombre;
    }
    
}
