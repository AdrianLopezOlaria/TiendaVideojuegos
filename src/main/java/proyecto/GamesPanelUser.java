package proyecto;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GamesPanelUser extends JPanel {
    private JList<String> gamesList;

    public GamesPanelUser(String username) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(950, 600));
        setBackground(Color.BLACK); // Establecer el fondo en negro

        // Panel para el título y el botón "Atrás"
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);

        // Botón de "Atrás"
        JButton backButton = new JButton("Atrás");
        backButton.setFont(new Font("Merriweather", Font.BOLD, 16));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        backButton.addActionListener(new ActionListener() {
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

                System.out.println("- Biblioteca abierta -");

            }
        });

        headerPanel.add(backButton, BorderLayout.WEST);

        // Título "JUEGOS"
        JLabel titleLabel = new JLabel("JUEGOS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Merriweather", Font.BOLD, 55));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        System.out.println("- Visualizando Juegos -");

        // Obtener la lista de juegos de la base de datos
        List<String[]> games = getGamesFromDatabase(); // Método que obtiene los juegos de la base de datos

        // Crear el panel para mostrar los juegos y descripciones
        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        gamesPanel.setBackground(Color.BLACK);

        // Espacio encima del primer juego
        gamesPanel.add(Box.createVerticalStrut(10));

        // Agregar los títulos de los juegos, descripciones y precios al panel
        for (String[] game : games) {
            String gameName = game[0];
            String gameDescription = game[1];
            String gamePrice = game[2];

            JPanel gamePanel = new JPanel(new BorderLayout());
            gamePanel.setBackground(Color.BLACK);
            gamePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // Nombre del juego
            JLabel nameLabel = new JLabel(gameName);
            nameLabel.setFont(new Font("Merriweather", Font.BOLD, 24));
            nameLabel.setForeground(Color.WHITE);
            gamePanel.add(nameLabel, BorderLayout.NORTH);

            // Descripción del juego
            JLabel descriptionLabel = new JLabel(gameDescription);
            descriptionLabel.setFont(new Font("Merriweather", Font.PLAIN, 17));
            descriptionLabel.setForeground(Color.WHITE);
            gamePanel.add(descriptionLabel, BorderLayout.CENTER);

            // Precio del juego
            JLabel priceLabel = new JLabel(gamePrice + " €");
            priceLabel.setFont(new Font("Merriweather", Font.BOLD, 20));
            priceLabel.setForeground(Color.WHITE);
            gamePanel.add(priceLabel, BorderLayout.SOUTH);


            // Agregar el evento de clic al panel del juego
            gamePanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    try {
                        Connection connection = DatabaseConnector.getConnection();

                        String query1 = "SELECT id FROM usuarios WHERE usuario = ?";
                        PreparedStatement statement2 = connection.prepareStatement(query1);
                        statement2.setString(1, username);
                        ResultSet resultSetId = statement2.executeQuery();

                        int id_Usuario = resultSetId.getInt("id");

                        //sacar el id

                        String query2 = "SELECT id FROM juegos WHERE nombre = ?";
                        PreparedStatement statement3 = connection.prepareStatement(query2);
                        statement3.setString(1, gameName);
                        ResultSet resultSetIdJuego = statement3.executeQuery();

                        int id_Juego = resultSetIdJuego.getInt("id");

                        String query3 = "INSERT INTO linia_fac ( id_juego, id_usuario) VALUES  (?, ?)";

                        PreparedStatement statement4 = connection.prepareStatement(query3);
                        statement4.setInt(1, id_Juego);
                        statement4.setInt(2, id_Usuario);
                        statement4.executeUpdate();

                        System.out.println("Juego añadido");
                        JOptionPane.showMessageDialog(null, gameName+" añadido a la biblioteca","Juego añadido", JOptionPane.INFORMATION_MESSAGE);

                        resultSetId.close();
                        resultSetIdJuego.close();
                        statement2.close();
                        statement3.close();
                        statement4.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            gamesPanel.add(gamePanel);
            gamesPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane gamesScrollPane = new JScrollPane(gamesPanel);
        gamesScrollPane.setBorder(new LineBorder(Color.WHITE, 3)); // Establecer borde grueso y de color blanco
        add(gamesScrollPane, BorderLayout.CENTER);
    }

    public List<String[]> getGamesFromDatabase() {
        List<String[]> games = new ArrayList<>();

        // Conexión a la base de datos y consulta para obtener la lista de juegos
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/basededatos.db");

            // Crear la sentencia SQL para obtener los juegos
            String sql = "SELECT nombre, descripcion, precio FROM juegos";

            // Verificar el nombre de usuario y contraseña en la base de datos
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Recorrer los resultados y agregar los juegos, descripciones y precios a la lista
            while (resultSet.next()) {
                String gameName = resultSet.getString("nombre");
                String gameDescription = resultSet.getString("descripcion");
                String gamePrice = resultSet.getString("precio");
                games.add(new String[]{gameName, gameDescription, gamePrice});
            }

            // Cerrar la conexión y liberar recursos
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    public static void juegos(String username) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame("Lista de Juegos");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                GamesPanelUser gamesPanelUser = new GamesPanelUser(username);
                frame.getContentPane().add(gamesPanelUser);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
