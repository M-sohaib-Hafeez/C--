

#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<string>
#include<cmath>
#include<cstring>
using namespace std;
int main ()
{
    int i,j;
    int arr[5][5];
    for (i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            cout<<"Enter the Array elements : ";
            cin>>arr[i][j];
            cout<<"\n";
        }
        cout<<"\n";
    }
    cout<<"\n\n";
    cout<<"The Array is....\n\n";
    for (i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            cout<<arr[i][j]<<" ";
        }
        cout<<"\n";
    }
    cout<<"\n\n";
    cout<<"The Transpose Of Array is....\n\n";
    for (i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            cout<<arr[j][i]<<" ";
        }
        cout<<"\n";
    }
    getch();
    return 0;
}