


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
    person* p = new person[num];
    cout<<"\nEnter the Data of your Memebers ";
    cin.ignore();
    cout<<"\n\n";
    while(i<num)
    {
        cout<<"\nEnter the full name of member "<<i+1<<" : ";
        getline(cin,p[i].name);
        cout<<"\nEnter the age of member "<<i+1<<" : ";
        cin>>p[i].age;
        cout<<"\nEnter the salary of member "<<i+1<<" : ";
        cin>>p[i].salary;
        cin.ignore();
        cout<<"\nEnter the qualification of member "<<i+1<<" : ";
        getline(cin,p[i].qualification);
        cout<<"\n";
        i++;
    }
    cout<<"\n\n\n";
    cout<<"Following Is the data of your Members ";
    for(i=0;i<num;i++)
    {
        cout<<"\n The Full Name Of member "<<i+1<<" Is : "<<p[i].name;
        cout<<"\n The Age OF memeber "<<i+1<<" Is : "<<p[i].age;
        cout<<"\n The Salary Of member "<<i+1<<" Is : "<<p[i].salary;
        cout<<"\n The Qualification of memeber "<<i+1<<" Is : "<<p[i].qualification;
        cout<<"\n\n";
    }
    getch();
    return 0;
}