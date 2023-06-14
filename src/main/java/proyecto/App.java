package proyecto;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String nombreadmin = "";

    public App() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 675));

        // Fondo de imagen
        ImageIcon backgroundImage = new ImageIcon("src/main/java/proyecto/fotos/fondo.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        // Panel de inicio de sesión
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(true);
        loginPanel.setBackground(new Color(26, 26, 26)); // Establecer color de fondo en gris #1A1A1A
       loginPanel.setBorder(new LineBorder(Color.WHITE, 10)); // Establecer borde blanco con grosor 4
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setForeground(Color.WHITE); // Cambiar color de letra a blanco
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE); // Cambiar color de letra a blanco
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Acceder");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        add(loginPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear el nuevo panel después de hacer clic en "Iniciar sesión"
                JPanel newPanel = new JPanel();
                newPanel.setBackground(Color.YELLOW);
                String usuarioInput = usernameField.getText();
                System.out.println(usuarioInput);
                String passwordInput = passwordField.getText();


                boolean registroVerificado = false;


                try {

                    String query = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
                    Map<Integer, String> parameters = new HashMap<>();
                    parameters.put(1, usuarioInput);
                    parameters.put(2, passwordInput);

                    ResultSet queryResult= RegistroBD.query(query, parameters);

                    registroVerificado = queryResult.next();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (registroVerificado) {

                    String usuario = usuarioInput;
                    JOptionPane.showMessageDialog(null, "Sesion iniciada con éxito", "Bienvenido "+usuario, JOptionPane.DEFAULT_OPTION);
                    if(usuarioInput.equals("Admin1")|| usuarioInput.equals("Admin2")){
                        nombreadmin +=usuarioInput;
                        System.out.println("--- ACCESO ADMIN ---");
                        AdminPanel.adminControl(nombreadmin);

                    }else{
                        //Usuario
                        String username=usuarioInput;
                        System.out.println("--- ACCESO A CUENTA ---");
                        Biblioteca.usuarioBiblioteca(username);

                    }
                    usuarioInput = "";

                } else {

                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto", "Advertencia", JOptionPane.DEFAULT_OPTION);
                    System.out.println("Incorrecto");

                }
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick(); // Simular clic en el botón "Acceder"
            }
        });
    }
}
