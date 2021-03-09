__global__ void funcao_sequencial(int n, float a, float *x, float *y) {
    int i = blockIdx.x*blockDim.x + threadIdx.x;
    if (i < n) y[i] = a*x[i] + y[i];
}

//Invoca função
funcao_sequencial<<<1, 256>>(n, 2.0, x, y);