


#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<cmath>
#include<string>
#include<cstring>
#include<stdlib.h>
int fibonacci(int s);
using namespace std;
int main()
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
    cout<<"\n\n\n";
    int no,result;
    int sum = 0;
    cout<<"Enter the number of terms in  Fibonacci series : ";
    cin>>no;
    for (int j = 0 ; j<no ; ++j)
    {
        result = fibonacci(j);
        sum = sum + fibonacci(j);
        cout<<"\n"<<result;
    } 
    cout<<"\n.\n.\n.\n.";
    cout <<"\n\nThe Sum of Given Number of Terms of Fibonacci Series is  : "<<sum;
    getch();
    return 0;
}
 int fibonacci(int s)
{
    if(s<=1)
    {
        return s;
    }
    else
    {
        return fibonacci (s-1) + fibonacci (s-2);
    }
}