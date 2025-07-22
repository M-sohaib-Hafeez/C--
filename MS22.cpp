


#include<iostream>
#include<stdio.h>
#include<conio.h>
#include<cmath>
#include<string>
#include<cstring>
using namespace std;
int main()
{
    int* ptr_arr;
    int arr[10];
    int i;
    ptr_arr = arr;
    cout<<"\t\t Enter the Elements In Array ........";
    cout<<"\n\n"; 
    for(i=0;i<10;i++)
    {
        cout<<"Enter the "<<i + 1<<" Array element : ";
        cin>>arr[i];
        cout<<"\n";
    }
    cout<<"\n";
    cout<<"\t\t Printing The Array's Element's Addresses using Pointers....";
    cout<<"\n\n";
    for(i=0;i<10;i++)
    {
        cout<<"Printing Address of Array ["<<i<<"] using Pointer : "<<ptr_arr+i;
        cout<<"\n";
    }
    cout<<"\n";
    cout<<"\t\t Printing The Array's Elemet's Values using Pointers....";
    cout<<"\n\n";
    for(i=0;i<10;i++)
    {
        cout<<"Printing value of Array ["<<i<<"] using Pointer : "<<*ptr_arr+i;
        cout<<"\n";
    }
    getch();
    return 0;
}