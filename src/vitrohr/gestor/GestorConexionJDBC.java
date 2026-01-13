package vitrohr.gestor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author DELL PRECISION 3551
 */
public class GestorConexionJDBC {
    private Connection con;
    
    /*
    * Se inicia la variable de conexion dentro del constructor para que pasarle esta misma a las clases JDBC
    * esto se debe a que hacer mas de una conexion en la base de datos de Freedb no es posible ademas
    * de bloquear el acceso de la aplicacion durante un tiempo considerable, se adjuntara el SQL de la
    * base de datos en caso de pruebas mas rigurosas
    */
    public GestorConexionJDBC() {
        try {
            // Variables a modificar en caso de que FreeDB rechace la conexion despues de una conexion
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_Prueba_dam",
                    usuario = "freedb_freedb_okyom_prueba",
                    password = "xv3M?hE*4mF7Et8";

            con = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            System.getLogger(GestorConexionJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /*
    * Devuelve la variable de tipo Connection ya iniciada
    */
    public Connection getConexionJDBC() {
            return con;
    }

    /*
    * Se devuelve el ResultSet con la consulta de los estilos
    */
    public ResultSet getConsultaEstilo() {
        ResultSet resultado = null;
            try {
                String consulta = "SELECT * FROM T_ESTILO";

                Statement sentencia = this.getConexionJDBC().createStatement();
                resultado = sentencia.executeQuery(consulta);
            } catch (SQLException ex) {
        System.getLogger(GestorConexionJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
            return resultado;
    }

    /*
    * Se devuelve el ResultSet con la consulta de los artistas mas los datos de los estilos
    */
    public ResultSet getConsultaArtista() {
        ResultSet resultado = null;
            try {
                String consulta = """
                                  SELECT ta.cod_artista , ta.nombre as nombreArt, ta.url_img as urlArt, te.cod_estilo, te.descripcion, te.nombre as nombreEst, te.url_img as urlEst
                                  FROM T_ARTISTA ta join T_ESTILO te
                                  on ta.cod_estilo = te.cod_estilo;""";

                Statement sentencia = this.getConexionJDBC().createStatement();
                resultado = sentencia.executeQuery(consulta);
            } catch (SQLException ex) {
        System.getLogger(GestorConexionJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
            return resultado;
    }

    /*
    * Se devuelve el ResultSet con la consulta de las canciones mas los datos de los artistas
    */
    public ResultSet getConsultaCancion() {
    ResultSet resultado = null;
    try {
        String consulta = """
                                  SELECT ta.cod_artista , ta.nombre as nombreArt, ta.url_img as urlArt, tc.cod_cancion, tc.nombre as nombreCan, tc.url_img as urlCan, te.nombre as nombreEst
                                  FROM T_CANCION tc JOIN T_ARTISTA ta JOIN T_ESTILO te
                                  on tc.cod_artista = ta.cod_artista AND ta.cod_estilo = te.cod_estilo;""";

        Statement sentencia = this.getConexionJDBC().createStatement();
        resultado = sentencia.executeQuery(consulta);
    } catch (SQLException ex) {
        System.getLogger(GestorConexionJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
    return resultado;
    }
}
