import matplotlib.pyplot as plt
import numpy as np
import math

resultFiles = ["C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - hp, 0.txt",
               "C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - toshiba, 0.txt",
               "C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - toshiba, 1.txt",
               ]
# Toma un nombre de archivo que tiene los resultados y lo formatea en una tabla.
def parseToTable(fileName):
    with open(fileName) as textFile:
        lines = [line.rstrip('\n') for line in textFile]
        table = [['n [chars]', 'm [chars]', 'construction_time [ms]', 'search_time [ms]', 'count']]
        for line in lines:
            if line.split(": ")[0] is 'n':
                n = line.split(": ")[1]
            if line.split(": ")[0] is 'm':
                row = [int(n)]
                columns = line.split(", ")
                for column in columns:
                    val = column.split(": ")[1]
                    row.append(int(val))
                table.append(row)
        print('   done')
        return table

# Toma dos tablas y les hace join por su columna m, sumando sobre los valores correspondientes.
def join_tables(prevTable, currTable):
    if len(prevTable) == 0: # Caso base
        return currTable
    else: # Caso iterativo
        dic = {} # Dejo contenidos de ambas tablas en diccionario
        n_list = []  ## Registrar n's que aparecen
        for row in prevTable[1:]:
            n = row[0]
            m = row[1]
            c_time = row[2]
            s_time = row[3]
            count = row[4]
            if n in dic:
                if m in dic[n]: # Si ya existia par (n,m) en diccionario, sumo sus valores.
                    prev_list = dic[n][m]
                    dic[n][m] = [c_time + prev_list[0],
                                 s_time + prev_list[1],
                                 count + prev_list[2]]
                else: # Si existia n, pero no m, agrego sus valores por primera vez.
                    dic[n][m] = [c_time, s_time, count]
            else: # Si no existia n ni m, agrego n y m con sus valores.
                dic[n] = {}
                dic[n][m] = [c_time, s_time, count]
        for row in currTable[1:]: # Lo mismo para la otra tabla
            n = row[0]
            m = row[1]
            c_time = row[2]
            s_time = row[3]
            count = row[4]
            if n in dic:
                if m in dic[n]:
                    prev_list = dic[n][m]
                    dic[n][m] = [c_time + prev_list[0],
                                 s_time + prev_list[1],
                                 count + prev_list[2]]
                else:
                    dic[n][m] = [c_time, s_time, count]
            else:
                dic[n] = {}
                dic[n][m] = [c_time, s_time, count]

        ret_table = [currTable[0]]  # header
        for n in dic: # ordeno valores de n,  para obtener filas ordenadas por n
            n_list.append(n)
        n_list = sorted(n_list) # ordenar por n
        for n in n_list:
            for m in dic[n]:
                prev_list = dic[n][m]
                ret_table.append([n, m, prev_list[0], prev_list[1], prev_list[2]]) # exporto dic a tabla
        return ret_table # Esquema: 'n [chars]', 'm [chars]', 'construction_time [ms]', 'search_time [ms]', 'count'

# Toma lista de archivos de texto y les hace join por su (n,m) sumando sus valores.
def mixer(listoffilenames):
    aux_table = []
    for fileName in listoffilenames:
        aux_table = join_tables(aux_table, parseToTable(fileName))
    return aux_table

# Para cada n, toma el promedio de T° de construccion y busqueda, eliminando columna de count
def averager(table):
    new_table = [['n [chars]', 'm [chars]', 'avg_construction_time [ms]', 'avg_search_time [ms]']]
    for rowIndex in range(len(table)):
        if rowIndex == 0:  # header
            continue
        row = table[rowIndex]
        if row[4] < 10: # filtro para descartar promedios con menos de 10 muestras
            continue

        avg1 = row[2] / row[4]
        avg2 = row[3] / row[4]
        new_table.append([row[0], row[1], avg1, avg2])

    print("  done")
    return new_table

averaged_table = averager(mixer(resultFiles))

# Hip 1
# Plot (m, Tiempo de construccion)
def plot(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('N° Caracteres del patrón vs T° Construccion', fontsize=18)
    plt.xlabel('N° Caracteres del patron')
    plt.ylabel('Tiempo de construccion [ms]')
    plt.show()

def plot_avg(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('N° Caracteres del patrón vs T° Construcción (promedio)', fontsize=18)
    plt.xlabel('N° Caracteres del patrón')
    plt.ylabel('Tiempo de construccion [ms]')
    plt.show()


listoftuples = []
for row in averaged_table[1:]:  # Aislar (m, Tiempo de construccion)
    listoftuples.append((row[1], row[2]))

listoftuples = sorted(listoftuples, key=lambda x: x[0])  # ordenar por m
x = []
y = []
for tuple in listoftuples: # los separo en 2 vectores
    x.append(tuple[0])
    y.append(tuple[1])

m = x[0]
avg = 0
cnt = 0
new_x = []
new_y = []
i = 0
while i < len(x):  # tomar promedio para cada m, quedan m unicos (antes habian m repetidos)
    m = x[i]
    sum = 0
    cnt = 0
    while x[i] == m:
        sum += y[i]
        cnt += 1
        i += 1
        if i >= len(x):
            break
    new_x.append(m)
    new_y.append((sum*0.1)/cnt)
    i += 1
plot(np.array(x), np.array(y)) # plot sin promedio
plot_avg(np.array(new_x), np.array(new_y)) # plot de promedio


# Hip 2
# Plot (n, Tiempo de ejecucion)
def plot(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('N° Caracteres del texto vs T° Busqueda', fontsize=18)
    plt.xlabel('N° Caracteres del texto')
    plt.ylabel('Tiempo de busqueda [ms]')
    plt.show()

def plot_avg(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('N° Caracteres del texto vs T° Busqueda (promedio)', fontsize=18)
    plt.xlabel('N° Caracteres del texto')
    plt.ylabel('Tiempo de busqueda [ms]')
    plt.show()


# HDR: 'n [chars]', 'm [chars]', 'avg_construction_time [ms]', 'avg_search_time [ms]'

x = []
y = []
for row in averaged_table[1:]:  # filtrar por n y tiempo de busqueda
    x.append(row[0])
    y.append(row[3])

n = x[0]
new_x = []
new_y = []
i = 0
while i < len(x):  # tomar promedio para cada m
    n = x[i]
    sum = 0
    cnt = 0
    while x[i] == n:
        sum += y[i]
        cnt += 1
        i += 1
        if i >= len(x):
            break
    new_x.append(n)
    new_y.append((sum*0.1)/cnt)
    i += 1
plot(np.array(x), np.array(y))
plot_avg(np.array(new_x), np.array(new_y))
