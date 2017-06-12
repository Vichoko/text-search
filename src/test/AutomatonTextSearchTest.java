package test;

import algorithms.Automaton.AutomatonTextSearch;
import utils.Preprocessor;
import utils.Triple;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import static utils.Global.*;


public class AutomatonTextSearchTest {
    /**
     * Decomentar primera y comentar la segunda para comprobar exactitud del algoritmo. Vice-versa para ejecutar los
     * experimentos propuestos en el informe.
     *
     * @param args En desuso.
     * @throws Exception En caso de error con el AFD o de I/O de archivos.
     */
    public static void main(String[] args) throws Exception {
        //accurracyTest();
        completeTest(false);
    }

    /**
     * Este metodo estatico ejecuta todas las pruebas expuestas en el informe. Para cargar archivos de sobre 2^23 palabras
     * se requiere aumentar el heapspace de la maquina virtual de Java al rededor de 4GB.
     *
     * @param verbose boolean para verificar comportamiento y tiempos en pantalla. Dejar en falso en pruebas, para evitar sobrecosto.
     * @throws Exception Por problemas de lectura o con el automata.
     */
    private static void completeTest(boolean verbose) throws Exception {
        File statisticsFile = new File("./results/automaton_test_results.txt");
        PrintWriter pw = new PrintWriter(statisticsFile);

        // Si hay problemas de espacio, eliminar de 2^23 en adelante.
        // archivos vienen comprimidos, es necesario decomprimirlos
        String[] files = {"source/2^15.txt",
                "source/2^16.txt",
                "source/2^17.txt",
                "source/2^18.txt",
                "source/2^19.txt",
                "source/2^20.txt",
                "source/2^21.txt",
                "source/2^22.txt",
                "source/2^23.txt",
                "source/2^24.txt",
        };

        for (String textFile : files) { // por cada archivo
            // Map almacena resultados de la prueba
            // key: M, value: CONSTRUCTION_TIME, SEARCH_TIME, COUNT
            HashMap<Integer, Triple<Long, Long, Integer>> map = new HashMap<>();

            // Pre-procesamiento
            Preprocessor p = new Preprocessor(textFile);
            String fullText = p.clean();
            makeAlphabet(fullText.toCharArray(), verbose);
            System.out.println("n: " + fullText.length() + " = 2^" + Math.log(fullText.length()) / Math.log(2));
            pw.println("n: " + fullText.length());

            // Muestreo de patrones
            String[] sample = Preprocessor.takeSample(fullText, verbose);

            // Preparacion de iteraciones sobre patrones
            int progressCounter = 0;
            int stepCounter = 0;
            int progressLimit = sample.length;
            long deltaProgress = progressLimit / 100;
            System.out.print("Progress: " + progressCounter); // variables para mostrar progreso en pantalla
            // iteracion sobre patrones
            for (String pattern : sample) {
                if (pattern.length() < 3) continue; // causan problemas con la creacion del afd por ser chicos
                stepCounter++;
                if (stepCounter >= deltaProgress) { // display de progreso
                    System.out.print(++progressCounter);
                    stepCounter = 0;
                }
                // construccion de afd y busqueda
                // Resultados de tiempo quedan en variables globales M, CONSTRUCTION_TIME Y SEARCH_TIME
                AutomatonTextSearch ats = new AutomatonTextSearch(fullText, pattern, verbose);
                ats.run();

                // Agregar resultados parciales a tabla de hash
                if (!map.containsKey(M)) {
                    map.put(M, new Triple<>(
                            CONSTRUCTION_TIME,
                            SEARCH_TIME,
                            1));
                } else { // se suman y agrega 1 al contador, si se repite m
                    Triple<Long, Long, Integer> previous = map.get(M);
                    map.put(M,
                            new Triple<>(
                                    CONSTRUCTION_TIME + previous.getFirst(),
                                    SEARCH_TIME + previous.getSecond(),
                                    previous.getThird() + 1));
                }
            }

            // hacer output de estadisticas al archivo de salida
            for (Integer m : map.keySet()) {
                Triple<Long, Long, Integer> val = map.get(m);
                pw.println("m: " + m + ", construction_time: " + val.getFirst() + ", search_time: " + val.getSecond() + ", counter: " + val.getThird());

            }
            System.out.println("");
            pw.println("");
            pw.flush();
        }
        pw.close();
    }

    /**
     * Metodo ejecuta prueba sobre uno de los archivos, permitiendo comprobar en pantalla que los patrones son buscados
     * y encontrados en el texto la cantidad correcta de veces.
     *
     * @throws Exception Si es que hay problemas con el automata o leyendo el archivo de texto fuente.
     */
    public static void accurracyTest() throws Exception {
        Preprocessor p = new Preprocessor("source/2^21.txt");
        String result = p.clean();
        String[] sample = Preprocessor.takeSample(result, true);

        for (String pattern : sample) {
            if (pattern.length() < 3) continue;
            System.out.println("Searching: " + pattern);
            AutomatonTextSearch ats = new AutomatonTextSearch(result, pattern, true);
            System.out.println("AFD Found: " + ats.run() + " times.");
            System.out.println("Real Quantity: " + realCount(result, pattern) + " times.\n");

        }
    }
}
