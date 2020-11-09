/**
 * REO 01 - Programacao Paralela e Concorrente 
 * Pedro Antonio de Souza (201810557)
 */

#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h>
#include <ctime>
#include <iostream>

using namespace std;

// Funcao para converter array em string
string arrayParaString(int arr[]) {
    string retorno = "";
    for (int i = 0; i < 10; i++) {
        if (i > 0) retorno += " ";
        retorno += to_string(arr[i]);
    }

    return retorno;
}

// Implementacao selection sort
void selectionSort(int arr[]) {
    int i, j, min, aux;

    for (i = 0; i < 9; i++) {
        min = i;

        for (j = (i+1); j < 10; j++) {
            if (arr[j] < arr[min]) min = j;
        }

        if (i != min) {
            aux = arr[i];
            arr[i] = arr[min];
            arr[min] = aux;
        }
    }
}

// Implementacao quick sort
void quickSort(int arr[], int ini, int fim) {
    int i, j, pivo, aux;
    
    i = ini;
    j = fim - 1;
    pivo = arr[(ini + fim) / 2];

    while (i <= j) {
        while (arr[i] < pivo && i < fim) i++;
        while (arr[j] > pivo && j > ini) j--;

        if (i <= j) {
            aux = arr[i];
            arr[i] = arr[j];
            arr[j] = aux;
            i++;
            j--;
        }
    }

    if (j > ini) quickSort(arr, ini, j+1);
    if (i < fim) quickSort(arr, i, fim);
}

int main () {
    // Lista que armazenara os numeros aleatorios
    int lista[] = {8, 5, 2, 6, 9, 3, 1, 4, 0, 7};
    pid_t filho;
    clock_t inicio, fim;

    cout << "Lista antes da ordenacao: " << arrayParaString(lista) << endl << endl;

    // Cria processo filho
    filho = fork();

    if (filho < 0) {
        // Erro na criacao do processo filho
        cerr << "Erro na criação do processo." << endl;
        return 1;
    }
    else if (filho == 0) {
        // Processo filho
        inicio = clock();
        quickSort(lista, 0, 10);
        fim = clock();
        // Tempo gasto em segundos
        double diferenca = double(fim - inicio)/CLOCKS_PER_SEC;

        cout << "Valores ordenados no processo filho:" << arrayParaString(lista) << endl;
        cout << "Tempo gasto no processo filho: " << diferenca << " s" << endl << endl;
    }
    else {
        // Processo pai 
        wait(NULL);

        inicio = clock();
        selectionSort(lista);
        fim = clock();
        // Tempo gasto em segundos
        double diferenca = double(fim - inicio)/CLOCKS_PER_SEC;

        cout << "Valores ordenados no processo pai:" << arrayParaString(lista) << endl;
        cout << "Tempo gasto no processo pai: " << diferenca << " s" << endl << endl;
    }

    return 0;
}