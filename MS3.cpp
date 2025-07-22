

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
    int num;
    cout<<"Enter any Number : ";
    cin>>num;
    if(num%2 == 0)
    {
        cout<<"\nThe Number is EVEN ...";
    }
    else
    {
        cout<<"\nThe Number is Odd ...";
    }
    getch();
}