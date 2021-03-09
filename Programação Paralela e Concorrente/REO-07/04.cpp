#include <mpi.h>
#include <iostream>
using namespace std;

#define ROOT 0

int main( int argc, char** argv ) {
    int rank, size;
    int max_value = 0;

    MPI_Init( &argc, &argv );
    MPI_Comm_rank(MPI_COMM_WORLD, &rank); 
    MPI_Comm_size(MPI_COMM_WORLD, &size); 


    int * vector = new int[size];

    if (rank == ROOT) {
        srand(time(NULL));
        for (int i = 0; i < size; i++) {
            vector[i] = rand() % 100;
        }
    }

    MPI_Bcast(vector, size, MPI_INT, ROOT, MPI_COMM_WORLD);
    
    // descomentar linha abaixo para debugar
    // cout << "number of process " << rank << " = " << vector[rank] << endl;
    
    MPI_Reduce(&vector[rank], &max_value, 1, MPI_INT, MPI_MAX, ROOT, MPI_COMM_WORLD);

    if (rank == ROOT)
        cout << "max = " << max_value << endl;

    MPI_Finalize();
    return 0;
}