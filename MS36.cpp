


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
    int i;
    float sum = 0;
    float avg;
    float arr[10];
    for(i=0;i<=9;i++)
    {
        cout<<"\nEnter the "<<i + 1<<" Array element : ";
        cin>>arr[i];
        sum = sum + arr[i];
    }
    cout<<"\n The Sum of Array's elemets is : "<<sum;
    avg = sum/10;
    cout<<"\n The Average of Array's elemets is : "<<avg;
    getch();
    return 0;
}