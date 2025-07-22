

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
    int num,range;
    int i = 1;
    cout<<"\nEnter the Number you want table of : ";
    cin>>num;
    cout<<"\nEnter the Range of table : ";
    cin>>range;
    cout<<"\n";
    do
    {
        cout<<num<<" X "<<i<<" = "<<num*i;
        cout<<"\n";
        i++;
    } 
    while (i<=range);
    getch();
    return 0;
}