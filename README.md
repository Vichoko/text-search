# CC4102 Diseño y Analisis de Algoritmos - Tarea 2
## Busqueda de patrón en texto


### 1. Pre-procesamiento del texto
Se utilizaran textos de lenguaje natural en ingles. 
Para lo cual se recibe el archivo de texto de entrada, 
se hacen las siguientes tareas:
1. Eliminar saltos de linea.
2. Eliminar puntuacion.
3. Llevar todo a minusculas.
4. Eliminar multiples espacios consecutivos, dejando sólo mono-espacios

Dejando el resultado en un String de salida.
##### Uso
1. Instanciar objeto ```Preprocess``` con ruta de archivo fuente.
2. Llamar método ```Preprocess.clean()```
##### Ejemplo
        Preprocess p = new Preprocess("source/2^21.txt");
        String result = p.clean();
### 1.1 Muestreo de N/10 palabras
Utilizando el método estático ```String[] Preprocess.takeSample(String source)``` se obtiene 
una muestra de N/10 palabras aleatorias, con distribucion uniforme, del string fuente.

**Observación:** Se debe tomar muestreo después de hacer la limpieza, i.e. correr el método clean.

**Observación 2:** Se impuso un limite heuristico al tamaño de la muestra, tal que ejecutar la busqueda sobre todos 
ellos no tarde más de 5 horas.

##### Uso
1. Llamar al método estático ```Preprocess.takeSample(String cleanText)```, recibir el arreglo de palabras como retorno.

##### Ejemplo
      Preprocess p = new Preprocess("source/2^21.txt");
      String result = p.clean();
      String[] wordSample = Preprocess.takeSample(result, true); // 'true' para leer mensajes de depuración
### 2. Algoritmos de busqueda de patrones
#### 2.1 Arreglo de sufijos

#### 2.2 Algoritmo con automata
Implementado en clase ```algorithm.AutomatonTextSearch```.

Se puede ejecutar la clase ```test.AutomatonTextSearchTest``` para visualizar el funcionamiento de éste, y comparar el 
la correctitud del algoritmo en comparación con el método de fuerza bruta; al igual que para ejecutar los experimentos 
propuestos en el informe.

Ejecutar ```AutomatonTextSearchTest.acurracyTest``` para verificar que la busqueda encuentra todas las ocurrencias de diversos
patrones.

Ejecutar ```AutomatonTextSearchTest.completeTest``` para efectuar experimentos propuestos en el informe.

**Observación**: El algoritmo supone un alfabeto ASCII, tanto para el patron como el texto fuente.

##### Uso
1. Instanciar clase ```AutomatonTextSearch(String fullText, String pattern)```; la cual construye el AFD
para ```pattern```, basandose en el alfabeto descubierto de ```fulltext```.
2. Llamar metodo del objeto ```Int AutomatonTextSearch.run()```; el cual retorna la cantidad de ocurrencias de 
```pattern``` en ```fulltext```.

##### Ejemplo
        Preprocess p = new Preprocess("source/2^21.txt");
        String result = p.clean();
        String[] patterns = Preprocess.takeSample(result, true);
        AutomatonTextSearch ats = new AutomatonTextSearch(result, patterns[0], true); // true for verbose
        System.out.println("AFD found pattern: " + ats.run() + " times.");


