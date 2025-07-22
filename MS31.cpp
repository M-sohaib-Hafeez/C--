


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
    float m1,m2,m3,m4,m5,total,percentage;
    cout<<"\n Enter your Maths marks : ";
    cin>>m1;
    cout<<"\n Enter your Physics marks : ";
    cin>>m2;
    cout<<"\n Enter your English marks : ";
    cin>>m3;
    cout<<"\n Enter your Urdu marks : ";
    cin>>m4;
    cout<<"\n Enter your Computer marks : ";
    cin>>m5;
    total = m1 + m2 + m3 + m4 + m5;
    percentage = (total * 100)/500;
    cout<<"\n\n Your Total Numbers out of 500 is : "<<total;
    cout<<"\n Your percentage is : "<<percentage;
    getch();
    return 0;
}