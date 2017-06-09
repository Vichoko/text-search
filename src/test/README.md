# CC4102 Diseño y Analisis de Algoritmos - Tarea 2
## Pruebas y experimentos


### AFD 
Para probar el correcto funcionamiento del simulador de Automata Finito Determinista
se creó la clase ```test.AFDTest```.

Al ejecutar la clase directamente (su metodo main), se ejecuta una bateria de tests
que crean y prueban con varias entradas, diversos automatas conocidos.

### Busqueda en texto con AFD
Las pruebas para ésto estan en la clase ```AutomatonTextSearchTest```.

Este posee dos metodos (cada uno ejecuta una prueba distinta):
* AutomatonTextSearchTest.AcurracyTest
* AutomatonTextSearchTest.CompleteTest

#### AcurracyTest
Toma un archivo de texto en ingles del data-set, saca una muestra de patrones aleatorios y comienza
a hacer busquedas de aquellos patrones en el texto, comparando la eficacia con el método
de fuerza bruta.

Esto para comprobar que la busqueda con automáta detecta correctamente todas las ocurrencias
del patron, en el texto.

##### Uso
Ejecutar método ```AutomatonTextSearchTest.AcurracyTest```.
Más explicitamente, en el método Main, de-comentar la priemr alinea y comentar la segunda.

#### CompleteTest
Contiene todas las pruebas con las que se obtuvieron los resultados expuestos en el informe.
Toma muestras de patrones para cada uno de los archivos, hacendolo sobre todos los archivos.

Para los archivos más grandes, quizas se acabe el espacio en el heap para almacenarlo, para ello
es necesario expandirlo manualmente, a lo más 4GB.

Finalmenta deja los resultados, de todos los textos, en el archivo ```results.automaton_test_results.txt```.

##### Uso
Ejecutar método ```AutomatonTextSearchTest.CompleteTest```.
Más explicitamente, ejecutar el método Main.

Observacion: Verificar que se esté ejecutando el método CompleteTest, en el método Main.

