
package com.mycompany.compiladores;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AFDConvertir {
    
    public static Map<Set<String>, Map<Character, Set<String>>> convertAFNtoAFD(
        Map<Set<String>, Map<Character, Set<String>>> afn, Set<Character> alfabeto) {
        //Map<Set<String>, Map<Character, Set<String>>> afnMap = afn.getespeciales();
        Map<Set<String>, Map<Character, Set<String>>> AFD = new HashMap<>();
        Queue<Set<String>> estadosNoProcesados = new LinkedList<>();
        Set<Set<String> > estadosProcesados = new HashSet<>();
        
        // Obtener el estado inicial del AFD
        Set<String> iniEdo = epsilonClosure(afn, afn.keySet().iterator().next());
        estadosNoProcesados.add(iniEdo);

        while (!estadosNoProcesados.isEmpty()) {
            Set<String> currentStateSet = estadosNoProcesados.poll();

            if (estadosProcesados.contains(currentStateSet)) {
                continue;
            }

            estadosProcesados.add(currentStateSet);

            Map<Character, Set<String>> transitions = new HashMap<>();

            for (Character symbol : alfabeto) {
                Set<String> nextStateSet = new HashSet<>();

                for (String currentState : currentStateSet) {
                    Set<String> nextStates = afn.getOrDefault(Collections.singleton(currentState), new HashMap<>()).get(symbol);
                    if (nextStates != null) {
                        nextStateSet.addAll(epsilonClosure(afn, nextStates));
                    }
                }

                if (!nextStateSet.isEmpty()) {
                    transitions.put(symbol, nextStateSet);
                    if (!estadosProcesados.contains(nextStateSet) && !estadosNoProcesados.contains(nextStateSet)) {
                        estadosNoProcesados.add(nextStateSet);
                    }
                }
            }

            AFD.put(currentStateSet, transitions);
        }

        return AFD;
    }

    public static Set<String> epsilonClosure(
            Map<Set<String>, Map<Character, Set<String>>> afn, Set<String> states) {
        Set<String> closure = new HashSet<>(states);
        Stack<String> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            String currentState = stack.pop();
            Set<String> epsilonTransitions = afn.getOrDefault(Collections.singleton(currentState), new HashMap<>()).get('\u03B5');
            if (epsilonTransitions != null) {
                for (String nextState : epsilonTransitions) {
                    if (!closure.contains(nextState)) {
                        closure.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }

        return closure;
    }
   }