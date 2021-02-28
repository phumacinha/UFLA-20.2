#include <iostream>
#include <omp.h>
#include <sstream>
#include <cmath>
using namespace std;

long num_threads = 8;
long long num_passos = pow(10, 2);
double passo;

int main() {
    int i;
    double x, pi, soma = 0.0, time_inicio, time_fim;
    time_inicio = omp_get_wtime();
    passo = 1.0 / (double)num_passos;

    #pragma omp parallel for private(i, x) reduction(+:soma) num_threads(num_threads)
    for (i = 0; i < num_passos; i++) {
        x = (i + 0.5) * passo;
        soma = soma + 4.0 / (1.0 + x * x);
    }

    pi = soma * passo;
    time_fim = omp_get_wtime();
    cout << time_fim - time_inicio << ',';
    // cout << "Tempo:" << time_fim - time_inicio << endl;
    // cout << "O valor de PI é: " << pi << endl;
    return 0;
}
