package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import model.Alojamiento;
import java.awt.Color;

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private DefaultTableModel modeloAlojamientos;
    private JTable tablaAlojamientos;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Test frame = new Test();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Test() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel LbMensaje = new JLabel("");
        LbMensaje.setOpaque(true);
        LbMensaje.setBackground(new Color(128, 128, 128));
        LbMensaje.setBounds(10, 11, 464, 35);
        contentPane.add(LbMensaje);

        JButton btConectar = new JButton("CONECTAR");
        btConectar.setBounds(10, 60, 120, 40);
        contentPane.add(btConectar);

        JButton btnInsertar = new JButton("INSERTAR");
        btnInsertar.setBounds(140, 60, 120, 40);
        contentPane.add(btnInsertar);

        JButton btListar = new JButton("Listar Alojamientos");
        btListar.setBounds(270, 60, 150, 40);
        contentPane.add(btListar);

        modeloAlojamientos = new DefaultTableModel();
        tablaAlojamientos = new JTable(modeloAlojamientos);
        JScrollPane scroll = new JScrollPane(tablaAlojamientos);
        scroll.setBounds(10, 110, 464, 240);
        contentPane.add(scroll);

        btConectar.addActionListener(e -> {
            Conexion con = new Conexion();
            try (Connection miConexion = con.conectar()) {
                if (miConexion != null) {
                    LbMensaje.setText("Conexión exitosa");
                } else {
                    LbMensaje.setText("Error al conectar");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                LbMensaje.setText("Error al conectar");
            }
        });

        btnInsertar.addActionListener(e -> {
            Alojamiento aloj = new Alojamiento(
                    "MONTSENY",
                    "MONTSENY HOTEL",
                    "FERMIN",
                    "BUENA PREGUNTA",
                    20
            );

            ControllerAlojamiento cAlojamiento = new ControllerAlojamiento();
            cAlojamiento.insertar(aloj);
        });

        btListar.addActionListener(e -> {
            ControllerAlojamiento cAlojamiento = new ControllerAlojamiento();
            cAlojamiento.cargarTodos(modeloAlojamientos);
        });
    }
}
