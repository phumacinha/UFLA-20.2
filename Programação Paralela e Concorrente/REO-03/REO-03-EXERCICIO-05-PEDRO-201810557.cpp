/**
 * Pedro Antonio de Souza (201810557)
 * REO 3 - Exercicio 5
 */

#include <unistd.h>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>
#include <cmath>

using namespace std;

// Define constante de tamanho de vetor e quantidade de processos
#define N 8

// Vetor global de entrada
int A[N] = {1, 5, 8, 21, 2, 3, 13, 34};

// Vetor global de saida
int L[N];

int main () {
    for (int i = 0; i < N; i++) {
        // Variaveis auxiliares na busca binaria
        int x, low, high, index;

        // Cria novo processo e executa merge no modelo CREW PRAM
        if (fork() == 0) {
            cout << "Processo " << getpid() << " responsavel pela insercao ";

            // Calcula os limites para busca binaria.
            // Aqui, houve alteracao do comparador de <= para < pois
            // os indices se inciam em 0 e nao em 1.
            // Tambem, houve adaptacao no calculo dos limites pelo mesmo
            // motivo.
            if (i < N/2) {
                // Limites da segunda metade
                low = N/2;
                high = N - 1;
            }
            else {
                // Limites da primeira metade
                low = 0;
                high = N/2 -1;
            }

            // Elemento da lista 
            x = A[i];

            // Imprime elemento
            cout << "do elemento " << x << " ";

            // Realiza busca binaria
            do {
                index = ceil((low + high)/2.0);

                if (x < A[index]) high = index - 1;
                else  low = index + 1;

            } while (low <= high);

            // Aqui tambem houve adaptacao do algoritmo em decorrencia
            // dos limites dos indices.
            int posicao = high + (i + 1) - N/2;

            // Coloca valor na posicao correta de L
            L[posicao] = x;

            // Imprime posicao de insercao
            cout << "na posicao " << posicao << endl;


            // Sai do processo com sucesso
            exit(EXIT_SUCCESS);
        }
    }
    
    // Processo espera os outros terminarem
    for (int i = 0; i < N; i++){
        wait(NULL);
    }

    return 0;
}
