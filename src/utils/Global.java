package utils;

import java.util.TreeSet;

public final class Global {
    // Alfabeto para AFD
    public static TreeSet<Character> ALPHABET;


    // Global for experiments
    // invariante: Despues de instanciar clase AutomatonTextSearch y correr metodo .run, estas 3 variables estan determinadas.
    public static long CONSTRUCTION_TIME;
    public static long SEARCH_TIME;
    public static long MAKE_ALPHABET_TIME;
    public static int M;

    /**
     * Metodo necesario para cargar el alfabeto sobre el cual se construira y ejecutara el AFD. Es necesario llamar
     * a este metodo cada vez que se utiliza un archivo de texto fuente nuevo.
     *
     * @param textAsArray Texto fuente como un arreglo de caracteres.
     * @param verbose     Booleano para mostrar informacion en pantalla.
     */
    public static void makeAlphabet(char[] textAsArray, boolean verbose) {
        ALPHABET = new TreeSet<>();
        long t0 = System.currentTimeMillis();
        for (char c : textAsArray) {
            ALPHABET.add(c);
        }
        long t1 = System.currentTimeMillis();
        MAKE_ALPHABET_TIME = t1 - t0;
        if (verbose) System.out.println("  done makeAlphabet in " + MAKE_ALPHABET_TIME);
    }

    /**
     * Metodo para contar la cantidad de ocurrencias de un patron en un texto. Utilizado para probar efectividad del metodo
     * con automata
     *
     * @param src     Texto fuente que contiene texto sobre el cual buscar.
     * @param pattern Texto de patron que se quiere buscar.
     * @return Cantidad de ocurrencias del patron 'pattern' en texto 'src'.
     */
    public static int realCount(String src, String pattern) {
        int lastIndex = 0;
        int count = 0;
        while (lastIndex != -1) {
            lastIndex = src.indexOf(pattern, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += pattern.length();
            }
        }
        return count;
    }

}
