#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

// Define os indices de leitura e escrita
#define READ 0
#define WRITE 1

// Define tamanho do vetor (so pode ser par)
#define VEC_SIZE 10

// Funcao de comparacao como vista em
// http://www.cplusplus.com/reference/cstdlib/qsort/
int compare (const void * a, const void * b) {
  return ( *(int*)a > *(int*)b );
}

// Funcao para imprimir vetor
void imprime_vetor(int *vetor, int tamanho) {
    cout << endl << "[";
    for (int i = 0; i < tamanho; i++) {
        if (i > 0) cout << ", ";
         cout << vetor[i];
    }
    cout << "]" << endl;
}

// Como a ordenacao nos processos filhos funcionam da mesma maneira,
// foi criada uma funcao para evitar repeticao de codigo
void ordenacao_processo_filho (int *pipe_leitura, int *pipe_escrita) {
    // Vetor para armazenar valores
        int vetor[VEC_SIZE/2];
        // Fecha o lado de escrita do pipe de leitura
        close(pipe_leitura[WRITE]);
        // Le valores do pipe de leitura
        read(pipe_leitura[READ], vetor, sizeof(int)*(VEC_SIZE/2));
        // Fecha leitura do pipe de leitura
        close(pipe_leitura[READ]);

        qsort(vetor, VEC_SIZE/2, sizeof(int), compare);
        // Fecha leitura do pipe de escrita
        close(pipe_escrita[READ]);
        // Escreve no pipe de escrita a metade do vetor ordenado 
        write(pipe_escrita[WRITE], &vetor, sizeof(int)*(VEC_SIZE/2));
        // Fecha escrita do pipe de escrita
        close(pipe_escrita[WRITE]);
}

int main () {
    // No pipe 00, o pai escreve e o filho 0 le
    // No pipe 01, o pai le e o filho 0 escreve
    int pipe00[2], pipe01[2];
    // No pipe 10, o pai escreve e o filho 1 le
    // No pipe 11, o pai le e o filho 1 escreve
    int pipe10[2], pipe11[2];
    // Identificador do processo
    pid_t pid0, pid1;

    // Tenta criar pipe
    if ((pipe(pipe00) < 0) or
        (pipe(pipe01) < 0) or
        (pipe(pipe10) < 0) or
        (pipe(pipe11) < 0)) {
        cout << "Falha na criação de um ou mais pipes." << endl;
        // Termina a aplicacao com codigo 1 (EXIT_FAILURE)
        _exit(EXIT_FAILURE);
    }

    // Tenta criar novo processo
    if ((pid0 = fork()) < 0) {
        cout << "Processo nao criado." << endl;
        // Termina a aplicacao com codigo 1 (EXIT_FAILURE)
        _exit(EXIT_FAILURE);
    }
    // Processo pai
    else if (pid0 > 0) {
        // Cria mais um processo filho
        if ((pid1 = fork()) < 0) {
            cout << "Processo nao criado." << endl;
            // Termina a aplicacao com codigo 1 (EXIT_FAILURE)
            _exit(EXIT_FAILURE);
        }
        else if (pid1 == 0) {
            // Chama ordenacao para o filho 1
            ordenacao_processo_filho(pipe10, pipe11);
            // Termina a aplicacao com codigo 0 (EXIT_SUCCESS)
            _exit(EXIT_SUCCESS);
        }
        else {
            // Processo pai
            cout << "VETOR:";
            // Cria vetor com valores aleatorios
            int vetor[VEC_SIZE];
            for (int i = 0; i < VEC_SIZE; i++) {
                vetor[i] = rand() % 100;
            }
            // Imprime o vetor
            imprime_vetor(vetor, VEC_SIZE);

            // Fecha o lado de leitura do pipe 00
            close(pipe00[READ]);        
            // Escreve vetor no pipe 00
            write(pipe00[WRITE], &vetor[0], sizeof(int)*(VEC_SIZE/2));
            // Fecha o lado de escrita do pipe 00
            close(pipe00[WRITE]);

            // Fecha o lado de leitura do pipe 10
            close(pipe10[READ]);        
            // Escreve vetor no pipe 10
            write(pipe10[WRITE], &vetor[VEC_SIZE/2], sizeof(int)*(VEC_SIZE/2));
            // Fecha o lado de escrita do pipe 10
            close(pipe10[WRITE]);

            // Vetores para guardar as metades ordenadas
            int metade0[VEC_SIZE/2], metade1[VEC_SIZE/2];
            
            // Fecha o lado de escrita do pipe 01
            close(pipe01[WRITE]);
            // Le a metade do vetor
            read(pipe01[READ], metade0, sizeof(int)*(VEC_SIZE/2));
            // Fecha o lado de leitura do pipe 01
            close(pipe01[READ]);

            // Fecha o lado de escrita do pipe 01
            close(pipe11[WRITE]);
            // Le a metade do vetor
            read(pipe11[READ], metade1, sizeof(int)*(VEC_SIZE/2));
            // Fecha o lado de leitura do pipe 01
            close(pipe11[READ]);

            // Iteradores para cada metade
            int iter0 = 0, iter1 = 0;

            cout << "VETOR ORDENADO:";
            // Merge dos vetores
            for (int i = 0; i < VEC_SIZE; i++) {
                // Funcao semelhante ao merge sort
                if ((iter0 < VEC_SIZE/2) and ((iter1 >= VEC_SIZE/2) or
                        (metade0[iter0] < metade1[iter1]))) {
                    vetor[i] = metade0[iter0++];
                }
                else {
                    vetor[i] = metade1[iter1++];
                }
            }
            // Imprime vetor ordenado
            imprime_vetor(vetor, VEC_SIZE);

            // Aguarda o processo filho terminar
            wait(NULL);
            // Termina a aplicacao com codigo 0 (EXIT_SUCCESS)
            exit(EXIT_SUCCESS);
        }
    }
    else {
        // Chama ordenacao para o filho 1
        ordenacao_processo_filho(pipe00, pipe01);
        // Termina a aplicacao com codigo 0 (EXIT_SUCCESS)
        exit(EXIT_SUCCESS);
    }

    return 0;
}