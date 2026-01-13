package vitrohr;

import jaco.mp3.player.MP3Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import vitrohr.gestor.GestorConexionJDBC;
import vitrohr.impl.ArtistaJDBC;
import vitrohr.impl.CancionJDBC;
import vitrohr.impl.EstiloJDBC;
import vitrohr.vo.Artista;
import vitrohr.vo.Cancion;
import vitrohr.vo.Estilo;
import vitrohr.vo.PanelGenero;

/**
 *
 * @author DELL PRECISION 3551
 */
public class Ventana extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Ventana.class.getName());
    private GestorConexionJDBC conexion = new GestorConexionJDBC();
    
    // Clases de donde obtendremos informacion de la base de datos
    private EstiloJDBC estilos = new EstiloJDBC(conexion);
    private ArtistaJDBC artistas = new ArtistaJDBC(conexion);
    private CancionJDBC canciones = new CancionJDBC(conexion);
    
    // Para mejorar el rendimiento del programa usaremos las clases anteriores dentro de hilos
    private Thread hiloEstilo = new Thread(estilos);
    private Thread hiloArtista = new Thread(artistas);
    private Thread hiloCancion = new Thread(canciones);
    
    // Se crea el MP3Player junto a un boolean encargado de si hay una cancion reproduciendose
    private MP3Player _player = new MP3Player();
    private boolean _isPlaying = true;
    
    // Clases List donde guardaremos toda la informacion de la base de datos
    private List<Estilo> listaEstilos = new ArrayList<Estilo>();
    private List<Artista> listaArtistas = new ArrayList<Artista>();
    private List<Cancion> listaCanciones = new ArrayList<Cancion>();
    private List<Cancion> listaCancionesAux = new ArrayList<Cancion>();
    
    // Lista de jMenuItem donde se guardaran los estilos, artistas y las funciones del MP3Player
    private List<JMenuItem> listaItemsMenuEstilos = new ArrayList<>();
    private List<JMenuItem> listaItemsMenuArtistas = new ArrayList<>();
    private List<JMenuItem> listaItemsMenuReproductor = new ArrayList<>();
    
    // Modelo usado para la lista de canciones
    private DefaultListModel _modeloLista = new DefaultListModel<>();
    
    // Ancho de la ventana con el cual se reasignara el tamaño de los paneles
    private int anchoVentana = this.getWidth();
    
    // Iconos de los controles del reproductor
    private ImageIcon scaledIconBotonAtras;
    private ImageIcon scaledIconBotonAdelante;
    private ImageIcon scaledIconBotonPausa;
    private ImageIcon scaledIconBotonPlay;
    private ImageIcon scaledIconBotonBucle;
    
    // Barras de scroll para los paneles de los generos y artistas
    private JScrollPane scrollPaneEstilos;
    private JScrollPane scrollPaneArtistas;
    
    // Clase personalizada que se usa para generar la informacion del genero selecionado
    private PanelGenero panelGenero;
    
    public Ventana() {
        initComponents();
        this.setTitle("Vitrohr");
        
        // Variable que sera el logo de la ventana
        ImageIcon logo;
        
        // Calculos para saber el tamaño maximo de la pantalla, se usa -40 para no cubrir la barra de tareas
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int altoVentanaTemp = screenSize.height - 40;
        int anchoVentanaTemp = screenSize.width;
        screenSize.setSize(anchoVentanaTemp, altoVentanaTemp);
        this.setSize(anchoVentanaTemp, altoVentanaTemp);
        this.setMaximumSize(screenSize);
        this.setExtendedState(altoVentanaTemp);
        
        // Se inician los hilos de la base de datos
        this.hiloEstilo.start();
        this.hiloArtista.start();
        this.hiloCancion.start();
        try {
            logo = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=1LEopOs9SH6psth_KfDDVXn2-3to6RSrf"));
            Image originalImage = logo.getImage();
            
            // Se escala la imagen para el boton
            Image scaledImage = originalImage.getScaledInstance(65, 65, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            this.setIconImage(logo.getImage());
            this.LImagenCancion.setIcon(scaledIcon);
            
            ImageIcon boton1Img = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=1JuttdvTtplvp_y4-GJ7UWhgr7gxbqmQp"));
            Image originalImageBoton1 = boton1Img.getImage();
            Image scaledImageBoton1 = originalImageBoton1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            scaledIconBotonAtras = new ImageIcon(scaledImageBoton1);
            
            ImageIcon boton2Img = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=160pGH99gUphUkTOL9QbnpPgwRbKMkTZo"));
            Image originalImageBoton2 = boton2Img.getImage();
            Image scaledImageBoton2 = originalImageBoton2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            scaledIconBotonAdelante = new ImageIcon(scaledImageBoton2);
            
            ImageIcon boton3Img = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=1iQepNbqIsPTfFUdEyxUpj8ugL33PJt1Y"));
            Image originalImageBoton3 = boton3Img.getImage();
            Image scaledImageBoton3 = originalImageBoton3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            scaledIconBotonPlay = new ImageIcon(scaledImageBoton3);
            
            ImageIcon boton4Img = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=1d6gDsGNEd7LLmd4xk8Yc71sa27HqDa_i"));
            Image originalImageBoton4 = boton4Img.getImage();
            Image scaledImageBoton4 = originalImageBoton4.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            scaledIconBotonPausa = new ImageIcon(scaledImageBoton4);
            
            ImageIcon boton5Img = new ImageIcon(new URL("https://drive.google.com/uc?export=download&id=1VA4hDnLomB1a7PbjRz8FH3GfRLZ-ARwD"));
            Image originalImageBoton5 = boton5Img.getImage();
            Image scaledImageBoton5 = originalImageBoton5.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            scaledIconBotonBucle = new ImageIcon(scaledImageBoton5);
            
            // Se asignan las imagenes escaladas a los botones
            this.BBotonAtras.setIcon(scaledIconBotonAtras);
            this.BBotonPlayPause.setIcon(scaledIconBotonPlay);
            this.BBotonSiguiente.setIcon(scaledIconBotonAdelante);
            this.BBotonRepetir.setIcon(scaledIconBotonBucle);
            
            // Se usa el metodo join para evitar que la ventana cargue sin la informacion de la base de datos
            this.hiloEstilo.join();
            this.hiloArtista.join();
            this.hiloCancion.join();
            
        } catch (MalformedURLException | InterruptedException ex) {
            System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        // Se llama a tres metodos que usaran la informacion para generar todo dinamicamente
        this.ListarEstilosJDBC();
        this.ListarArtistasJDBC();
        this.ListarCancionesJDBC();
        
        // Se asignan las barras de scroll a los paneles
        this.scrollPaneEstilos = new JScrollPane(PEstilos);
        this.scrollPaneEstilos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPaneEstilos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        this.scrollPaneArtistas = new JScrollPane(PArtistas);
        this.scrollPaneArtistas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPaneArtistas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JScrollBar horizontalBarEstilo = scrollPaneEstilos.getHorizontalScrollBar();
        horizontalBarEstilo.setUnitIncrement(200);
        horizontalBarEstilo.setBlockIncrement(5500);
        
        JScrollBar horizontalBarArtista = scrollPaneArtistas.getHorizontalScrollBar();
        horizontalBarArtista.setUnitIncrement(200);
        horizontalBarArtista.setBlockIncrement(5500);

        PContenedorEstilos.removeAll();
        PContenedorEstilos.add(scrollPaneEstilos, BorderLayout.CENTER);
        PContenedorEstilos.revalidate();
        
        PContenedorArtistas.removeAll();
        PContenedorArtistas.add(scrollPaneArtistas, BorderLayout.CENTER);
        PContenedorArtistas.revalidate();
        
        // Se instancia la clase personalizada del panelGenero
        this.panelGenero = new PanelGenero(this.PCentral.getSize());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PInicio = new javax.swing.JPanel();
        PBotones = new javax.swing.JPanel();
        PBotonImagenCancion = new javax.swing.JPanel();
        LImagenCancion = new javax.swing.JLabel();
        PBotonAtras = new javax.swing.JPanel();
        BBotonAtras = new javax.swing.JButton();
        PBotonPlay = new javax.swing.JPanel();
        BBotonPlayPause = new javax.swing.JButton();
        PBotonSiguiente = new javax.swing.JPanel();
        BBotonSiguiente = new javax.swing.JButton();
        PBotonBucle = new javax.swing.JPanel();
        BBotonRepetir = new javax.swing.JButton();
        PLateral = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        SPCanciones = new javax.swing.JScrollPane();
        LCanciones = new javax.swing.JList<>();
        PCentral = new javax.swing.JPanel();
        PCentralGeneroArtistas = new javax.swing.JPanel();
        PContenedorEstilos = new javax.swing.JPanel();
        PEstilos = new javax.swing.JPanel();
        PContenedorArtistas = new javax.swing.JPanel();
        PArtistas = new javax.swing.JPanel();
        BarraMenu = new javax.swing.JMenuBar();
        MEstilos = new javax.swing.JMenu();
        MArtistas = new javax.swing.JMenu();
        MReproductor = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 800));
        setPreferredSize(new java.awt.Dimension(1300, 616));

        PInicio.setPreferredSize(new java.awt.Dimension(160, 320));
        PInicio.setLayout(new java.awt.BorderLayout());

        PBotones.setAutoscrolls(true);
        PBotones.setFocusCycleRoot(true);
        PBotones.setFocusTraversalPolicyProvider(true);
        PBotones.setMaximumSize(new java.awt.Dimension(32767, 150));
        PBotones.setMinimumSize(new java.awt.Dimension(160, 75));
        PBotones.setPreferredSize(new java.awt.Dimension(160, 75));
        PBotones.setLayout(new java.awt.GridLayout(1, 0, 0, 15));

        PBotonImagenCancion.setBackground(new java.awt.Color(0, 0, 0));
        PBotonImagenCancion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        PBotonImagenCancion.setLayout(new java.awt.BorderLayout());

        LImagenCancion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LImagenCancion.setToolTipText("Imagen de la cancion actual");
        PBotonImagenCancion.add(LImagenCancion, java.awt.BorderLayout.CENTER);
        LImagenCancion.getAccessibleContext().setAccessibleDescription("");

        PBotones.add(PBotonImagenCancion);

        PBotonAtras.setBackground(new java.awt.Color(0, 0, 0));
        PBotonAtras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        PBotonAtras.setLayout(new java.awt.BorderLayout());

        BBotonAtras.setBackground(new java.awt.Color(0, 0, 0));
        BBotonAtras.setToolTipText("Retroceder cancion");
        BBotonAtras.setAlignmentY(0.0F);
        BBotonAtras.setBorderPainted(false);
        BBotonAtras.setContentAreaFilled(false);
        BBotonAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BBotonAtras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BBotonAtras.setIconTextGap(0);
        BBotonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBotonAtrasActionPerformed(evt);
            }
        });
        PBotonAtras.add(BBotonAtras, java.awt.BorderLayout.CENTER);

        PBotones.add(PBotonAtras);

        PBotonPlay.setBackground(new java.awt.Color(0, 0, 0));
        PBotonPlay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        PBotonPlay.setLayout(new java.awt.BorderLayout());

        BBotonPlayPause.setBackground(new java.awt.Color(0, 0, 0));
        BBotonPlayPause.setToolTipText("Reproducir cancion");
        BBotonPlayPause.setAlignmentY(0.0F);
        BBotonPlayPause.setBorderPainted(false);
        BBotonPlayPause.setContentAreaFilled(false);
        BBotonPlayPause.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BBotonPlayPause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BBotonPlayPause.setIconTextGap(0);
        BBotonPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBotonPlayPauseActionPerformed(evt);
            }
        });
        PBotonPlay.add(BBotonPlayPause, java.awt.BorderLayout.CENTER);

        PBotones.add(PBotonPlay);

        PBotonSiguiente.setBackground(new java.awt.Color(0, 0, 0));
        PBotonSiguiente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        PBotonSiguiente.setLayout(new java.awt.BorderLayout());

        BBotonSiguiente.setBackground(new java.awt.Color(0, 0, 0));
        BBotonSiguiente.setToolTipText("Siguiente cancion");
        BBotonSiguiente.setAlignmentY(0.0F);
        BBotonSiguiente.setBorderPainted(false);
        BBotonSiguiente.setContentAreaFilled(false);
        BBotonSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BBotonSiguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BBotonSiguiente.setIconTextGap(0);
        BBotonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBotonSiguienteActionPerformed(evt);
            }
        });
        PBotonSiguiente.add(BBotonSiguiente, java.awt.BorderLayout.CENTER);

        PBotones.add(PBotonSiguiente);

        PBotonBucle.setBackground(new java.awt.Color(0, 0, 0));
        PBotonBucle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        PBotonBucle.setLayout(new java.awt.BorderLayout());

        BBotonRepetir.setBackground(new java.awt.Color(0, 0, 0));
        BBotonRepetir.setToolTipText("Reproducir en bucle");
        BBotonRepetir.setAlignmentY(0.0F);
        BBotonRepetir.setBorderPainted(false);
        BBotonRepetir.setContentAreaFilled(false);
        BBotonRepetir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BBotonRepetir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BBotonRepetir.setIconTextGap(0);
        BBotonRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBotonRepetirActionPerformed(evt);
            }
        });
        PBotonBucle.add(BBotonRepetir, java.awt.BorderLayout.CENTER);

        PBotones.add(PBotonBucle);

        PInicio.add(PBotones, java.awt.BorderLayout.PAGE_END);

        PLateral.setBackground(new java.awt.Color(204, 255, 51));
        PLateral.setMinimumSize(new java.awt.Dimension(200, 100));
        PLateral.setPreferredSize(new java.awt.Dimension(190, 584));
        PLateral.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                Resized(evt);
            }
        });
        PLateral.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(164, 249, 8));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setLayout(new java.awt.CardLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Canciones");
        jLabel1.setAlignmentY(0.0F);
        jLabel1.setAutoscrolls(true);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel1, "card2");

        PLateral.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.CardLayout());

        SPCanciones.setForeground(new java.awt.Color(0, 0, 0));
        SPCanciones.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        LCanciones.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        LCanciones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Mensaje para ramiro", "he dejado adjuntado la", "base de datos porque", "la conexion de FreeDB", "tiende a fallar despues", "de una prueba de conexion.", " ", "Se puede modificar", "la clase ", "GestorConexionJDBC", "para usar MySQL", "en local de ser necesario", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        LCanciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LCanciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LCancionesMouseClicked(evt);
            }
        });
        SPCanciones.setViewportView(LCanciones);

        jPanel2.add(SPCanciones, "card2");

        PLateral.add(jPanel2, java.awt.BorderLayout.CENTER);

        PInicio.add(PLateral, java.awt.BorderLayout.LINE_START);

        PCentral.setBackground(new java.awt.Color(221, 245, 128));
        PCentral.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 255, 0), new java.awt.Color(51, 255, 0), new java.awt.Color(0, 204, 0), new java.awt.Color(0, 204, 51)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        PCentral.setAutoscrolls(true);
        PCentral.setFocusCycleRoot(true);
        PCentral.setFocusTraversalPolicyProvider(true);
        PCentral.setPreferredSize(new java.awt.Dimension(194, 584));
        PCentral.setLayout(new javax.swing.BoxLayout(PCentral, javax.swing.BoxLayout.LINE_AXIS));

        PCentralGeneroArtistas.setBackground(new java.awt.Color(255, 255, 51));
        PCentralGeneroArtistas.setLayout(new java.awt.GridLayout(2, 1));

        PContenedorEstilos.setBackground(new java.awt.Color(164, 249, 8));
        PContenedorEstilos.setPreferredSize(new java.awt.Dimension(334, 348));
        PContenedorEstilos.setLayout(new java.awt.BorderLayout());

        PEstilos.setMinimumSize(new java.awt.Dimension(250, 250));
        PEstilos.setPreferredSize(new java.awt.Dimension(1338, 300));
        PEstilos.setLayout(new javax.swing.BoxLayout(PEstilos, javax.swing.BoxLayout.X_AXIS));
        PContenedorEstilos.add(PEstilos, java.awt.BorderLayout.NORTH);

        PCentralGeneroArtistas.add(PContenedorEstilos);

        PContenedorArtistas.setBackground(new java.awt.Color(164, 249, 8));
        PContenedorArtistas.setMinimumSize(new java.awt.Dimension(250, 250));
        PContenedorArtistas.setPreferredSize(new java.awt.Dimension(334, 348));
        PContenedorArtistas.setLayout(new java.awt.BorderLayout());

        PArtistas.setAlignmentY(0.0F);
        PArtistas.setMinimumSize(new java.awt.Dimension(250, 250));
        PArtistas.setPreferredSize(new java.awt.Dimension(1338, 300));
        PArtistas.setLayout(new javax.swing.BoxLayout(PArtistas, javax.swing.BoxLayout.X_AXIS));
        PContenedorArtistas.add(PArtistas, java.awt.BorderLayout.NORTH);

        PCentralGeneroArtistas.add(PContenedorArtistas);

        PCentral.add(PCentralGeneroArtistas);

        PInicio.add(PCentral, java.awt.BorderLayout.CENTER);

        MEstilos.setText("Estilos");
        BarraMenu.add(MEstilos);

        MArtistas.setText("Artistas");
        BarraMenu.add(MArtistas);

        MReproductor.setText("Artistas");
        BarraMenu.add(MReproductor);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
    * Metodo que toma la informacion de la lista de estilos para generar dinamicamente 
    * la visualizacion de los estilos en la ventana
    */
    private void ListarEstilosJDBC() {
        this.listaEstilos = this.estilos.getLista();

        int panelWidth = 250;
        int panelHeight = 200;
        int espacio = 10;
        
        for (Estilo estiloElem : this.listaEstilos) {
            this.CreaItemMenuEstilo(estiloElem);
            Color colorPanel = new Color(204, 255, 51);
            Color colorPanelAlter = new Color(15, 214, 98);
            
            Border colorPanelBorde =  BorderFactory.createLineBorder(new Color(0, 0, 0), 2);
            Border espacioPanelBorde = BorderFactory.createEmptyBorder(5, 5, 5, 5);

            CompoundBorder bordePanel = BorderFactory.createCompoundBorder(colorPanelBorde, espacioPanelBorde);
            
            JPanel estiloPanel = new JPanel(new BorderLayout());
            estiloPanel.setBackground(colorPanel);
            estiloPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            estiloPanel.setMaximumSize(new Dimension(panelWidth, panelHeight));

            estiloPanel.setBorder(bordePanel);
            
            this.crearPanelEstilo(estiloPanel, estiloElem, colorPanel, colorPanelAlter, bordePanel);
            
            JLabel nombreLabel = new JLabel(estiloElem.getNombre(), SwingConstants.CENTER);
            nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            estiloPanel.add(nombreLabel, BorderLayout.NORTH);

            if (estiloElem.getImagen() != null) {
                JLabel imagenLabel = new JLabel(estiloElem.getImagen());
                imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
                estiloPanel.add(imagenLabel, BorderLayout.CENTER);
            }

            JPanel contenedor = new JPanel();
            contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, espacio, espacio));
            contenedor.setBackground(new Color(204, 255, 51));
            contenedor.add(estiloPanel);
            
            Cursor cursorMano = new Cursor(java.awt.Cursor.HAND_CURSOR);
            
            estiloPanel.setCursor(cursorMano);
            nombreLabel.setCursor(cursorMano);

            PEstilos.add(contenedor);
        }

        PEstilos.add(Box.createVerticalGlue());

        PEstilos.revalidate();
        PEstilos.repaint();
    }
    
    /*
    * Funcion que crea un elemento dentro del menu que se refiere
    */
    private void CreaItemMenuEstilo(Estilo estiloElem) {
        JMenuItem estiloElemMenu = new JMenuItem();
        estiloElemMenu.setText(estiloElem.getNombre());
        estiloElemMenu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        _modeloLista.clear();
                        listaCancionesAux.clear();
                        _player.pause();
                        _player = new MP3Player();
                        for(Cancion cancion: listaCanciones){
                            if(cancion.getArtista().getEstilo().getNombre().equals(estiloElem.getNombre())){
                                try {
                                    listaCancionesAux.add(cancion);
                                    _modeloLista.addElement(cancion);
                                    _player.addToPlayList(new URL(cancion.getUrl()));
                                } catch (MalformedURLException ex) {
                                    System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                                }
                            }
                        }
                        BBotonPlayPause.setIcon(scaledIconBotonPausa);
                        _player.play();
                        _isPlaying = false;
                }
        });
        this.MEstilos.add(estiloElemMenu);
    }
    
    /*
    * Creacion del Panel que surge al darle a un estilo
    * se modifica o elimina eventos dentro de este mismo para cambiar la lista de reproduccion,
    * tambien se usan eventos para volver a el panel anterior
    * el alumno casi se vuelve loco realizando esta parte
    */
    private void crearPanelEstilo(JPanel estiloPanel, Estilo estiloElem, Color colorPanel, Color colorPanelAlter, CompoundBorder bordePanel) {
        estiloPanel.addMouseListener(new MouseAdapter() {
            /*
            * Cambia el panel de PEstilos a un panel con informacion del genero
            */
            @Override
            public void mouseClicked(MouseEvent evt) {
                PCentralGeneroArtistas.setLayout(new java.awt.GridLayout(1, 1));
                PCentralGeneroArtistas.remove(PContenedorEstilos);
                PContenedorEstilos.setVisible(false);
                PCentralGeneroArtistas.remove(PContenedorArtistas);
                PContenedorArtistas.setVisible(false);
                panelGenero.setImagen(estiloElem.getUrl());
                PCentralGeneroArtistas.add(panelGenero);
                PCentralGeneroArtistas.revalidate();
                PCentralGeneroArtistas.repaint();
                panelGenero.setDescripcion(estiloElem.getDescripcion());
                panelGenero.setImagen(estiloElem.getUrl());
                
                JButton botonGenero = panelGenero.getBReproducirGenero();
                for (ActionListener al : botonGenero.getActionListeners()) {
                    botonGenero.removeActionListener(al);
                }
                botonGenero.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        _modeloLista.clear();
                        listaCancionesAux.clear();
                        _player.pause();
                        _player = new MP3Player();
                        for(Cancion cancion: listaCanciones){
                            if(cancion.getArtista().getEstilo().getNombre().equals(estiloElem.getNombre())){
                                try {
                                    listaCancionesAux.add(cancion);
                                    _modeloLista.addElement(cancion);
                                    _player.addToPlayList(new URL(cancion.getUrl()));
                                } catch (MalformedURLException ex) {
                                    System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                                }
                            }
                        }
                        BBotonPlayPause.setIcon(scaledIconBotonPausa);
                        _player.play();
                        _isPlaying = false;
                    }
                });
                
                JButton botonArtistas = panelGenero.getBVerArtistasGenero();
                for (ActionListener al : botonArtistas.getActionListeners()) {
                    botonArtistas.removeActionListener(al);
                }
                botonArtistas.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < PArtistas.getComponentCount() - 1; i++){
                            if(listaArtistas.get(i).getEstilo().getCodigo() != estiloElem.getCodigo()){
                                PArtistas.getComponent(i).setVisible(false);
                            } else {
                                PArtistas.getComponent(i).setVisible(true);
                            }
                        }
                        PCentralGeneroArtistas.setLayout(new java.awt.GridLayout(2, 1));
                        PCentralGeneroArtistas.remove(panelGenero);
                        PCentralGeneroArtistas.add(PContenedorEstilos);
                        PContenedorEstilos.setVisible(true);
                        PCentralGeneroArtistas.add(PContenedorArtistas);
                        PContenedorArtistas.setVisible(true);
                        PCentralGeneroArtistas.revalidate();
                        PCentralGeneroArtistas.repaint();
                    }
                });
                
                JButton botonVolver = panelGenero.getBVolver();
                for (ActionListener al : botonVolver.getActionListeners()) {
                    botonVolver.removeActionListener(al);
                }
                botonVolver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PCentralGeneroArtistas.setLayout(new java.awt.GridLayout(2, 1));
                        PCentralGeneroArtistas.remove(panelGenero);
                        PCentralGeneroArtistas.add(PContenedorEstilos);
                        PContenedorEstilos.setVisible(true);
                        PCentralGeneroArtistas.add(PContenedorArtistas);
                        PContenedorArtistas.setVisible(true);
                        PCentralGeneroArtistas.revalidate();
                        PCentralGeneroArtistas.repaint();
                    }
                });
            }

            /*
            * Cambia el color original del panel
            */
            @Override
            public void mouseEntered(MouseEvent evt) {
                estiloPanel.setBackground(colorPanelAlter);
                estiloPanel.setBorder(bordePanel);
            }

            /*
            * Restaurar color original del panel
            */
            @Override
            public void mouseExited(MouseEvent evt) {
                estiloPanel.setBackground(colorPanel);
                estiloPanel.setBorder(bordePanel);
            }
        });
    }
    
    /*
    * Metodo que toma la informacion de la lista de artistas para generar dinamicamente 
    * la visualizacion de los estilos en la ventana
    */
    private void ListarArtistasJDBC() {
        this.listaArtistas = this.artistas.getLista();

        int panelWidth = 250;
        int panelHeight = 200;
        int espacio = 10;
        
        for (Artista artistaElem : this.listaArtistas) {
            Color colorPanel = new Color(204, 255, 51);
            Color colorPanelAlter = new Color(15, 214, 98);
            
            Border colorPanelBorde =  BorderFactory.createLineBorder(new Color(0, 0, 0), 2);
            Border espacioPanelBorde = BorderFactory.createEmptyBorder(5, 5, 5, 5);

            CompoundBorder bordePanel = BorderFactory.createCompoundBorder(colorPanelBorde, espacioPanelBorde);
            
            JPanel artistaPanel = new JPanel(new BorderLayout());
            artistaPanel.setBackground(colorPanel);
            artistaPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            artistaPanel.setMaximumSize(new Dimension(panelWidth, panelHeight));

            artistaPanel.setBorder(bordePanel);
            
            artistaPanel.addMouseListener(new MouseAdapter() {
                /*
                * Sobre escribe el evento de dar click para que reproduzca las canciones de el artista
                */
                @Override
                public void mouseClicked(MouseEvent evt) {
                    _player.pause();
                    _player = new MP3Player();
                    _modeloLista.clear();
                    listaCancionesAux.clear();
                    for (int i = 0; i < listaCanciones.size(); i++) {
                        int codigoArt = listaCanciones.get(i).getArtista().getCodigo();
                        if (codigoArt == artistaElem.getCodigo()) {
                            try {
                                listaCancionesAux.add(listaCanciones.get(i));
                                _modeloLista.addElement(listaCanciones.get(i));
                                _player.addToPlayList(new URL(listaCanciones.get(i).getUrl()));
                            } catch (MalformedURLException ex) {
                                System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                            }
                        }
                    }
                    BBotonPlayPause.setIcon(scaledIconBotonPausa);
                    _player.play();
                    _isPlaying = false;
                }

                /*
                * Cambia el color original del panel
                */
                @Override
                public void mouseEntered(MouseEvent evt) {
                    artistaPanel.setBackground(colorPanelAlter);
                    artistaPanel.setBorder(bordePanel);
                }

                /*
                * Restaurar color original del panel
                */
                @Override
                public void mouseExited(MouseEvent evt) {
                    
                    artistaPanel.setBackground(colorPanel);
                    artistaPanel.setBorder(bordePanel);
                }
            });

            JLabel nombreLabel = new JLabel(artistaElem.getNombre(), SwingConstants.CENTER);
            nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
            artistaPanel.add(nombreLabel, BorderLayout.NORTH);

            if (artistaElem.getImagen() != null) {
                JLabel imagenLabel = new JLabel(artistaElem.getImagen());
                imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
                artistaPanel.add(imagenLabel, BorderLayout.CENTER);
            }

            JPanel contenedor = new JPanel();
            contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, espacio, espacio));
            contenedor.setBackground(new Color(204, 255, 51));
            contenedor.add(artistaPanel);
            
            /*
            * Espacio *2 por el margen izquierdo y derecho del FlowLayout en cada contenedor
            * Establecer el tamaño preferido de PArtistas, el alumno tardo 3 dias en hacer solo este 
            * ajuste de tamaño para los artistas, se ve mal cuando los artistas se reducen
            */
            int totalWidth = (panelWidth + espacio * 2) * this.listaArtistas.size();
            PArtistas.setPreferredSize(new Dimension(totalWidth, panelHeight));
            
            Cursor cursorMano = new Cursor(java.awt.Cursor.HAND_CURSOR);
            
            artistaPanel.setCursor(cursorMano);
            nombreLabel.setCursor(cursorMano);

            this.PArtistas.add(contenedor);
        }
        PArtistas.add(Box.createVerticalGlue());

        PArtistas.revalidate();
        PArtistas.repaint();
    }
    
    private void ListarCancionesJDBC() {
        this.listaCanciones = this.canciones.getLista();
        
        for (Cancion artistaElem : this.listaCanciones) {
            try {
                //this._modeloLista.addElement(artistaElem);
                URL urlCancion = new URL(artistaElem.getUrl());
                this._player.addToPlayList(urlCancion);
            } catch (MalformedURLException ex) {
                System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        // las siguientes dos lineas se hacen para que el reproductor tenga el estado de
        // isPaused inicializado
        this._player.play();
        this._player.pause();
        this._player.setRepeat(false);
        this.LCanciones.setModel(_modeloLista);
    }
    
    /*
    * Metodo que toma constantemente el tamaño de la ventana para reasignar el tamaño de los paneles internos
    * el PLateral toam un 20% de la ventana y el PCentral toma el 80% de la ventana
    */
    private void Resized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_Resized
        int anchoLateral = (anchoVentana * 20) / 100;
        int anchoCentral = (anchoVentana * 80) / 100;
        if(this.anchoVentana < 500){
            this.anchoVentana = 500;
            Dimension tamañoVentana = new Dimension(anchoVentana, this.getHeight());
            this.setSize(tamañoVentana);
        } else if(this.getHeight() < 600){
            Dimension tamañoVentana = new Dimension(anchoVentana, 700);
            this.setSize(tamañoVentana);
        }
        Dimension sizeLateral = new Dimension(anchoLateral, this.PLateral.getHeight());
        Dimension sizeCentral = new Dimension(anchoCentral, this.PCentral.getHeight());
        Dimension sizeCentralEstilos = new Dimension(anchoCentral, this.PEstilos.getHeight());
        Dimension sizeCentralArtistas = new Dimension(anchoCentral * 2, this.PArtistas.getHeight());
        this.PLateral.setPreferredSize(sizeLateral);
        this.PLateral.setSize(sizeLateral);
        this.PCentral.setPreferredSize(sizeCentral);
        this.PCentral.setSize(sizeCentral);
        this.PEstilos.revalidate();
        this.PArtistas.revalidate();
        
        if(this.panelGenero != null){
            panelGenero.setSize(sizeCentral);
            panelGenero.setPreferredSize(sizeCentral);
            panelGenero.revalidate();
        }
        
        if(scrollPaneEstilos != null){
            this.scrollPaneEstilos.setSize(sizeCentralEstilos);
            this.scrollPaneEstilos.revalidate();
        }
        if(scrollPaneArtistas != null){
            this.scrollPaneArtistas.setSize(sizeCentralArtistas);
            this.scrollPaneArtistas.revalidate();
        }
        this.PContenedorEstilos.revalidate();
        this.PContenedorArtistas.revalidate();
        
        this.anchoVentana = this.getWidth();
    }//GEN-LAST:event_Resized

    // Boton que retrocede la cancion en la lista de reproduccion
    private void BBotonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBotonAtrasActionPerformed
        this._player.skipBackward();
    }//GEN-LAST:event_BBotonAtrasActionPerformed

    /*
    * Boton que verifica si hay una cancion en curso para pausar y reproducir
    */
    private void BBotonPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBotonPlayPauseActionPerformed
        if(this._isPlaying){
            this.BBotonPlayPause.setIcon(this.scaledIconBotonPausa);
            this._player.play();
            this._isPlaying = false;
        } else {
            this.BBotonPlayPause.setIcon(this.scaledIconBotonPlay);
            this._player.pause();
            this._isPlaying = true;
        }
    }//GEN-LAST:event_BBotonPlayPauseActionPerformed

    // Boton para adelantar la cancion en la lista de reproduccion
    private void BBotonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBotonSiguienteActionPerformed
        this._player.skipForward();
    }//GEN-LAST:event_BBotonSiguienteActionPerformed

    // Boton para poner una cancion en bucle
    private void BBotonRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBotonRepetirActionPerformed
        if(this._player.isRepeat()){
            this._player.setRepeat(false);
        } else {
            this._player.setRepeat(true);
        }
    }//GEN-LAST:event_BBotonRepetirActionPerformed

    private void LCancionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LCancionesMouseClicked
        //this._player.
    }//GEN-LAST:event_LCancionesMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BBotonAtras;
    private javax.swing.JButton BBotonPlayPause;
    private javax.swing.JButton BBotonRepetir;
    private javax.swing.JButton BBotonSiguiente;
    private javax.swing.JMenuBar BarraMenu;
    private javax.swing.JList<String> LCanciones;
    private javax.swing.JLabel LImagenCancion;
    private javax.swing.JMenu MArtistas;
    private javax.swing.JMenu MEstilos;
    private javax.swing.JMenu MReproductor;
    private javax.swing.JPanel PArtistas;
    private javax.swing.JPanel PBotonAtras;
    private javax.swing.JPanel PBotonBucle;
    private javax.swing.JPanel PBotonImagenCancion;
    private javax.swing.JPanel PBotonPlay;
    private javax.swing.JPanel PBotonSiguiente;
    private javax.swing.JPanel PBotones;
    private javax.swing.JPanel PCentral;
    private javax.swing.JPanel PCentralGeneroArtistas;
    private javax.swing.JPanel PContenedorArtistas;
    private javax.swing.JPanel PContenedorEstilos;
    private javax.swing.JPanel PEstilos;
    private javax.swing.JPanel PInicio;
    private javax.swing.JPanel PLateral;
    private javax.swing.JScrollPane SPCanciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}