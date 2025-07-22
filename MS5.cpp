

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
    int i;
    int j = 0;
    cout<<"The First 20 EVEN Numbers are.. ";
    for(i=2;i<=40;i+=2)
    {
        j++;
        cout<<"\nThe "<<j<<" Even  Number is : "<<i;
    }
    getch();
}