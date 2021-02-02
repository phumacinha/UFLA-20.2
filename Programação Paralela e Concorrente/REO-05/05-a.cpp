#include <iostream>
#include <omp.h>
#define n 5
using namespace std;

int main(){
   int i, y[n]={0, 1, 2, 10, 4};

   int x = 1;
   #pragma omp parallel for firstprivate(x) private(i)  num_threads(3)
      for (i = 0; i < n; i++){
         y[i] = x + y[i];
         x++;
      }
   for (i = 0; i < n; i++)
      cout << y[i] << endl;
   return 0;
}