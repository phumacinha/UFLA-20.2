/**
 * Pedro Antonio de Souza (201810557)
 * REO 5 - Exercicio 3-b (OpenMP)
 */

#include <iostream>
#include <omp.h>

#define n 50

using namespace std;

double f (int i){
   double x;
   x = (double) i/(double) n;
   return 4.0/(1.0 + x*x);
}

int main(int argc, char *argv[]){
   double area;
   int i;
   area = f(0) - f(n);

   #pragma omp parallel for reduction(+:area)
      for (i=1; i <= n/2; i++)
         area += 4.0*f(2*i-1) + 2*f(2*i);

   area /= (3.0 * n);

   cout << "Aproximação de pi: " << area << endl;
   
   return 0;
}