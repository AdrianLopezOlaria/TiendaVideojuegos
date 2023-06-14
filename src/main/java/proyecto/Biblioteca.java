package proyecto;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    public static void usuarioBiblioteca(String username) {
        System.out.println(username);
        String usuario= username;

        JFrame frame = new JFrame("Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1200, 675));
        panel.setBackground(Color.BLACK);

        ImageIcon icon1 = new ImageIcon("src/main/java/proyecto/fotos/juegos.png");
        Image scaledImage1 = icon1.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        ImageIcon newIcon1 = new ImageIcon(scaledImage1);

        JLabel playerLabel = new JLabel("  Juegos  ");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 35));
        playerLabel.setIcon(newIcon1);
        playerLabel.setVerticalTextPosition(SwingConstants.BOTTOM);

        panel.add(playerLabel, BorderLayout.WEST);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel libraryLabel = new JLabel(" Biblioteca    ", SwingConstants.CENTER);
        libraryLabel.setForeground(Color.WHITE);
        libraryLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titlePanel.add(libraryLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Atrás");
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorderPainted(false);
        backButton.setBackground(Color.BLACK);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Eliminar el panel actual al hacer clic en "Atrás"
            }
        });
        titlePanel.add(backButton, BorderLayout.WEST);

        panel.add(titlePanel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon("src/main/java/proyecto/fotos/carrito.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);

        JButton storeButton = new JButton("  Tienda  ");
        storeButton.setForeground(Color.WHITE);
        storeButton.setFont(new Font("Arial", Font.BOLD, 35));
        storeButton.setBorder(new LineBorder(Color.WHITE, 4));
        storeButton.setBackground(Color.BLACK);
        storeButton.setIcon(newIcon);

        panel.add(storeButton, BorderLayout.EAST);

        storeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GamesPanelUser.juegos(username);
            }
        });

        List<String> juegos = obtenerJuegosDesdeBaseDeDatos(username); // Obtener los juegos desde la base de datos

        JList<String> juegosList = new JList<>(juegos.toArray(new String[0]));
        juegosList.setForeground(Color.WHITE);
        juegosList.setBackground(Color.BLACK);
        juegosList.setFont(new Font("Arial", Font.BOLD, 25));

        JScrollPane juegosScrollPane = new JScrollPane(juegosList);
        juegosScrollPane.setBorder(new LineBorder(Color.BLACK, 30));
        panel.add(juegosScrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
        frame.setVisible(true);
        // Actualizar el contenido del panel cada 5 segundos
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                juegos.clear(); // Limpiar la lista de juegos
                juegos.addAll(obtenerJuegosDesdeBaseDeDatos(username)); // Obtener los juegos actualizados desde la base de datos
                juegosList.setListData(juegos.toArray(new String[0])); // Actualizar la lista de juegos en el panel
            }
        });
        timer.start();

    }

    private static List<String> obtenerJuegosDesdeBaseDeDatos(String username) {
        List<String> juegos = new ArrayList<>();

        try {
            Connection connection = DatabaseConnector.getConnection();

            String query1 = "SELECT id FROM usuarios WHERE usuario = '" + username+ "'";
            PreparedStatement statement1 = connection.prepareStatement(query1);
            ResultSet resultSet1 = statement1.executeQuery();
            int idUsuario = resultSet1.getInt("id");

             String query = "SELECT j.nombre FROM juegos j inner JOIN linia_fac lf ON j.id = lf.id_juego WHERE lf.id_usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nombreJuego = resultSet.getString("nombre");
                juegos.add(nombreJuego);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
}
