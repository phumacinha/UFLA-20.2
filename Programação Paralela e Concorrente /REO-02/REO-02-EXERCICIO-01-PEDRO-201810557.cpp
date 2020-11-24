#include <unistd.h>
#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>

using namespace std;

int main () {
    // Criacao do file descriptor para o pipe
    int fd[2];

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
        cout << "PROCESSO PAI" << endl;
        // Caminho do arquivo
        char caminho[] = "lorem.txt";
        // Fecha a leitura
        close(fd[0]);
        // Escreve no pipe
        write(fd[1], &caminho, 9);
        // Fecha a escrita
        close(fd[1]);
        // Aguarda o processo filho terminar
        wait(NULL);
    }

    // Processo filho
    if (pid == 0) {
        cout << "PROCESSO FILHO" << endl;
        // Inteiro para armazenar o tamanho da mensagem
        int n;
        // Vetor de caracteres que armazenarah mensagem do pipe
        char caminho[9];
        // Fecha a escrita
        close(fd[1]);
        // Le a mensagem do pipe
        n = read(fd[0], caminho, 9);
        // Escreve na saida padrao
        cout << "Executando o comando wc no arquivo para contar palavras e caracteres:" << endl;
        // Executa wc
        execl("/bin/wc", "wc", "-wm", &caminho, NULL);
        // Fecha leitura
        close(fd[0]);
    }

    return 0;
}