
package com.mycompany.compiladores;

import java.util.Stack;


public class Lexico {

    int token, EdoActual, EdoTransicion, IniLexema, FinLexema, IndiceCaracterActual;
    String CadenaSigma;
    public String Lexema;
    boolean PasoPorEdoAcept;
    char CaracterActual;
    Stack<Integer> Pila = new Stack<>();
    //AFD AutomataFD;
    
    public Lexico()
    {
        CadenaSigma = "";
        PasoPorEdoAcept = false;
        IniLexema = FinLexema = -1;
        IndiceCaracterActual = -1;
        token = -1;
        Pila.clear();
      //AutomataFD = null;
    }
    
    public Lexico(String sigma, String FileAFD, int IdAFD)
    {
        //AutomataFD = new AFD();
        CadenaSigma = sigma;
        PasoPorEdoAcept = false;
        IniLexema = 0;
        FinLexema = -1;
        IndiceCaracterActual = 0;
        token = -1;
        Pila.clear();
        //AutomataFD.LeerAFDdeArchivo(FileAFD, IdAFD);
    }
    
    public Lexico(String sigma, String FileAFD)
    {
        //AutomataFD = new AFD();
        CadenaSigma = sigma;
        PasoPorEdoAcept = false;
        IniLexema = 0;
        FinLexema = -1;
        IndiceCaracterActual = 0;
        token = -1;
        Pila.clear();
        //AutomataFD.LeerAFDdeArchivo(FileAFD, IdAFD);
    }
    
    public Lexico(String FileAFD, int IdAFD)
    {
        //AutomataFD = new AFD();
        CadenaSigma = "";
        PasoPorEdoAcept = false;
        IniLexema = 0;
        FinLexema = -1;
        IndiceCaracterActual = 0;
        token = -1;
        Pila.clear();
        //AutomataFD.LeerAFDdeArchivo(FileAFD, IdAFD);
    }
    
     public Lexico(String sigma /*,AFD AutFD*/)
    {
        //AutomataFD = new AFD();
        CadenaSigma = sigma;
        PasoPorEdoAcept = false;
        IniLexema = 0;
        FinLexema = -1;
        IndiceCaracterActual = 0;
        token = -1;
        Pila.clear();
        //AutomataFD.AutFD;
    }
   
     public ClassEstadoAnalizLexico GetEdoActualLexico(){
         ClassEstadoAnalizLexico EdoActualAnaliz = new ClassEstadoAnalizLexico();
         EdoActualAnaliz.CaracterActual = CaracterActual;
         EdoActualAnaliz.EdoActual = EdoActual;
         EdoActualAnaliz.EdoTransicion = EdoTransicion;
         EdoActualAnaliz.FinLexema = FinLexema;
         EdoActualAnaliz.IndiceCaracterActual = IndiceCaracterActual;
         EdoActualAnaliz.IniLexema = IniLexema;
         EdoActualAnaliz.Lexema = Lexema;
         EdoActualAnaliz.PasoPorEdoAcept = PasoPorEdoAcept;
         EdoActualAnaliz.token = token;
         EdoActualAnaliz.Pila = Pila;
         return EdoActualAnaliz;
     }
     
     public boolean SetEdoAnalizLexico(ClassEstadoAnalizLexico e)
     {
         CaracterActual=e.CaracterActual;
         EdoActual=e.EdoActual;
         EdoTransicion=e.EdoTransicion;
         FinLexema=e.FinLexema;
         IndiceCaracterActual=e.IndiceCaracterActual;
         IniLexema=e.IniLexema;
         Lexema=e.Lexema;
         PasoPorEdoAcept=e.PasoPorEdoAcept;
         token=e.token;
         Pila=e.Pila;
         return true;
     } 
     
     public void SetSigma(String sigma)
     {
         CadenaSigma = sigma;
         PasoPorEdoAcept = false;
         IniLexema = 0;
         FinLexema = -1;
         IndiceCaracterActual = 0;
         token = -1;
         Pila.clear();
     }
     
     public String CadenaxAnalizar()
     {
         return CadenaSigma.substring(IndiceCaracterActual, CadenaSigma.length() - IndiceCaracterActual);
     }
     
     public int yylex()
     {
         while(true)
         {
             Pila.push(IndiceCaracterActual);
             if (IndiceCaracterActual >= CadenaSigma.length())
             {
                 Lexema = "";
                 return SimEspeciales.FIN;
             }
             IniLexema = IndiceCaracterActual;
             EdoActual = 0;
             PasoPorEdoAcept = false;
             FinLexema = -1;
             token = -1;
             while (IndiceCaracterActual < CadenaSigma.length())
             {
                 CaracterActual = CadenaSigma.charAt(IndiceCaracterActual);
                 //EdoTransicion = AutomataFD.TablaFD(EdoActual, CaracterActual);
                 if(EdoTransicion != -1) 
                 {
                    // if(AutomataFD.TablaFD[EdoTransicion, 256] != -1)
                     {
                        PasoPorEdoAcept = true;
                        //token = AutomataFD.TablaFD[EdoTransicion, 256];
                        FinLexema = IndiceCaracterActual;
                     }
                     IndiceCaracterActual++;
                     EdoActual = EdoTransicion;
                 }
                 break;
             } // No hay transicion con el caracter actual
             
             if(!PasoPorEdoAcept)
             {
                 IndiceCaracterActual = IniLexema + 1;
                 Lexema = CadenaSigma.substring(IniLexema, 1);
                 token = SimEspeciales.TOKENERROR;
                 return token; //Error
             }
             // No hay transicon con el caracter actual, pero ya se habia pasado por edo de aceptacion
             Lexema = CadenaSigma.substring(IniLexema, FinLexema - IniLexema + 1);
             IndiceCaracterActual = FinLexema + 1;
             if(token == SimEspeciales.OMITIR) 
             {
                 continue;
             } 
             else
                 return token;
         }  
        
     }

     public boolean UndoToken()
     {
         if(Pila.empty())
             return false;
         IndiceCaracterActual = Pila.pop();
         return true;
     }
     
}


