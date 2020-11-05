#include <iostream>  
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
using namespace std;  
int main(  ){  
	pid_t pid;
	//Cria processo filho
	pid = fork( );   
	if (pid <0){ //Ocorreu erro
		cerr << "Fork falhou" << endl;
		return 1;
	} 
	else if (pid == 0){
		     execlp("/bin/ls", "ls", NULL);
		 }
		 else { // Processo pai
			 // Pai espera filho para completar execução
			 wait(NULL);
			 cout << "Filho completou" << endl;
		 }
	
    return 0;
}
