import matplotlib.pyplot as plt
import numpy as np
import math

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

def join_tables(prevTable, currTable):
    if len(prevTable) == 0:
        return currTable
    else:
        dic = {}
        n_list = [] ## lookup available n's
        for row in prevTable[1:]:
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
        for row in currTable[1:]:
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

        ret_table = [currTable[0]] # header
        for n in dic:
            n_list.append(n)
        n_list = sorted(n_list)
        for n in n_list:
            for m in dic[n]:
                prev_list = dic[n][m]
                ret_table.append([n, m, prev_list[0],prev_list[1], prev_list[2]])
        return ret_table

def mixer(listoffilenames):
    aux_table = []
    for fileName in listoffilenames:
        aux_table = join_tables(aux_table, parseToTable(fileName))
    return aux_table

def averager(table):
    new_table = [['n [chars]', 'm [chars]', 'avg_construction_time [ms]', 'avg_search_time [ms]']]
    for rowIndex in range(len(table)):
        if rowIndex == 0:  # header
            continue
        row = table[rowIndex]
        avg1 = row[2] / row[4]
        avg2 = row[3] / row[4]
        new_table.append([row[0], row[1], avg1, avg2])

    print("  done")
    return new_table

# parseToTable("C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - hp,0.txt")
resultFiles = ["C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - hp,0.txt",
                "C:/Users/vicen/Documents/GitHub/daa_t2/results/automaton_test_results - toshiba, 0.txt"
               ]
averaged_table = averager(mixer(resultFiles))

# Hip 1
# Plot (m, Tiempo de construccion)
def plot(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('Cantidad de caracteres del patron vs Tiempo de construccion', fontsize=18)
    plt.xlabel('Cantidad de caracteres del patron')
    plt.ylabel('Tiempo de construccion [ms]')
    plt.show()

listoftuples = []
for row in averaged_table[1:]: # (m, Tiempo de construccion)
    listoftuples.append((row[1], row[2]))

listoftuples = sorted(listoftuples, key=lambda x: x[0]) # ordenar por m
x = []
y = []
for tuple in listoftuples:
    x.append(tuple[0])
    y.append(tuple[1])

m = x[0]
avg = 0
cnt = 0
new_x = []
new_y = []
for i in range(len(x)): # tomar promedio para cada m
    if x[i] == m:
        avg += y[i]
        cnt += 1
    else:
        if cnt != 0:
            avg = avg / cnt
        else:
            avg = 0
        new_x.append(m)
        new_y.append(avg)
        m = x[i]
        avg = 0
        cnt = 0
        i -= 1
if cnt != 0:
    avg = avg / cnt
else:
    avg = 0
new_x.append(m)
new_y.append(avg)
plot(np.array(x), np.array(y))
plot(np.array(new_x), np.array(new_y))


# Hip 2
# Plot (n, Tiempo de ejecucion)
def plot(x, y):
    plt.figure(figsize=(6 * 1.2, 4 * 1.2))
    plt.rcParams.update({'font.size': 12})
    # plt.plot(n_values, times_ms, marker='x', label='Tiempos experimentales')
    plt.plot(x, y, marker='x', label='Mediciones experimentales')
    plt.legend()
    plt.title('Cantidad de caracteres del texto vs Tiempo de busqueda', fontsize=18)
    plt.xlabel('Cantidad de caracteres del texto')
    plt.ylabel('Tiempo de busqueda [ms]')
    plt.show()



# HDR: 'n [chars]', 'm [chars]', 'avg_construction_time [ms]', 'avg_search_time [ms]'

x = []
y = []
for row in averaged_table[1:]:  # filtrar por n y tiempo de busqueda
    x.append(row[0])
    y.append(row[3])

n = x[0]
avg = 0
cnt = 0
new_x = []
new_y = []
for i in range(len(x)):  # tomar promedio de tiempo de busqueda para un mismo n
    if x[i] == n:
        avg += y[i]
        cnt += 1
    else:
        avg = avg / cnt
        new_x.append(n)
        new_y.append(avg)
        n = x[i]
        avg = 0
        cnt = 0
        i -= 1
avg = avg / cnt
new_x.append(n)
new_y.append(avg)
plot(np.array(x), np.array(y))
plot(np.array(new_x), np.array(new_y))


