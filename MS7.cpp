

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
    float num1,num2;
    float avg;
    cout<<"\nEnter 1st Number : ";
    cin>>num1;
    cout<<"\nEnter 2nd Number : ";
    cin>>num2;
    avg = (num1 + num2)/2;
    cout<<"\nThe Average of Given 2 NUmbers are : "<<avg;
    getch();
    return 0;
}