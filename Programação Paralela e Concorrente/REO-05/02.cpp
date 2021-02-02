/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 2
 */

#include <pthread.h>
#include <iostream>

using namespace std;

// Define estrutura para enviar como argumento à função de subtração
typedef struct {
    int tid;
    int numA, numB;
} data;

int *vetorA, *vetorB, *vetorC;  // Vetores para calculo
int i;  // Inteiro usado em iterações

// Função a ser executada pelas threads
void* subtracao(void * num)
{
    data * numeros = (data *) num;
    vetorC[numeros->tid] = numeros->numA - numeros->numB;

    free(numeros);

    pthread_exit(NULL);
}

int main (int argc, char *argv[])
{
    // Variavel que armazena a quantidade de elementos dos vetores
    int n;
    cout << "Digite a dimensão do vetor: ";
    cin >> n;

    pthread_t threads[n];  // Vetor de threads
    // Aloca espaço para vetores
    vetorA = (int*) malloc( n * sizeof(int) );
    vetorB = (int*) malloc( n * sizeof(int) );
    vetorC = (int*) malloc( n * sizeof(int) );

    // Preenche vetor A
    cout << "Preencha o vetor A com " << n << " elemento(s): ";
    for (i = 0; i < n; i++) {
        cin >> vetorA[i];
    }

    // Preeche vetor B
    cout << "Preencha o vetor B com " << n << " elemento(s): ";
    for (i = 0; i < n; i++) {
        cin >> vetorB[i];
    }

    // Cria threads
    for (i = 0; i < n; i++) {
        data *numeros;
        numeros = (data*) malloc(sizeof (data));
        numeros->tid = i;
        numeros->numA = vetorA[i];
        numeros->numB = vetorB[i];

        pthread_create( &threads[i], NULL, subtracao, (void*) numeros);
    }

    // Aguarda filhas
    for (i = 0; i < n; i++) {
        pthread_join(threads[i], NULL);
    }

    // Imprime o vetor resultante
    cout << "Vetor resultante da subtração: ";
    for (i = 0; i < n; i++) {
        cout << vetorC[i] << " ";
    }
    cout << endl;

    // Libera memória
    free(vetorA);
    free(vetorB);
    free(vetorC);

    return 0;
}