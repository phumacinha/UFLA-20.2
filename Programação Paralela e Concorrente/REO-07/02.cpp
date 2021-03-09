#include <iostream>
#include <mpi.h>

using namespace std;

const int SIZE = 5;
const int ROOT = 0;

int main(int argc, char* argv[ ]){
    int rank, numtasks, i;
    float recvbuf[SIZE][SIZE], sendbuf[SIZE];

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Status status;

    if (numtasks == SIZE) {
        for (i = 0; i < SIZE; i++) {
            sendbuf[i] = (float) rank*SIZE + i;
        }

        MPI_Send(sendbuf, SIZE, MPI_FLOAT, ROOT, rank, MPI_COMM_WORLD);
        if(rank == ROOT) {
            for (i = 0; i < SIZE; i++) {
                MPI_Recv(recvbuf[i], SIZE, MPI_FLOAT, i, i, MPI_COMM_WORLD, &status);
            }

            for (i = 0; i < SIZE; i++) {
                cout << "Results proc "<< rank << ":\t" << recvbuf[i][0] << "\t" << recvbuf[i][1] << "\t" << recvbuf[i][2] << "\t" << recvbuf[i][3] << endl; 
            }
        }
    } else if (rank == 0) {
        cout << "Especifique " << SIZE << " processos para executar este exemplo: mpiexec -n " << SIZE << " ./<nome_do_arquivo>.o" << endl;
    }


    MPI_Finalize();

    return 0;
}

