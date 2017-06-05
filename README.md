# CC4102 Diseño y Analisis de Algoritmos - Tarea 2
## Busqueda de patrón en texto


### 1. Pre-procesamiento del texto
Se utilizaran textos de lenguaje natural en ingles. 
Para lo cual se recibe el archivo de texto de entrada, 
se hacen las siguientes tareas:
1. Eliminar saltos de linea.
2. Eliminar puntuacion.
3. Llevar todo a minusculas.

Dejando el resultado en un String de salida.
##### Uso
1. Instanciar objeto ```Preprocess``` con ruta de archivo fuente.
2. Llamar método ```Preprocess.clean()```
##### Ejemplo
        Preprocess p = new Preprocess("source/wiki.txt");
        String result = p.clean();
        System.out.print("  done [debug for inspection].");

### 2. Algoritmos de busqueda de patrones
#### 2.1 Arreglo de sufijos

#### 2.2 Algoritmo con automata

