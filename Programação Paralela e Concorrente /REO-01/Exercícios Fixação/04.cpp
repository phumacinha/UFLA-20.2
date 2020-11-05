#include <iostream>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

using namespace std;

int main(){
    pid_t filho14, filho16, neto26, neto30;
    sleep(14);
    filho14 = fork();
    if (filho14 == 0) {
            // cria processo filho aos 14
            cout << "Filho 1 aos 14 anos" << endl;
            sleep(12);
            neto26 = fork();
            if (neto26 == 0) {
                cout << "Neto 1 aos 26 anos" << endl;
                sleep(12);
                cout << "Morte do neto 1 aos 12 anos" << endl;
                exit(0);
            }
            else { // processo filho aos 14
                sleep(18);
                cout << "Morte do filho 1 aos 30 anos" << endl;
                exit(0);
            }
    }
    else { // processo pai
        // cria processo filho aos 16
        sleep(2);
        filho16 = fork();
        if (filho16 == 0) {
            cout << "Filho 2 aos 16 anos" << endl;
            sleep(14);
            neto30 = fork();
            if (neto30 == 0) {
                cout << "Neto 2 aos 30 anos" << endl;
                sleep(14);
                cout << "Morte do neto 2 aos 14 anos" << endl;
                exit(0);
            }
            else { // processo filho aos 16
                sleep(14);
                cout << "Morte do filho 2" << endl;
                exit(0);
            }
        }
        else { // processo pai
            sleep(44);
            cout << "Morte do pai aos 60 anos" << endl;
            exit(0);
        }
        
    }
    return 0;
}
