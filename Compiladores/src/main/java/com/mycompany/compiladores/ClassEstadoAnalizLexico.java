
package com.mycompany.compiladores;

import java.util.Stack;


public class ClassEstadoAnalizLexico {
    
    int token, EdoActual, EdoTransicion, IniLexema, FinLexema, IndiceCaracterActual;
    String CadenaSigma;
    public String Lexema;
    boolean PasoPorEdoAcept;
    char CaracterActual;
    Stack<Integer> Pila = new Stack<>();
    
    public ClassEstadoAnalizLexico()
    {   
        token = -1;
        EdoActual = 0;
        EdoTransicion = 0;
        IniLexema = 0;
        FinLexema = -1;
        IndiceCaracterActual = 0;
        CadenaSigma = "";
        PasoPorEdoAcept = false;
        Pila.clear();
    }
 
}
