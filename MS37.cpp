


#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<string>
#include<cmath>
#include<cstring>
using namespace std;
int main ()
{
    int i,j,s;
    int k = 0;
    int arr[5][5];
    int arr2[10];
    for(s=0;s<=9;s++)
    {
        cout<<"\nEnter the "<<s + 1<<" Array element : ";
        cin>>arr2[s];
    }
    cout<<"\n\n\n The elements of 1D Array's are \n\n";
    for(s=0;s<=9;s++)
    {
        cout<<arr2[s];
        cout<<"\n";
    }
    cout<<"\n\n\n";
    for (i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            k++;
            cout<<"Enter the "<<k<<" Array elements : ";
            cin>>arr[i][j];
            cout<<"\n";
        }
        cout<<"\n";
    }
    cout<<"\n\n";
    cout<<"The elements of 2D Array's are \n\n";
    for (i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            cout<<arr[i][j]<<" ";
        }
        cout<<"\n";
    }
    getch();
    return 0;
}    