
package com.mycompany.compiladores;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;


public class AFN implements Serializable
{
    static int contIdAFN = 0;
     Estado edoIni;
    HashSet<Estado> edosAFN = new HashSet<Estado>();
    HashSet<Estado> edosAcept = new HashSet<Estado>();
    HashSet<Character> alfabeto = new HashSet<Character>();
    int idAFN;
        
       
    public AFN()
    {
	this.idAFN = contIdAFN++;
	this.edoIni = null;
	this.edosAFN.clear();
	this.edosAcept.clear();
	this.alfabeto.clear();
    }
	
    public AFN createAFN(char s)
    {
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(s, e2);
        e1.setTransicion(t);
        e2.setEdoAcept(true);
        this.alfabeto.add(s);
        this.edoIni = e1;
        this.edosAFN.add(e1);
        this.edosAFN.add(e2);
        this.edosAcept.add(e2);
		
        return this;
    }
	
    public AFN createAFN(int s)
    {
	Transicion t;
	Estado e1, e2;
	e1 = new Estado();
	e2 = new Estado();
	t = new Transicion((char)s, e2);
	e1.setTransicion(t);
	e2.setEdoAcept(true);
	this.alfabeto.add((char)s);
	this.edoIni = e1;
	this.edosAFN.add(e1);
	this.edosAFN.add(e2);
	this.edosAcept.add(e2);
		
	return this;
    }
	
    public AFN createAFN(char s1, char s2)
    {
	char i;
		
	Transicion t;
	Estado e1, e2;
	e1 = new Estado();
	e2 = new Estado();
	t = new Transicion(s1, s2, e2);
	e1.setTransicion(t);
	e2.setEdoAcept(true);
	for(i=s2; i<=s1; i++)
        {
            alfabeto.add(i);
	}
            edoIni = e1;
            edosAFN.add(e1);
            edosAFN.add(e2);
            edosAcept.add(e2);
		
            return this;
	}
	
    public AFN createAFN(int s1, int s2)
    {
	char i;
		
	Transicion t;
	Estado e1, e2;
	e1 = new Estado();
	e2 = new Estado();
	t = new Transicion((char)s1, (char)s2, e2);
	e1.setTransicion(t);
	e2.setEdoAcept(true);
	for(i=(char)s2; i<=(char)s1; i++)
        {
            alfabeto.add(i);
	}
	edoIni = e1;
	edosAFN.add(e1);
	edosAFN.add(e2);
	edosAcept.add(e2);
		
	return this;
    }
	
    public Estado getEdoInicial()
    {
        return edoIni;
    }
	
    public AFN unirAFN(AFN f2)//A|B
    {	
        Estado e1 = new Estado();
	Estado e2 = new Estado();
		
	Transicion t1 = new Transicion(SimEspeciales.EPSILON, this.edoIni);
	Transicion t2 = new Transicion(SimEspeciales.EPSILON, f2.edoIni);
	e1.setTransicion(t1);
	e1.setTransicion(t2);
		
	for(Estado e: this.edosAcept)
        {
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
	}
	for(Estado e: f2.edosAcept)
        {
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
	}
	this.edosAcept.clear();
	f2.edosAcept.clear();
	this.edoIni = e1;
	e2.setEdoAcept(true);
	this.edosAcept.add(e2);
	this.edosAFN.addAll(f2.edosAFN);
	this.edosAFN.add(e2);
	this.edosAFN.add(e1);
	this.alfabeto.addAll(f2.alfabeto);
	return this;
    }
	
    public AFN concatenarAFN(AFN f2) //AB
    {		
        for(Transicion t: f2.edoIni.getTransiciones())
        {
            for(Estado e: this.edosAcept)
            {
                e.setTransicion(t);
                e.setEdoAcept(false);
            }
	}
        f2.edosAFN.remove(f2.edoIni);
        this.edosAcept = f2.edosAcept;
        this.edosAFN.addAll(f2.edosAFN);
        this.alfabeto.addAll(f2.alfabeto);
        return this;
    }
	
    public AFN cerraduraPosit() //+
    {		
	Estado e1 = new Estado();
	Estado e2 = new Estado();
		
	e1.setTransicion(new Transicion(SimEspeciales.EPSILON, edoIni));
	for(Estado e: edosAcept)
        {
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, edoIni));
            e.setEdoAcept(false);
	}
        e2.setEdoAcept(true);
		
	edoIni = e1;
	edosAcept.clear();
	edosAcept.add(e2);
	edosAFN.add(e1);
	edosAFN.add(e2);
	return this;
    }
	
    public AFN cerraduraKleen() //*
    {		
	Estado e1 = new Estado();
	Estado e2 = new Estado();
		
	e1.setTransicion(new Transicion(SimEspeciales.EPSILON, edoIni));
	for(Estado e: edosAcept)
        {
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, edoIni));
            e.setEdoAcept(false);
	}
	e2.setEdoAcept(true);
	e1.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
		
	edoIni = e1;
	edosAFN.add(e1);
	edosAFN.add(e2);
	edosAcept.clear();
	edosAcept.add(e2);
        return this;
    }
	
    public AFN cerraduraOpcional() //?
    {		
        Estado e1 = new Estado();
        Estado e2 = new Estado();
		
        e1.setTransicion(new Transicion(SimEspeciales.EPSILON, edoIni));
        for(Estado e: edosAcept)
        {
            e.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
	}
        e2.setEdoAcept(true);
        e1.setTransicion(new Transicion(SimEspeciales.EPSILON, e2));
		
        edoIni = e1;
        edosAFN.add(e1);
        edosAFN.add(e2);
        edosAcept.clear();
        edosAcept.add(e2);
        return this;
	}
	
    //Funciones
    public HashSet<Estado> cerraduraEpsilon(Estado e)
    {
	HashSet<Estado> conjunto = new HashSet<Estado>();
	Stack<Estado> stackDeEstados = new Stack<Estado>();
	Estado aux;
		
	stackDeEstados.push(e);
	conjunto.add(e);
		
	while(!stackDeEstados.empty())
        {
            aux = stackDeEstados.pop();	
            for(Transicion t: aux.getTransiciones())
            {
                if(!conjunto.contains(t.getEdoTransicion(SimEspeciales.EPSILON)) && t.getEdoTransicion(SimEspeciales.EPSILON)!=null)
                {
                    conjunto.add(t.getEdoTransicion(SimEspeciales.EPSILON));
                    stackDeEstados.push(t.getEdoTransicion(SimEspeciales.EPSILON));
		}
            }
	}
        return conjunto;
    }
	
    public HashSet<Estado> moverA(Estado e, char item)
    {
        HashSet<Estado> salidaEstados = new HashSet<Estado>();
        Estado aux;
		
        for(Transicion t: e.getTransiciones())
        {
            aux = t.getEdoTransicion(item);
            if(aux !=null)
            {
                salidaEstados.add(aux);
            }
        }
		
        return salidaEstados;
    }
	
    public HashSet<Estado> moverA(HashSet<Estado> es, char item)
    {
	HashSet<Estado> salidaEstados = new HashSet<Estado>();
	Estado aux;
		
	for(Estado e: es)
        {
            for(Transicion t: e.getTransiciones())
            {
                aux = t.getEdoTransicion(item);
                if(aux !=null)
                {
                    salidaEstados.add(aux);
                }
            }
	}
		
	return salidaEstados;
    }
	 
    String auxiliar = "";
    public void mostrarAFN(int type)
    {
	auxiliar = "";
	for(char c: alfabeto)
        {
            auxiliar = auxiliar + "\t"+c;
            System.out.printf("\t%c", c);
	}
	System.out.printf("\tEpsilon");
        auxiliar = auxiliar +"\tEpsilon";
	System.out.printf("\tAceptacion");
        auxiliar = auxiliar + "\tAceptacion";
		
	for(Estado e: edosAFN)
        {
            System.out.printf("\n%d", e.getIdEstado());
            auxiliar = auxiliar + "\n"+ e.getIdEstado();
            if(e == edoIni)
            {
		System.out.printf("(in)");
                auxiliar = auxiliar+"(in)";
            }
			
            for(char c: alfabeto)
            {
		System.out.printf("\t");
                auxiliar = auxiliar + "\t";
		if(moverA(e, c).isEmpty()) {
                    System.out.printf("-1");
                    auxiliar = auxiliar + "-1";
		}else
                {
                    for(Estado e2: moverA(e, c))
                    {
			System.out.printf("%d ", e2.getIdEstado());
                        auxiliar = auxiliar +" "+e2.getIdEstado();
                    }
		}
            }
            System.out.printf("\t");
            auxiliar = auxiliar + "\t";
            if(moverA(e, SimEspeciales.EPSILON).isEmpty())
            {
		System.out.print("-1");
                auxiliar = auxiliar + "-1";
            }else
            {
		for(Estado e2: moverA(e, SimEspeciales.EPSILON))
                {
                    System.out.printf("%d ", e2.getIdEstado());
                    auxiliar = auxiliar +" "+e2.getIdEstado();
		}
            }
			
            if(type==1)
            {
		if(e.getEdoAcept())
                {
                    auxiliar = auxiliar +"\tSi";
                    System.out.printf("\tSi");
		}else
                {
                    auxiliar = auxiliar +"\tNo";
                    System.out.printf("\tNo");
		}
            }else if(type==2)
            {
		if(e.getEdoAcept())
                {
                    auxiliar = auxiliar +"\t"+e.getToken();
                    System.out.printf("\t%d",e.getToken());
		}else
                {
                    auxiliar = auxiliar + "\t-1";
                    System.out.printf("\t-1");
		}
            }
	}
        
        auxiliar = auxiliar + "\n";
	System.out.println();
    }
        
    public String publicarAFN()
    {
        return auxiliar;
    }
}
	
