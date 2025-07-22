#include <iostream>
using namespace std;
// Define a class with a static variable
class MyClass {
public:
    // Static variable declaration
    static int count;
    // Constructor to increment the static variable
    MyClass() {
        count++; // Increment the static variable when an object is created
    }
    // Function to display the value of the static variable
    static void displayCount() {
        cout << "Number of objects created: " << count << endl;
    }
};
// Initialize the static variable
int MyClass::count = 0; // Initialization must be done outside the class definition
int main() {
    // Accessing the static variable without creating an object
    cout << "Initial count (without creating objects): " << MyClass::count << endl;
    // Creating objects of MyClass
    MyClass obj1;
    MyClass obj2;
    // Accessing the static variable through an object
    cout << "Count after creating two objects: " << obj1.count << endl;
    // Using the static function to display the count
    MyClass::displayCount(); // Can be called without an object
    return 0;
}