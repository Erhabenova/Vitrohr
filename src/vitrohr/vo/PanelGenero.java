package vitrohr.vo;

import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ramiromartin
 * @refactor Mauricio Santos Ruiz
 */
public class PanelGenero extends JPanel {

    public PanelGenero(Dimension tamanio) {
        initComponents();
        
        // Permitir HTML pero no deshabilitar completamente
        LDescripcion.putClientProperty("html.disable", false);
    }

    /*
    * Metodo que recibe una cadena de texto para asignarsela a el label dentro de un html
    */
    public void setDescripcion(String descripcion) {
        descripcion = "<html><div style='text-align: justify; width: 300px ; background-color: rgba(0, 0, 0, 0.5);"
                + "padding: 5px 15px solid rgba(0, 0, 0, 0); display: inline-block;'>"
                + descripcion + "</div></html>";
        this.LDescripcion.setText(descripcion);
        LDescripcion.revalidate();
        LDescripcion.repaint();
    }
    
    /*
    * Metodo que recibe una cadena de texto para descargarla con la clase URL y asignarla
    */
    public void setImagen(String url){
        try {
            ImageIcon imagen = new ImageIcon(new URL(url));

            SwingUtilities.invokeLater(() -> {
                Dimension panelSize = getSize();
                if (panelSize.width <= 0 || panelSize.height <= 0) {
                    panelSize = new Dimension(600, 600);
                }
                Image originalImage = imagen.getImage();
                int anchoPanel = panelSize.width / 2;
                int altoPanel = panelSize.height / 2;
                Image scaledImage = originalImage.getScaledInstance(
                        anchoPanel,
                        altoPanel,
                        Image.SCALE_SMOOTH
                );
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                this.LDescripcion.setIcon(scaledIcon);
                this.LDescripcion.revalidate();
                this.LDescripcion.repaint();
            });

        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
            });
        }
    }

    /**/
    public JLabel getPanel(){
        return this.LDescripcion;
    }

    /*
    * Conjunto de metodos que devuelven la instancia de los botones para su manipulacion
    */
    public JButton getBReproducirGenero() {
        return BReproducirGenero;
    }

    public JButton getBVerArtistasGenero() {
        return BVerArtistasGenero;
    }

    public JButton getBVolver() {
        return BVolver;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PContenedor = new javax.swing.JPanel();
        panelGeneroDescripcion2 = new javax.swing.JPanel();
        panelTexto = new javax.swing.JPanel();
        LDescripcion = new javax.swing.JLabel();
        PBotonesGenero = new javax.swing.JPanel();
        BVerArtistasGenero = new javax.swing.JButton();
        BReproducirGenero = new javax.swing.JButton();
        BVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(221, 245, 128));
        setLayout(new java.awt.BorderLayout());

        PContenedor.setBackground(new java.awt.Color(51, 51, 51));
        PContenedor.setForeground(new java.awt.Color(51, 51, 51));
        PContenedor.setMaximumSize(new java.awt.Dimension(1200, 2147483647));
        PContenedor.setOpaque(false);
        PContenedor.setPreferredSize(new java.awt.Dimension(534, 348));
        PContenedor.setLayout(new java.awt.BorderLayout());

        panelGeneroDescripcion2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, -10, 1, 1));
        panelGeneroDescripcion2.setLayout(new java.awt.BorderLayout());

        panelTexto.setLayout(new java.awt.BorderLayout());

        LDescripcion.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        LDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        LDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LDescripcion.setText("jLabel1");
        LDescripcion.setAutoscrolls(true);
        LDescripcion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LDescripcion.setMaximumSize(new java.awt.Dimension(500, 200));
        LDescripcion.setPreferredSize(new java.awt.Dimension(300, 200));
        panelTexto.add(LDescripcion, java.awt.BorderLayout.CENTER);

        panelGeneroDescripcion2.add(panelTexto, java.awt.BorderLayout.CENTER);

        PContenedor.add(panelGeneroDescripcion2, java.awt.BorderLayout.CENTER);

        add(PContenedor, java.awt.BorderLayout.CENTER);

        PBotonesGenero.setMinimumSize(new java.awt.Dimension(456, 40));
        PBotonesGenero.setLayout(new java.awt.GridLayout(1, 3));

        BVerArtistasGenero.setText("Artistas de este genero");
        PBotonesGenero.add(BVerArtistasGenero);

        BReproducirGenero.setText("Reproducir este genero");
        PBotonesGenero.add(BReproducirGenero);

        BVolver.setText("Regresar");
        PBotonesGenero.add(BVolver);

        add(PBotonesGenero, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BReproducirGenero;
    private javax.swing.JButton BVerArtistasGenero;
    private javax.swing.JButton BVolver;
    private javax.swing.JLabel LDescripcion;
    private javax.swing.JPanel PBotonesGenero;
    private javax.swing.JPanel PContenedor;
    private javax.swing.JPanel panelGeneroDescripcion2;
    private javax.swing.JPanel panelTexto;
    // End of variables declaration//GEN-END:variables
}
