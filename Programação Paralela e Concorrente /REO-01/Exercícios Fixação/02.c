#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main(void)
{
    int i, soma = 0;
    pid_t pid;

    if ((pid = fork()) < 0)
    {
        perror("fork");
        exit(1);
    }
    if (pid == 0)
    {
        //O código aqui dentro será executado no processo filho
        printf("pid do Filho: %d\n", getpid());
        for (int i=1; i <= 100; i++) {
            if (i%2 != 0) soma += i;
        }
    }
    else
    {
        //O código neste trecho será executado no processo pai
        printf("pid do Pai: %d\n", getpid());
        for (int i=1; i <= 100; i++) {
            if (i%2 == 0) soma += i;
        }
    }


    printf("pid = %d, soma = %d\n\n", getpid(), soma);
    
    exit(0);
}


