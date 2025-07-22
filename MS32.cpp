


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
    int a,b,c,d;
    cout<<"Enter the Dividend : ";
    cin>>a;
    cout<<"Enter the Divider : ";
    cin>>b;
    c = a / b;
    d = a % b;
    cout<<"\n\n The quotient of the division is : "<<c;
    cout<<"\n The remainder of the division is : "<<d;
    getch();
    return 0;
} 