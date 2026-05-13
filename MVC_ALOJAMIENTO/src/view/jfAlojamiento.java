package view;

import javax.swing.*;
import controller.ControllerAlojamiento;
import model.Alojamiento;

public class jfAlojamiento extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField tfNombre, tfCompleto, tfResponsable, tfDireccion, tfDistancia;
    private JButton btnGuardar;
    private Alojamiento aloj;

    public jfAlojamiento() { this(null); }

    public jfAlojamiento(Alojamiento aloj) {
        this.aloj = aloj;

        setTitle("Alojamiento");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbNombre = new JLabel("Alojamiento:");
        lbNombre.setBounds(20, 20, 100, 25);
        add(lbNombre);

        tfNombre = new JTextField();
        tfNombre.setBounds(130, 20, 200, 25);
        add(tfNombre);

        JLabel lbCompleto = new JLabel("Nombre Completo:");
        lbCompleto.setBounds(20, 60, 120, 25);
        add(lbCompleto);

        tfCompleto = new JTextField();
        tfCompleto.setBounds(130, 60, 200, 25);
        add(tfCompleto);

        JLabel lbResponsable = new JLabel("Responsable:");
        lbResponsable.setBounds(20, 100, 100, 25);
        add(lbResponsable);

        tfResponsable = new JTextField();
        tfResponsable.setBounds(130, 100, 200, 25);
        add(tfResponsable);

        JLabel lbDireccion = new JLabel("Dirección:");
        lbDireccion.setBounds(20, 140, 100, 25);
        add(lbDireccion);

        tfDireccion = new JTextField();
        tfDireccion.setBounds(130, 140, 200, 25);
        add(tfDireccion);

        JLabel lbDistancia = new JLabel("Distancia:");
        lbDistancia.setBounds(20, 180, 100, 25);
        add(lbDistancia);

        tfDistancia = new JTextField();
        tfDistancia.setBounds(130, 180, 200, 25);
        add(tfDistancia);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(130, 230, 100, 30);
        add(btnGuardar);

        if (aloj != null) {
            tfNombre.setText(aloj.getAlojamiento());
            tfCompleto.setText(aloj.getNombreCompleto());
            tfResponsable.setText(aloj.getResponsable());
            tfDireccion.setText(aloj.getDireccion());
            tfDistancia.setText(String.valueOf(aloj.getDistancia()));
        }

        btnGuardar.addActionListener(e -> {
            ControllerAlojamiento ca = new ControllerAlojamiento();
            int distancia = tfDistancia.getText().isEmpty() ? 0 : Integer.parseInt(tfDistancia.getText());

            if (aloj == null) {
                Alojamiento nuevo = new Alojamiento(
                        tfNombre.getText(),
                        tfCompleto.getText(),
                        tfResponsable.getText(),
                        tfDireccion.getText(),
                        distancia
                );
                ca.insertar(nuevo);
            } else {
                aloj.setAlojamiento(tfNombre.getText());
                aloj.setNombreCompleto(tfCompleto.getText());
                aloj.setResponsable(tfResponsable.getText());
                aloj.setDireccion(tfDireccion.getText());
                aloj.setDistancia(distancia);
                ca.editar(aloj);
            }
            dispose();
        });
    }
}

