

#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<string>
#include<cmath>
#include<cstring>
using namespace std;
int main ()
{
    int num,i,j,k;
    int nums[5][5];
    bool found = false;
    //string index;
    for(i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            ++k;
            cout<<"Enter The "<<k<<" item number :";
            cin>>nums[i][j];
        }
    }
    cout<<"\nEnter the number that you want to find in item numbers : ";
    cin>>num;
    for(i=0;i<5;i++)
    {
        for(j=0;j<5;j++)
        {
            if(nums[i][j]==num)
            {
                found = true;
                //index = i+j;
                break;
            }
        }
    }
    if(found == true)
    {
        cout<<"\nYour Given Number has been founded at index number : "<<"["<<i<<" , "<<j<<"]";
    }
    else
    {
        cout<<"\nYour Given Number was not on the list...";
    }
    getch();
    return 0;
}