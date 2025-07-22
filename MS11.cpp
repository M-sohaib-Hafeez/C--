

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
    string name;
    cout<<"Enter Your Name : ";
    cin>>name;
    if (name == "Julie")
    {
        cout<<"\nWe're delighted you're here! Julie , we'd like to extend our warmest welcome and best wishes...";
    }
    else if (name != "Neil")
    {
        cout<<"\nHello ...Welcome.";
    }
    else
    {
        cout<<"\n We Welcome You Neil , we're happy to have you here ..... ";
    }
    getch();
    return 0;
}