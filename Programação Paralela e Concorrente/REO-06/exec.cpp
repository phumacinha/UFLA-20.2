#include <iostream>
#include <cstdlib>
#include <sstream>
#include <cstring>

int main(int argc, char *argv[]) {

    double teste = system("./calculopiparalelo.o");
    std::cout << std::endl << teste*1000 << std::endl;
    return 0;
}