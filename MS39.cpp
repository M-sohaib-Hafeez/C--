

#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<string>
#include<cmath>
#include<cstring>
using namespace std;
struct person
{
    string name;
    int age;
    double salary;
    string qualification; 
};
int main()
{
    int i = 0;
    int num;
    cout<<"\nEnter the total number of members : ";
    cin>>num;
    person ms[num];
    cout<<"\nEnter the Data of your Memebers ";
    cin.ignore();
    cout<<"\n\n";
    while(i<num)
    {
        cout<<"\nEnter the full name of member "<<i+1<<" : ";
        getline(cin,ms[i].name);
        cout<<"\nEnter the age of member "<<i+1<<" : ";
        cin>>ms[i].age;
        cout<<"\nEnter the salary of member "<<i+1<<" : ";
        cin>>ms[i].salary;
        cin.ignore();
        cout<<"\nEnter the qualification of member "<<i+1<<" : ";
        getline(cin,ms[i].qualification);
        cout<<"\n";
        i++;
    }
    cout<<"\n\n\n";
    cout<<"Following Is the data of your Members ";
    cout<<"\n\n";
    for(i=0;i<num;i++)
    {
        cout<<"\n The Full Name Of member "<<i+1<<" Is : "<<ms[i].name;
        cout<<"\n The Age OF memeber "<<i+1<<" Is : "<<ms[i].age;
        cout<<"\n The Salary Of member "<<i+1<<" Is : "<<ms[i].salary;
        cout<<"\n The Qualification of memeber "<<i+1<<" Is : "<<ms[i].qualification;
        cout<<"\n";
    }
    getch();
    return 0;
}