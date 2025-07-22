

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
    cout<<"Enter the Month Number : ";
    cin>>num;
    cout<<"\nThe NUmber of Days in your given Month is : ";
    switch (num)
    {
    case 1:
        cout<<" 31 and the Month is january";
        break;
    case 2:
        cout<<"28 and the Month is febuary";
        break;
    case 3:
        cout<<"31 and the Month is March";
        break;
    case 4:
        cout<<"30 and the Month is April";
        break;
    case 5:
        cout<<"31 and the Month is May";
        break;
    case 6:
        cout<<"30 and the Month is June";
        break;
    case 7:
        cout<<"31 and the Month is July";
        break;
    case 8:
        cout<<"31 and the Month is August";
        break;
    case 9:
        cout<<"30 and the Month is Setember";
        break;
    case 10:
        cout<<"31 and the Month is October";
        break;
    case 11:
        cout<<"30 and the Month is November";
        break;
    case 12:
        cout<<"31 and the Month is December";
        break;
    default:
        cout<<"Invalid Month Number";
        break;
    }
    getch();
    return 0;
}
