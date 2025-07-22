

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
    cout<<"Enter Any Number : ";
    cin>>num;
    if(num<0)
    {
        cout<<"\nYour Entered Number is Negative ";
    }
    else if(num>0)
    {
        cout<<"\nYour Entered Number is Positive ";
    }
    else
    {
        cout<<"\nYou Entered Zero ";
    }
    getch();
    return 0;
}