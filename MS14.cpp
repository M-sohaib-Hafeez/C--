

#include <iostream>
#include <stdio.h>
#include <string>
#include <cstring>
#include <cmath>
#include <conio.h>
#include <stdlib.h>
using namespace std;
int main()
{
    string name,father_name,department,roll_no;
    cout<<"\nEnter Your Name : ";
    getline(cin,name);
    cout<<"\nEnter Your Father's Name : ";
    getline(cin,father_name);
    cout<<"\nEnter Your Department Name : ";
    getline(cin,department);
    cout<<"\nEnter Your Roll no : ";
    getline(cin,roll_no);
    cout<<"\n\n";
    cout<<"Your Name Is : "<<name;
    cout<<"\n\nYour father's Name Is : "<<father_name;
    cout<<"\n\nYour Department Is : "<<department;
    cout<<"\n\nYour Roll no Is : "<<roll_no;
    getch();
    return 0;
}