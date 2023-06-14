package proyecto.Factura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImprimirFactura extends JPanel {

    private JLabel juegoLabel;
    private Point labelLocation;

    public ImprimirFactura() {
        setLayout(new BorderLayout());

        // Cargar la imagen de fondo
        ImageIcon backgroundImage = new ImageIcon("src/main/java/proyecto/Factura/Factura.png");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(backgroundImage.getIconWidth(), backgroundImage.getIconHeight(), Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(scaledImage);

        // Crear un JLabel personalizado para mostrar la imagen de fondo
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());

        // Crear el panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(null);

        // Crear el JLabel para el Juego 1
        juegoLabel = new JLabel("Minecraft                  $ 29.99");
        juegoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        juegoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        juegoLabel.setBounds(168, 227, 160, 20);

        // Obtener la posición inicial del JLabel
        labelLocation = juegoLabel.getLocation();

        // Agregar el JLabel del Juego 1 al panel de contenido
        contentPanel.add(juegoLabel);

        // Agregar el Juego 2
        JLabel juego2Label = new JLabel("GTA V                      $ 59.99");
        juego2Label.setFont(new Font("Arial", Font.BOLD, 12));
        juego2Label.setHorizontalAlignment(SwingConstants.CENTER);
        juego2Label.setBounds(172, 256, 160, 20);
        contentPanel.add(juego2Label);

        // Agregar el Juego 3
        JLabel juego3Label = new JLabel("SIMS 4                      $  4.99");
        juego3Label.setFont(new Font("Arial", Font.BOLD, 12));
        juego3Label.setHorizontalAlignment(SwingConstants.CENTER);
        juego3Label.setBounds(169, 285, 160, 20);
        contentPanel.add(juego3Label);


        JLabel usuario = new JLabel("Pepe Garcia");
        usuario.setFont(new Font("Arial", Font.BOLD, 14));
        usuario.setHorizontalAlignment(SwingConstants.CENTER);
        usuario.setBounds(-15, 365, 200, 20);
        contentPanel.add(usuario);


        JLabel preutotal = new JLabel("  94.97");
        preutotal.setFont(new Font("Arial", Font.BOLD, 26));
        preutotal.setHorizontalAlignment(SwingConstants.CENTER);
        preutotal.setBounds(166, 385, 200, 20);
        contentPanel.add(preutotal);


        // Agregar el panel de contenido al JLabel de fondo
        backgroundLabel.add(contentPanel, BorderLayout.CENTER);

        // Agregar el JLabel de fondo al panel principal
        add(backgroundLabel, BorderLayout.CENTER);

        // Agregar el listener del mouse para mover el texto
        juegoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Guardar la posición inicial del mouse cuando se presiona el botón
                labelLocation = e.getPoint();
            }
        });

        juegoLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Calcular el nuevo desplazamiento del JLabel en función del movimiento del mouse
                int dx = e.getX() - labelLocation.x;
                int dy = e.getY() - labelLocation.y;

                // Actualizar la posición del JLabel
                juegoLabel.setLocation(juegoLabel.getX() + dx, juegoLabel.getY() + dy);

                // Guardar la nueva posición del mouse
                labelLocation = e.getPoint();

                // Repintar el panel
                repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Factura");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ImprimirFactura());

            // Centrar la ventana en la pantalla
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;
            int frameWidth = frame.getWidth();
            int frameHeight = frame.getHeight();
            int x = (screenWidth - frameWidth) /2;
            int y = (screenHeight - frameHeight) / 4;
            frame.setLocation(x, y);

            frame.pack();
            frame.setVisible(true);
        });
    }
}
