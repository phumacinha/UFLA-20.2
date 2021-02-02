/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 1
 */

#include <iostream>
#include <omp.h>
using namespace std;

int main() {
    int n, i, j, k;
    
    // Define dimensões das matrizes
    cout << "Defina a dimensão das matrizes: ";
    cin >> n;

    // Matrizes
    int a[n][n], b[n][n], c[n][n];

    // Preenche matriz a
    cout << "Preencha a matriz A(" << n << "x" << n << "): " << endl;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            cin >> a[i][j];
        }
    }

    // Preenche matriz b
    cout << "Preencha a matriz B(" << n << "x" << n << "): " << endl;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            cin >> b[i][j];
        }
    }


    // Executa for paralelo para multiplicar as matrizes
    #pragma omp parallel shared(a,b,c) private(i,j,k) 
    {
        #pragma omp for schedule(static)
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++){
                c[i][j] = 0.0;
                for (k = 0; k < n; k++){
                    c[i][j] += a[i][k]*b[k][j];
                }
            }
        }

        #pragma omp barrier
    }

    // Imprime matriz c com resultado da multiplicação

    cout << "Resultado da multiplicação das matrizes: " << endl;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            cout << c[i][j] << " ";
        }
        cout << endl;
    }

    return 0;
}