


#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<cmath>
#include<string>
#include<cstring>
#include<stdlib.h>
using namespace std;
int main()
{
    int age;
    string a = "\nThe Person is Eligible for vote ";
    string b = "\nThe person is not Eligible for vote";
    cout<<"Enter the age of the person : ";
    cin>>age;
    (age >= 18)?cout<<a:cout<<b;
    getch();
    return 0;
}