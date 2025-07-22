#include <iostream>
#include <string>
using namespace std;
// Define a structure
struct Student {
    string name;
    int age;
    float marks;
};
// Function that receives the structure by value
void displayStudent(Student s) {
    cout << "Name: " << s.name << endl;
    cout << "Age: " << s.age << endl;
    cout << "Marks: " << s.marks << endl;
}
int main() {
    // Create and initialize a structure variable
    Student s1;
    s1.name = "Sohaib";
    s1.age = 20;
    s1.marks = 89.5;
    // Pass the entire structure to the function
    displayStudent(s1);
    return 0;
}