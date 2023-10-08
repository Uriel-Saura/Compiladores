
package com.mycompany.compiladores;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;

public class ventana extends JFrame
{
    private Map<String, AFN> listaAutomatas = new HashMap<String, AFN>();
    
    JPanel panel1 = new JPanel(); //Izq
    JPanel panel2 = new JPanel(); //Der
    JPanel panel3 = new JPanel(); //Arriba
    
    analizador analizador = new analizador();
    
    public ventana()
    {
        setSize(1300,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("PROYECTO");
        iniciarComponentes();        
    }
    
        public void iniciarComponentes()
    {    
        JButton button1 = new JButton("Crear AFN");
        button1.setFocusPainted(false);
        button1.setMargin(new Insets(0, 0, 0, 0));        
        button1.setBorderPainted(false);
        
        JButton button2 = new JButton("Operaciones sobre un AFN");
        button2.setFocusPainted(false);
        button2.setMargin(new Insets(0, 0, 0, 0));        
        button2.setBorderPainted(false);
        
        JButton button3 = new JButton("Mostrar AFN");
        button3.setFocusPainted(false);
        button3.setMargin(new Insets(0, 0, 0, 0));        
        button3.setBorderPainted(false);
        
        JButton buttonEXIT = new JButton("Salir");
        buttonEXIT.setFocusPainted(false);
        buttonEXIT.setMargin(new Insets(0, 0, 0, 0));        
        buttonEXIT.setBorderPainted(false);
        
        JTextArea areaTexto = new JTextArea();
        areaTexto.setBounds(0,10,884,661);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Avenir",Font.PLAIN,14));    
        areaTexto.setForeground(new Color(29,35,57));
             
        this.setLayout(null);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);

        /* Etiquetas */
        JLabel label1 = new JLabel("OPCIONES",SwingConstants.CENTER);        
        label1.setFont(new Font("Avenir",Font.PLAIN,20));

        JLabel label2 = new JLabel("COMPILADORES - P1",SwingConstants.CENTER);        
        label2.setFont(new Font("Avenir",Font.PLAIN,30));
        label2.setForeground(Color.black);
               
        panel1.setBounds(0, 0, 261, 1024);               
        panel2.setBounds(300, 54, 1200, 970);
        panel3.setBounds(0, 0, 1200, 500);
        
        button1.setBounds(0,200,261,50);      
        button2.setBounds(0,260,261,50);              
        button3.setBounds(0,320,261,50); 
        buttonEXIT.setBounds(0, 550, 261, 50);    
        
        label1.setBounds(20,10,210,50);      
        label2.setBounds(301,10,800,35);            
        
        panel1.add(label1);                
        panel3.add(label2);

        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(buttonEXIT);        
        
        panel2.add(areaTexto);
        
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JButton btn1 = new JButton(); btn1 = (JButton) e.getSource();
                
                if(btn1.getText() == "Crear AFN")
                {                  
                    analizador.menu(1);
                }
                
                if(btn1.getText() == "Operaciones sobre un AFN")
                {                 
                    analizador.menu(2);
                }
                
                if(btn1.getText() == "Mostrar AFN")
                {
                    areaTexto.setText("");                 
                    analizador.menu(3);
                    
                    String palabra = analizador.publish();
                    areaTexto.append("\n"+palabra);                    
                }
                
                if(btn1.getText() == "Salir")
                {
                    System.exit(0);
                }
            }
        };
        
        button1.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        buttonEXIT.addActionListener(listener);        
        
        this.getContentPane().add(panel1);
        this.getContentPane().add(panel2);
        this.getContentPane().add(panel3);
    }
        
}