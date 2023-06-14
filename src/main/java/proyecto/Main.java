package proyecto;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int usuario = 0;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Inicio de Sesión");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                App loginPanel = new App();
                frame.getContentPane().add(loginPanel);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });


    }
}

