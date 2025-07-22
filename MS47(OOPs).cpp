#include <iostream>
using namespace std;

// Class without any user-defined constructors
class MyClass {
public:
    int x; // Member variable
    double y; // Another member variable
};

int main() {
    // Creating an object of MyClass without defining any constructors
    MyClass obj;

    // Accessing the members (note: they are uninitialized)
    cout << "x = " << obj.x << endl; // May print garbage value for 'x'
    cout << "y = " << obj.y << endl; // May print garbage value for 'y'

    return 0;
}