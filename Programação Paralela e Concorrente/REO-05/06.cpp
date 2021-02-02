/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 6
 */

#include <pthread.h>
#include <iostream>

using namespace std;

pthread_mutex_t Mutex;  // Semáforo
int *vector;  // Vetores para calculo
int i;  // Inteiro usado em iterações
int n;  // Variavel que armazena a quantidade de elementos do vetor

// Função a ser executada pelas threads
void *swap (void *param)
{
    long i = (long) param;
    int aux;

    // Área crítica
    pthread_mutex_lock(&Mutex);
    for (int j = 0; j < n-1-i; j++) {
        if (vector[j] > vector[j+1]) {
                aux = vector[j];
                vector[j] = vector[j+1];
                vector[j+1] = aux;
        }
    }
    pthread_mutex_unlock(&Mutex);

    pthread_exit(NULL);
}

int main (int argc, char *argv[])
{
    // Tamanho do vetor
    cout << "Digite o tamanho do vetor: ";
    cin >> n;

    pthread_t threads[n];  // Vetor de threads
    // Aloca espaço para vetores
    vector = (int*) malloc( n * sizeof(int) );

    // Preenche vetor
    cout << "Preencha o vetor com " << n << " elemento(s): ";
    for (i = 0; i < n; i++) {
        cin >> vector[i];
    }

    // Cria threads
    for (i = 0; i < n; i++) {
        pthread_create( &threads[i], NULL, swap, (void*) i);
    }

    // Aguarda filhas
    for (i = 0; i < n; i++) {
        pthread_join(threads[i], NULL);
    }

    // Imprime o vetor resultante
    cout << "Vetor ordenado: ";
    for (i = 0; i < n; i++) {
        cout << vector[i] << " ";
    }
    cout << endl;

    // Libera memória
    free(vector);

    return 0;
}