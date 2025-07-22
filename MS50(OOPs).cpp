#include <iostream>
using namespace std;
// Function to allocate memory using a double pointer
void allocateArray(int** arr, int size) {
    *arr = new int[size]; // Allocate memory and assign to the pointer
    for (int i = 0; i < size; i++) {
        (*arr)[i] = i * 10; // Fill the array
    }
}
int main() {
    int* myArray = nullptr; // Initially null
    int size = 5;
    allocateArray(&myArray, size); // Pass address of the pointer
    // Print values
    for (int i = 0; i < size; i++) {
        cout << "myArray[" << i << "] = " << myArray[i] << endl;
    }
    // Free dynamically allocated memory
    delete[] myArray;
    return 0;
}