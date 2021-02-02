/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 4
 */
#include <omp.h>
#include <iostream>

using namespace std;

int main (int argc, char *argv[]) {
    int nthreads, i, tid;
    float total = 0.0;  // A inicialização do valor da variável deve ser feita antes da paralelização

    #pragma omp parallel num_threads(4) 
    {
        tid = omp_get_thread_num();
        if (tid == 0) {
            nthreads = omp_get_num_threads();
            cout << "Número de threads = " << nthreads <<endl;
        }
        #pragma omp barrier

        #pragma omp for schedule(dynamic,10) private(i) reduction(+:total)
        for (i=0; i<1000; i++)
            total += i;
    }
    
    cout << "Total = " <<total << endl;

    return 0;
}