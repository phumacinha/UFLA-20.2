/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 3-a (Pthread)
 */

#include <iostream>
#include <omp.h>

#define n 50

using namespace std;

pthread_mutex_t Mutex;  // Semáforo
double area;

double f (int i){
    double x;
    x = (double) i/(double) n;
    return 4.0/(1.0 + x*x);
}

void *task (void *param)
{
    long i = (long) param;

    // Área crítica
    pthread_mutex_lock(&Mutex);
    area += 4.0*f(2*i-1) + 2*f(2*i);
    pthread_mutex_unlock(&Mutex);

    pthread_exit(NULL);
}

int main(int argc, char *argv[]){
    pthread_t threads[n/2];  // Vetor de threads
    int i;

    area = f(0) - f(n);

    // Cria threads
    for (i = 1; i <= n/2; i++) {
        pthread_create( &threads[i-1], NULL, task, (void*) i);
    }

    // Aguarda filhas
    for (i = 1; i < n/2; i++) {
        pthread_join(threads[i], NULL);
    }

    area /= (3.0 * n);

    cout << "Aproximação de pi: " << area << endl;
   
    return 0;
}