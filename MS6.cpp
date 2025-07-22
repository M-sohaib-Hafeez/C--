

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
    int sum = 0;
    cout<<"Enter the numbers : ";
    cout<<"\nEnter 0 to terminate the loop and get sum : ";
    while(i != 0)
    {
        cout<<"\n";
        cin>>i;
        sum = sum + i;
    }
    cout<<"\nThe Total sum of Given Numbers Is : "<<sum;
    getch();
    return 0;
}