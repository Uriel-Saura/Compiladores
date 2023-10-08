
package com.mycompany.compiladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.swing.JOptionPane;


public class analizador
{
	private Scanner in = new Scanner(System.in);
	private Map<String, AFN> listaAutomatas = new HashMap<String, AFN>();

        String palabras_panel = "";
	
	public boolean menu(int opcion)
        {
		                		
		switch(opcion)
                {
			case 1:
				createAFN();
				break;
                                   
			case 2:
				opAFN();
				break;
			case 3:
				printAFN();
				break;
			default:
				JOptionPane.showMessageDialog(null,"ERROR: Ingrese una opcion válida");
				break;
		}
		return false;
		
	}
	
	private void createAFN()
        {
            char opcion;
            String rango=null;
            boolean correcto=false;
            AFN a1 = new AFN();

            opcion = JOptionPane.showInputDialog(null,"Ingresará un rango de valores? (S / N)").charAt(0);
		
            switch(opcion)
            {
		
                case 's','S':
				
                while(!correcto)
                {

                    rango = JOptionPane.showInputDialog(null,"Ingrese el rango de la transición: ");    //A-C	c65-67
                    if(rango.charAt(0)=='c')
                    {
			rango = rango.substring(1, rango.length());
			String[] rangos = rango.split("-");
			if(verifyRange((char)Integer.parseInt(rangos[0]),(char)Integer.parseInt(rangos[1]))) //a-z
                        {
                            JOptionPane.showMessageDialog(null,"Completado correctamente");
                            a1.createAFN(Integer.parseInt(rangos[1]) /* Superior */, Integer.parseInt(rangos[0]) /* Inferior */);
                            rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                            listaAutomatas.put(rango, a1);
                            correcto=true;
			}else
                        {
                            JOptionPane.showMessageDialog(null,"ERROR: el rango izquierdo es mayor al derecho.");
			}
                    }else
                    {
			if(verifyRange(rango.charAt(0),rango.charAt(2))) //a-z
                        {		
                            JOptionPane.showMessageDialog(null,"ÉXITO");
                            a1.createAFN(rango.charAt(2) /* Superior */, rango.charAt(0) /* Inferior */);
                            rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                            listaAutomatas.put(rango, a1);
                            correcto=true;
			}else
                        {
                            JOptionPane.showMessageDialog(null,"Error, el rango izquierdo es mayor al derecho. Favor de verificar ascii.");
			}
                    }
		}
			
		break;
				
		case 'n','N':
		rango = JOptionPane.showInputDialog(null,"Ingrese la Transicion");
		if(rango.charAt(0)=='c')
                {
                    rango = rango.substring(1, rango.length());
                    if(rango.length()>=1)
                    {
                        JOptionPane.showMessageDialog(null,"Completado correctamente");
                        a1.createAFN(Integer.parseInt(rango));						
                        rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                        listaAutomatas.put(rango, a1);
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"Ocurrio un error desconocido, pruebe de nuevo.");
                    }
		}else
                {
                    if(rango.length()==1)
                    {
                        JOptionPane.showMessageDialog(null,"ÉXITO");
                        a1.createAFN(rango.charAt(0));
                        rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                        listaAutomatas.put(rango, a1);
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"Ocurrio un error desconocido, pruebe de nuevo.");
                    }
		}
                
		break;	
            }
	}
	
	private void opAFN()
        {   
            int opcion;
            String opcion1;
            opcion1 = JOptionPane.showInputDialog(null,"Ingrese la operación que quiera realizar: \n1) Generar cerradura positiva + \n"
                        + "2) Generar cerradura de Kleen * \n3) Generar cerradura opcional ? \n4) Unir autómatas AFN A|B"
                        + "\n5) Concatenar autómatas AFN AB\n6) Regresar");
            opcion = Integer.parseInt(opcion1);
            switch (opcion)
            {
		case 1:
                    CerraduraPositiva();
                    break;
		case 2:
                    CerrKleen();
                    break;
		case 3:
                    genCerrOpcional();
                    break;
		case 4:
                    unirAutomatas();
                    break;
		case 5:
                    concatenarAutomatas();
                    break;
		case 6:
                    break;
		default: 
                    JOptionPane.showMessageDialog(null,"ERROR: Opción inválida");
                    break;
            }
            
            JOptionPane.showMessageDialog(null,"ÉXITO");
	}
		
        private void printAFN()
        {
            String automata, auxiliar = "";

            if (listaAutomatas.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No hay autómatas que mostrar");
                return;
            }

            while (true)
            {
                auxiliar = "Seleccione el autómata que mostrar:\n";
                for (String s : listaAutomatas.keySet())
                {
                    auxiliar = auxiliar + "Autómata " + s + "\n";
                }

                automata = JOptionPane.showInputDialog(auxiliar);
                auxiliar = "";

                if (listaAutomatas.containsKey(automata))
                {
                    listaAutomatas.get(automata).mostrarAFN(1);
                    palabras_panel = listaAutomatas.get(automata).publicarAFN();
                    break;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "ERROR: Autómata no válido");
                }
            }
        }

		
	private void CerraduraPositiva()
        {
            if(listaAutomatas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"ERROR: No hay autómatas suficientes");
		return;
            }
		
            AFN a;
            String key,concat;             
            boolean correcto=false;
            concat = "Lista de autómatas, ";
            while(!correcto)
            {
		concat = concat + "\nAutómatas creados:\n";
		for(String s: listaAutomatas.keySet())
                {
                    concat = concat + "\t"+s+"\n";
		}
		key = JOptionPane.showInputDialog(concat);
		if(listaAutomatas.containsKey(key))
                {
                    correcto = true;
                    a = listaAutomatas.get(key);
                    a = a.cerraduraPosit();
                    listaAutomatas.remove(key);
                    listaAutomatas.put(key, a);
		}else
                {
                JOptionPane.showMessageDialog(null,"INCORRECTO, escriba el autómata correctamente");
		}
            }
	}
	
	private void CerrKleen()
        {
            if(listaAutomatas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"ERROR: No tiene los suficientes autómatas para realizar esta acción");
		return;
            }
		
            AFN a;
            String key,concat;
            boolean correcto=false;
            concat = "Lista de autómatas, escoja el deseado: \n";
            while(!correcto)
            {
		concat = concat + "Autómatas\n";
		for(String s: listaAutomatas.keySet())
                {
                    concat = concat + "\t" + s + "\n";
		}
		key = JOptionPane.showInputDialog(concat);
                concat = "Lista de autómatas, escoja el deseado: \n";
		if(listaAutomatas.containsKey(key))
                {
                    correcto = true;
                    a = listaAutomatas.get(key);
                    a = a.cerraduraKleen();
                    listaAutomatas.remove(key);
                    listaAutomatas.put(key, a);
		}else
                {
                    JOptionPane.showMessageDialog(null,"INCORRECTO, escriba el autómata correctamente");
		}
            }
	}
		
	private void genCerrOpcional()
        {
            if(listaAutomatas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"ERROR: No tiene los suficientes autómatas para realizar esta acción");
		return;
            }
		
            AFN a;
            String key,concat;
            boolean correcto=false;
            concat = "Lista de autómatas, escoja el deseado: \n";
            while(!correcto)
            {
                concat = concat + "Autómatas\n";
                for(String s: listaAutomatas.keySet())
                {
                concat = concat + "\t" + s + "\n";
                }
                key = JOptionPane.showInputDialog(concat);
                concat = "Lista de autómatas, escoja el deseado: \n";
                if(listaAutomatas.containsKey(key))
                {
                    correcto = true;
                    a = listaAutomatas.get(key);
                    a = a.cerraduraOpcional();
                    listaAutomatas.remove(key);
                    listaAutomatas.put(key, a);
                }else
                {
                    JOptionPane.showMessageDialog(null,"INCORRECTO, escriba el autómata correctamente");
                }
            }
	}
	
	private void unirAutomatas()
        {
            AFN a1;
            String key1, key2,concat;
            boolean correcto=false;
            if(listaAutomatas.size()>=2)
            {
                concat = "Lista de autómatas, escoja el deseado: ";
                while(!correcto)
                {
                    concat = concat + "\nAutómatas:\n";
                    for(String s: listaAutomatas.keySet())
                    {
                        concat = concat + "\t" + s + "\n";
                    }
                    key1 = JOptionPane.showInputDialog(concat+"\nPrimer autómata");
                    key2 = JOptionPane.showInputDialog("Segundo autómata");
                    if(listaAutomatas.containsKey(key1) && listaAutomatas.containsKey(key2))
                    {
                        correcto = true;
                        a1 = listaAutomatas.get(key1);
                        a1 = a1.unirAFN(listaAutomatas.get(key2));
                        listaAutomatas.remove(key1);
                        listaAutomatas.remove(key2);
                        listaAutomatas.put(key1, a1);
                        JOptionPane.showMessageDialog(null,"ÉXITO");
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"INCORRECTO, escriba el autómata correctamente");
                    }
                }
		}else
                {
                    JOptionPane.showMessageDialog(null,"No cuenta con los suficientes autómatas para realizar esta operacion");
		}
	}

	private void concatenarAutomatas()
        {
            AFN a1;
            String key1, key2,concat;
            boolean correcto=false;
            if(listaAutomatas.size()>=2)
            {			
                while(!correcto)
                {
                    concat = "Lista de autómatas, escoja el deseado: ";
                    concat = concat + "Autómatas\n";
                    for(String s: listaAutomatas.keySet())
                    {
                        concat = concat + "\t" + s + "\n";
                    }
                    key1 = JOptionPane.showInputDialog(concat+"\nPrimer automata");
                    key2 = JOptionPane.showInputDialog("Segundo automata");
                    if(listaAutomatas.containsKey(key1) && listaAutomatas.containsKey(key2))
                    {
                        correcto = true;
                        a1 = listaAutomatas.get(key1);
                        a1 = a1.concatenarAFN(listaAutomatas.get(key2));
                        listaAutomatas.remove(key1);
                        listaAutomatas.remove(key2);
                        listaAutomatas.put(key1, a1);
                        JOptionPane.showMessageDialog(null,"ÉXITO");
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"INCORRECTO, escriba el autómata correctamente");
                    }
		}
		}else
                {
                    JOptionPane.showMessageDialog(null,"ERROR: No tiene ");
		}
	}
	
	private boolean verifyRange(char rangoMenor, char rangoMayor)
        {
            if((int)rangoMayor>=(int)rangoMenor)
            {
                return true;
            }
            return false;
	}
        
        public String publish()
        {
            return palabras_panel;
        }
        
}

