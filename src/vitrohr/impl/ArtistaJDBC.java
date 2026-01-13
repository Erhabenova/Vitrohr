/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vitrohr.impl;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import vitrohr.gestor.GestorConexionJDBC;
import vitrohr.vo.Artista;
import vitrohr.vo.Estilo;

/**
 *
 * @author Mauricio Santos Ruiz
 */
public class ArtistaJDBC implements Runnable {
    
    // Se pasa la clase para no instanciar mas de una vez y evitar problemas con FreeDB
    private GestorConexionJDBC gestorConexion;
    private List<Artista> lista = new ArrayList<Artista>();

    public ArtistaJDBC(GestorConexionJDBC gestorConexion) {
        this.gestorConexion = gestorConexion;
    }

    @Override
    public void run() {
        try {
            ResultSet resultado = this.gestorConexion.getConsultaArtista();
            while(resultado.next() && resultado != null){
                String nombreEst = resultado.getString("nombreEst"),
                    urlStringEst = resultado.getString("urlEst"),
                    descripcion = resultado.getString("descripcion"),
                    nombreArt = resultado.getString("nombreArt"),
                    urlStringArt = resultado.getString("urlArt");
                int codigoEst = resultado.getInt("cod_estilo"),
                    codigoArt = resultado.getInt("cod_artista");
                Estilo estilo = new Estilo(nombreEst, urlStringEst, descripcion, codigoEst);
                Artista artista = new Artista(codigoArt, nombreArt, nombreArt, estilo);
                
                URL url = new URL(urlStringArt);
                ImageIcon imagen = new ImageIcon(url);
                Image originalImage = imagen.getImage();
                Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.setDescription("Artista " + nombreArt);
                artista.setImagen(scaledIcon);
                System.out.println(artista);
                this.lista.add(artista);
            }
        } catch (SQLException | MalformedURLException ex) {
            System.getLogger(ArtistaJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public List<Artista> getLista(){
        return this.lista;
    }
    
}
