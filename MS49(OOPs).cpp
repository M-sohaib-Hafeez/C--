#include <iostream>
using namespace std;

int main() {
    int num = 85;
    int* ptr = &num;

    // Printing the address stored in the pointer
    cout << "Address stored in ptr: " << ptr << endl;

    // Printing the value at the address (dereferencing)
    cout << "Value at ptr: " << *ptr << endl;

    return 0;
}