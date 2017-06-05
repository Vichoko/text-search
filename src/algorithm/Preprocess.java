package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 */
public class Preprocess {
    private String m_text;

    /**
     * Instancia la clase de pre-procesamiento con el nombre del archivo fuente.

     * @param inFile Archivo de texto fuente.
     * @throws IOException En caso de error de lectura del archivo fuente.
     */
    public Preprocess(String inFile) throws IOException {
        m_text = readFileAsString(inFile);
    }

    /**
     * Toma una muestra de N/10 palabras aleatorias (probabilidad uniforme) del string en source.
     *
     * @param source Texto fuente.
     * @param debug Flag que habilita mensajes de depuracion
     * @return Arreglo de N/10 palabras muestreadas aleatoriamente.
     */
    public static String[] takeSample(String source, boolean debug){
        String[] wordArray = source.split("\\s+");
        Random rand = new Random();

        int sampleSize = wordArray.length/10;
        String[] wordSample = new String[sampleSize];

        for(int i = 0; i < sampleSize; i++){
            wordSample[i] = wordArray[rand.nextInt(wordArray.length)];
        }
        if (debug) System.out.println("takeSample:: source-words= "+sampleSize+", sample-words= "+wordArray.length);
        return wordSample;
    }
    /**
     * Ejecuta todos los metodos para limpiar el texto.
     *
     * @return Texto procesado.
     */
    public String clean() {
        m_text = cleanLineBreaks(m_text);
        m_text = cleanPunctuation(m_text);
        m_text = cleanCapitalization(m_text);
        m_text = cleanSpaces(m_text);
        return m_text;
    }

    /**
     * DEja el texto sólo mono-espacios. i.e. elimina mutlples espacios consecutivos
     * @param source Texto fuente.
     * @return Texto procesado.
     */
    private String cleanSpaces(String source) {
        return source.replace("  ", " ")
                .replace("   ", " ");
    }

    /**
     * Deja el texto sin saltos de lineas.
     *
     * @param source Texto fuente.
     * @return Texto procesado.
     */
    private String cleanLineBreaks(String source) {
        return source.replace("\n", "")
                .replace("\r", "");
    }

    /**
     * Deja el texto sin puntuacion.
     *
     * @param source Texto fuente.
     * @return Texto procesado.
     */
    private String cleanPunctuation(String source) {
        return source.replace(".", "")
                .replace(",", "")
                .replace(";", "")
                .replace(":", "");
    }

    /**
     * Deja el texto en minusculas.
     *
     * @param source Texto fuente.
     * @return Texto procesado.
     */
    private String cleanCapitalization(String source) {
        return source.toLowerCase();
    }

    /**
     * Lee archivo dejandolo en un String.
     *
     * @param inFile Nombre de archivo de texto fuente.
     * @return Archivo de texto como string.
     * @throws IOException En caso de error de lectura del archivo fuente.
     */
    private String readFileAsString(String inFile) throws IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new FileReader(inFile));
        char[] buf = new char[1024];
        int numRead;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
}
