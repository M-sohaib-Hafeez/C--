

#include <iostream>
#include <stdio.h>
#include <string>
#include <cstring>
#include <cmath>
#include <conio.h>
#include <stdlib.h>
using namespace std;
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
int main() 
{
    int num, result;
    int sum = 0;
    cout<<"Enter the number of terms in  Fibonacci series : ";
    cin>>num;
    for (int i = 0 ; i<num ; ++i)
    {
        result = fibonacci(i);
        sum = sum + fibonacci(i);
        cout<<"\n"<<result;
    } 
    cout<<"\n.\n.\n.\n.";
    cout <<"\n\nThe Sum of Given Number of Terms of Fibonacci Series is  : "<<sum;
    getch();
    return 0;
}