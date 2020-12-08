#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

// Define os indices de leitura e escrita
#define READ 0
#define WRITE 1

// Define tamanho do vetor
#define VEC_SIZE 10

int main () {
    // No pipe 1, o pai escreve e o filho le
    // No pipe 2, o filho escreve e o pai le
    int pipe1[2], pipe2[2];
    // Identificador do processo
    pid_t pid;

    // Tenta criar pipe
    if ((pipe(pipe1) < 0) or (pipe(pipe2) < 0)) {
        cout << "Pipe nao criado." << endl;
        // Termina a aplicacao com codigo 1 (EXIT_FAILURE)
        _exit(EXIT_FAILURE);
    }

    // Tenta criar novo processo
    if ((pid = fork()) < 0) {
        cout << "Processo nao criado." << endl;
        // Termina a aplicacao com codigo 1 (EXIT_FAILURE)
        _exit(EXIT_FAILURE);
    }

    // Processo pai
    if (pid > 0) {
        cout << "VETOR:";
        // Cria vetor com valores aleatorios
        int vetor[VEC_SIZE];
        for (int i = 0; i < VEC_SIZE; i++) {
            vetor[i] = rand() % 100;
            cout << " " << vetor[i];
        }
        cout << endl;

        // Fecha o lado de leitura do pipe 1
        close(pipe1[READ]);        
        // Escreve vetor no pipe 1
        write(pipe1[WRITE], vetor, VEC_SIZE*sizeof(int));
        // Fecha o lado de escrita do pipe 1
        close(pipe1[WRITE]);

        // Encontra maior valor
        int maior = 0;
        for (int i = 0; i < VEC_SIZE; i++) {
            maior = (vetor[i] > maior) ? vetor[i] : maior;
        }
        
        // Fecha lado de escrita do pipe 2
        close(pipe2[WRITE]);

        int menor;

        read(pipe2[READ], &menor, sizeof(int));

        close(pipe2[READ]);

        cout << "Maior valor: " << maior << endl;
        cout << "Menor valor: " << menor << endl;

        // Aguarda o processo filho terminar
        wait(NULL);

        exit(EXIT_SUCCESS);
    }
    else {
        // Vetor para armazenar valores
        int vetor[VEC_SIZE];
        // Fecha o lado de escrita do pipe 1
        close(pipe1[WRITE]);
        // Le valores do pipe
        read(pipe1[READ], vetor, VEC_SIZE*sizeof(int));
        // Fecha leitura do pipe 1
        close(pipe1[READ]);

        // Encontra menor valor
        int menor = RAND_MAX;
        for (int i = 0; i < VEC_SIZE; i++) {
            menor = (vetor[i] < menor) ? vetor[i] : menor;
        }

        // Fecha leitura do pipe 2
        close(pipe2[READ]);
        // Escreve menor valor no pipe 2
        write(pipe2[WRITE], &menor, sizeof(int));
        // Fecha escrita do pipe 2
        close(pipe2[WRITE]);
        

        exit(EXIT_SUCCESS);
    }

    return 0;
}