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

}
