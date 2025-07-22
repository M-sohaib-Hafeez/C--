

#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<string>
#include<cmath>
#include<cstring>
using namespace std;
int main ()
{
    int num,i;
    int nums[10];
    bool found = false;
    int index;
    for(i=0;i<10;i++)
    {
        cout<<"Enter The item number "<<i+1<<" : ";
        cin>>nums[i];
    }
    cout<<"\nEnter the number that you want to find in item numbers : ";
    cin>>num;
    for(i=0;i<10;i++)
    {
        if(nums[i]==num)
        {
            found = true;
            index = i;
            break;
        }
    }
    if(found == true)
    {
        cout<<"\nYour Given Number has been founded at index number : "<<index;
    }
    else
    {
        cout<<"\nYour Given Number was not on the list...";
    }
    getch();
    return 0;
}