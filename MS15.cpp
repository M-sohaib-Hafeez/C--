

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
    float sec,min,hrs;
    cout<<"Enter the Time in Hours : ";
    cin>>hrs;
    min = hrs*60;
    cout<<"\n\nYour Time In Minutes Is : "<<min;
    sec = min*60;
    cout<<"\n\nYour time In seconds Is : "<<sec;
    getch();
    return 0;
}