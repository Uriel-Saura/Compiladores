
package com.mycompany.compiladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentanaAnalizLexico extends JFrame{
    
    public JPanel panel;
    public JTextArea area;
    public String cadena;
    
    public VentanaAnalizLexico(){
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Prueba Cadena");
        iniciarComponentes();
    }
    
    public void iniciarComponentes(){
        paneles();
        labels();
        textField();
        textArea();
        comboBox();
        buttons();
    }
        
    public void paneles(){
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
    }
    
    public void labels(){
        JLabel mensaje = new JLabel();
        mensaje.setText("Cargar AFD desde archivo");
        mensaje.setBounds(10,10,200,25);
        panel.add(mensaje);
        
        JLabel id = new JLabel();
        id.setText("Id asginado");
        id.setBounds(10, 35, 75, 25);
        panel.add(id);
        
        JLabel AFD = new JLabel();
        AFD.setText("AFD a utilizar");
        AFD.setBounds(10,85,100,25);
        panel.add(AFD);
        
        JLabel cadena = new JLabel();
        cadena.setText("Cadena a analizar");
        cadena.setBounds(10,110,150,25);
        panel.add(cadena);
        
    }
    
    public void textField(){
        JTextField id = new JTextField();
        id.setBounds(100, 35, 50, 25);
        panel.add(id);
    }
    
    public void textArea(){
        area = new JTextArea();
        area.setBounds(125,120,200,100);
        panel.add(area);
    }
    
    public void comboBox(){
        JComboBox box = new JComboBox();
        box.setBounds(100, 85, 100, 25);
        box.addItem("Rojo");
        box.addItem("Azul");
        panel.add(box);
    }
    
    public void buttons(){
        JButton fichero = new JButton();
        fichero.setBounds(180,35,150,25);
        fichero.setText("Buscar archivo");
        panel.add(fichero);
        
        JButton analizar = new JButton();
        analizar.setText("Analizar");
        analizar.setBounds(10,190,100,25);
        panel.add(analizar);
        
       ActionListener listener = (ActionEvent ae) -> {

        };
        
       fichero.addActionListener(listener);
       
       ActionListener listner2 = (ActionEvent ae) -> {
           cadena = area.getText();
           System.out.println(cadena);
        };
       analizar.addActionListener(listner2);
    }
    
}
