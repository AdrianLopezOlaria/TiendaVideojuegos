package proyecto;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JPanel {
    public AdminPanel(String nombreadmin) {


        setLayout(new BorderLayout());
        setBackground(Color.BLACK); // Establecer el fondo en negro

        // Título "TIENDA ADMIN"
        JLabel titleLabel = new JLabel("PANEL "+nombreadmin.toUpperCase());
        titleLabel.setFont(new Font("Merriweather", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE); // Cambiar color de letra a blanco
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel "Atras" en la parte inferior izquierda
        JPanel backButtonPanel = new JPanel(new BorderLayout());
        backButtonPanel.setPreferredSize(new Dimension(200, 40));
        backButtonPanel.setBackground(Color.BLACK); // Establecer el fondo en negro
        backButtonPanel.setBorder(new LineBorder(Color.WHITE, 3)); // Establecer borde grueso y de color blanco

        JButton backButton = new JButton("Atras");
        backButton.setPreferredSize(new Dimension(100, 90));
        backButton.setForeground(Color.WHITE); // Cambiar color de letra a blanco
        backButton.setBackground(Color.BLACK);
        backButton.setBorder(new LineBorder(Color.BLACK, 0)); // Establecer borde grueso y de color blanco
        backButton.setBorderPainted(false);


        backButtonPanel.add(backButton, BorderLayout.WEST);
        add(backButtonPanel, BorderLayout.SOUTH);

        // Panel "Añadir Juegos"
        JPanel addGamesPanel = new JPanel(new BorderLayout());
        addGamesPanel.setPreferredSize(new Dimension(400, 300));
        addGamesPanel.setBackground(Color.BLACK); // Establecer el fondo en negro
        addGamesPanel.setBorder(new LineBorder(Color.WHITE, 2)); // Establecer borde grueso y de color blanco

        ImageIcon icon = new ImageIcon("src/main/java/proyecto/fotos/mas.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);

        JLabel addGamesLabel = new JLabel("Añadir Juegos");
        addGamesLabel.setFont(new Font("Merriweather", Font.BOLD, 35));
        addGamesLabel.setForeground(Color.WHITE);
        addGamesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addGamesLabel.setIcon(newIcon);

        addGamesPanel.add(addGamesLabel, BorderLayout.CENTER);

        addGamesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Acción al hacer clic en el panel de "Añadir Juegos"
                Nuevo.addJuego();
            }
        });


        add(addGamesPanel, BorderLayout.WEST);

        // Panel "Juegos en tienda"
        JPanel gamesInStorePanel = new JPanel(new BorderLayout());
        gamesInStorePanel.setPreferredSize(new Dimension(400, 300));
        gamesInStorePanel.setBackground(Color.BLACK); // Establecer el fondo en negro
        gamesInStorePanel.setBorder(new LineBorder(Color.WHITE, 2)); // Establecer borde grueso y de color blanco

        ImageIcon icon1 = new ImageIcon("src/main/java/proyecto/fotos/tienda.png");
        Image image1 = icon1.getImage();
        Image scaledImage1 = image1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon newIcon1 = new ImageIcon(scaledImage1);

        JLabel gamesInStoreLabel = new JLabel("Juegos en tienda");
        gamesInStoreLabel.setFont(new Font("Merriweather", Font.BOLD, 35));
        gamesInStoreLabel.setForeground(Color.WHITE);
        gamesInStoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gamesInStoreLabel.setIcon(newIcon1);

        gamesInStorePanel.add(gamesInStoreLabel, BorderLayout.CENTER);


        add(gamesInStorePanel, BorderLayout.EAST);

        System.out.println("--Añadir Juegos / Juegos en tienda--");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                while (parent != null && !(parent instanceof JFrame)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    JFrame frame = (JFrame) parent;
                    frame.dispose();
                }
                System.out.println("------ Panel cerrado ------");
                System.out.println("          ");
                System.out.println("--- Inicio de Session ---");


            }
        });

        gamesInStorePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Acción al hacer clic en el panel de "Juegos en tienda"
                // Llamar al panel GameListPanel
                JFrame gameListFrame = new JFrame("Lista de juegos");
                gameListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                GamesPanelAdmin gamesPanelAdmin = new GamesPanelAdmin();
                gameListFrame.getContentPane().add(gamesPanelAdmin);

                gameListFrame.pack();
                gameListFrame.setLocationRelativeTo(null);
                gameListFrame.setVisible(true);
            }
        });
    }

    public static void adminControl(String nombreadmin) {
        JFrame frame = new JFrame("Panel de control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AdminPanel adminPanel = new AdminPanel(nombreadmin);
        frame.getContentPane().add(adminPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}