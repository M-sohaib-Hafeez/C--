


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
    int a,area,perimeter;
    cout<<"Enter the Length or Height of Square : ";
    cin>>a;
    area = pow(a,2);
    perimeter = 4*a;
    cout<<"\nThe Area of given Square is : "<<area;
    cout<<"\nThe Perimeter of given Square is : "<<perimeter;
    getch();
    return 0;
}