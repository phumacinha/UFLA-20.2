#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
int main(int argc, char *argv[]){
   int pid, wstatus;
   pid = fork();
   if (pid <0) {
      fprintf(stderr, "Erro ao criar processo\n");
   }
   else if (pid==0){
           printf("Processo filho\n");
           execlp("/usr/bin/firefox", "firefox", NULL);
        }
        else if (pid > 0) {
                wait(&wstatus);
                printf("Sou o processo Pai\n");
                execlp("/bin/ls", "ls", NULL);
             }
    return 0;
}
