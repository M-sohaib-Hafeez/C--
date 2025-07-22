


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
    int a;
    string b = "\nYour Given Number is Positive ";
    string c = "\nYour Givrn Number is Negative ";
    cout<<"Enter the any number : ";
    cin>>a;
    (a>0)?cout<<b:cout<<c;
    getch();
    return 0;
}
