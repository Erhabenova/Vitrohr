/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vitrohr.impl;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import vitrohr.gestor.GestorConexionJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import vitrohr.vo.Estilo;

/**
 *
 * @author Mauricio Santos Ruiz
 */
public class EstiloJDBC implements Runnable {
    
    // Se pasa la clase para no instanciar mas de una vez y evitar problemas con FreeDB
    private GestorConexionJDBC gestorConexion;
    private List<Estilo> lista = new ArrayList<Estilo>();
    
    public EstiloJDBC(GestorConexionJDBC gestorConexion) {
        this.gestorConexion = gestorConexion;
    }

    @Override
    public void run() {
        try {
            ResultSet resultado = this.gestorConexion.getConsultaEstilo();
            while(resultado.next() && resultado != null){
                String nombre = resultado.getString("nombre"),
                    urlString = resultado.getString("url_img"),
                    descripcion = resultado.getString("descripcion");
                int codigo = resultado.getInt("cod_estilo");
                URL url = new URL(urlString);
                ImageIcon imagen = new ImageIcon(url);
                Estilo estilo = new Estilo(nombre, urlString, descripcion, codigo);
                Image originalImage = imagen.getImage();
                Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.setDescription("Genero " + nombre);
                estilo.setImagen(scaledIcon);
                System.out.println(estilo);
                this.lista.add(estilo);
            }
        } catch (SQLException | MalformedURLException ex) {
            System.getLogger(EstiloJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public List<Estilo> getLista() {
        return lista;
    }
}
