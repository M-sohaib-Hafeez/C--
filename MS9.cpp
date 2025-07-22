

#include <iostream>
#include <stdio.h>
#include <string>
#include <cstring>
#include <cmath>
#include <conio.h>
#include <stdlib.h>
using namespace std;
float add(float num1, float num2)
{
    float result;
    result = num1 + num2;
    return result;
}
float sub(float num1, float num2)
{
    float result;
    result = num1 - num2;
    return result;
}
float product(float num1, float num2)
{
    float result;
    result = num1*num2;
    return result;
}
float did(float num1, float num2)
{
    float result;
    result =  num1/num2;
    return result;
}
int main()
{
    float num1,num2,result;
    string operation;
    cout<<"\nEnter the First Number : ";
    cin>>num1;
    cout<<"\nEnter the Second Number : ";
    cin>>num2;
    cout<<"\nEnter the operation That you want to perform between the given operands : ";
    cout<<"\n 1. Add \n 2. Subtract \n 3. Multiply \n 4. Divide ";
    cout<<"\n\t";
    cin>>operation;
    if(operation == "1" || operation == "Add" || operation == "add" )
    {
        result = add(num1,num2);
        cout<<"\nThe Addition between given Numbers is : "<<result;
    }
    else if(operation == "2" || operation == "Subtract" || operation == "subtract" )
    {
        result = sub(num1,num2);
        cout<<"\nThe Subtraction between given Numbers is : "<<result;
    }
    else if(operation == "3" || operation == "Multiply" || operation == "multiply" )
    {
        result = product(num1,num2);
        cout<<"\nThe Multiplication between given Numbers is : "<<result;
    }
    else if(operation == "4" || operation == "Divide" || operation == "divide" )
    {
        result = did(num1,num2);
        cout<<"\nThe division between given Numbers is : "<<result;
    }
    else
    {
        cout<<"\nInvalid Operation";
    }
    getch();
    return 0;
}