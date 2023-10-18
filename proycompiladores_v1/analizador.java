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
        private Map<String, AFN> especiales = new HashMap<String, AFN>();

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
                                
                        case 4:
				unirEspecial();
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
            String rango=null, tokenText=null;
            boolean correcto=false;
            AFN a1 = new AFN(); // Se crea una instancia de la clase AFN

            opcion = JOptionPane.showInputDialog(null,"Ingresará un rango de valores? (S / N)").charAt(0);
		
            switch(opcion)
            {
                case 's','S':
				
                while(!correcto)
                {

                    rango = JOptionPane.showInputDialog(null,"Ingrese el rango de la transición: ");
                    if(rango.charAt(0)=='c')
                    {
			rango = rango.substring(1, rango.length());
			String[] rangos = rango.split("-");
			if(verifyRange((char)Integer.parseInt(rangos[0]),(char)Integer.parseInt(rangos[1]))) // Verifica que el rango sea válido
                        {
                            JOptionPane.showMessageDialog(null,"ÉXITO");
                            // Crea un AFN con el rango especificado y lo asigna a la instancia a1
                            a1.createAFN(Integer.parseInt(rangos[1]) /* Superior */, Integer.parseInt(rangos[0]) /* Inferior */);
                            rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                            listaAutomatas.put(rango, a1); // Almacena el AFN en el HashMap
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
                            JOptionPane.showMessageDialog(null,"Error, el rango izquierdo es mayor al derecho. Favor de verificar ASCII.");
			}
                    }
		}
			
		break;
				
		case 'n','N':
		rango = JOptionPane.showInputDialog(null,"Ingrese la transicion");
		if(rango.charAt(0)=='c')
                {
                    rango = rango.substring(1, rango.length());
                    if(rango.length()>=1)
                    {
                        JOptionPane.showMessageDialog(null,"ÉXITO");
                        // Crea un AFN con el valor numérico especificado y lo asigna a la instancia a1
                        a1.createAFN(Integer.parseInt(rango));						
                        rango = JOptionPane.showInputDialog(null,"Ingrese el nombre del automata:");
                        listaAutomatas.put(rango, a1); // Almacena el AFN en el HashMap
                    }else
                    {
                        JOptionPane.showMessageDialog(null,"Ocurrio un error desconocido, pruebe de nuevo.");
                    }
		}else
                {
                    if(rango.length()==1)
                    {
                        JOptionPane.showMessageDialog(null,"ÉXITO");
                        // Crea un AFN con el valor numérico especificado y lo asigna a la instancia a1
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
            String automata,auxiliar = "";
            int opcion=0;
            boolean correcto=false, imprimir = true;
		
            while(!correcto)
            {
                opcion = Integer.parseInt(JOptionPane.showInputDialog("Que quiere imprimir:\n1) AFN\n2) AFN especial"));
            	if(opcion >=1 && opcion<=2)
                {
                    correcto = true;
            	}else
                {
                    JOptionPane.showMessageDialog(null,"El dato que ingresó no se encuentra en las opciones, intentelo de nuevo:");
            	}
            }
            correcto = false;
		
            if(opcion == 1)
            {
                if(listaAutomatas.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No hay autómatas que mostrar");
                    imprimir = false;
                    correcto = true;
                }
		}else
                {
                    if(especiales.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"No hay autómatas especiales que mostrar");
                        imprimir = false;
                        correcto = true;
                    }
		}
		
		if(imprimir)
                {
                    switch(opcion)
                        {
                    case 1: 
                            while(!correcto) {
                                    auxiliar = "Seleccione el autómata que mostrará:\n";
                                    for(String s: listaAutomatas.keySet()) {
                                    	auxiliar = auxiliar + "Automata "+ s + "\n";
                                    }
					
                                    automata = JOptionPane.showInputDialog(auxiliar);
                                        auxiliar = "";
                                    if(listaAutomatas.containsKey(automata)) {
                                    	correcto=true;
                                    	listaAutomatas.get(automata).mostrarAFN(1);
                                                palabras_panel = listaAutomatas.get(automata).publicarAFN();
                                                //Imprimir AFN
                                    }else {
                                    	JOptionPane.showMessageDialog(null,"ERROR: Escribe correctamente el autómata");
                                    }
                            }
                            break;
                    default:
                        while(!correcto)
                        {
                            auxiliar = "Seleccione el autómata que mostrará:\n";
                            for(String s: especiales.keySet())
                            {
                                auxiliar = auxiliar + "Automata "+ s;
                            }
					
                            automata = JOptionPane.showInputDialog(auxiliar);
                            auxiliar = "";
                            if(especiales.containsKey(automata))
                            {
                                correcto=true;
                                especiales.get(automata).mostrarAFN(2);
                                palabras_panel = especiales.get(automata).publicarAFN();
                            }else
                            {
                                JOptionPane.showMessageDialog(null,"ERROR: Escribe correctamente el autómata");
                            }
                        }
                	break;
                    }
		}	
	}

        private void unirEspecial()
        {
            int tokenRing;
            boolean salga = false, dejarDePedirToken = false;
            String key,concat;
            HashSet<String> automatasAUnir = new HashSet<String>();
            Map<Integer, AFN> aUnir = new HashMap<Integer, AFN>(); 
		
            if(listaAutomatas.size()<2)
            {                    
                JOptionPane.showMessageDialog(null,"No se cuenta con los suficientes AFN's para realizar esta operación");
                return;
            }
		
            concat = "Lista de automatas:\n";
            String automatas = "";
            int flag = 0;
            for(String s: listaAutomatas.keySet())
            {
                concat = concat + "\t" + s + "\n";
                automatas = automatas + "\t" + s + "\n";                        
            }   		
		
            while(aUnir.size() < listaAutomatas.size() && !salga)
            {
                if(flag == 1)
                    concat = concat +"Automatas: \n"+ automatas + "\nEscriba el nombre del otro autómata a añadir:\n";
                else
                    concat = concat + "Escriba el nombre del automata a añadir:\n";
		key = JOptionPane.showInputDialog(concat);
                flag = 1;
                concat = "";
                if(key.contentEquals("exit") && !aUnir.isEmpty())
                {
                    salga = true;
                }else
                {
                    if(key.contentEquals("exit"))
                    {
			JOptionPane.showMessageDialog(null,"No es posible avanzar si no se ha seleccionado por lo menos un automata");
                    }else
                    {
                        if(automatasAUnir.contains(key))
                        {
                            JOptionPane.showMessageDialog(null,"Ingrese un automata que no haya seleccionado antes");
			}else
                        {
                            while(!dejarDePedirToken)
                            {		
                                String opcTok = JOptionPane.showInputDialog("Ingrese el Token asociado a este automata");
				tokenRing = Integer.parseInt(opcTok);
				if(aUnir.keySet().contains(tokenRing))
                                {
                                    JOptionPane.showMessageDialog(null,"ERROR: Este token ya ha sido asignado");
				}else
                                {
                                    dejarDePedirToken = true;
                                    aUnir.put(tokenRing, listaAutomatas.get(key));
				}
                            }
                            dejarDePedirToken = false;
                            automatasAUnir.add(key);
			}
                    }
		}
            }
            concat = "Se uniran los siguientes autómatas con su respectivo token\n";
            for(String s: automatasAUnir)
            {
                concat = concat + s + "\n";
            }
            JOptionPane.showMessageDialog(null,concat);
            for(Integer i: aUnir.keySet())
            {
                for(Estado e: aUnir.get(i).edosAcept)
                {
                    e.setToken(i);
                }
            }
		
            AFN especial = new AFN();
            Estado eInicial = new Estado();
            Transicion t;
            for(Integer i: aUnir.keySet())
            {
                t = new Transicion(SimEspeciales.EPSILON, aUnir.get(i).edoIni);
                eInicial.setTransicion(t);
                especial.alfabeto.addAll(aUnir.get(i).alfabeto);
                especial.edosAcept.addAll(aUnir.get(i).edosAcept);
                especial.edosAFN.addAll(aUnir.get(i).edosAFN);
            }
            especial.edoIni=eInicial;
            especial.edosAFN.add(eInicial);
				
            key = JOptionPane.showInputDialog("Ingrese un nombre para el autómata especial:");
            especiales.put(key, especial);
            JOptionPane.showMessageDialog(null,"AFN especial creado correctamente");
		
            for(String s: automatasAUnir)
            {
                listaAutomatas.remove(s);
            }
		
            FileOutputStream fos;
            try
            {
                fos = new FileOutputStream("AFNespecial.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(especiales);
                oos.close();
            } catch (IOException e)
                {
                    System.out.println("analizador.unirEspecial()");
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
                    JOptionPane.showMessageDialog(null,"ERROR: No tiene");
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
