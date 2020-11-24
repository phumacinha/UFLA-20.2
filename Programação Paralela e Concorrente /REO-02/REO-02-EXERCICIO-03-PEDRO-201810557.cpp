#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

// Define os indices de leitura e escrita
#define READ 0
#define WRITE 1

// Define a quantidade de consumidores e de produtos produzidos
#define QTD_CONSUMIDORES 5
#define QTD_PRODUTOS 5

// Criacao do file descriptor para o pipe
int fd[2];

int main () {
    // Identificador do processo
    pid_t pid;

    // Tenta criar pipe
    if (pipe(fd) < 0) {
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
        // Total de itens produzidos
        int totalProduzido = 0;
        // Fecha o lado de leitura do pipe
        close(fd[READ]);

        // Cria novos produtos
        while (totalProduzido < QTD_PRODUTOS) {

            // Produz item
            int produto = rand() % 100;
            // Insere item no pipe
            write(fd[WRITE], &produto, sizeof(int));

            cout << "Pai produziu item " << produto << endl;
            totalProduzido++;
        }

        // Fecha o lado de escrita
        close(fd[WRITE]);

        // Aguarda o processo filho terminar
        wait(NULL);

        exit(EXIT_SUCCESS);
    }
    else {
        // Total de itens produzidos
        int totalConsumido = 0;
        // Fecha o lado de escrita
        close(fd[WRITE]);

        while (totalConsumido < QTD_PRODUTOS) {

            int produto;
            // Le item do pipe
            int resp = read(fd[READ], &produto, sizeof(int));

            if (resp > 0) {
                cout << "Filho " << getpid() << " consumiu item " << produto << endl;
                totalConsumido++;
            }
        }
        // Fecha lado de leitura
        close(fd[READ]);
        
        exit(EXIT_SUCCESS);
    }

    return 0;
}