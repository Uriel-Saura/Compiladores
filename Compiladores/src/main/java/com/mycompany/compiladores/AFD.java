/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.compiladores;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author skoll
 */
public class AFD {
     public static void saveHashMapToFile(String AFD, Map<Set<String>, Map<Character, Set<String>>> afd) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AFD))) {
            for (Map.Entry<Set<String>, Map<Character, Set<String>>> entry : afd.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error");
        }
    }
}
