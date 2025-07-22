#include<iostream>
#include<string>
#include<stdio.h>
#include<conio.h>
#include<cmath>
using namespace std;
class employee
{
    private:
        int salary;
    public:
        string name;
        string address;
        void setsalary()
        {
            int s;
            cout<<"Enter the Amount of salary ";
            cin>>s;
            salary = s;
        }
        void getsalary()
        {
            cout<<"\nThe Salary of Employee is "<<salary;
        }
};
int main()
{
    employee p1;
    cout<<"Enter the name of Employee 1 ";
    getline(cin,p1.name);
    cout<<"Enter the address of Employee 1 ";
    getline(cin,p1.address);
    p1.setsalary();
    cout<<"\n\n\t THE DETAILS OF EMPLOYEE 1";
    cout<<"\nThe Name of Employee 1 is "<<p1.name;
    cout<<"\nThe Address of Employee 1 is "<<p1.address;
    p1.getsalary();
    getch();
    return 0;
}