/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vitrohr.impl;

import java.util.ArrayList;
import java.util.List;
import vitrohr.gestor.GestorConexionJDBC;
import vitrohr.vo.Cancion;
import java.sql.ResultSet;
import java.sql.SQLException;
import vitrohr.vo.Artista;
import vitrohr.vo.Estilo;

/**
 *
 * @author Mauricio Santos Ruiz
 */
public class CancionJDBC implements Runnable {

    // Se pasa la clase para no instanciar mas de una vez y evitar problemas con FreeDB
    private GestorConexionJDBC gestorConexion;
    private List<Cancion> lista = new ArrayList<Cancion>();

    public CancionJDBC(GestorConexionJDBC gestorConexion) {
        this.gestorConexion = gestorConexion;
    }
    
    @Override
    public void run() {
        try {
            ResultSet resultado = this.gestorConexion.getConsultaCancion();
            while (resultado.next() && resultado != null) {
                String nombreEst = resultado.getString("nombreEst"),
                    nombreCan = resultado.getString("nombreCan"),
                    nombreArt = resultado.getString("nombreArt"),
                    urlStringCan = resultado.getString("urlCan"),
                    urlStringArt = resultado.getString("urlArt");
                int codigoCan = resultado.getInt("cod_cancion"),
                    codigoArt = resultado.getInt("cod_artista");
                Estilo estilo = new Estilo(nombreEst);
                Artista artista = new Artista(codigoArt, nombreArt, urlStringArt, estilo);
                Cancion cancion = new Cancion(codigoCan, nombreCan, urlStringCan, artista);
                
                System.out.println(cancion);
                this.lista.add(cancion);
            }
        } catch (SQLException ex) {
            System.getLogger(CancionJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public List<Cancion> getLista() {
        return lista;
    }
}
