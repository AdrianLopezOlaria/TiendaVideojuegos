package proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Nuevo {
    public static void addJuego() {
        JFrame frame = new JFrame("AÑADIR JUEGO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1000, 450));
        panel.setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);

        JLabel libraryLabel = new JLabel("AÑADIR JUEGO   ", SwingConstants.CENTER);
        libraryLabel.setForeground(Color.WHITE);
        libraryLabel.setFont(new Font("Arial", Font.BOLD, 70));
        titlePanel.add(libraryLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Atrás");
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBorderPainted(false);
        backButton.setBackground(Color.BLACK);
        titlePanel.add(backButton, BorderLayout.WEST);

        panel.add(titlePanel, BorderLayout.NORTH);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cerrar el panel actual
            }
        });

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 0); // Agregar espaciado vertical entre los campos

        JLabel nameLabel = new JLabel("Nombre del Juego:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);
        contentPanel.add(nameLabel, gbc);

        JTextField nameTextField = new JTextField(40);
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 17));
        contentPanel.add(nameTextField, gbc);

        JLabel descriptionLabel = new JLabel("Descripción:");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        descriptionLabel.setForeground(Color.WHITE);
        contentPanel.add(descriptionLabel, gbc);

        JTextField descriptionTextField = new JTextField(40);
        descriptionTextField.setFont(new Font("Arial", Font.PLAIN, 17));
        contentPanel.add(descriptionTextField, gbc);

        JLabel priceLabel = new JLabel("Precio:");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(Color.WHITE);
        contentPanel.add(priceLabel, gbc);

        JTextField priceTextField = new JTextField(40);
        priceTextField.setFont(new Font("Arial", Font.PLAIN, 17));
        contentPanel.add(priceTextField, gbc);

        JButton saveButton = new JButton("Guardar Juego");
        saveButton.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(35, 0, 0, 10); // Agregar espaciado vertical y horizontal al botón
        contentPanel.add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para guardar el juego aquí
                String nombre = nameTextField.getText();
                String precio = priceTextField.getText();
                String descripcion = descriptionTextField.getText();

                // Configurar la conexión a la base de datos

                try {
                    // Establecer la conexión

                    Connection connection = DatabaseConnector.getConnection();
                    // Crear la consulta SQL para insertar los datos
                    String query = "INSERT INTO juegos (nombre, precio, descripcion) VALUES (?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, nombre);
                    statement.setString(2, precio);
                    statement.setString(3, descripcion);

                    // Ejecutar la consulta
                    statement.execute();
                    connection.close();
                    // Cerrar la conexión y liberar recursos
                    statement.close();

                    System.out.println("--Juego guardado en la base de datos--");
                    JOptionPane.showMessageDialog(null, nombre+" añadido a la tienda!", "Juego añadido", JOptionPane.DEFAULT_OPTION);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error al guardar los datos del juego en la base de datos.");
                }
            }
        });

        panel.add(contentPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
        frame.setVisible(true);
    }
}
